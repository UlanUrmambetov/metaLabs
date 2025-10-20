package metalabs.metalabstest.mapper;

import metalabs.metalabstest.DTO.UserDTO;
import metalabs.metalabstest.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAvatars(rs.getString("avatar"));
        return user;

    }

    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .build();
    }
}

