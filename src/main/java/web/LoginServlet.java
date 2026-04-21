package web;

import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import services.UserService;
import services.UserServiceImpl;

import java.io.IOException;
 
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        userService.initDefaultUsers();
        System.out.println("=== LoginServlet initialisé ===");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("true".equals(req.getParameter("logout"))) {
            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userConnecte") != null) {
            resp.sendRedirect(req.getContextPath() + "/produits");
            return;
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty()
         || password == null || password.trim().isEmpty()) {
            req.setAttribute("erreur", "Veuillez remplir tous les champs.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.authenticate(username.trim(), password.trim());

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userConnecte", user);
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(30 * 60);
            resp.sendRedirect(req.getContextPath() + "/produits");
        } else {
            req.setAttribute("erreur", "Identifiants incorrects.");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}