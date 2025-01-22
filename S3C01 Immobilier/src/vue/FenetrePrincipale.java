package vue;

import javax.swing.*;
import controleur.GestionFenetrePrincipale;
import outils.ImportCSV;
import outils.LireCSV;

public class FenetrePrincipale extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem menuItemConnecter;
	private JMenuItem menuItemDeconnecter;

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

		// Nouveau bouton pour importer le CSV
		JMenuItem menuItemImporterCSV = new JMenuItem("Importer CSV");
		menuParametres.add(menuItemImporterCSV);

		// Ajout des contrôleur
		GestionFenetrePrincipale gestionClic = new GestionFenetrePrincipale(this);
		menuItemQuitter.addActionListener(gestionClic);
		menuItemProprietaire.addActionListener(gestionClic);
		this.menuItemConnecter.addActionListener(gestionClic);
		this.menuItemDeconnecter.addActionListener(gestionClic);

		// Action pour importer un CSV
		menuItemImporterCSV.addActionListener(e -> {
		    ImportCSV importCSV = new ImportCSV();
		    importCSV.choisirChemin();
		    String cheminFichier = importCSV.getSelectedFilePath();

		    if (cheminFichier != null && !cheminFichier.isEmpty()) {
		        try {
		            // Appeler la méthode pour importer les quittances
		            LireCSV.importerQuittances(cheminFichier);
		            JOptionPane.showMessageDialog(this, "Importation réussie du fichier : " + cheminFichier,
		                    "Import CSV", JOptionPane.INFORMATION_MESSAGE);
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this, "Erreur lors de l'importation : " + ex.getMessage(),
		                    "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Aucun fichier sélectionné.", "Erreur",
		                JOptionPane.ERROR_MESSAGE);
		    }
		});
	}

	public void setConnecte(boolean connecte) {
		this.menuItemDeconnecter.setEnabled(connecte);
		this.menuItemConnecter.setEnabled(!connecte);
	}

	public static void main(String[] args) {
		FenetrePrincipale fenetre = new FenetrePrincipale();
		fenetre.setVisible(true);
	}
}
