package services;

import dao.ProduitDAO;
import dao.ProduitDAOImpl;
import entities.Produit;

import java.util.List;

public class ProduitServiceImpl implements ProduitService {

    private final ProduitDAO dao = new ProduitDAOImpl();

    @Override
    public void addProduit(Produit p) {
        if (p.getNom() == null || p.getNom().trim().isEmpty())
            throw new IllegalArgumentException("Le nom du produit ne peut pas être vide.");
        if (p.getPrix() == null || p.getPrix() < 0)
            throw new IllegalArgumentException("Le prix doit être un nombre positif.");
        dao.addProduit(p);
    }

    @Override
    public void deleteProduit(Long id) {
        if (id == null)
            throw new IllegalArgumentException("L'identifiant est requis.");
        dao.deleteProduit(id);
    }

    @Override
    public Produit getProduitById(Long id) {
        if (id == null) return null;
        return dao.getProduitById(id);
    }

    @Override
    public List<Produit> getAllProduits() {
        return dao.getAllProduits();
    }

    @Override
    public void updateProduit(Produit p) {
        if (p.getNom() == null || p.getNom().trim().isEmpty())
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        if (p.getPrix() == null || p.getPrix() < 0)
            throw new IllegalArgumentException("Le prix doit être positif.");
        dao.updateProduit(p);
    }

    @Override
    public List<Produit> rechercherParMotCle(String motCle) {
        if (motCle == null || motCle.trim().isEmpty())
            return dao.getAllProduits();
        return dao.rechercherParMotCle(motCle.trim());
    }
}