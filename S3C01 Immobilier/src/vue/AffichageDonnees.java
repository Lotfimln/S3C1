package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;

import controleur.GestionSelecteur;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AffichageDonnees extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JPanel panelAttributs;
    private JList<String> list;

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
        
        JComboBox<TablesBD> tableSelector = new JComboBox<>(TablesBD.values());
        panel.add(tableSelector);
        
        JButton btnNewButton = new JButton("+");
        panel.add(btnNewButton);
        
        list = new JList<String>();
        leftPanel.add(list, BorderLayout.WEST);

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
        
        JButton btnNewButton_1 = new JButton("Mettre à jour");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        panel_2.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Supprimer");
        panel_2.add(btnNewButton_2);
        
        JPanel panelFonctionnalites = new JPanel();
        panel_1.add(panelFonctionnalites);
        
        panelAttributs = new JPanel();
        panelAttributs.setBackground(new Color(255, 255, 255));
        mainPanel.add(panelAttributs, BorderLayout.CENTER);
        panelAttributs.setLayout(new GridLayout(0, 1, 0, 0));
        
        //Ajout des controleurs
        tableSelector.addActionListener(new GestionSelecteur(this));
     
        
    }

	public JPanel getPanelAttributs() {
		return panelAttributs;
	}

	public void setPanelAttributs(JPanel panelAttributs) {
		this.panelAttributs = panelAttributs;
	}

	public JList<String> getList() {
		return list;
	}

	public void setList(JList<String> list) {
		this.list = list;
	}
}
