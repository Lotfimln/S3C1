package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import vue.Parametres;

public class GestionParametres implements ActionListener{

	private Parametres fenetreParametres;

	public GestionParametres(Parametres fenetreParametres) {
		this.fenetreParametres = fenetreParametres;
	}

	//Enregistrer les données
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		
		case "Enregistrer":
			String[] tabParametres = fenetreParametres.getParametres();
			fenetreParametres.enregistrerDansFichier(tabParametres);
		    JOptionPane.showMessageDialog(fenetreParametres, "Données enregistrées avec succès !");		    
		    break;
		    
		case "Annuler":
			this.fenetreParametres.dispose();
			break;
		}
	}
}
