package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;

import controleur.GestionSelecteur;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

public class AffichageDonnees extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JTable tableListeElements;
    private JScrollPane panelAttributs;

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
        
        tableListeElements = new JTable();
        tableListeElements.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Num", "El\u00E9ment"
        	}
        ));
        tableListeElements.getColumnModel().getColumn(0).setResizable(false);
        tableListeElements.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableListeElements.getColumnModel().getColumn(0).setMinWidth(20);
        tableListeElements.getColumnModel().getColumn(1).setMinWidth(30);
        leftPanel.add(tableListeElements, BorderLayout.CENTER);

        // Panneau principal pour l'affichage des détails
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblNewLabel = new JLabel("\"Object selectionné\" ID n°\"id\"");
        panel_1.add(lblNewLabel);
        
        JPanel panel_2 = new JPanel();
        panel_1.add(panel_2);
        
        JButton bouttonMAJ = new JButton("Mettre à jour");
        bouttonMAJ.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        panel_2.add(bouttonMAJ);
        
        JButton bouttonSupprimer = new JButton("Supprimer");
        panel_2.add(bouttonSupprimer);
        
        JButton bouttonArchiver = new JButton("Archiver");
        panel_2.add(bouttonArchiver);
        
        JButton BouttonDocument = new JButton("Creer le document");
        panel_2.add(BouttonDocument);
        
        panelAttributs = new JScrollPane();
        mainPanel.add(panelAttributs, BorderLayout.CENTER);
        
        //Ajout des controleurs
        tableSelector.addActionListener(new GestionSelecteur(this));
     
        
    }

	public JScrollPane getPanelAttributs() {
		return panelAttributs;
	}

	public void setPanelAttributs(JScrollPane panelAttributs) {
		this.panelAttributs = panelAttributs;
	}

}
