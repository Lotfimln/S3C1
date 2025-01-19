package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.*;

import modele.dao.CictOracleDataSource;
import modele.dao.DaoLouable;

public class RapportSoldeToutCompte {
	
	public static void main(String[] args) throws Exception {
        int idLouable = Integer.parseInt(args[0]);
        creerRapport(idLouable); // Appelle la méthode de création du rapport
	}

    public static void creerRapport(int idLouable) throws Exception {
        // DAO pour accéder aux données
        DaoLouable dao = new DaoLouable(CictOracleDataSource.getConnectionBD());

        // Récupération des données nécessaires
        double prixConso = dao.prixConsoLogement(idLouable);
        double loyersImpayes = dao.calculerSommeLoyersImpayes(idLouable);
        double chargesRecuperables = dao.calculerChargesRecuperables(idLouable);
        double travauxLogement = dao.totalDesTravauxBien(idLouable);
        double soldeToutCompte = dao.calculerSoldeDeToutCompte(idLouable);

        // Création du document Word
        OutputStream fileOut = new FileOutputStream("src/rapport/RapportSoldeToutCompte.docx");
        InputStream modele = new FileInputStream("src/rapport/vide.docx");
        XWPFDocument document = new XWPFDocument(modele);

        // Titre
        XWPFParagraph titre = document.createParagraph();
        XWPFRun runTitre = titre.createRun();
        runTitre.setText("Rapport de Solde de Tout Compte pour le Logement ID: " + idLouable);
        runTitre.setBold(true);
        runTitre.setFontSize(16);

        // Contenu
        XWPFParagraph contenu = document.createParagraph();
        XWPFRun runContenu = contenu.createRun();
        runContenu.setText("Prix des consommations : " + prixConso + " €\n");
        runContenu.setText("Loyers impayés : " + loyersImpayes + " €\n");
        runContenu.setText("Charges récupérables : " + chargesRecuperables + " €\n");
        runContenu.setText("Travaux réalisés : " + travauxLogement + " €\n");
        runContenu.setText("Solde de tout compte : " + soldeToutCompte + " €\n");

        // Enregistrement
        document.write(fileOut);
        fileOut.close();
        modele.close();
        document.close();

        System.out.println("Le rapport de solde de tout compte a été généré avec succès.");
    }
}
