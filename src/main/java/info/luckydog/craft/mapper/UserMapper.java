package info.luckydog.craft.mapper;

import info.luckydog.craft.model.dto.UserDTO;

import java.util.List;

/**
 * UserMapper
 *
 * @author eric
 * @since 2019/6/6
 */
public interface UserMapper {

    UserDTO getUserById(Long id);

    void addUser(UserDTO user);

    void updateUserById(UserDTO user);

    void deleteUserById(Long id);

    List<UserDTO> getAllUsers();
}
