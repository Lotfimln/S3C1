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
                    // Utiliser CictOracleDataSource pour établir la connexion
                    String username = fenetreConnexion.getUsername();
                    String password = fenetreConnexion.getPassword();

                    Connection connection = CictOracleDataSource.creerAcces(username, password);

                    // Vérifier si la connexion est réussie
                    if (connection != null && !connection.isClosed()) {
                        fenetrePrincipale.setConnecte(true);
                        this.fenetreConnexion.dispose();

                        // Afficher la fenêtre AffichageDonnees
                        AffichageDonnees affichageDonnees = new AffichageDonnees(this.fenetrePrincipale);
                        this.fenetrePrincipale.getLayeredPane().add(affichageDonnees);
                        affichageDonnees.setVisible(true);
                        affichageDonnees.moveToFront();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(fenetreConnexion,
                        "Erreur de connexion : " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "Annuler":
                this.fenetreConnexion.dispose();
                break;
        }
    }
}
