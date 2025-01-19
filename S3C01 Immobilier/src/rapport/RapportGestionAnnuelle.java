package rapport;
import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class RapportGestionAnnuelle {

    public static void genererRapportGestionAnnuelle(int proprietaireId, String cheminFichier) {
        try (XWPFDocument document = new XWPFDocument()) {
            // Récupération des données via les services ou DAO
            BigDecimal totalLoyers = ImmeubleDAO.getTotalLoyersImmeuble(proprietaireId);
            List<Travaux> travaux = TravauxDAO.getTravauxParImmeuble(proprietaireId);
            BigDecimal totalCharges = ImmeubleDAO.getTotalChargesImmeuble(proprietaireId);

            // Ajout du titre
            ajouterTitre(document, "Rapport Annuel de Gestion");

            // Section des revenus
            ajouterSectionRevenus(document, totalLoyers);

            // Section des travaux
            ajouterSectionTravaux(document, travaux);

            // Section des charges
            ajouterSectionCharges(document, totalCharges);

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

    private static void ajouterSectionRevenus(XWPFDocument document, BigDecimal totalLoyers) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().setText("1. Revenus locatifs");
        XWPFTable table = document.createTable();
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText("Total des loyers encaissés");
        row.addNewTableCell().setText(totalLoyers + " €");
    }

    private static void ajouterSectionTravaux(XWPFDocument document, List<Travaux> travaux) {
        document.createParagraph().createRun().setText("2. Travaux réalisés");
        XWPFTable table = document.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Date");
        header.addNewTableCell().setText("Nature des travaux");
        header.addNewTableCell().setText("Montant (€)");
        for (Travaux t : travaux) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(t.getDate().toString());
            row.getCell(1).setText(t.getNature());
            row.getCell(2).setText(t.getMontant().toString());
        }
    }

    private static void ajouterSectionCharges(XWPFDocument document, BigDecimal totalCharges) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().setText("3. Charges totales");
        XWPFTable table = document.createTable();
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText("Total des charges");
        row.addNewTableCell().setText(totalCharges + " €");
    }
}
