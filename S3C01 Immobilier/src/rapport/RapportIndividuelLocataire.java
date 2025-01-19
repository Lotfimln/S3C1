package rapport;

import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class RapportIndividuelLocataire {

    public static void genererRapportIndividuel(String locataireId, String cheminFichier) {
        try (XWPFDocument document = new XWPFDocument()) {
            // Récupération des données
            List<LoyerImpayes> loyersImpayes = LocataireDAO.getLoyersImpayesParLocataire(locataireId);
            BigDecimal sommeImpayes = LocataireDAO.getSommeLoyersImpayes(locataireId);
            BigDecimal soldeDeToutCompte = LouableDAO.getSoldeDeToutCompte(locataireId);

            // Ajout du titre
            ajouterTitre(document, "Rapport Individuel du Locataire");

            // Section des loyers impayés
            ajouterSectionLoyersImpayes(document, loyersImpayes, sommeImpayes);

            // Section du solde de tout compte
            ajouterSectionSoldeDeToutCompte(document, soldeDeToutCompte);

            // Sauvegarde du fichier
            try (FileOutputStream out = new FileOutputStream(cheminFichier)) {
                document.write(out);
                System.out.println("Rapport généré : " + cheminFichier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ajouterTitre(XWPFDocument document, String titre) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(16);
        run.setText(titre);
    }

    private static void ajouterSectionLoyersImpayes(XWPFDocument document, List<LoyerImpayes> loyersImpayes, BigDecimal sommeImpayes) {
        document.createParagraph().createRun().setText("1. Loyers Impayés");
        XWPFTable table = document.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Date");
        header.addNewTableCell().setText("Montant (€)");
        for (LoyerImpayes loyer : loyersImpayes) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(loyer.getDate().toString());
            row.getCell(1).setText(loyer.getMontant().toString());
        }
        document.createParagraph().createRun().setText("Total des loyers impayés : " + sommeImpayes + " €");
    }

    private static void ajouterSectionSoldeDeToutCompte(XWPFDocument document, BigDecimal soldeDeToutCompte) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().setText("2. Solde de Tout Compte");
        XWPFTable table = document.createTable();
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText("Solde final");
        row.addNewTableCell().setText(soldeDeToutCompte + " €");
    }
}
