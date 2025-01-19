package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import controleur.GestionAffichageDonnees;
import controleur.GestionSelecteur;
import modele.dao.Dao;
import rapport.RapportAnnuelImmeuble;

import java.awt.GridBagLayout;

public class AffichageDonnees extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private JTable tableListeElements; // Table de gauche affichant les éléments d'une entité
    private JPanel panelAttributs; // Panneau à droite pour afficher les attributs d'un élément
    private JScrollPane scrollPaneListeElements;
    private JCheckBox caseArchive;
    private JButton btnADI;
    private JButton btnSTC;
    private JButton btnDF;
    private JButton btnDO;

    private GestionAffichageDonnees gestionAffichageDonnees;

    public AffichageDonnees(FenetrePrincipale fenetrePrincipale) {
        this.setTitle("Gestion Immobilière");
        this.setBounds(100, 100, 1200, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        // --- Panneau gauche pour la sélection des tables et des entités ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        this.getContentPane().add(leftPanel, BorderLayout.WEST);

        // Sélecteur pour choisir l'entité
        JPanel panelSelecteur = new JPanel();
        leftPanel.add(panelSelecteur, BorderLayout.NORTH);
        panelSelecteur.setLayout(new GridLayout(0, 2, 0, 0));

        JComboBox<ElementsSelectionnables> tableSelector = new JComboBox<>(ElementsSelectionnables.values());
        tableSelector.setSelectedIndex(-1);
        panelSelecteur.add(tableSelector);

        JButton btnAjouterElement = new JButton("+");
        panelSelecteur.add(btnAjouterElement);
        btnAjouterElement.addActionListener(e -> gestionAffichageDonnees.ajouterElement());


        caseArchive = new JCheckBox("Archivés");
        panelSelecteur.add(caseArchive);

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

        // --- Panneau principal pour l'affichage des détails ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Panneau supérieur pour les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(panelBoutons, BorderLayout.NORTH);
        panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton bouttonMAJ = new JButton("Mettre à jour");
        panelBoutons.add(bouttonMAJ);
        bouttonMAJ.addActionListener(e -> gestionAffichageDonnees.enregistrerModifications());

        JButton bouttonSupprimer = new JButton("Supprimer");
        panelBoutons.add(bouttonSupprimer);
        bouttonSupprimer.addActionListener(e -> gestionAffichageDonnees.supprimerElement());

        btnADI = new JButton("Annuel de l'immeuble");
        panelBoutons.add(btnADI);
        btnADI.addActionListener(e -> gestionAffichageDonnees.genererRapportAnnuelImmeuble());
        btnADI.setVisible(false);
        
        btnSTC = new JButton("Solde tout compte");
        panelBoutons.add(btnSTC);
        btnSTC.addActionListener(e -> gestionAffichageDonnees.genererRapportSoldeToutCompte());
        btnSTC.setVisible(false);
        
        btnDF = new JButton("Declaration Fiscale");
        panelBoutons.add(btnDF);
        btnDF.addActionListener(e -> gestionAffichageDonnees.genererRapportDeclarationFiscale());
        btnDF.setVisible(false);
        
        btnDO = new JButton("Diagnostics obligatoires");
        panelBoutons.add(btnDO);
        btnDO.addActionListener(e -> gestionAffichageDonnees.genererRapportDiagnosticsObligatoires());
        btnDO.setVisible(false);
        
        JButton btnLI = new JButton("Loyers impayés");
        panelBoutons.add(btnLI);
        btnLI.addActionListener(e -> gestionAffichageDonnees.genererRapportLoyersImpayes());

        // Panneau pour afficher les attributs de l'élément sélectionné
        panelAttributs = new JPanel();
        JScrollPane scrollPaneAttributs = new JScrollPane(panelAttributs);
        panelAttributs.setLayout(new GridLayout(0, 2, 0, 0));
        mainPanel.add(scrollPaneAttributs, BorderLayout.CENTER);

        // --- Ajout des contrôleurs ---
        GestionSelecteur gestionSelecteur = new GestionSelecteur(this);
        tableSelector.addActionListener(gestionSelecteur);

        // Gestion dynamique de l'affichage des attributs
        gestionAffichageDonnees = new GestionAffichageDonnees(this, null);

        // Boutons liés à la gestion des données
        bouttonMAJ.addActionListener(e -> gestionAffichageDonnees.enregistrerModifications());
        bouttonSupprimer.addActionListener(e -> {
            // Suppression de l'élément (à implémenter si besoin)
        });
        btnADI.addActionListener(e -> {
            // Génération de document (à implémenter si besoin)
        });
    }

    // --- Getters et Setters pour interagir avec les contrôleurs ---
    public JTable getTableListeElements() {
        return tableListeElements;
    }

    public JPanel getPanelAttributs() {
        return panelAttributs;
    }
    
    public void setDao(Dao<?> dao) {
        if (gestionAffichageDonnees != null) {
            gestionAffichageDonnees.setDao(dao); // Définit le DAO dans GestionAffichageDonnees
        }
    }
    
	public boolean isArchiveCoche() {
	    return caseArchive.isSelected(); // 'caseArchive' correspond au JCheckBox
	}

	public JButton getBtnADI() {
		return btnADI;
	}

	public void setBtnADI(JButton btnADI) {
		this.btnADI = btnADI;
	}

	public JButton getBtnSTC() {
		return btnSTC;
	}

	public void setBtnSTC(JButton btnSTC) {
		this.btnSTC = btnSTC;
	}

	public JButton getBtnDF() {
		return btnDF;
	}

	public void setBtnDF(JButton btnDF) {
		this.btnDF = btnDF;
	}

	public JButton getBtnDO() {
		return btnDO;
	}

	public void setBtnDO(JButton btnDO) {
		this.btnDO = btnDO;
	}

	

}
