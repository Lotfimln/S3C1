package controleur;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.dao.Dao;
import vue.AffichageDonnees;

public class GestionAffichageDonnees<T> {

    private AffichageDonnees fenetreAffichageDonnees;
    private Dao<T> dao;  // DAO générique avec type T
    private T elementSelectionne;
    private Map<String, Component> composantsAttributs;

    public GestionAffichageDonnees(AffichageDonnees fenetreAffichageDonnees, Dao<T> dao) {
        this.fenetreAffichageDonnees = fenetreAffichageDonnees;
        this.dao = dao;
        this.composantsAttributs = new HashMap<>();

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
                    String idElement = fenetreAffichageDonnees.getTableListeElements().getValueAt(ligneSelectionnee, 0).toString();
                    afficherDetailsElement(idElement);
                }
            }
        });
    }

    private void afficherDetailsElement(String idElement) {
        try {
            elementSelectionne = dao.findById(idElement);

            JPanel panelAttributs = fenetreAffichageDonnees.getPanelAttributs();
            panelAttributs.removeAll();
            composantsAttributs.clear();

            for (Field champ : elementSelectionne.getClass().getDeclaredFields()) {
                champ.setAccessible(true);

                JLabel label = new JLabel(champ.getName() + " :");
                panelAttributs.add(label);

                Component composant = creerComposantPourAttribut(champ, elementSelectionne);
                panelAttributs.add(composant);

                composantsAttributs.put(champ.getName(), composant);
            }

            panelAttributs.revalidate();
            panelAttributs.repaint();

        } catch (SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private Component creerComposantPourAttribut(Field champ, Object element) throws IllegalAccessException {
        Object valeur = champ.get(element);
        JTextField textField = new JTextField(valeur != null ? valeur.toString() : "");
        return textField;
    }

    public void enregistrerModifications() {
        try {
            for (Field champ : elementSelectionne.getClass().getDeclaredFields()) {
                champ.setAccessible(true);
                Component composant = composantsAttributs.get(champ.getName());

                if (composant instanceof JTextField) {
                    String nouvelleValeur = ((JTextField) composant).getText();
                    Object valeurCast = casterValeur(nouvelleValeur, champ.getType());
                    champ.set(elementSelectionne, valeurCast);
                }
            }

            // Appel correct de la méthode update
            dao.update(elementSelectionne);

        } catch (SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private Object casterValeur(String valeur, Class<?> type) {
        try {
            if (type == int.class || type == Integer.class) {
                return Integer.parseInt(valeur);
            } else if (type == double.class || type == Double.class) {
                return Double.parseDouble(valeur);
            } else if (type == java.util.Date.class || type == java.sql.Date.class) {
                return java.sql.Date.valueOf(valeur);
            }
        } catch (Exception e) {
            return null;
        }
        return valeur;
    }
}
