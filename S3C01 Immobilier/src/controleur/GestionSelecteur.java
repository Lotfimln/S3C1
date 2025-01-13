package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modele.*;
import modele.dao.*;
import vue.AffichageDonnees;
import vue.ElementsSelectionnables;

public class GestionSelecteur implements ActionListener {

    private AffichageDonnees fenetreAffichageDonnees;

    public GestionSelecteur(AffichageDonnees fenetreAffichageDonnees) {
        this.fenetreAffichageDonnees = fenetreAffichageDonnees;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
            Object selectedItem = comboBox.getSelectedItem();
            ElementsSelectionnables selection = (ElementsSelectionnables) selectedItem;

            if (selection == null) {
                return;
            }

            try {
                switch (selection) {
                case LOCATAIRE:
                	DaoLocataire daoLocataire = new DaoLocataire(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoLocataire, Locataire.class);
                    fenetreAffichageDonnees.setDao(daoLocataire);
                    break;
                case INDEX_COMPTEUR:
                	DaoIndexCompteur daoIndexCompteur = new DaoIndexCompteur(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoIndexCompteur, IndexCompteur.class);
                    fenetreAffichageDonnees.setDao(daoIndexCompteur);
                    break;
                case ASSUREUR:
                    afficherDonnees(new DaoAssureur(CictOracleDataSource.getConnectionBD()), Assureur.class);
                    break;
                case ENTREPRISE:
                    afficherDonnees(new DaoEntreprise(CictOracleDataSource.getConnectionBD()), Entreprise.class);
                    break;
                case IMMEUBLE:
                    afficherDonnees(new DaoImmeuble(CictOracleDataSource.getConnectionBD()), Immeuble.class);
                    break;
                case TAXE:
                    afficherDonnees(new DaoTaxe(CictOracleDataSource.getConnectionBD()), Taxe.class);
                    break;
                case FACTURE:
                    afficherDonnees(new DaoFacture(CictOracleDataSource.getConnectionBD()), Facture.class);
                    break;
                case DIAGNOSTIC:
                    afficherDonnees(new DaoDiagnostic(CictOracleDataSource.getConnectionBD()), Diagnostic.class);
                    break;
                case CONTRAT_DE_LOCATION:
                    afficherDonnees(new DaoContratDeLocation(CictOracleDataSource.getConnectionBD()), ContratDeLocation.class);
                    break;
                case CHARGE:
                    afficherDonnees(new DaoCharge(CictOracleDataSource.getConnectionBD()), Charge.class);
                    break;
                case QUITTANCES:
                    afficherDonnees(new DaoQuittances(CictOracleDataSource.getConnectionBD()), Quittances.class);
                    break;
                case LOUABLE:
                	DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoLouable, Louable.class);
                    fenetreAffichageDonnees.setDao(daoLouable);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(fenetreAffichageDonnees,
                    "1. Erreur lors de l'accès à la base de données : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private <T> void afficherDonnees(Dao<T> dao, Class<T> type) throws SQLException {
        // Récupérer toutes les données du type spécifié depuis le DAO
        List<T> donnees = dao.findAll();

        // Initialisation des colonnes et des données
        String[] colonnes;
        Object[][] data;

        // Déterminer l'affichage en fonction du type d'objet
        switch (type.getSimpleName().toUpperCase()) {
        case "LOCATAIRE":
            colonnes = new String[]{"ID Locataire", "Nom"};
            data = donnees.stream()
                .map(obj -> (Locataire) obj)
                .map(locataire -> new Object[]{
                    locataire.getIdLocataire(),
                    locataire.getNom()
                })
                .toArray(Object[][]::new);
            break;

        case "INDEXCOMPTEUR":
            colonnes = new String[]{"ID Compteur", "Type"};
            data = donnees.stream()
                .map(obj -> (IndexCompteur) obj)
                .map(compteur -> new Object[]{
                    compteur.getIdIndexCompteur(),
                    compteur.getTypeCompteur()
                })
                .toArray(Object[][]::new);
            break;

        case "ASSUREUR":
            colonnes = new String[]{"ID Assurance", "Nom"};
            data = donnees.stream()
                .map(obj -> (Assureur) obj)
                .map(assureur -> new Object[]{
                    assureur.getIdAssurance(),
                    assureur.getNom()
                })
                .toArray(Object[][]::new);
            break;

        case "ENTREPRISE":
            colonnes = new String[]{"ID Entreprise", "Nom"};
            data = donnees.stream()
                .map(obj -> (Entreprise) obj)
                .map(entreprise -> new Object[]{
                    entreprise.getIdEntreprise(),
                    entreprise.getNom()
                })
                .toArray(Object[][]::new);
            break;

        case "IMMEUBLE":
            colonnes = new String[]{"ID Immeuble", "Adresse"};
            data = donnees.stream()
                .map(obj -> (Immeuble) obj)
                .map(immeuble -> new Object[]{
                    immeuble.getIdImmeuble(),
                    immeuble.getAdresse()
                })
                .toArray(Object[][]::new);
            break;

        case "TAXE":
            colonnes = new String[]{"ID Taxe", "Montant"};
            data = donnees.stream()
                .map(obj -> (Taxe) obj)
                .map(taxe -> new Object[]{
                    taxe.getIdTaxe(),
                    taxe.getMontantTaxeFoncieres()
                })
                .toArray(Object[][]::new);
            break;

        case "FACTURE":
            colonnes = new String[]{"ID Facture", "Date"};
            data = donnees.stream()
                .map(obj -> (Facture) obj)
                .map(facture -> new Object[]{
                    facture.getIdFacture(),
                    facture.getDateFacture()
                })
                .toArray(Object[][]::new);
            break;

        case "DIAGNOSTIC":
            colonnes = new String[]{"ID Diagnostic", "Type"};
            data = donnees.stream()
                .map(obj -> (Diagnostic) obj)
                .map(diagnostic -> new Object[]{
                    diagnostic.getIdDiagnostic(),
                    diagnostic.getTypeDiagnostic()
                })
                .toArray(Object[][]::new);
            break;

        case "CONTRATDELOCATION":
            colonnes = new String[]{"ID Contrat", "Montant Loyer"};
            data = donnees.stream()
                .map(obj -> (ContratDeLocation) obj)
                .map(contrat -> new Object[]{
                    contrat.getIdContratDeLocation(),
                    contrat.getMontantLoyer()
                })
                .toArray(Object[][]::new);
            break;

        case "CHARGE":
            colonnes = new String[]{"ID Charge", "Type"};
            data = donnees.stream()
                .map(obj -> (Charge) obj)
                .map(charge -> new Object[]{
                    charge.getIdCharge(),
                    charge.getTypeCharge()
                })
                .toArray(Object[][]::new);
            break;

        case "QUITTANCES":
            colonnes = new String[]{"ID Quittance", "Montant Loyer"};
            data = donnees.stream()
                .map(obj -> (Quittances) obj)
                .map(quittance -> new Object[]{
                    quittance.getIdQuittances(),
                    quittance.getMontantLoyer()
                })
                .toArray(Object[][]::new);
            break;

        case "LOUABLE":
            colonnes = new String[]{"ID Louable", "Type"};
            data = donnees.stream()
                .map(obj -> (Louable) obj)
                .map(louable -> new Object[]{
                		louable.getIdLouable(),
                		louable.getTypeLouable()
                })
                .toArray(Object[][]::new);
            break;

        default:
            throw new IllegalArgumentException("Type non pris en charge : " + type.getSimpleName());
    }

        // Met à jour le tableau de la fenêtre avec les données
        JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
        DefaultTableModel model = new DefaultTableModel(data, colonnes) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rend les cellules non éditables
            }
        };
        tableListeElements.setModel(model); // Applique le nouveau modèle
    }

}
