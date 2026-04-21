package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "nom", nullable = false, length = 200)
    private String nom;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "prix", nullable = false)
    private Double prix;

    public Produit() {}

    public Produit(String nom, String description, Double prix) {
        this.nom         = nom;
        this.description = description;
        this.prix        = prix;
    }

    public Long   getIdProduit()              { return idProduit; }
    public void   setIdProduit(Long id)       { this.idProduit = id; }

    public String getNom()                    { return nom; }
    public void   setNom(String nom)          { this.nom = nom; }

    public String getDescription()            { return description; }
    public void   setDescription(String desc) { this.description = desc; }

    public Double getPrix()                   { return prix; }
    public void   setPrix(Double prix)        { this.prix = prix; }

    @Override
    public String toString() {
        return "Produit{id=" + idProduit + ", nom='" + nom + "', prix=" + prix + "}";
    }
}