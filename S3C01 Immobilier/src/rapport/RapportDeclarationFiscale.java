package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.poi.xwpf.usermodel.*;
import modele.dao.CictOracleDataSource;
import modele.dao.DaoImmeuble;
import modele.dao.DaoLouable;

public class RapportDeclarationFiscale {

    public static void main(String[] args) {
        int idImmeuble = Integer.parseInt(args[0]);
        creerRapport(idImmeuble);
    }

    public static void creerRapport(int idImmeuble) {
        try {
            DaoImmeuble daoImmeuble = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
            DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
            Properties proprietaire = chargerProprietaire();

            double totalRevenus = daoImmeuble.totalLoyersPayes(idImmeuble);
            double totalTravaux = daoImmeuble.totalDesTravauxImmeuble(idImmeuble);
            double totalCharges = daoImmeuble.totalChargesImmeuble(idImmeuble);
            double totalTaxes = daoImmeuble.facturesImpayeParImmeuble(idImmeuble);

            OutputStream fileOut = new FileOutputStream("src/rapport/RapportDeclarationFiscale.docx");
            InputStream modele = new FileInputStream("src/rapport/vide.docx");
            XWPFDocument document = new XWPFDocument(modele);

            // Titre
            XWPFParagraph titre = document.createParagraph();
            XWPFRun runTitre = titre.createRun();
            runTitre.setText("Rapport de Déclaration Fiscale pour l'Immeuble ID: " + idImmeuble);
            runTitre.setBold(true);
            runTitre.setFontSize(16);

            // Informations du propriétaire
            XWPFParagraph proprietaireParagraphe = document.createParagraph();
            XWPFRun proprietaireRun = proprietaireParagraphe.createRun();
            proprietaireRun.setText("Propriétaire : " + proprietaire.getProperty("prenom") + " " + proprietaire.getProperty("nom"));
            proprietaireRun.setText("\nContact : " + proprietaire.getProperty("mail") + ", " + proprietaire.getProperty("telephone"));

            // Contenu
            XWPFParagraph contenu = document.createParagraph();
            XWPFRun runContenu = contenu.createRun();
            runContenu.setText("Revenus locatifs totaux : " + totalRevenus + " €\n");
            runContenu.setText("Dépenses totales (charges et travaux) : " + (totalCharges + totalTravaux) + " €\n");
            runContenu.setText("Taxes et factures impayées : " + totalTaxes + " €\n");

            // Enregistrement
            document.write(fileOut);
            fileOut.close();
            modele.close();
            document.close();

            System.out.println("Le rapport de déclaration fiscale a été généré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties chargerProprietaire() throws Exception {
        Properties proprietaire = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            proprietaire.load(input);
        }
        return proprietaire;
    }
}
