package vue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import controleur.GestionFenetrePrincipale;

public class FenetrePrincipale extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem menuItemConnecter;
	private JMenuItem menuItemDeconnecter;
	private boolean estConnecte = false;

	public FenetrePrincipale() {
		this.setTitle("Immo' Gestion 31");
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		// Création de la barre de menu
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Menu Fichier
		JMenu menuFichier = new JMenu("Fichier");
		menuBar.add(menuFichier);
		JMenuItem menuItemQuitter = new JMenuItem("Quitter");
		menuFichier.add(menuItemQuitter);

		// Menu Connexion
		JMenu menuConnexion = new JMenu("Connexion");
		menuBar.add(menuConnexion);
		this.menuItemConnecter = new JMenuItem("Connecter");
		menuConnexion.add(menuItemConnecter);
		this.menuItemDeconnecter = new JMenuItem("Deconnecter");
		menuConnexion.add(this.menuItemDeconnecter);
		this.menuItemDeconnecter.setEnabled(false); // Désactivé au démarrage

		// Menu Parametres
		JMenu menuParametres = new JMenu("Paramètres");
		menuBar.add(menuParametres);
		JMenuItem menuItemProprietaire = new JMenuItem("Proprietaire");
		menuParametres.add(menuItemProprietaire);

		// Ajout des contrôleur
		GestionFenetrePrincipale gestionClic = new GestionFenetrePrincipale(this);
		menuItemQuitter.addActionListener(gestionClic);
		menuItemProprietaire.addActionListener(gestionClic);
		this.menuItemConnecter.addActionListener(gestionClic);
		this.menuItemDeconnecter.addActionListener(gestionClic);
	}

	public void setConnecte(boolean connecte) {
		this.estConnecte = connecte;
		this.menuItemDeconnecter.setEnabled(connecte);
		this.menuItemConnecter.setEnabled(!connecte);
	}

	public static void main(String[] args) {
		FenetrePrincipale fenetre = new FenetrePrincipale();
		fenetre.setVisible(true);
	}
}
