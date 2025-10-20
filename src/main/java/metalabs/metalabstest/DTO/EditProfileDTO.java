package metalabs.metalabstest.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EditProfileDTO {

    private String name;


    private String surname;

    @Min(value = 16, message = "Возраст должен быть не меньше 16")
    @Max(value = 100, message = "Возраст не должен быть больше 100")
    private int age;

    @Email(message = "Неверный формат email")
    private String email;

    private String password;

    @Pattern(regexp = "(^$|\\+?[0-9]{9,15})", message = "Неверный формат номера")
    private String phoneNumber;

    private String avatar;

    @NotNull(message = "Тип аккаунта обязателен")
    private Integer accountType;
}
