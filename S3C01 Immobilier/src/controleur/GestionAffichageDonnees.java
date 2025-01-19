package controleur;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modele.dao.*;
import rapport.RapportAnnuelImmeuble;
import rapport.RapportDeclarationFiscale;
import rapport.RapportDiagnosticsObligatoires;
import rapport.RapportLoyersImpayes;
import rapport.RapportSoldeToutCompte;
import modele.*;
import vue.AffichageDonnees;

public class GestionAffichageDonnees<T> {

    private AffichageDonnees fenetreAffichageDonnees;
    private Dao<T> dao; // DAO générique pour récupérer les données de la table sélectionnée
    private Object elementSelectionne; // L'élément actuellement sélectionné dans la table
    private Map<String, Component> composantsAttributs; // Map pour relier les attributs à leurs composants graphiques

    /**
     * @wbp.parser.entryPoint
     */
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
    
    ///////////////////////////////////////////////////////
    // Affichage des attributs et des liaisons multiples //
    ///////////////////////////////////////////////////////

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
	            afficherAssociationsMultiples("colocataire", elementSelectionne);
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
   

    private void afficherAssociationsMultiples(String association, Object elementPrincipal) {
        try {
            JPanel panelAttributs = fenetreAffichageDonnees.getPanelAttributs();
            JPanel panelBoutons = new JPanel(); // Panel pour les boutons
            JTable tableAssociations = new JTable();
            JScrollPane scrollPaneTable = new JScrollPane(tableAssociations);

            // Boutons "MAJ", "+" et "-"
            JButton boutonAjouter = new JButton("+");
            JButton boutonSupprimer = new JButton("-");
            JButton boutonMAJ = new JButton("MAJ");
            panelBoutons.add(boutonMAJ);
            panelBoutons.add(boutonAjouter);
            panelBoutons.add(boutonSupprimer);
            
            // Initialisation du modèle avec une seule colonne
            

         // Charger les données en fonction de l'association
            switch (association.toLowerCase()) {
                case "correspondre_locataire": // Locataire ↔ Contrat_de_location
                    if (elementPrincipal instanceof Locataire) {
                        String idLocataire = ((Locataire) elementPrincipal).getIdLocataire();
                        DaoCorrespondre daoCorrespondre = new DaoCorrespondre(CictOracleDataSource.getConnectionBD());
                        List<Correspondre> correspondances = daoCorrespondre.findByLocataire(new String[]{idLocataire});

                        // Création du modèle
                        DefaultTableModel model = new DefaultTableModel(new String[]{"Contrats associés"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les contrats associés au modèle
                        for (Correspondre correspondance : correspondances) {
                            model.addRow(new Object[]{correspondance.getIdContratDeLocation()});
                        }
                    }
                    break;

                case "colocataire": // Locataire ↔ Locataire
                    if (elementPrincipal instanceof Locataire) {
                        String idLocataire = ((Locataire) elementPrincipal).getIdLocataire();
                        DaoColocataire daoColocataire = new DaoColocataire(CictOracleDataSource.getConnectionBD());
                        List<Colocataire> colocataires = daoColocataire.findByLocataire(new String[]{idLocataire});

                        // Création du modèle
                        DefaultTableModel model = new DefaultTableModel(new String[]{"Colocataires associés"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les colocataires associés au modèle
                        for (Colocataire colocataire : colocataires) {
                            model.addRow(new Object[]{colocataire.getIdLocataire1()});
                        }
                    }
                    break;

                case "correspondre_contratdelocation": // Contrat_de_location ↔ Locataire
                    if (elementPrincipal instanceof ContratDeLocation) {
                        int idContrat = ((ContratDeLocation) elementPrincipal).getIdContratDeLocation();
                        DaoCorrespondre daoCorrespondre = new DaoCorrespondre(CictOracleDataSource.getConnectionBD());
                        List<Correspondre> correspondances = daoCorrespondre.findByContratDeLocation(new String[]{String.valueOf(idContrat)});

                        // Création du modèle
                        DefaultTableModel model = new DefaultTableModel(new String[]{"Locataires associés"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les locataires associés au modèle
                        for (Correspondre correspondance : correspondances) {
                            model.addRow(new Object[]{correspondance.getIdLocataire()});
                        }
                    }
                    break;

                case "apparaitre_charge": // Charge ↔ Index_Compteur
                    if (elementPrincipal instanceof Charge) {
                        int idCharge = ((Charge) elementPrincipal).getIdCharge();
                        DaoApparaitre daoApparaitre = new DaoApparaitre(CictOracleDataSource.getConnectionBD());
                        List<Apparaitre> apparitions = daoApparaitre.findByCharge(new String[]{String.valueOf(idCharge)});

                        // Création du modèle
                        DefaultTableModel model = new DefaultTableModel(new String[]{"Index Compteurs associés"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les index compteurs associés au modèle
                        for (Apparaitre apparition : apparitions) {
                            model.addRow(new Object[]{apparition.getIdIndexCompteur()});
                        }
                    }
                    break;

                case "apparaitre_index": // Index_Compteur ↔ Charge
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoApparaitre daoApparaitre = new DaoApparaitre(CictOracleDataSource.getConnectionBD());
                        List<Apparaitre> apparitions = daoApparaitre.findByIndex(new String[]{String.valueOf(idIndex)});

                        // Création du modèle
                        DefaultTableModel model = new DefaultTableModel(new String[]{"Charges associées"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les charges associées au modèle
                        for (Apparaitre apparition : apparitions) {
                            model.addRow(new Object[]{apparition.getIdCharge()});
                        }
                    }
                    break;

                case "associer_louable": // Louable ↔ Index_Compteur
                    if (elementPrincipal instanceof Louable) {
                        int idLouable = ((Louable) elementPrincipal).getIdLouable();
                        DaoAssocier daoAssocier = new DaoAssocier(CictOracleDataSource.getConnectionBD());
                        List<Associer> associations = daoAssocier.findByLouable(new String[]{String.valueOf(idLouable)});

                        // Création du modèle avec colonnes pour les attributs
                        DefaultTableModel model = new DefaultTableModel(new String[]{
                            "Index Compteur", "Date Relevé", "Prix Abonnement", "Date Régularisation"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les données au modèle
                        for (Associer associer : associations) {
                            model.addRow(new Object[]{
                                associer.getIdIndexCompteur(),
                                associer.getDateReleve(),
                                associer.getPrixAbonnement(),
                                associer.getDateRegularisation()
                            });
                        }
                    }
                    break;

                case "associer_index": // Index_Compteur ↔ Louable
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoAssocier daoAssocier = new DaoAssocier(CictOracleDataSource.getConnectionBD());
                        List<Associer> associations = daoAssocier.findByIndexCompteur(new String[]{String.valueOf(idIndex)});

                        // Création du modèle avec colonnes pour les attributs
                        DefaultTableModel model = new DefaultTableModel(new String[]{
                            "Louable", "Date Relevé", "Prix Abonnement", "Date Régularisation"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les données au modèle
                        for (Associer associer : associations) {
                            model.addRow(new Object[]{
                                associer.getIdLouable(),
                                associer.getDateReleve(),
                                associer.getPrixAbonnement(),
                                associer.getDateRegularisation()
                            });
                        }
                    }
                    break;


                case "indexer_immeuble": // Immeuble ↔ Index_Compteur
                    if (elementPrincipal instanceof Immeuble) {
                        int idImmeuble = ((Immeuble) elementPrincipal).getIdImmeuble();
                        DaoIndexer daoIndexer = new DaoIndexer(CictOracleDataSource.getConnectionBD());
                        List<Indexer> associations = daoIndexer.findByImmeuble(new String[]{String.valueOf(idImmeuble)});

                        // Création du modèle avec colonnes pour les attributs
                        DefaultTableModel model = new DefaultTableModel(new String[]{
                            "Index Compteur", "Date Relevé", "Prix Abonnement", "Date Régularisation"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les données au modèle
                        for (Indexer indexer : associations) {
                            model.addRow(new Object[]{
                                indexer.getIdIndexCompteur(),
                                indexer.getDateReleve(),
                                indexer.getPrixAbonnement(),
                                indexer.getDateRegularisation()
                            });
                        }
                    }
                    break;

                case "indexer_index": // Index_Compteur ↔ Immeuble
                    if (elementPrincipal instanceof IndexCompteur) {
                        int idIndex = ((IndexCompteur) elementPrincipal).getIdIndexCompteur();
                        DaoIndexer daoIndexer = new DaoIndexer(CictOracleDataSource.getConnectionBD());
                        List<Indexer> associations = daoIndexer.findByIndexCompteur(new String[]{String.valueOf(idIndex)});

                        // Création du modèle avec colonnes pour les attributs
                        DefaultTableModel model = new DefaultTableModel(new String[]{
                            "Immeuble", "Date Relevé", "Prix Abonnement", "Date Régularisation"}, 0);
                        tableAssociations.setModel(model);

                        // Ajouter les données au modèle
                        for (Indexer indexer : associations) {
                            model.addRow(new Object[]{
                                indexer.getIdImmeuble(),
                                indexer.getDateReleve(),
                                indexer.getPrixAbonnement(),
                                indexer.getDateRegularisation()
                            });
                        }
                    }
                    break;

                default:
                    System.out.println("Association non reconnue : " + association);
                    break;
            }
            
            
            // Ajouts des controleurs
            
            boutonMAJ.addActionListener(e -> {
                mettreAJourAssociations(association, elementPrincipal, tableAssociations);
            });

            boutonAjouter.addActionListener(e -> {
                // Vérifie que la table est bien initialisée
                if (tableAssociations.getModel() instanceof DefaultTableModel) {
                    DefaultTableModel model = (DefaultTableModel) tableAssociations.getModel();
                    model.addRow(new Object[]{""}); // Ajoute une ligne vide avec une seule colonne
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur : La table des associations n'est pas initialisée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            boutonSupprimer.addActionListener(e -> {
                // Vérifie qu'une ligne est sélectionnée dans la table des associations
                int selectedRow = tableAssociations.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        // Vérifie que la table est bien initialisée
                        if (tableAssociations.getModel() instanceof DefaultTableModel) {
                            DefaultTableModel model = (DefaultTableModel) tableAssociations.getModel();

                            // Récupère l'ID de l'élément principal sélectionné dans la liste de gauche
                            String idElementPrincipal = "";
                            int selectedElementRow = fenetreAffichageDonnees.getTableListeElements().getSelectedRow();
                            if (selectedElementRow >= 0) {
                                idElementPrincipal = fenetreAffichageDonnees.getTableListeElements()
                                                        .getValueAt(selectedElementRow, 0).toString();
                            } else {
                                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un élément dans la liste à gauche.", "Erreur", JOptionPane.WARNING_MESSAGE);
                                return; // Arrête si aucun élément principal n'est sélectionné
                            }

                            // Récupère l'ID associé à supprimer (depuis la table des associations)
                            String idAssocie = model.getValueAt(selectedRow, 0).toString();

                            // Supprime l'association dans la base de données via le DAO approprié
                            switch (association.toLowerCase()) {
                            case "correspondre_locataire": // Locataire ↔ Contrat_de_location
                                new DaoCorrespondre(CictOracleDataSource.getConnectionBD())
                                        .delete(new Correspondre(idElementPrincipal, Integer.parseInt(idAssocie)));
                                break;

                            case "correspondre_contratdelocation": // Contrat_de_location ↔ Locataire
                                new DaoCorrespondre(CictOracleDataSource.getConnectionBD())
                                        .delete(new Correspondre(idAssocie, Integer.parseInt(idElementPrincipal)));
                                break;

                            case "colocataire": // Locataire ↔ Locataire
                                new DaoColocataire(CictOracleDataSource.getConnectionBD())
                                        .delete(new Colocataire(idElementPrincipal, idAssocie));
                                break;

                            case "associer": // Louable ↔ Index_Compteur
                                new DaoAssocier(CictOracleDataSource.getConnectionBD())
                                        .deleteById(idElementPrincipal, idAssocie);
                                break;

                            case "apparaitre": // Charge ↔ Index_Compteur
                                new DaoApparaitre(CictOracleDataSource.getConnectionBD())
                                        .delete(new Apparaitre(Integer.parseInt(idElementPrincipal), Integer.parseInt(idAssocie)));
                                break;

                            case "indexer": // Immeuble ↔ Index_Compteur
                                new DaoIndexer(CictOracleDataSource.getConnectionBD())
                                		.deleteById(idElementPrincipal, idAssocie);
                                break;

                            case "associer_louable": // Louable ↔ Index_Compteur
                                new DaoAssocier(CictOracleDataSource.getConnectionBD())
                                		.deleteById(idElementPrincipal, idAssocie);
                                break;

                            case "apparaitre_charge": // Charge ↔ Index_Compteur
                                new DaoApparaitre(CictOracleDataSource.getConnectionBD())
                                        .delete(new Apparaitre(Integer.parseInt(idElementPrincipal), Integer.parseInt(idAssocie)));
                                break;

                            case "indexer_immeuble": // Immeuble ↔ Index_Compteur
                                new DaoIndexer(CictOracleDataSource.getConnectionBD())
                                		.deleteById(idElementPrincipal, idAssocie);                               
                                break;

                            case "indexer_index": // Index_Compteur ↔ Immeuble
                                new DaoIndexer(CictOracleDataSource.getConnectionBD())
                                		.deleteById(idElementPrincipal, idAssocie);
                                break;
                            }

                            // Supprime la ligne dans la table graphique
                            model.removeRow(selectedRow);

                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur : La table des associations n'est pas initialisée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erreur lors de la suppression dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erreur de format : ID invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            });

        
        
        // Ajout des composants au panneau
        scrollPaneTable.setPreferredSize(new Dimension(500, 80));
        panelAttributs.add(scrollPaneTable);
        panelAttributs.add(panelBoutons);
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
    
    
	///////////////////////////
	// Bouton de Mise a Jour //
	///////////////////////////
    
    
    @SuppressWarnings("unchecked")
	public void enregistrerModifications() {
        try {
            // Parcourt les attributs et met à jour l'objet avec les nouvelles valeurs des champs
            for (Field champ : elementSelectionne.getClass().getDeclaredFields()) {
                champ.setAccessible(true); // Accéder aux champs privés
                Component composant = composantsAttributs.get(champ.getName());

                if (composant instanceof JTextField) {
                    String nouvelleValeur = ((JTextField) composant).getText();

                    switch (champ.getType().getSimpleName()) {
                        case "Entreprise": {
                            // Gestion spécifique pour Entreprise
                            DaoEntreprise daoEntreprise = new DaoEntreprise(CictOracleDataSource.getConnectionBD());
                            Entreprise entreprise = daoEntreprise.findById(nouvelleValeur);
                            if (entreprise == null) {
                                throw new IllegalArgumentException("L'entreprise avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, entreprise);
                            break;
                        }
                        case "Locataire": {
                            // Gestion spécifique pour Locataire
                            DaoLocataire daoLocataire = new DaoLocataire(CictOracleDataSource.getConnectionBD());
                            Locataire locataire = daoLocataire.findById(nouvelleValeur);
                            if (locataire == null) {
                                throw new IllegalArgumentException("Le locataire avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, locataire);
                            break;
                        }
                        case "Immeuble": {
                            // Gestion spécifique pour Immeuble
                            DaoImmeuble daoImmeuble = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
                            Immeuble immeuble = daoImmeuble.findById(nouvelleValeur);
                            if (immeuble == null) {
                                throw new IllegalArgumentException("L'immeuble avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, immeuble);
                            break;
                        }
                        case "IndexCompteur": {
                            // Gestion spécifique pour IndexCompteur
                            DaoIndexCompteur daoIndexCompteur = new DaoIndexCompteur(CictOracleDataSource.getConnectionBD());
                            IndexCompteur indexCompteur = daoIndexCompteur.findById(nouvelleValeur);
                            if (indexCompteur == null) {
                                throw new IllegalArgumentException("L'index compteur avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, indexCompteur);
                            break;
                        }
                        case "Louable": {
                            // Gestion spécifique pour Louable
                            DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
                            Louable louable = daoLouable.findById(nouvelleValeur);
                            if (louable == null) {
                                throw new IllegalArgumentException("Le louable avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, louable);
                            break;
                        }
                        case "Taxe": {
                            // Gestion spécifique pour Taxe
                            DaoTaxe daoTaxe = new DaoTaxe(CictOracleDataSource.getConnectionBD());
                            Taxe taxe = daoTaxe.findById(nouvelleValeur);
                            if (taxe == null) {
                                throw new IllegalArgumentException("La taxe avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, taxe);
                            break;
                        }
                        case "Facture": {
                            // Gestion spécifique pour Facture
                            DaoFacture daoFacture = new DaoFacture(CictOracleDataSource.getConnectionBD());
                            Facture facture = daoFacture.findById(nouvelleValeur);
                            if (facture == null) {
                                throw new IllegalArgumentException("La facture avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, facture);
                            break;
                        }
                        case "Diagnostic": {
                            // Gestion spécifique pour Diagnostic
                            DaoDiagnostic daoDiagnostic = new DaoDiagnostic(CictOracleDataSource.getConnectionBD());
                            Diagnostic diagnostic = daoDiagnostic.findById(nouvelleValeur);
                            if (diagnostic == null) {
                                throw new IllegalArgumentException("Le diagnostic avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, diagnostic);
                            break;
                        }
                        case "ContratDeLocation": {
                            // Gestion spécifique pour Contrat_de_location
                            DaoContratDeLocation daoContratDeLocation = new DaoContratDeLocation(CictOracleDataSource.getConnectionBD());
                            ContratDeLocation contratDeLocation = daoContratDeLocation.findById(nouvelleValeur);
                            if (contratDeLocation == null) {
                                throw new IllegalArgumentException("Le contrat de location avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, contratDeLocation);
                            break;
                        }
                        case "Charge": {
                            // Gestion spécifique pour Charge
                            DaoCharge daoCharge = new DaoCharge(CictOracleDataSource.getConnectionBD());
                            Charge charge = daoCharge.findById(nouvelleValeur);
                            if (charge == null) {
                                throw new IllegalArgumentException("La charge avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, charge);
                            break;
                        }
                        case "Quittances": {
                            // Gestion spécifique pour Quittances
                            DaoQuittances daoQuittances = new DaoQuittances(CictOracleDataSource.getConnectionBD());
                            Quittances quittances = daoQuittances.findById(nouvelleValeur);
                            if (quittances == null) {
                                throw new IllegalArgumentException("La quittance avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, quittances);
                            break;
                        }
                        case "Assureur": {
                            // Gestion spécifique pour Assureur
                            DaoAssureur daoAssureur = new DaoAssureur(CictOracleDataSource.getConnectionBD());
                            Assureur assureurs = daoAssureur.findById(nouvelleValeur);
                            if (assureurs == null) {
                                throw new IllegalArgumentException("L'assureur avec l'ID " + nouvelleValeur + " n'existe pas.");
                            }
                            champ.set(elementSelectionne, assureurs);
                            break;
                        }
                        default: {
                            // Gestion par défaut pour les autres types (String, int, double, etc.)
                            Object valeurCast = casterValeur(nouvelleValeur, champ.getType());
                            champ.set(elementSelectionne, valeurCast);
                            break;
                        }
                    }
                }
            }

            // Enregistre l'objet mis à jour dans la base via le DAO
            dao.update((T) elementSelectionne);

        } catch (SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
    
    //////////////////////////////////
    // Bouton MAJ table associative //
    //////////////////////////////////
    
    private void mettreAJourAssociations(String association, Object elementPrincipal, JTable tableAssociations) {
        try {
            // Identifie l'élément principal
            String idPrincipal = "";
            if (elementPrincipal instanceof Locataire) {
                idPrincipal = ((Locataire) elementPrincipal).getIdLocataire();
            } else if (elementPrincipal instanceof ContratDeLocation) {
                idPrincipal = String.valueOf(((ContratDeLocation) elementPrincipal).getIdContratDeLocation());
            } else if (elementPrincipal instanceof Louable) {
                idPrincipal = String.valueOf(((Louable) elementPrincipal).getIdLouable());
            } else if (elementPrincipal instanceof Charge) {
                idPrincipal = String.valueOf(((Charge) elementPrincipal).getIdCharge());
            } else if (elementPrincipal instanceof Immeuble) {
                idPrincipal = String.valueOf(((Immeuble) elementPrincipal).getIdImmeuble());
            } else if (elementPrincipal instanceof IndexCompteur) {
                idPrincipal = String.valueOf(((IndexCompteur) elementPrincipal).getIdIndexCompteur());
            }

            // Supprime les associations existantes
            switch (association.toLowerCase()) {
                case "correspondre_locataire":
                    new DaoCorrespondre(CictOracleDataSource.getConnectionBD()).deleteByLocataire(idPrincipal);
                    break;
                case "colocataire":
                    new DaoColocataire(CictOracleDataSource.getConnectionBD()).deleteByLocataire(idPrincipal);
                    break;
                case "correspondre_contratdelocation":
                    new DaoCorrespondre(CictOracleDataSource.getConnectionBD()).deleteByContratDeLocation(idPrincipal);
                    break;
                case "associer_louable":
                    new DaoAssocier(CictOracleDataSource.getConnectionBD()).deleteByLouable(idPrincipal);
                    break;
                case "apparaitre_charge":
                    new DaoApparaitre(CictOracleDataSource.getConnectionBD()).deleteByCharge(idPrincipal);
                    break;
                case "indexer_immeuble":
                    new DaoIndexer(CictOracleDataSource.getConnectionBD()).deleteByImmeuble(idPrincipal);
                    break;
                case "associer_index":
                    new DaoAssocier(CictOracleDataSource.getConnectionBD()).deleteByIndexCompteur(idPrincipal);
                    break;
                case "apparaitre_index":
                    new DaoApparaitre(CictOracleDataSource.getConnectionBD()).deleteByIndexCompteur(idPrincipal);
                    break;
                case "indexer_index":
                    new DaoIndexer(CictOracleDataSource.getConnectionBD()).deleteByIndexCompteur(idPrincipal);
                    break;
            }
            
            // Valide les modifications en cours dans la table
            if (tableAssociations.isEditing()) {
                tableAssociations.getCellEditor().stopCellEditing();
            }

            // Ajout des nouvelles relations
            DefaultTableModel model = (DefaultTableModel) tableAssociations.getModel();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date attendu
            for (int i = 0; i < model.getRowCount(); i++) {
                
                String idAssocie = model.getValueAt(i, 0).toString();

                switch (association.toLowerCase()) {
                case "correspondre_locataire":
                    new DaoCorrespondre(CictOracleDataSource.getConnectionBD())
                            .create(new Correspondre(idPrincipal, Integer.parseInt(idAssocie)));
                    break;
                case "colocataire":
                    new DaoColocataire(CictOracleDataSource.getConnectionBD())
                            .create(new Colocataire(idPrincipal, idAssocie));
                    break;
                case "correspondre_contratdelocation":
                    new DaoCorrespondre(CictOracleDataSource.getConnectionBD())
                            .create(new Correspondre(idAssocie, Integer.parseInt(idPrincipal)));
                    break;
                case "associer_louable":{
                    // Gestion des colonnes multiples pour Associer
                    String dateReleveStr = model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : null;
                    Date dateReleve = dateReleveStr != null ? dateFormat.parse(dateReleveStr) : null;

                    String prixAbonnementStr = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "0";
                    double prixAbonnement = Double.parseDouble(prixAbonnementStr);

                    String dateRegularisationStr = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : null;
                    Date dateRegularisation = dateRegularisationStr != null ? dateFormat.parse(dateRegularisationStr) : null;

                    new DaoAssocier(CictOracleDataSource.getConnectionBD())
                            .create(new Associer(Integer.parseInt(idPrincipal), Integer.parseInt(idAssocie),
                                    dateReleve, prixAbonnement, dateRegularisation));
                    break;}
                case "apparaitre_charge":
                    new DaoApparaitre(CictOracleDataSource.getConnectionBD())
                            .create(new Apparaitre(Integer.parseInt(idPrincipal), Integer.parseInt(idAssocie)));
                    break;
                    
                case "indexer_immeuble": {
                    // Gestion des colonnes multiples pour Indexer
                    String dateReleveStr = model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : null;
                    Date dateReleve = dateReleveStr != null ? dateFormat.parse(dateReleveStr) : null;

                    String prixAbonnementStr = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "0";
                    double prixAbonnement = Double.parseDouble(prixAbonnementStr);

                    String dateRegularisationStr = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : null;
                    Date dateRegularisation = dateRegularisationStr != null ? dateFormat.parse(dateRegularisationStr) : null;

                    // Crée une nouvelle indexation avec les bonnes valeurs
                    new DaoIndexer(CictOracleDataSource.getConnectionBD())
                            .create(new Indexer(Integer.parseInt(idAssocie), Integer.parseInt(idPrincipal),
                                    dateReleve, prixAbonnement, dateRegularisation));
                    break;}
                
                case "associer_index":{
                    // Gestion des colonnes multiples pour Associer
                    String dateReleveStr = model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : null;
                    Date dateReleve = dateReleveStr != null ? dateFormat.parse(dateReleveStr) : null;

                    String prixAbonnementStr = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "0";
                    double prixAbonnement = Double.parseDouble(prixAbonnementStr);

                    String dateRegularisationStr = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : null;
                    Date dateRegularisation = dateRegularisationStr != null ? dateFormat.parse(dateRegularisationStr) : null;

                    new DaoAssocier(CictOracleDataSource.getConnectionBD())
                            .create(new Associer(Integer.parseInt(idAssocie), Integer.parseInt(idPrincipal),
                                    dateReleve, prixAbonnement, dateRegularisation));
                    break;}
                
                case "apparaitre_index":
                	new DaoApparaitre(CictOracleDataSource.getConnectionBD())
                    		.create(new Apparaitre(Integer.parseInt(idAssocie), Integer.parseInt(idPrincipal)));
                	break;
                case "indexer_index":{
                    // Gestion des colonnes multiples pour Indexer
                    String dateReleveStr = model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : null;
                    Date dateReleve = dateReleveStr != null ? dateFormat.parse(dateReleveStr) : null;

                    String prixAbonnementStr = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "0";
                    double prixAbonnement = Double.parseDouble(prixAbonnementStr);

                    String dateRegularisationStr = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : null;
                    Date dateRegularisation = dateRegularisationStr != null ? dateFormat.parse(dateRegularisationStr) : null;

                    // Crée une nouvelle indexation avec les bonnes valeurs
                    new DaoIndexer(CictOracleDataSource.getConnectionBD())
                            .create(new Indexer(Integer.parseInt(idPrincipal), Integer.parseInt(idAssocie),
                                    dateReleve, prixAbonnement, dateRegularisation));
                    break;}
            }
        }

            JOptionPane.showMessageDialog(null, "Mise à jour des associations réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour des associations : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du parsing des dates : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    ////////////////////////////////
    // Bouton supprimer une ligne //
    ////////////////////////////////
    
	public void supprimerElement() {
		try {
			JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
			int ligneSelectionnee = tableListeElements.getSelectedRow();
			
			if (ligneSelectionnee >= 0) {
				// Récupérer l'ID de l'élément à partir de la première colonne
				String idElement = tableListeElements.getValueAt(ligneSelectionnee, 0).toString();
				
				// Trouver l'élément correspondant via le DAO
				T elementASupprimer = dao.findById(idElement);
				
				if (elementASupprimer != null) {
				
					// Supprimer l'élément de la base via le DAO
					dao.delete(elementASupprimer);
					
					// Supprimer la ligne de la table graphique
					DefaultTableModel tableModel = (DefaultTableModel) tableListeElements.getModel();
					tableModel.removeRow(ligneSelectionnee);
					
					// Réinitialiser les détails affichés à droite
					JPanel panelAttributs = fenetreAffichageDonnees.getPanelAttributs();
					panelAttributs.removeAll();
					panelAttributs.revalidate();
					panelAttributs.repaint();
				
				} else {
					// L'élément n'existe pas ou a déjà été supprimé
					javax.swing.JOptionPane.showMessageDialog(
					fenetreAffichageDonnees, 
					"L'élément sélectionné n'existe pas.", 
					"Erreur", 
					javax.swing.JOptionPane.WARNING_MESSAGE
					);
				}
			} else {
				// Aucun élément sélectionné
				javax.swing.JOptionPane.showMessageDialog(
				fenetreAffichageDonnees, 
				"Veuillez sélectionner un élément à supprimer.", 
				"Erreur", 
				javax.swing.JOptionPane.WARNING_MESSAGE
				);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(
				fenetreAffichageDonnees, 
				"Une erreur est survenue lors de la suppression.", 
				"Erreur", 
			javax.swing.JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
	////////////////////////
	// Bouton ajouter (+) //
	////////////////////////
	
	public void ajouterElement() {
	    try {
	        // Récupère les champs de la classe de l'objet
	        Field[] champs = elementSelectionne.getClass().getDeclaredFields();
	        Object[] valeursParametres = new Object[champs.length];
	        Class<?>[] typesParametres = new Class<?>[champs.length];

	        // Remplit les valeurs et les types pour le constructeur
	        for (int i = 0; i < champs.length; i++) {
	            champs[i].setAccessible(true);
	            Component composant = composantsAttributs.get(champs[i].getName());

	            if (composant instanceof JTextField) {
	                String valeurSaisie = ((JTextField) composant).getText();
	                Class<?> typeChamp = champs[i].getType();

	                // Gérer les dates spécifiquement
	                if (typeChamp == java.util.Date.class || typeChamp == java.sql.Date.class) {
	                    if (valeurSaisie == null || valeurSaisie.isEmpty()) {
	                        valeursParametres[i] = null; // Si la date est vide, on met null
	                    } else {
	                        try {
	                            // Conversion de la chaîne en Date
	                            java.util.Date utilDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(valeurSaisie);
	                            if (typeChamp == java.sql.Date.class) {
	                                valeursParametres[i] = new java.sql.Date(utilDate.getTime());
	                            } else {
	                                valeursParametres[i] = utilDate;
	                            }
	                        } catch (java.text.ParseException e) {
	                            throw new IllegalArgumentException("Format de date invalide pour le champ " + champs[i].getName() + ". Attendu : yyyy-MM-dd");
	                        }
	                    }
	                } else if (estClasseMetier(typeChamp)) {
	                    // Si le champ est une classe métier, récupérer l'objet via le DAO
	                    Object objetMetier = recupererObjetMetier(typeChamp, valeurSaisie);
	                    if (objetMetier == null) {
	                        throw new IllegalArgumentException(typeChamp.getSimpleName() + " avec ID " + valeurSaisie + " introuvable.");
	                    }
	                    valeursParametres[i] = objetMetier;
	                } else {
	                    // Conversion standard pour les autres types
	                    valeursParametres[i] = casterValeur(valeurSaisie, typeChamp);
	                }

	                typesParametres[i] = typeChamp; // Type du champ
	            }
	        }

	        // Instancie l'objet avec le constructeur à paramètres
	        @SuppressWarnings("unchecked")
	        T nouvelElement = (T) elementSelectionne.getClass()
	                                .getDeclaredConstructor(typesParametres)
	                                .newInstance(valeursParametres);

	        // Insère l'objet dans la base via le DAO
	        dao.create(nouvelElement);

	        // Mise à jour de l'interface graphique
	        DefaultTableModel tableModel = (DefaultTableModel) fenetreAffichageDonnees.getTableListeElements().getModel();
	        Object[] ligne = new Object[champs.length];
	        for (int i = 0; i < champs.length; i++) {
	            champs[i].setAccessible(true);
	            Object valeur = champs[i].get(nouvelElement);
	            ligne[i] = valeur != null ? valeur.toString() : "";
	        }
	        tableModel.addRow(ligne);

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(fenetreAffichageDonnees, "Erreur lors de l'ajout de l'élément : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	    }
	}


	private boolean estClasseMetier(Class<?> type) {
	    // Liste des classes métier à vérifier
	    return type == Louable.class || type == Diagnostic.class || type == Immeuble.class || type == Locataire.class || type == Entreprise.class
	        || type == Assureur.class || type == Charge.class || type == Facture.class || type == ContratDeLocation.class;
	}

	private Object recupererObjetMetier(Class<?> type, String id) throws SQLException {
	    if (type == Louable.class) {
	        DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
	        return daoLouable.findById(id);
	    } else if (type == Diagnostic.class) {
	        DaoDiagnostic daoDiagnostic = new DaoDiagnostic(CictOracleDataSource.getConnectionBD());
	        return daoDiagnostic.findById(id);
	    } else if (type == Immeuble.class) {
	        DaoImmeuble daoImmeuble = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
	        return daoImmeuble.findById(id);
	    } else if (type == Locataire.class) {
	        DaoLocataire daoLocataire = new DaoLocataire(CictOracleDataSource.getConnectionBD());
	        return daoLocataire.findById(id);
	    } else if (type == Assureur.class) {
	        DaoAssureur daoAssureur = new DaoAssureur(CictOracleDataSource.getConnectionBD());
	        return daoAssureur.findById(id);
	    } else if (type == Charge.class) {
	        DaoCharge daoCharge = new DaoCharge(CictOracleDataSource.getConnectionBD());
	        return daoCharge.findById(id);
	    } else if (type == Facture.class) {
	        DaoFacture daoFacture = new DaoFacture(CictOracleDataSource.getConnectionBD());
	        return daoFacture.findById(id);
	    } else if (type == Entreprise.class) {
	        DaoEntreprise daoEntreprise = new DaoEntreprise(CictOracleDataSource.getConnectionBD());
	        return daoEntreprise.findById(id);
	    } else if (type == ContratDeLocation.class) {
	        DaoContratDeLocation daoContratDeLocation = new DaoContratDeLocation(CictOracleDataSource.getConnectionBD());
	        return daoContratDeLocation.findById(id);
	    }
	    return null;
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