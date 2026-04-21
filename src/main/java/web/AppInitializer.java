package web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import util.HibernateUtil;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== AppInitializer : démarrage de l'application ===");
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("=== Hibernate initialisé avec succès ===");
        } catch (Exception e) {
            System.err.println("=== ERREUR : Hibernate n'a pas pu s'initialiser ===");
            System.err.println("Vérifiez la configuration MySQL dans hibernate.cfg.xml");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== AppInitializer : arrêt de l'application ===");
        HibernateUtil.shutdown();
    }
}