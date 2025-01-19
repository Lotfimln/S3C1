package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.xwpf.usermodel.*;
import modele.Diagnostic;
import modele.Louable;
import modele.dao.CictOracleDataSource;
import modele.dao.DaoDiagnostic;
import modele.dao.DaoLouable;

public class RapportDiagnosticsObligatoires {

    public static void main(String[] args) {
        int idImmeuble = Integer.parseInt(args[0]);
        creerRapport(idImmeuble);
    }

    public static void creerRapport(int idImmeuble) {
        try {
            // DAO pour accéder aux données
            DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
            DaoDiagnostic daoDiagnostic = new DaoDiagnostic(CictOracleDataSource.getConnectionBD());

            // Récupération des logements pour l'immeuble
            List<Louable> louables = daoLouable.findByImmeuble(String.valueOf(idImmeuble));

            // Création du document Word
            OutputStream fileOut = new FileOutputStream("src/rapport/RapportDiagnosticsObligatoires.docx");
            InputStream modele = new FileInputStream("src/rapport/vide.docx");
            XWPFDocument document = new XWPFDocument(modele);

            // Titre
            XWPFParagraph titre = document.createParagraph();
            XWPFRun runTitre = titre.createRun();
            runTitre.setText("Rapport des Diagnostics Obligatoires pour l'Immeuble ID: " + idImmeuble);
            runTitre.setBold(true);
            runTitre.setFontSize(16);

            // Tableau des diagnostics
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ID Logement");
            headerRow.addNewTableCell().setText("Type Diagnostic");
            headerRow.addNewTableCell().setText("Date Diagnostic");
            headerRow.addNewTableCell().setText("Statut");

            for (Louable louable : louables) {
                // Récupération des diagnostics pour le logement
                List<Diagnostic> diagnostics = daoDiagnostic.findByLouable(String.valueOf(louable.getIdLouable()));

                for (Diagnostic diagnostic : diagnostics) {
                    // Vérifier si le diagnostic est expiré
                    boolean isExpired = isDiagnosticExpired(diagnostic.getDateDiagnostic());

                    // Ajouter une ligne au tableau
                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(String.valueOf(louable.getIdLouable()));
                    row.getCell(1).setText(diagnostic.getTypeDiagnostic());
                    row.getCell(2).setText(diagnostic.getDateDiagnostic().toString());
                    row.getCell(3).setText(isExpired ? "Expiré" : "Valide");
                }
            }

            // Enregistrement
            document.write(fileOut);
            fileOut.close();
            modele.close();
            document.close();

            System.out.println("Le rapport des diagnostics obligatoires a été généré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Vérifie si un diagnostic est expiré.
     
    private static boolean isDiagnosticExpired(java.util.Date dateDiagnostic) {
        if (dateDiagnostic == null) {
            throw new IllegalArgumentException("La date du diagnostic ne peut pas être nulle.");
        }

        // Obtenir la date actuelle moins 10 ans en timestamp
        long thresholdTime = java.sql.Date.valueOf(LocalDate.now().minusYears(10)).getTime();

        // Comparer les timestamps
        return dateDiagnostic.getTime() < thresholdTime;
    }


}

