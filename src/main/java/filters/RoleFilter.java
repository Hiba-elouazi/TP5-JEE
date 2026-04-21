package filters;

import entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;


public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String action = req.getParameter("action");
        boolean actionAdmin = action != null && (
            action.equals("add")    ||
            action.equals("edit")   ||
            action.equals("update") ||
            action.equals("delete")
        );

        if (actionAdmin) {
            HttpSession session = req.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("userConnecte") : null;

            if (user == null || !"ADMIN".equals(user.getRole())) {
                req.setAttribute("messageErreur",
                    " Accès refusé : vous devez être ADMIN pour effectuer cette action.");
                req.setAttribute("listeProduits",
                    new services.ProduitServiceImpl().getAllProduits());
                req.getRequestDispatcher("/views/error.jsp").forward(req, resp);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}