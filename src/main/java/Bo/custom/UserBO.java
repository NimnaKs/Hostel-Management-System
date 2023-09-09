package Bo.custom;

import Bo.SuperBO;
import dto.UserDto;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    void updateTenant(UserDto userDto);

    UserDto viewCredentials(String text) throws SQLException, ClassNotFoundException;

    UserDto getUserDetails(String userId) throws SQLException, ClassNotFoundException;

    void saveUserDetails(UserDto userDto);
}
