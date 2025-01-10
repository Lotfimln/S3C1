package vue;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controleur.GestionConnexion;

public class Connexion extends JInternalFrame {

	private JTextField champUtilisateur;
	private JPasswordField champMotDePasse;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Connexion(FenetrePrincipale fenetrePrincipale) {
		this.setTitle("Connexion");
		this.setSize(300, 200);
		this.setClosable(true);
		this.setResizable(false);
		this.setLayout(null);

		// Ajout des composants
		JLabel labelUtilisateur = new JLabel("Utilisateur :");
		labelUtilisateur.setBounds(20, 30, 100, 25);
		this.add(labelUtilisateur);

		champUtilisateur = new JTextField();
		champUtilisateur.setBounds(120, 30, 150, 25);
		this.add(champUtilisateur);

		JLabel labelMotDePasse = new JLabel("Mot de passe :");
		labelMotDePasse.setBounds(20, 70, 100, 25);
		this.add(labelMotDePasse);

		champMotDePasse = new JPasswordField();
		champMotDePasse.setBounds(120, 70, 150, 25);
		this.add(champMotDePasse);

		JButton boutonConnecter = new JButton("Connecter");
		boutonConnecter.setBounds(60, 120, 100, 30);
		this.add(boutonConnecter);

		JButton boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.setBounds(170, 120, 100, 30);
		this.add(boutonAnnuler);

		// Ajout du contrôleur pour la gestion des événements
		GestionConnexion gestionClic = new GestionConnexion(this, fenetrePrincipale);
		boutonConnecter.addActionListener(gestionClic);
		boutonAnnuler.addActionListener(gestionClic);
	}

	public String getUsername() {
		return champUtilisateur.getText();
	}

	public String getPassword() {
		return new String(champMotDePasse.getPassword());
	}
}
