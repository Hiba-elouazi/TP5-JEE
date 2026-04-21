package services;

import dao.UserDAO;
import dao.UserDAOImpl;
import entities.User;


public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public User authenticate(String username, String password) {
        if (username == null || password == null) return null;
        return userDAO.findByUsernameAndPassword(username.trim(), password.trim());
    }

    @Override
    public void initDefaultUsers() {
        insertIfNotExists("admin",    "admin123", "ADMIN");
        insertIfNotExists("user",     "user123",  "USER");
        insertIfNotExists("cherradi", "tdia2024", "ADMIN");
        System.out.println("=== Utilisateurs par défaut initialisés ===");
    }

    private void insertIfNotExists(String username, String password, String role) {
        if (!userDAO.existsByUsername(username)) {
            userDAO.addUser(new User(username, password, role));
        }
    }
}