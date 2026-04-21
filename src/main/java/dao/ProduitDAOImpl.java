package dao;

import entities.Produit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ProduitDAOImpl implements ProduitDAO {
 
    @Override
    public void addProduit(Produit p) {
        Session session = null;
        Transaction tx  = null;
        try {
            session = HibernateUtil.getSession();
            tx      = session.beginTransaction();
            session.persist(p);  
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout du produit : " + e.getMessage(), e);
        } finally {
            if (session != null) session.close();
        }
    }
 
    @Override
    public Produit getProduitById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.getSession(); 
            return session.get(Produit.class, id);
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<Produit> getAllProduits() {
        Session session = null;
        try {
            session = HibernateUtil.getSession(); 
            Query<Produit> query = session.createQuery(
                "FROM Produit p ORDER BY p.idProduit ASC", Produit.class
            );
            return query.list();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<Produit> rechercherParMotCle(String motCle) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            String kw = "%" + motCle.toLowerCase() + "%"; 
            Query<Produit> query = session.createQuery(
                "FROM Produit p WHERE LOWER(p.nom) LIKE :kw OR LOWER(p.description) LIKE :kw",
                Produit.class
            );
            query.setParameter("kw", kw);
            return query.list();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }
 
    @Override
    public void updateProduit(Produit p) {
        Session session = null;
        Transaction tx  = null;
        try {
            session = HibernateUtil.getSession();
            tx      = session.beginTransaction();
            session.merge(p);    
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erreur lors de la mise à jour : " + e.getMessage(), e);
        } finally {
            if (session != null) session.close();
        }
    }
 
    @Override
    public void deleteProduit(Long id) {
        Session session = null;
        Transaction tx  = null;
        try {
            session = HibernateUtil.getSession();
            tx      = session.beginTransaction();
            Produit p = session.get(Produit.class, id);
            if (p != null) {
                session.remove(p);    
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erreur lors de la suppression : " + e.getMessage(), e);
        } finally {
            if (session != null) session.close();
        }
    }
}