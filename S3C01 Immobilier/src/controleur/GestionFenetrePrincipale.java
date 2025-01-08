package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vue.*;

public class GestionFenetrePrincipale implements ActionListener {
	private FenetrePrincipale fenetrePrincipale;

	public GestionFenetrePrincipale(FenetrePrincipale fenetrePrincipale) {
		this.fenetrePrincipale = fenetrePrincipale;
	}

	// Methode action performed déplacée
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "Quitter":
			this.fenetrePrincipale.dispose();
			break;
		case "Connecter":
			// Afficher la fenêtre de connexion
			Connexion connexion = new Connexion(this.fenetrePrincipale);
			this.fenetrePrincipale.getLayeredPane().add(connexion);
			connexion.setVisible(true);
			connexion.moveToFront();
			break;
		case "Deconnecter":
			// Déconnecter l'utilisateur et désactiver les menus
			this.fenetrePrincipale.setConnecte(false);
			JOptionPane.showMessageDialog(this.fenetrePrincipale, "Déconnexion réussie.");
			break;
		case "Proprietaire":
			// Passe à l'onglet paramètres
			Parametres parametres = new Parametres(this.fenetrePrincipale);
			this.fenetrePrincipale.getLayeredPane().add(parametres);
			parametres.setVisible(true);
			parametres.moveToFront();
			break;
		}
	}
}
