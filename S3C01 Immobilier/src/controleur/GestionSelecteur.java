package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modele.Immeuble;
import modele.dao.CictOracleDataSource;
import modele.dao.Dao;
import modele.dao.DaoImmeuble;
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
                    case IMMEUBLE:
                    	DaoImmeuble dao = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
                        afficherDonnees(dao, Immeuble.class);
                        break;

                    // Ajoutez d'autres cas pour d'autres types d'entités ici
                    default:
                        JOptionPane.showMessageDialog(fenetreAffichageDonnees, 
                            "Option non prise en charge : " + selection, 
                            "Erreur", 
                            JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(fenetreAffichageDonnees, 
                    "Erreur lors de l'accès à la base de données : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private <T> void afficherDonnees(Dao<T> dao, Class<T> type) throws SQLException {
        // Récupérer toutes les données du type spécifié depuis le DAO
        List<T> donnees = dao.findAll();

        // Affiche les données récupérées dans la console pour vérification
        System.out.println("Données récupérées par findAll() :");
        donnees.forEach(System.out::println);

        // Initialisation des colonnes et des données
        String[] colonnes;
        Object[][] data;

        // Déterminer l'affichage en fonction du type d'objet
        switch (type.getSimpleName().toUpperCase()) {
            case "IMMEUBLE":
                colonnes = new String[]{"ID Immeuble", "Adresse"};
                data = donnees.stream()
                    .map(obj -> (Immeuble) obj)
                    .map(immeuble -> new Object[]{immeuble.getIdImmeuble(), immeuble.getAdresse()})
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
