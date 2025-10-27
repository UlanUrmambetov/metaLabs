package DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String phoneNumber;
    private String avatar;
    private Boolean enabled;
}
