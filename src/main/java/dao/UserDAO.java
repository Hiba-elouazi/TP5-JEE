package dao;

import entities.User;
 
public interface UserDAO {
 
    User findByUsernameAndPassword(String username, String password);
 
    void addUser(User user);
 
    boolean existsByUsername(String username);
}