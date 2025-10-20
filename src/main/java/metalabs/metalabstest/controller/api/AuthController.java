package metalabs.metalabstest.controller.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import metalabs.metalabstest.DTO.RegisterRequest;
import metalabs.metalabstest.DTO.UserDTO;
import metalabs.metalabstest.mapper.UserMapper;
import metalabs.metalabstest.model.User;
import metalabs.metalabstest.model.repository.UserRepository;
import metalabs.metalabstest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/personalAccount")
    public UserDTO personalAccount(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return userMapper.toDto(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(extractErrors(bindingResult));
        }
        userService.register(registerRequest);
        return ResponseEntity.ok("Регистрация прошла успешна");
    }

    private String extractErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }


}
