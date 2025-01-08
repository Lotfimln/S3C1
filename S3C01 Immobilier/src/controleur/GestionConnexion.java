package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modele.dao.CictOracleDataSource;
import vue.AffichageDonnees;
import vue.Connexion;
import vue.FenetrePrincipale;

public class GestionConnexion implements ActionListener {
    
    private Connexion fenetreConnexion;
    private FenetrePrincipale fenetrePrincipale;

    public GestionConnexion(Connexion fenetreConnexion, FenetrePrincipale fenetrePrincipale) {
        this.fenetreConnexion = fenetreConnexion;
        this.fenetrePrincipale = fenetrePrincipale;
    }

    @Override
    public void actionPerformed(ActionEvent e1) {
        String command = e1.getActionCommand();
        switch (command) {
        case "Connecter":
            try {
            	// Récupérer les identifiants de connexion
                String login = fenetreConnexion.getValeurChLogin();
                String password = fenetreConnexion.getValeurPasswordField();

                // Créer un accès à la base de données
                CictOracleDataSource.creerAcces(login, password);

                // Tester la connexion
                Connection connection = CictOracleDataSource.getConnectionBD();
                if (connection != null && !connection.isClosed()) {
                    fenetrePrincipale.setConnecte(true);
                    JOptionPane.showMessageDialog(fenetreConnexion, "Connexion établie.");
                    this.fenetreConnexion.dispose();
                    
                    // Afficher la fenêtre
                    AffichageDonnees affichageDonnees = new AffichageDonnees(this.fenetrePrincipale);
                    this.fenetrePrincipale.getLayeredPane().add(affichageDonnees);
                    affichageDonnees.setVisible(true);
                    affichageDonnees.moveToFront();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(fenetreConnexion, "Erreur de connexion : " + e.getMessage());
            }
            break;
        case "Annuler":
            this.fenetreConnexion.dispose();
            break;
        }
    }
}
