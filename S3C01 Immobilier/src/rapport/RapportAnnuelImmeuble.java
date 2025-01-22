package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import org.apache.poi.xwpf.usermodel.*;
import modele.Louable;
import modele.dao.CictOracleDataSource;
import modele.dao.DaoImmeuble;
import modele.dao.DaoLouable;

public class RapportAnnuelImmeuble {

    public static void main(String[] args) {
        int idImmeuble = Integer.parseInt(args[0]);
        creerRapport(idImmeuble);
    }

    public static void creerRapport(int idImmeuble) {
        try {
            DaoImmeuble daoImmeuble = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
            DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
            Properties proprietaire = chargerProprietaire();

            List<Louable> louables = daoLouable.findByImmeuble(String.valueOf(idImmeuble));
            double totalChargesImmeuble = daoImmeuble.totalChargesImmeuble(idImmeuble);
            double travauxImmeuble = daoImmeuble.totalDesTravauxImmeuble(idImmeuble);
            double facturesImpayees = daoImmeuble.facturesImpayeParImmeuble(idImmeuble);

            OutputStream fileOut = new FileOutputStream("src/rapport/RapportAnnuelImmeuble.docx");
            InputStream modele = new FileInputStream("src/rapport/vide.docx");
            XWPFDocument document = new XWPFDocument(modele);

            // Titre
            XWPFParagraph titre = document.createParagraph();
            XWPFRun runTitre = titre.createRun();
            runTitre.setText("Rapport Annuel des Charges de l'Immeuble ID: " + idImmeuble);
            runTitre.setBold(true);
            runTitre.setFontSize(16);

            // Détails du propriétaire
            XWPFParagraph proprietaireParagraphe = document.createParagraph();
            XWPFRun proprietaireRun = proprietaireParagraphe.createRun();
            proprietaireRun.setText("Propriétaire : " + proprietaire.getProperty("prenom") + " " + proprietaire.getProperty("nom"));
            proprietaireRun.setText("\nContact : " + proprietaire.getProperty("mail") + ", " + proprietaire.getProperty("telephone"));

            // Tableau
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ID Logement");
            headerRow.addNewTableCell().setText("Total Charges (€)");
            headerRow.addNewTableCell().setText("Ordures Ménagères (€)");
            headerRow.addNewTableCell().setText("Entretien Ménager (€)");

            for (Louable louable : louables) {
                int idLouable = louable.getIdLouable();
                double totalCharges = daoLouable.prixConsoLogement(idLouable);
                double orduresMenageres = daoLouable.totalOrduresMenageres(idLouable);
                double entretienMenager = daoLouable.calculerEntretienMenager(idLouable);

                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(idLouable));
                row.getCell(1).setText(String.format("%.2f", totalCharges));
                row.getCell(2).setText(String.format("%.2f", orduresMenageres));
                row.getCell(3).setText(String.format("%.2f", entretienMenager));
            }

            // Résumé global
            XWPFParagraph resume = document.createParagraph();
            XWPFRun runResume = resume.createRun();
            runResume.setText("\nRésumé global :");
            runResume.setText("\nTotal des charges immeuble : " + totalChargesImmeuble + " €");
            runResume.setText("\nTotal des travaux réalisés : " + travauxImmeuble + " €");
            runResume.setText("\nTotal des factures impayées : " + facturesImpayees + " €");

            // Enregistrement
            document.write(fileOut);
            fileOut.close();
            modele.close();
            document.close();

            System.out.println("Le rapport annuel de l'immeuble a été généré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties chargerProprietaire() throws Exception {
        Properties proprietaire = new Properties();
        try (InputStream input = new FileInputStream("autres/config.properties")) {
            proprietaire.load(input);
        }
        return proprietaire;
    }
}
