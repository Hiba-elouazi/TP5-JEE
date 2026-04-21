package services;

import entities.Produit;
import java.util.List;


public interface ProduitService {

    void          addProduit(Produit p);
    void          deleteProduit(Long id);
    Produit       getProduitById(Long id);
    List<Produit> getAllProduits();
    void          updateProduit(Produit p);
    List<Produit> rechercherParMotCle(String motCle);
}