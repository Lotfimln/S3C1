package rapport;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class CreerAnnexe {

    public static void main(String[] args) {
        try {
            // Création du document
            XWPFDocument document = new XWPFDocument();

            // Récupération des données
            List<Proprietes> proprietes = Proprietes.recupererProprietes();
            List<InfosRecettes> recettes = InfosRecettes.recupererRecettes();
            List<FraisCharges> fraisCharges = FraisCharges.recupererFraisCharges();

            // Génération du rapport
            ajouterTitre(document, "Annexe 2044 - Déclaration des Revenus Fonciers");
            ajouterSectionProprietes(document, proprietes);
            ajouterSectionRecettes(document, recettes);
            ajouterSectionFraisCharges(document, fraisCharges);
            ajouterResultatFoncier(document, recettes, fraisCharges);

            // Sauvegarde du document
            FileOutputStream out = new FileOutputStream("Annexe_2044.docx");
            document.write(out);
            out.close();
            document.close();

            System.out.println("Rapport généré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Titre principal
    private static void ajouterTitre(XWPFDocument document, String titre) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(16);
        run.setText(titre);
    }

    // Section Propriétés
    private static void ajouterSectionProprietes(XWPFDocument document, List<Proprietes> proprietes) {
        document.createParagraph().createRun().setText("1. Caractéristiques des Propriétés");

        XWPFTable table = document.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Nom");
        header.addNewTableCell().setText("Type");
        header.addNewTableCell().setText("Période de Construction");
        header.addNewTableCell().setText("Adresse");
        header.addNewTableCell().setText("Nombre de Locaux");
        header.addNewTableCell().setText("Somme des Loyers (€)");

        for (Proprietes p : proprietes) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(p.getNom());
            row.getCell(1).setText(p.getType());
            row.getCell(2).setText(p.getPeriodeConstruction());
            row.getCell(3).setText(p.getAdresse());
            row.getCell(4).setText(String.valueOf(p.getNombreLocaux()));
            row.getCell(5).setText(String.valueOf(p.getSommeLoyers()));
        }
    }

    // Section Recettes
    private static void ajouterSectionRecettes(XWPFDocument document, List<InfosRecettes> recettes) {
        document.createParagraph().createRun().setText("2. Recettes");

        XWPFTable table = document.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Nom de la Propriété");
        header.addNewTableCell().setText("Description");
        header.addNewTableCell().setText("Montant (€)");

        for (InfosRecettes r : recettes) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(r.getNom());
            row.getCell(1).setText(r.getDescription());
            row.getCell(2).setText(String.valueOf(r.getMontant()));
        }
    }

    // Section Frais et Charges
    private static void ajouterSectionFraisCharges(XWPFDocument document, List<FraisCharges> fraisCharges) {
        document.createParagraph().createRun().setText("3. Frais et Charges");

        XWPFTable table = document.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Nom de la Propriété");
        header.addNewTableCell().setText("Description");
        header.addNewTableCell().setText("Montant (€)");

        for (FraisCharges fc : fraisCharges) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(fc.getNom());
            row.getCell(1).setText(fc.getDescription());
            row.getCell(2).setText(String.valueOf(fc.getMontant()));
        }
    }

    // Section Résultat Foncier
    private static void ajouterResultatFoncier(XWPFDocument document, List<InfosRecettes> recettes, List<FraisCharges> fraisCharges) {
        int totalRecettes = recettes.stream().mapToInt(InfosRecettes::getMontant).sum();
        int totalCharges = fraisCharges.stream().mapToInt(FraisCharges::getMontant).sum();
        int resultatFoncier = totalRecettes - totalCharges;

        document.createParagraph().createRun().setText("4. Résultat Foncier");

        XWPFTable table = document.createTable();
        XWPFTableRow row1 = table.getRow(0);
        row1.getCell(0).setText("Total des Recettes");
        row1.addNewTableCell().setText(String.valueOf(totalRecettes) + " €");

        XWPFTableRow row2 = table.createRow();
        row2.getCell(0).setText("Total des Charges");
        row2.getCell(1).setText(String.valueOf(totalCharges) + " €");

        XWPFTableRow row3 = table.createRow();
        row3.getCell(0).setText("Résultat Foncier");
        row3.getCell(1).setText(String.valueOf(resultatFoncier) + " €");
    }
}
