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

public class GestionAffichageDonnees<T> {

    private AffichageDonnees fenetreAffichageDonnees;
    private Dao<T> dao; // DAO générique pour récupérer les données de la table sélectionnée
    private Object elementSelectionne; // L'élément actuellement sélectionné dans la table
    private Map<String, Component> composantsAttributs; // Map pour relier les attributs à leurs composants graphiques

    public GestionAffichageDonnees(AffichageDonnees fenetreAffichageDonnees, Dao<T> dao) {
        this.fenetreAffichageDonnees = fenetreAffichageDonnees;
        this.dao = dao;
        this.composantsAttributs = new HashMap<>();

        // Ajouter un écouteur pour détecter les clics sur la table de gauche
        ajouterEcouteurTable();
    }

    public void setDao(Dao<T> dao) {
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
	        elementSelectionne = dao.findById(idElement);

	        // Effacer les anciens composants du panneau
	        JPanel panelAttributs = fenetreAffichageDonnees.getPanelAttributs();
	        panelAttributs.removeAll();
	        composantsAttributs.clear();

	        // Afficher les attributs de l'élément principal
	        for (Field champ : elementSelectionne.getClass().getDeclaredFields()) {
	            champ.setAccessible(true);
	            JLabel label = new JLabel(champ.getName() + " :");
	            panelAttributs.add(label);

	            Component composant = creerComposantPourAttribut(champ, elementSelectionne);
	            panelAttributs.add(composant);

	            composantsAttributs.put(champ.getName(), composant);
	        }

	        // Gérer les associations multiples dynamiquement
	        if (elementSelectionne instanceof Locataire) {
	            afficherAssociationsMultiples("correspondre_locataire", elementSelectionne);
	        } else if (elementSelectionne instanceof ContratDeLocation) {
	            afficherAssociationsMultiples("correspondre_contratdelocation", elementSelectionne);
	        } else if (elementSelectionne instanceof Charge) {
	            afficherAssociationsMultiples("apparaitre_charge", elementSelectionne);
	        } else if (elementSelectionne instanceof IndexCompteur) {
	            afficherAssociationsMultiples("apparaitre_index", elementSelectionne);
	            afficherAssociationsMultiples("associer_index", elementSelectionne);
	            afficherAssociationsMultiples("indexer_index", elementSelectionne);
	        } else if (elementSelectionne instanceof Louable) {
	            afficherAssociationsMultiples("associer_louable", elementSelectionne);
	        } else if (elementSelectionne instanceof Immeuble) {
	            afficherAssociationsMultiples("indexer_immeuble", elementSelectionne);
	        }

	        // Rafraîchir le panneau pour afficher les mises à jour
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
            dao.update((T) elementSelectionne);

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
                case "correspondre_locataire": // Locataire ↔ Contrat_de_location
                    if (elementPrincipal instanceof Locataire) {
                        String idLocataire = ((Locataire) elementPrincipal).getIdLocataire();
                        DaoCorrespondre daoCorrespondre = new DaoCorrespondre(CictOracleDataSource.getConnectionBD());
                        List<Correspondre> correspondances = daoCorrespondre.findByLocataire(new String[]{idLocataire});

                        DefaultTableModel modelContrats = new DefaultTableModel(new String[]{"Contrats associés"}, 0);
                        for (Correspondre correspondance : correspondances) {
                            DaoContratDeLocation daoContrat = new DaoContratDeLocation(CictOracleDataSource.getConnectionBD());
                            ContratDeLocation contrat = daoContrat.findById(String.valueOf(correspondance.getIdContratDeLocation()));
                            if (contrat != null) {
                                modelContrats.addRow(new Object[]{contrat.toString()});
                            }
                        }
                        tableAssociations.setModel(modelContrats);
                    }
                    break;

                case "correspondre_contratdelocation": // Contrat_de_location ↔ Locataire
                    if (elementPrincipal instanceof ContratDeLocation) {
                        int idContrat = ((ContratDeLocation) elementPrincipal).getIdContratDeLocation();
                        DaoCorrespondre daoCorrespondre = new DaoCorrespondre(CictOracleDataSource.getConnectionBD());
                        List<Correspondre> correspondances = daoCorrespondre.findByContratDeLocation(new String[]{String.valueOf(idContrat)});

                        DefaultTableModel modelLocataires = new DefaultTableModel(new String[]{"Locataires associés"}, 0);
                        for (Correspondre correspondance : correspondances) {
                            DaoLocataire daoLocataire = new DaoLocataire(CictOracleDataSource.getConnectionBD());
                            Locataire locataire = daoLocataire.findById(correspondance.getIdLocataire());
                            if (locataire != null) {
                                modelLocataires.addRow(new Object[]{locataire.toString()});
                            }
                        }
                        tableAssociations.setModel(modelLocataires);
                    }
                    break;

                case "apparaitre_charge": // Charge ↔ Index_Compteur
                    if (elementPrincipal instanceof Charge) {
                        int idCharge = ((Charge) elementPrincipal).getIdCharge();
                        DaoApparaitre daoApparaitre = new DaoApparaitre(CictOracleDataSource.getConnectionBD());
                        List<Apparaitre> apparitions = daoApparaitre.findByCharge(new String[]{String.valueOf(idCharge)});

                        DefaultTableModel modelIndexes = new DefaultTableModel(new String[]{"Index Compteurs associés"}, 0);
                        for (Apparaitre apparition : apparitions) {
                            DaoIndexCompteur daoIndex = new DaoIndexCompteur(CictOracleDataSource.getConnectionBD());
                            IndexCompteur index = daoIndex.findById(String.valueOf(apparition.getIdIndexCompteur()));
                            if (index != null) {
                                modelIndexes.addRow(new Object[]{index.toString()});
                            }
                        }
                        tableAssociations.setModel(modelIndexes);
                    }
                    break;

                case "apparaitre_index": // Index_Compteur ↔ Charge
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoApparaitre daoApparaitre = new DaoApparaitre(CictOracleDataSource.getConnectionBD());
                        List<Apparaitre> apparitions = daoApparaitre.findByIndex(new String[]{String.valueOf(idIndex)});

                        DefaultTableModel modelCharges = new DefaultTableModel(new String[]{"Charges associées"}, 0);
                        for (Apparaitre apparition : apparitions) {
                            DaoCharge daoCharge = new DaoCharge(CictOracleDataSource.getConnectionBD());
                            Charge charge = daoCharge.findById(String.valueOf(apparition.getIdCharge()));
                            if (charge != null) {
                                modelCharges.addRow(new Object[]{charge.toString()});
                            }
                        }
                        tableAssociations.setModel(modelCharges);
                    }
                    break;

