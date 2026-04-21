package web;

import entities.Produit;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.ProduitService;
import services.ProduitServiceImpl;

import java.io.IOException;
import java.util.List;

public class DispatcherServlet extends HttpServlet {

    private ProduitService produitService;

    @Override
    public void init() throws ServletException {
        produitService = new ProduitServiceImpl();
        System.out.println("=== DispatcherServlet initialisé (MVC2) ===");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                handleList(req, resp);
                break;
            case "edit":
                handleEdit(req, resp);
                break;
            case "delete":
                handleDelete(req, resp);
                break;
            case "search":
                handleSearch(req, resp);
                break;
            default:
                handleList(req, resp);
                break;
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                handleAdd(req, resp);
                break;
            case "update":
                handleUpdate(req, resp);
                break;
            default:
                handleList(req, resp);
                break;
        }
    }
    private void handleList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Produit> liste = produitService.getAllProduits();
        req.setAttribute("listeProduits", liste);
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    private void handleSearch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam     = req.getParameter("idProduit");
        String motCleParam = req.getParameter("motCle");
        List<Produit> liste;

        try {
            if (idParam != null && !idParam.trim().isEmpty()) {
                Long id = Long.parseLong(idParam.trim());
                Produit p = produitService.getProduitById(id);
                liste = p != null ? List.of(p) : List.of();
                if (p == null) req.setAttribute("messageInfo", "Aucun produit avec l'ID : " + id);
            } else if (motCleParam != null && !motCleParam.trim().isEmpty()) {
                liste = produitService.rechercherParMotCle(motCleParam.trim());
                req.setAttribute("motCle", motCleParam);
                if (liste.isEmpty()) req.setAttribute("messageInfo", "Aucun résultat pour : \"" + motCleParam + "\"");
            } else {
                liste = produitService.getAllProduits();
            }
        } catch (NumberFormatException e) {
            liste = produitService.getAllProduits();
            req.setAttribute("messageErreur", "ID invalide.");
        }

        req.setAttribute("listeProduits", liste);
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nom         = req.getParameter("nom");
        String description = req.getParameter("description");
        String prixStr     = req.getParameter("prix");

        try {
            Double prix = Double.parseDouble(prixStr.trim());
            Produit p   = new Produit(nom.trim(), description.trim(), prix);
            produitService.addProduit(p);
            resp.sendRedirect(req.getContextPath()
                + "/produits?action=list&messageSucces=Produit+ajouté+avec+succès");

        } catch (NumberFormatException e) {
            req.setAttribute("messageErreur", "Le prix doit être un nombre valide.");
            req.setAttribute("listeProduits", produitService.getAllProduits());
            req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("messageErreur", e.getMessage());
            req.setAttribute("listeProduits", produitService.getAllProduits());
            req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Long    id      = Long.parseLong(req.getParameter("id"));
            Produit produit = produitService.getProduitById(id);

            if (produit == null) {
                req.setAttribute("messageErreur", "Produit introuvable (ID : " + id + ").");
            } else {
                req.setAttribute("produitEdit", produit);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("messageErreur", "ID invalide.");
        }

        req.setAttribute("listeProduits", produitService.getAllProduits());
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Long   id   = Long.parseLong(req.getParameter("idProduit").trim());
            Double prix = Double.parseDouble(req.getParameter("prix").trim());
            String nom  = req.getParameter("nom");
            String desc = req.getParameter("description");

            Produit p = new Produit();
            p.setIdProduit(id);
            p.setNom(nom.trim());
            p.setDescription(desc.trim());
            p.setPrix(prix);

            produitService.updateProduit(p);
            resp.sendRedirect(req.getContextPath()
                + "/produits?action=list&messageSucces=Produit+mis+à+jour");

        } catch (NumberFormatException e) {
            req.setAttribute("messageErreur", "Données invalides.");
            req.setAttribute("listeProduits", produitService.getAllProduits());
            req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("messageErreur", e.getMessage());
            req.setAttribute("listeProduits", produitService.getAllProduits());
            req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(req.getParameter("id"));
            if (produitService.getProduitById(id) == null) {
                resp.sendRedirect(req.getContextPath()
                    + "/produits?action=list&messageErreur=Produit+introuvable");
                return;
            }
            produitService.deleteProduit(id);
            resp.sendRedirect(req.getContextPath()
                + "/produits?action=list&messageSucces=Produit+supprimé");
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath()
                + "/produits?action=list&messageErreur=ID+invalide");
        }
    }
}