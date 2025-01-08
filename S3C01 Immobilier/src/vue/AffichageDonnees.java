package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;

public class AffichageDonnees extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton btnNewButton;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel panel_1;
    private JPanel panel_2;
    private JLabel lblNewLabel;
    private JPanel panel_3;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JButton btnNewButton_3;

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
        
        panel = new JPanel();
        leftPanel.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 0, 0));
        
        JComboBox<TablesBD> tableSelector = new JComboBox<>(TablesBD.values());
        panel.add(tableSelector);
        
        btnNewButton = new JButton("+");
        panel.add(btnNewButton);
        
        scrollPane = new JScrollPane();
        leftPanel.add(scrollPane, BorderLayout.SOUTH);
        
        table = new JTable();
        scrollPane.setViewportView(table);

        // Panneau principal pour l'affichage des détails
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        lblNewLabel = new JLabel("\"Object selectionné\" ID n°\"id\"");
        panel_1.add(lblNewLabel, BorderLayout.WEST);
        
        panel_3 = new JPanel();
        panel_1.add(panel_3, BorderLayout.EAST);
        
        btnNewButton_1 = new JButton("Creer Document");
        panel_3.add(btnNewButton_1);
        
        btnNewButton_2 = new JButton("Modifier");
        panel_3.add(btnNewButton_2);
        
        btnNewButton_3 = new JButton("Supprimer");
        panel_3.add(btnNewButton_3);
        
        panel_2 = new JPanel();
        panel_2.setBackground(new Color(255, 255, 255));
        mainPanel.add(panel_2, BorderLayout.CENTER);
    }
}
