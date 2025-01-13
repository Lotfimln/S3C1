package controleur;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modele.dao.*;
import modele.*;
import vue.AffichageDonnees;

public class GestionAffichageDonnees {

    private AffichageDonnees fenetreAffichageDonnees;
    private Dao<?> dao; // DAO générique pour récupérer les données de la table sélectionnée
    private Object elementSelectionne; // L'élément actuellement sélectionné dans la table
    private Map<String, Component> composantsAttributs; // Map pour relier les attributs à leurs composants graphiques

    public GestionAffichageDonnees(AffichageDonnees fenetreAffichageDonnees, Dao<?> dao) {
        this.fenetreAffichageDonnees = fenetreAffichageDonnees;
        this.dao = dao;
        this.composantsAttributs = new HashMap<>();

        // Ajouter un écouteur pour détecter les clics sur la table de gauche
        ajouterEcouteurTable();
    }

    public void setDao(Dao<?> dao) {
		this.dao = dao;
	}

	private void ajouterEcouteurTable() {
        fenetreAffichageDonnees.getTableListeElements().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int ligneSelectionnee = fenetreAffichageDonnees.getTableListeElements().getSelectedRow();
                if (ligneSelectionnee >= 0) {
                    // Récupère l'ID de l'élément dans la première colonne
                    String idElement = fenetreAffichageDonnees.getTableListeElements().getValueAt(ligneSelectionnee, 0).toString();
                    afficherDetailsElement(idElement);
                }
            }
        });
    }

    private void afficherDetailsElement(String idElement) {
        try {
            // Récupère l'élément sélectionné via le DAO
            elementSelectionne = dao.findById(idElement);

            // Effacer les anciens composants du panneau
            JPanel panelAttributs = fenetreAffichageDonnees.getPanelAttributs();
            panelAttributs.removeAll();
            composantsAttributs.clear();

            // Parcourt les attributs de l'objet et crée des champs dynamiques
            for (Field champ : elementSelectionne.getClass().getDeclaredFields()) {
                champ.setAccessible(true); // Accéder aux champs privés

                // Crée un label pour le nom de l'attribut
                JLabel label = new JLabel(champ.getName() + " :");
                panelAttributs.add(label);

                // Crée un composant adapté au type de l'attribut
                Component composant = creerComposantPourAttribut(champ, elementSelectionne);
                panelAttributs.add(composant);

                // Enregistre le composant dans la map
                composantsAttributs.put(champ.getName(), composant);
            }

            // Rafraîchir l'affichage du panneau
            panelAttributs.revalidate();
            panelAttributs.repaint();

        } catch (SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private Component creerComposantPourAttribut(Field champ, Object element) throws IllegalAccessException {
        Object valeur = champ.get(element);
        if (champ.getType() == int.class || champ.getType() == Integer.class ||
            champ.getType() == double.class || champ.getType() == Double.class) {
            // Champ numérique
            JTextField textField = new JTextField(valeur != null ? valeur.toString() : "");
            return textField;
        } else if (champ.getType() == String.class) {
            // Champ texte
            JTextField textField = new JTextField(valeur != null ? (String) valeur : "");
            return textField;
        } else if (champ.getType() == java.util.Date.class || champ.getType() == java.sql.Date.class) {
            // Champ date
            JTextField textField = new JTextField(valeur != null ? valeur.toString() : "");
            return textField;
        }
        // Par défaut, retourne un champ texte pour les autres types
        JTextField textField = new JTextField(valeur != null ? valeur.toString() : "");
        return textField;
    }

    public void enregistrerModifications() {
        try {
            // Parcourt les attributs et met à jour l'objet avec les nouvelles valeurs des champs
            for (Field champ : elementSelectionne.getClass().getDeclaredFields()) {
                champ.setAccessible(true); // Accéder aux champs privés
                Component composant = composantsAttributs.get(champ.getName());

                if (composant instanceof JTextField) {
                    String nouvelleValeur = ((JTextField) composant).getText();
                    Object valeurCast = casterValeur(nouvelleValeur, champ.getType());
                    champ.set(elementSelectionne, valeurCast);
                }
            }

            // Enregistre l'objet mis à jour dans la base via le DAO
            dao.update(elementSelectionne);

        } catch (SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
    
    private void afficherAssociationsMultiples(String association, Object elementPrincipal) {
        try {
            JPanel panelAttributs = fenetreAffichageDonnees.getPanelAttributs();
            
            JTable tableAssociations = new JTable();
            JScrollPane scrollPaneTable = new JScrollPane(tableAssociations);

            switch (association.toLowerCase()) {
                case "correspondre": // Locataire ↔ Contrat_de_location
                    if (elementPrincipal instanceof ContratDeLocation) {
                        int idContrat = ((ContratDeLocation) elementPrincipal).getIdContratDeLocation();
                        DaoCorrespondre daoCorrespondre = new DaoCorrespondre(CictOracleDataSource.getConnectionBD());
                        List<Locataire> locataires = daoCorrespondre.findLocatairesByContrat(new String[]{String.valueOf(idContrat)});

                        DefaultTableModel modelLocataire = new DefaultTableModel(new String[]{"Locataire"}, 0);
                        for (Locataire locataire : locataires) {
                            modelLocataire.addRow(new Object[]{locataire.toString()});
                        }
                        tableAssociations.setModel(modelLocataire);
                    }
                    break;

                case "apparaitre": // Charge ↔ Index_Compteur
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoApparaitre daoApparaître = new DaoApparaitre(CictOracleDataSource.getConnectionBD());
                        List<Charge> charges = daoApparaître.findChargesByIndexCompteur(new String[]{String.valueOf(idIndex)});

                        DefaultTableModel modelCharge = new DefaultTableModel(new String[]{"Charge"}, 0);
                        for (Charge charge : charges) {
                            modelCharge.addRow(new Object[]{charge.toString()});
                        }
                        tableAssociations.setModel(modelCharge);
                    }
                    break;

                case "associer": // Louable ↔ Index_Compteur
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoAssocier daoAssocier = new DaoAssocier(CictOracleDataSource.getConnectionBD());
                        List<Louable> louables = daoAssocier.findByL(new String[]{String.valueOf(idIndex)});

                        DefaultTableModel modelLouable = new DefaultTableModel(new String[]{"Louable"}, 0);
                        for (Louable louable : louables) {
                            modelLouable.addRow(new Object[]{louable.toString()});
                        }
                        tableAssociations.setModel(modelLouable);
                    }
                    break;

                case "indexer": // Immeuble ↔ Index_Compteur
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoIndexer daoIndexer = new DaoIndexer(CictOracleDataSource.getConnectionBD());
                        List<Immeuble> immeubles = (List<Immeuble>) daoIndexer.findByIndexCompteur(new String[]{String.valueOf(idIndex)});

                        DefaultTableModel modelImmeuble = new DefaultTableModel(new String[]{"Immeuble"}, 0);
                        for (Immeuble immeuble : immeubles) {
                            modelImmeuble.addRow(new Object[]{immeuble.toString()});
                        }
                        tableAssociations.setModel(modelImmeuble);
                    }
                    break;

                default:
                    System.out.println("Association non reconnue : " + association);
                    return;
            }

            panelAttributs.add(scrollPaneTable);
            panelAttributs.revalidate();
            panelAttributs.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    private Object casterValeur(String valeur, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(valeur);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(valeur);
        } else if (type == java.util.Date.class || type == java.sql.Date.class) {
            try {
                return java.sql.Date.valueOf(valeur); // Convertir une chaîne en date
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return valeur; // Par défaut, retourne la valeur telle qu'elle est
    }
}
