package services;

import entities.User;

public interface UserService {
    User authenticate(String username, String password);
    void initDefaultUsers();
}