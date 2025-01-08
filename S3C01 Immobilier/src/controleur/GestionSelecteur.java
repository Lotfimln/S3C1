package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import modele.Assureur;
import modele.dao.DaoAssureur;
import vue.*;

public class GestionSelecteur implements ActionListener{

	private AffichageDonnees fenetreAffichageDonnees;

	public GestionSelecteur(AffichageDonnees fenetreAffichageDonnees) {
		this.fenetreAffichageDonnees = fenetreAffichageDonnees;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        // Vérifiez si l'événement vient du JComboBox
        if (e.getSource() instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();

            // Récupérez la table sélectionnée
            Object selection = comboBox.getSelectedItem();
            if (selection != null && selection.toString().equalsIgnoreCase("ASSUREUR")) {
                // Récupérez la liste des assureurs depuis le DAO (simulé)
                List<Assureur> assureurs = DaoAssureur.recupererAssureurs();

                // Modèle pour la JList
                DefaultListModel<String> listModel = new DefaultListModel<>();
                for (Assureur assureur : assureurs) {
                    listModel.addElement(assureur.toString()); // Utilise la méthode toString() de Assureur
                }

                // Mettre à jour la JList avec les assureurs
                fenetreAffichageDonnees.getList().setModel(listModel);
            }
        }
    }
}
