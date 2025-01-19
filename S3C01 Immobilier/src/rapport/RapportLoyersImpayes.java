package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.poi.xwpf.usermodel.*;
import modele.dao.CictOracleDataSource;
import modele.dao.DaoLouable;

public class RapportLoyersImpayes {

    public static void main(String[] args) {
        creerRapport();
    }

    public static void creerRapport() {
        try {
            // DAO pour récupérer les données
            DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
            List<Object[]> loyersImpayes = daoLouable.detecterLoyersImpayes();

            // Création du document Word
            OutputStream fileOut = new FileOutputStream("src/rapport/RapportLoyersImpayes.docx");
            InputStream modele = new FileInputStream("src/rapport/vide.docx");
            XWPFDocument document = new XWPFDocument(modele);

            // Titre
            XWPFParagraph titre = document.createParagraph();
            XWPFRun runTitre = titre.createRun();
            runTitre.setText("Rapport des Loyers Impayés");
            runTitre.setBold(true);
            runTitre.setFontSize(16);

            // Tableau
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ID Logement");
            headerRow.addNewTableCell().setText("Adresse");
            headerRow.addNewTableCell().setText("Date Impayée");
            headerRow.addNewTableCell().setText("Statut Paiement");

            // Ajouter les données au tableau
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Object[] row : loyersImpayes) {
                XWPFTableRow tableRow = table.createRow();
                tableRow.getCell(0).setText(String.valueOf(row[0])); // ID Logement
                tableRow.getCell(1).setText(String.valueOf(row[1])); // Adresse
                tableRow.getCell(2).setText(dateFormat.format(row[2])); // Date Impayée
                tableRow.getCell(3).setText(String.valueOf(row[3])); // Statut Paiement
            }

            // Enregistrement
            document.write(fileOut);
            fileOut.close();
            modele.close();
            document.close();

            System.out.println("Le rapport des loyers impayés a été généré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
