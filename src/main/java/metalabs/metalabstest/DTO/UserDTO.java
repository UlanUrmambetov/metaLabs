package metalabs.metalabstest.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String avatar;
    private boolean enabled;
}
