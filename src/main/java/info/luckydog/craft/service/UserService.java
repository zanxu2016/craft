package info.luckydog.craft.service;

import info.luckydog.craft.model.dto.UserDTO;

import java.util.List;

/**
 * UserService
 *
 * @author eric
 * @since 2019/6/6
 */
public interface UserService {

    UserDTO getUser(Long id);

    void addUser(UserDTO user);

    void updateUser(UserDTO user);

    void deleteUser(Long id);

    List<UserDTO> getAllUsers();
}
