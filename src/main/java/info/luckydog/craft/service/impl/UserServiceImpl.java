package info.luckydog.craft.service.impl;

import info.luckydog.craft.mapper.UserMapper;
import info.luckydog.craft.model.dto.UserDTO;
import info.luckydog.craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author eric
 * @since 2019/6/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO getUser(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    @Transactional
    public void addUser(UserDTO user) {
        userMapper.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO user) {
        userMapper.updateUserById(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
