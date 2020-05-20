package ua.nure.kulakov.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.kulakov.entity.dto.User;
import ua.nure.kulakov.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        List<User> validatedUser = getUserList().stream().filter(user -> user.getUsername().equals(username)
                && user.getPassword().equals(password)).collect(Collectors.toList());
        if (validatedUser.size() == 1) {
            return validatedUser.get(0);
        }
        return null;
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "alex@gmail.com", "password"));
        return userList;
    }
}
