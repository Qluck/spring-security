package ua.nure.kulakov.service;

import ua.nure.kulakov.entity.dto.User;

public interface UserService {
    User getUserByUsernameAndPassword(String username, String password);
}
