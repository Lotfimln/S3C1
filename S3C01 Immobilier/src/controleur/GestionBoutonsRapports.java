package controleur;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import rapport.RapportAnnuelImmeuble;
import rapport.RapportDeclarationFiscale;
import rapport.RapportDiagnosticsObligatoires;
import rapport.RapportLoyersImpayes;
import rapport.RapportSoldeToutCompte;
import vue.AffichageDonnees;

public class GestionBoutonsRapports {
	
	private AffichageDonnees fenetreAffichageDonnees;
	
	public GestionBoutonsRapports(AffichageDonnees fenetreAffichageDonnees) {
		this.fenetreAffichageDonnees = fenetreAffichageDonnees;
	}
	
	//////////////////////
	// Boutons Rapports //
	//////////////////////
	
	public void genererRapportAnnuelImmeuble() {
	    try {
	        JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
	        int ligneSelectionnee = tableListeElements.getSelectedRow();

	        if (ligneSelectionnee >= 0) {
	            // Récupérer l'ID de l'immeuble à partir de la première colonne de la table
	            String idImmeubleStr = tableListeElements.getValueAt(ligneSelectionnee, 0).toString();

	            // Convertir l'ID en entier si nécessaire
	            int idImmeuble = Integer.parseInt(idImmeubleStr);

	            // Appeler la méthode pour générer le rapport
	            String[] args = {String.valueOf(idImmeuble)};
	            RapportAnnuelImmeuble.main(args);

	            JOptionPane.showMessageDialog(fenetreAffichageDonnees, 
	                "Le rapport pour l'immeuble ID: " + idImmeuble + " a été généré avec succès.",
	                "Succès", 
	                JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(fenetreAffichageDonnees, 
	                "Veuillez sélectionner un immeuble dans la liste à gauche.", 
	                "Erreur", 
	                JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (NumberFormatException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees, 
	            "Erreur : L'ID de l'immeuble sélectionné est invalide.", 
	            "Erreur", 
	            JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees, 
	            "Une erreur est survenue lors de la génération du rapport.", 
	            "Erreur", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void genererRapportSoldeToutCompte() {
	    try {
	        JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
	        int ligneSelectionnee = tableListeElements.getSelectedRow();

	        if (ligneSelectionnee >= 0) {
	            // Récupérer l'ID du logement à partir de la première colonne de la table
	            String idLouableStr = tableListeElements.getValueAt(ligneSelectionnee, 0).toString();

	            // Convertir l'ID en entier si nécessaire
	            int idLouable = Integer.parseInt(idLouableStr);

	            // Appeler la méthode pour générer le rapport
	            String[] args = {String.valueOf(idLouable)};
	            RapportSoldeToutCompte.main(args);

	            JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	                "Le rapport de solde de tout compte pour le logement ID: " + idLouable + " a été généré avec succès.",
	                "Succès",
	                JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	                "Veuillez sélectionner un logement dans la liste à gauche.",
	                "Erreur",
	                JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (NumberFormatException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Erreur : L'ID du logement sélectionné est invalide.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Une erreur est survenue lors de la génération du rapport de solde de tout compte.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void genererRapportLoyersImpayes() {
	    try {
	        // Appeler la méthode principale de RapportLoyersImpayes sans paramètre
	        RapportLoyersImpayes.main(new String[0]);

	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Le rapport des loyers impayés a été généré avec succès.",
	            "Succès",
	            JOptionPane.INFORMATION_MESSAGE);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Une erreur est survenue lors de la génération du rapport des loyers impayés.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}

	public void genererRapportDeclarationFiscale() {
	    try {
	        JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
	        int ligneSelectionnee = tableListeElements.getSelectedRow();

	        if (ligneSelectionnee >= 0) {
	            // Récupérer l'ID de l'immeuble à partir de la première colonne de la table
	            String idImmeubleStr = tableListeElements.getValueAt(ligneSelectionnee, 0).toString();

	            // Convertir l'ID en entier si nécessaire
	            int idImmeuble = Integer.parseInt(idImmeubleStr);

	            // Appeler la méthode pour générer le rapport
	            String[] args = {String.valueOf(idImmeuble)};
	            RapportDeclarationFiscale.main(args);

	            JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	                "Le rapport de déclaration fiscale pour l'immeuble ID: " + idImmeuble + " a été généré avec succès.",
	                "Succès",
	                JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	                "Veuillez sélectionner un immeuble dans la liste à gauche.",
	                "Erreur",
	                JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (NumberFormatException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Erreur : L'ID de l'immeuble sélectionné est invalide.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Une erreur est survenue lors de la génération du rapport de déclaration fiscale.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}

	public void genererRapportDiagnosticsObligatoires() {
	    try {
	        JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
	        int ligneSelectionnee = tableListeElements.getSelectedRow();

	        if (ligneSelectionnee >= 0) {
	            // Récupérer l'ID de l'immeuble à partir de la première colonne de la table
	            String idImmeubleStr = tableListeElements.getValueAt(ligneSelectionnee, 0).toString();

	            // Convertir l'ID en entier si nécessaire
	            int idImmeuble = Integer.parseInt(idImmeubleStr);

	            // Appeler la méthode pour générer le rapport
	            String[] args = {String.valueOf(idImmeuble)};
	            RapportDiagnosticsObligatoires.main(args);

	            JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	                "Le rapport des diagnostics obligatoires pour l'immeuble ID: " + idImmeuble + " a été généré avec succès.",
	                "Succès",
	                JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	                "Veuillez sélectionner un immeuble dans la liste à gauche.",
	                "Erreur",
	                JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (NumberFormatException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Erreur : L'ID de l'immeuble sélectionné est invalide.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees,
	            "Une erreur est survenue lors de la génération du rapport des diagnostics obligatoires.",
	            "Erreur",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
}
