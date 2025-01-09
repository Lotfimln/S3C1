package vue;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.GestionParametres;


public class Parametres extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;


	/**
	 * Create the frame.
	 */
	public Parametres(FenetrePrincipale fenetrePrincipale) {
		this.setTitle("Proprietaire");
		this.setSize(900, 600);
		this.setClosable(true);
		this.setResizable(false);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{58, 0};
		gbl_panel.rowHeights = new int[] {25, 25, 25, 25, 25, 25, 25, 25, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblPrenom = new JLabel("Prenom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.fill = GridBagConstraints.BOTH;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 0;
		panel.add(lblPrenom, gbc_lblPrenom);

		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.fill = GridBagConstraints.BOTH;
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		panel.add(lblNom, gbc_lblNom);

		JLabel lblTelephone = new JLabel("Telephone");
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.fill = GridBagConstraints.BOTH;
		gbc_lblTelephone.insets = new Insets(0, 0, 5, 0);
		gbc_lblTelephone.gridx = 0;
		gbc_lblTelephone.gridy = 2;
		panel.add(lblTelephone, gbc_lblTelephone);

		JLabel lblRue = new JLabel("Rue");
		GridBagConstraints gbc_lblRue = new GridBagConstraints();
		gbc_lblRue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRue.insets = new Insets(0, 0, 5, 0);
		gbc_lblRue.gridx = 0;
		gbc_lblRue.gridy = 3;
		panel.add(lblRue, gbc_lblRue);

		JLabel lblCodePostal = new JLabel("Code Postal");
		GridBagConstraints gbc_lblCodePostal = new GridBagConstraints();
		gbc_lblCodePostal.fill = GridBagConstraints.BOTH;
		gbc_lblCodePostal.insets = new Insets(0, 0, 5, 0);
		gbc_lblCodePostal.gridx = 0;
		gbc_lblCodePostal.gridy = 4;
		panel.add(lblCodePostal, gbc_lblCodePostal);

		JLabel lblVille = new JLabel("Ville");
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.fill = GridBagConstraints.BOTH;
		gbc_lblVille.insets = new Insets(0, 0, 5, 0);
		gbc_lblVille.gridx = 0;
		gbc_lblVille.gridy = 5;
		panel.add(lblVille, gbc_lblVille);

		JLabel lblPays = new JLabel("Pays");
		GridBagConstraints gbc_lblPays = new GridBagConstraints();
		gbc_lblPays.fill = GridBagConstraints.BOTH;
		gbc_lblPays.insets = new Insets(0, 0, 5, 0);
		gbc_lblPays.gridx = 0;
		gbc_lblPays.gridy = 6;
		panel.add(lblPays, gbc_lblPays);

		JLabel lblMail = new JLabel("Mail");
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.insets = new Insets(0, 0, 5, 0);
		gbc_lblMail.fill = GridBagConstraints.BOTH;
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 7;
		panel.add(lblMail, gbc_lblMail);

		JButton btnAnnuler = new JButton("Annuler");
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.gridx = 0;
		gbc_btnAnnuler.gridy = 8;
		panel.add(btnAnnuler, gbc_btnAnnuler);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {10, 0};
		gbl_panel_2.rowHeights = new int[] {25, 25, 25, 25, 25, 25, 25, 25, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);

		JLabel lblPrenom_1 = new JLabel(":");
		GridBagConstraints gbc_lblPrenom_1 = new GridBagConstraints();
		gbc_lblPrenom_1.anchor = GridBagConstraints.WEST;
		gbc_lblPrenom_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblPrenom_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrenom_1.gridx = 0;
		gbc_lblPrenom_1.gridy = 0;
		panel_2.add(lblPrenom_1, gbc_lblPrenom_1);

		JLabel lblNom_1 = new JLabel(":");
		GridBagConstraints gbc_lblNom_1 = new GridBagConstraints();
		gbc_lblNom_1.fill = GridBagConstraints.BOTH;
		gbc_lblNom_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom_1.gridx = 0;
		gbc_lblNom_1.gridy = 1;
		panel_2.add(lblNom_1, gbc_lblNom_1);

		JLabel lblTelephone_1 = new JLabel(":");
		GridBagConstraints gbc_lblTelephone_1 = new GridBagConstraints();
		gbc_lblTelephone_1.fill = GridBagConstraints.BOTH;
		gbc_lblTelephone_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblTelephone_1.gridx = 0;
		gbc_lblTelephone_1.gridy = 2;
		panel_2.add(lblTelephone_1, gbc_lblTelephone_1);

		JLabel lblRue_1 = new JLabel(":");
		GridBagConstraints gbc_lblRue_1 = new GridBagConstraints();
		gbc_lblRue_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRue_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblRue_1.gridx = 0;
		gbc_lblRue_1.gridy = 3;
		panel_2.add(lblRue_1, gbc_lblRue_1);

		JLabel lblCodePostal_1 = new JLabel(":");
		GridBagConstraints gbc_lblCodePostal_1 = new GridBagConstraints();
		gbc_lblCodePostal_1.fill = GridBagConstraints.BOTH;
		gbc_lblCodePostal_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblCodePostal_1.gridx = 0;
		gbc_lblCodePostal_1.gridy = 4;
		panel_2.add(lblCodePostal_1, gbc_lblCodePostal_1);

		JLabel lblVille_1 = new JLabel(":");
		GridBagConstraints gbc_lblVille_1 = new GridBagConstraints();
		gbc_lblVille_1.fill = GridBagConstraints.BOTH;
		gbc_lblVille_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblVille_1.gridx = 0;
		gbc_lblVille_1.gridy = 5;
		panel_2.add(lblVille_1, gbc_lblVille_1);

		JLabel lblPays_1 = new JLabel(":");
		GridBagConstraints gbc_lblPays_1 = new GridBagConstraints();
		gbc_lblPays_1.fill = GridBagConstraints.BOTH;
		gbc_lblPays_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblPays_1.gridx = 0;
		gbc_lblPays_1.gridy = 6;
		panel_2.add(lblPays_1, gbc_lblPays_1);

		JLabel lblMail_1 = new JLabel(":");
		GridBagConstraints gbc_lblMail_1 = new GridBagConstraints();
		gbc_lblMail_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblMail_1.fill = GridBagConstraints.BOTH;
		gbc_lblMail_1.gridx = 0;
		gbc_lblMail_1.gridy = 7;
		panel_2.add(lblMail_1, gbc_lblMail_1);

		JLabel lblVide = new JLabel(" ");
		GridBagConstraints gbc_lblVide = new GridBagConstraints();
		gbc_lblVide.gridx = 0;
		gbc_lblVide.gridy = 8;
		panel_2.add(lblVide, gbc_lblVide);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 10, 10);
		getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{96, 0};
		gbl_panel_1.rowHeights = new int[]{20, 20, 20, 20, 20, 20, 20, 20, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.BOTH;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 1;
		panel_1.add(textField_1, gbc_textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.BOTH;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 0;
		gbc_textField_2.gridy = 2;
		panel_1.add(textField_2, gbc_textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.BOTH;
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.gridx = 0;
		gbc_textField_3.gridy = 3;
		panel_1.add(textField_3, gbc_textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.BOTH;
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.gridx = 0;
		gbc_textField_4.gridy = 4;
		panel_1.add(textField_4, gbc_textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.BOTH;
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.gridx = 0;
		gbc_textField_5.gridy = 5;
		panel_1.add(textField_5, gbc_textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.fill = GridBagConstraints.BOTH;
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.gridx = 0;
		gbc_textField_6.gridy = 6;
		panel_1.add(textField_6, gbc_textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 0);
		gbc_textField_7.fill = GridBagConstraints.BOTH;
		gbc_textField_7.gridx = 0;
		gbc_textField_7.gridy = 7;
		panel_1.add(textField_7, gbc_textField_7);

		JButton btnNewButton = new JButton("Enregistrer");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 8;
		panel_1.add(btnNewButton, gbc_btnNewButton);

		// Ajout du controlleur pour enregistrer les données
		GestionParametres gestionParametres = new GestionParametres(this);
		btnNewButton.addActionListener(gestionParametres);
		btnAnnuler.addActionListener(gestionParametres);

	}

	/**
	 * Méthode pour récupérer les valeurs des champs de texte sous forme de tableau.
	 */
	public String[] getParametres() {
	    String[] parametres = new String[8];
	    parametres[0] = textField.getText();       // Prenom
	    parametres[1] = textField_1.getText();     // Nom
	    parametres[2] = textField_2.getText();     // Telephone
	    parametres[3] = textField_3.getText();     // Rue
	    parametres[4] = textField_4.getText();     // Code Postal
	    parametres[5] = textField_5.getText();     // Ville
	    parametres[6] = textField_6.getText();     // Pays
	    parametres[7] = textField_7.getText();     // Mail
	    return parametres;
	}

	public void enregistrerDansFichier(String[] parametres) {
	    Properties properties = new Properties();

	    // Associer les paramètres aux clés
	    properties.setProperty("prenom", parametres[0]);
	    properties.setProperty("nom", parametres[1]);
	    properties.setProperty("telephone", parametres[2]);
	    properties.setProperty("rue", parametres[3]);
	    properties.setProperty("code_postal", parametres[4]);
	    properties.setProperty("ville", parametres[5]);
	    properties.setProperty("pays", parametres[6]);
	    properties.setProperty("mail", parametres[7]);

	    // Sauvegarder dans un fichier
	    try (FileOutputStream out = new FileOutputStream("config.properties")) {
	        properties.store(out, "Paramètres utilisateur");
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	    }
	}

}