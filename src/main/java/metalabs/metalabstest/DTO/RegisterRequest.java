package metalabs.metalabstest.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.management.relation.Role;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Обязательное поля для заполнения")
    private String email;

    @Min(value = 16, message = "Возраст должен быть не меньше 16")
    @Max(value = 100, message = "Возраст не должен быть больше 100")
    private int age;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен быть не короче 6 символов")
    private String password;

    @Pattern(regexp = "\\+?[0-9]{9,15}", message = "Неверный формат номера")
    private String phoneNumber;

    private List<Role> roles;
}
