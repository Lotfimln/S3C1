package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionSelecteur;

public class AffichageDonnees extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable tableListeElements;
    private JScrollPane scrollPaneListeElements;

    public AffichageDonnees(FenetrePrincipale fenetrePrincipale) {
        this.setTitle("Gestion Immobilière");
        this.setBounds(100, 100, 1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        // Panneau gauche pour la sélection des tables et des entités
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        this.getContentPane().add(leftPanel, BorderLayout.WEST);

        JPanel panel = new JPanel();
        leftPanel.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        JComboBox<ElementsSelectionnables> tableSelector = new JComboBox<>(ElementsSelectionnables.values());
        panel.add(tableSelector);

        JButton btnNewButton = new JButton("+");
        panel.add(btnNewButton);

        JCheckBox checkboxArchive = new JCheckBox("Eléments archivés");
        panel.add(checkboxArchive);

        // Initialisation du tableau
        tableListeElements = new JTable();
        tableListeElements.setModel(new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"ID", "Attribut"}
        ));
        tableListeElements.setFillsViewportHeight(true);

        // Ajout du tableau dans un JScrollPane
        scrollPaneListeElements = new JScrollPane(tableListeElements);
        leftPanel.add(scrollPaneListeElements, BorderLayout.CENTER);

        // Panneau principal pour l'affichage des détails
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel("\"Object sélectionné\" ID n°\"id\"");
        panel_1.add(lblNewLabel);

        JPanel panel_2 = new JPanel();
        panel_1.add(panel_2);

        JButton bouttonMAJ = new JButton("Mettre à jour");
        panel_2.add(bouttonMAJ);

        JButton bouttonSupprimer = new JButton("Supprimer");
        panel_2.add(bouttonSupprimer);

        JButton bouttonArchiver = new JButton("Archiver");
        panel_2.add(bouttonArchiver);

        JButton BouttonDocument = new JButton("Créer le document");
        panel_2.add(BouttonDocument);

        JScrollPane panelAttributs = new JScrollPane();
        mainPanel.add(panelAttributs, BorderLayout.CENTER);

        // Ajout des contrôleurs
        tableSelector.addActionListener(new GestionSelecteur(this));
    }

    // Getter pour accéder à la JTable
    public JTable getTableListeElements() {
        return tableListeElements;
    }

    // Setter pour mettre à jour le tableau
    public void setTableListeElementsModel(DefaultTableModel model) {
        tableListeElements.setModel(model);
    }
}
