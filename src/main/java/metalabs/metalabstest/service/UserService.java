package metalabs.metalabstest.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import metalabs.metalabstest.DTO.RegisterRequest;
import metalabs.metalabstest.model.Role;
import metalabs.metalabstest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.List;


public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    void register(RegisterRequest registerRequest);
    String getAvatarForUser(User user);
    List<User> getUsers();

    boolean updateUser(Long userId, RegisterRequest request);

    void updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void makeResetPasswdLink(HttpServletRequest request) throws UsernameNotFoundException, MessagingException, UnsupportedEncodingException;

    List<User> getAllUsersWithRole(Role role);
}
