package metalabs.metalabstest.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import metalabs.metalabstest.DTO.EditProfileDTO;
import metalabs.metalabstest.DTO.RegisterRequest;
import metalabs.metalabstest.Utils.Exceptions.UserNotFoundException;
import metalabs.metalabstest.mapper.EditProfileMapper;
import metalabs.metalabstest.model.Image;
import metalabs.metalabstest.model.Role;
import metalabs.metalabstest.model.User;
import metalabs.metalabstest.model.repository.ImageRepository;
import metalabs.metalabstest.model.repository.RoleRepository;
import metalabs.metalabstest.model.repository.UserRepository;
import metalabs.metalabstest.service.ImageService;
import metalabs.metalabstest.service.UserService;
import metalabs.metalabstest.service.impl.ImageServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthViewController {
    private final UserService userService;
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final EditProfileMapper editProfileMapper;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final ImageServiceImpl imageServiceImpl;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "userRegister";
    }

    @PostMapping("/register")
    public String handleRegistration(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest, BindingResult bindingResult, Model model,  HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "userRegister";
        }

        if (usersRepository.existsByEmail(registerRequest.getEmail())) {
            model.addAttribute("error", "Email уже зарегистрирован");
            return "userRegister";
        }


        User user = User.builder()
                .name(registerRequest.getName())
                .age(registerRequest.getAge())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .enabled(true)
                .build();

        usersRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );


        return "redirect:/auth/personalAccount";
    }

    @GetMapping("/personalAccount")
    public String showPersonalAccount(Model model, Principal principal) {
        User user = usersRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        String avatarPath = userService.getAvatarForUser(user);
        user.setAvatars(avatarPath);

        model.addAttribute("user", user);
        return "personalAccount";
    }


    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/auth/login";
        }
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "/personalAccount";
    }


    @GetMapping("/profile/edit")
    public String showEditForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<User> userOptional = usersRepository.findByEmail(userDetails.getUsername());
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("editProfileDTO", editProfileMapper.toDto(userOptional.get()));
        return "profileEdit";
    }

    @PostMapping("/profile/edit")
    public String editProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("editProfileDTO") EditProfileDTO dto,
            BindingResult bindingResult,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "profileEdit";
        }

        Optional<User> userOptional = usersRepository.findByEmail(userDetails.getUsername());
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOptional.get();

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String savedFileName = imageServiceImpl.saveUploadedFile(avatarFile, "images");
            user.setAvatars("/data/images/" + savedFileName);

            Image image = new Image();
            image.setFileName(savedFileName);
            image.setUserId(user.getId());
            imageRepository.save(image);
        }


        editProfileMapper.updateUserFromDto(dto, user);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        usersRepository.save(user);

        return "redirect:/auth/personalAccount";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException | UnsupportedEncodingException | MessagingException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "auth/forgot_password_form";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(
            @RequestParam String token,
            Model model
    ) {
        try {
            userService.getByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            User user = userService.getByResetPasswordToken(token);
            userService.updatePassword(user, password);
            redirectAttributes.addFlashAttribute("message", "You have successfully changed your password.");
            return "redirect:/auth/login";
        } catch (UsernameNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", "Invalid Token");
            return "redirect:/auth/message";
        }
    }


}
