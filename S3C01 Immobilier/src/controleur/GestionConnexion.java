package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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
                // Initialisation de la connexion via Singleton
                ConnexionBD connexionBD = ConnexionBD.getInstance(
                    fenetreConnexion.getUsername(),
                    fenetreConnexion.getPassword()
                );

                // Test de la connexion
                if (connexionBD.getConnection() != null) {
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
