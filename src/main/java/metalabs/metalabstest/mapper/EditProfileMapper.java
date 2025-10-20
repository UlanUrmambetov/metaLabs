package metalabs.metalabstest.mapper;

import metalabs.metalabstest.DTO.EditProfileDTO;
import metalabs.metalabstest.model.User;
import org.springframework.stereotype.Component;

@Component
public class EditProfileMapper {

    public EditProfileDTO toDto(User user) {
        if (user == null) return null;

        EditProfileDTO dto = new EditProfileDTO();
        dto.setName(user.getName());
        dto.setAge(user.getAge() != null ? user.getAge() : 0);
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvatar(user.getAvatars());
//        dto.setAccountType(user.getRoleId());
        dto.setPassword("");
        return dto;
    }

    public void updateUserFromDto(EditProfileDTO dto, User user) {
        if (dto == null || user == null) return;

        System.out.println("Updating user fields from DTO:");
        System.out.println("Name: " + dto.getName());
        System.out.println("Surname: " + dto.getSurname());
        System.out.println("Age: " + dto.getAge());
        System.out.println("Phone: " + dto.getPhoneNumber());
        System.out.println("AccountType: " + dto.getAccountType());

        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setPhoneNumber(dto.getPhoneNumber());
//        user.setRoleId(dto.getAccountType());
    }

}