                case "associer_louable": // Louable ↔ Index_Compteur
                    if (elementPrincipal instanceof Louable) {
                        int idLouable = ((Louable) elementPrincipal).getIdLouable();
                        DaoAssocier daoAssocier = new DaoAssocier(CictOracleDataSource.getConnectionBD());
                        List<Associer> associations = daoAssocier.findByLouable(new String[]{String.valueOf(idLouable)});

                        DefaultTableModel modelIndexes = new DefaultTableModel(new String[]{"Index Compteurs associés"}, 0);
                        for (Associer association1 : associations) {
                            DaoIndexCompteur daoIndex = new DaoIndexCompteur(CictOracleDataSource.getConnectionBD());
                            IndexCompteur index = daoIndex.findById(String.valueOf(association1.getIdIndexCompteur()));
                            if (index != null) {
                                modelIndexes.addRow(new Object[]{index.toString()});
                            }
                        }
                        tableAssociations.setModel(modelIndexes);
                    }
                    break;

                case "associer_index": // Index_Compteur ↔ Louable
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoAssocier daoAssocier = new DaoAssocier(CictOracleDataSource.getConnectionBD());
                        List<Associer> associations = daoAssocier.findByIndexCompteur(new String[]{String.valueOf(idIndex)});

                        DefaultTableModel modelLouables = new DefaultTableModel(new String[]{"Louables associés"}, 0);
                        for (Associer association2 : associations) {
                            DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
                            Louable louable = daoLouable.findById(String.valueOf(association2.getIdLouable()));
                            if (louable != null) {
                                modelLouables.addRow(new Object[]{louable.toString()});
                            }
                        }
                        tableAssociations.setModel(modelLouables);
                    }
                    break;

                case "indexer_immeuble": // Immeuble ↔ Index_Compteur
                    if (elementPrincipal instanceof Immeuble) {
                        int idImmeuble = ((Immeuble) elementPrincipal).getIdImmeuble();
                        DaoIndexer daoIndexer = new DaoIndexer(CictOracleDataSource.getConnectionBD());
                        List<Indexer> associations = daoIndexer.findByImmeuble(new String[]{String.valueOf(idImmeuble)});

                        DefaultTableModel modelIndexes = new DefaultTableModel(new String[]{"Index Compteurs associés"}, 0);
                        for (Indexer association3 : associations) {
                            DaoIndexCompteur daoIndex = new DaoIndexCompteur(CictOracleDataSource.getConnectionBD());
                            IndexCompteur index = daoIndex.findById(String.valueOf(association3.getIdIndexCompteur()));
                            if (index != null) {
                                modelIndexes.addRow(new Object[]{index.toString()});
                            }
                        }
                        tableAssociations.setModel(modelIndexes);
                    }
                    break;
                    
                case "indexer_index": // Index_Compteur ↔ Immeuble
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoIndexer daoIndexer = new DaoIndexer(CictOracleDataSource.getConnectionBD());
                        List<Indexer> associations = daoIndexer.findByIndexCompteur(new String[]{String.valueOf(idIndex)});

                        DefaultTableModel modelImmeubles = new DefaultTableModel(new String[]{"Immeubles associés"}, 0);
                        for (Indexer association4 : associations) {
                            DaoImmeuble daoImmeuble = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
                            Immeuble immeuble = daoImmeuble.findById(String.valueOf(association4.getIdImmeuble()));
                            if (immeuble != null) {
                                modelImmeubles.addRow(new Object[]{immeuble.toString()});
                            }
                        }
                        tableAssociations.setModel(modelImmeubles);
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