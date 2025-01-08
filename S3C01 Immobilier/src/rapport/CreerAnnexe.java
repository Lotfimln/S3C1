package rapport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modele.Immeuble;
import modele.dao.DaoImmeuble;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class CreerAnnexe {
    
	
    private static void ajouterResultatFoncier(XWPFDocument document, int resultatFoncier) {
        CreerAnnexe.ajouterUnSousTitre(document, "Résultat Foncier");
        CreerAnnexe.ajouterLigne(document, "RÉSULTAT", resultatFoncier);
    }


    public static void main(String[] args) {

        try {
            OutputStream fileOut = new FileOutputStream("src/rapport/annexe2044.docx");
            InputStream modele = new FileInputStream("src/rapport/vide.docx");

            XWPFDocument document = new XWPFDocument(modele);

            // Entête de la déclaration
            CreerAnnexe.ajouterUnTitre(document, "Annexe 2044 - Déclaration des Revenus Fonciers de " + LocalDate.now().getYear());

            // Informations
            List<Proprietes> proprietes = new ArrayList<>();
            List<InfosRecettes> recettes = new ArrayList<>();
            List<FraisCharges> fraisEtCharges = new ArrayList<>();
            
            // Utilisez le DAO pour récupérer les immeubles
            DaoImmeuble daoImmeuble = new DaoImmeuble();

            try {
                List<Immeuble> immeubles = daoImmeuble.findAll();

                for (Immeuble immeuble : immeubles) {
                	// Calcule du nombre de logement dans l'immeuble
                    int nombreLocaux = daoImmeuble.getNombreLogementsDansImmeuble(immeuble.getImmeuble());
                    // Calcule du total des loyers dans l'immeuble
                    int sommeLoyers = daoImmeuble.getSommeLoyersDansImmeublePourPeriode(immeuble.getImmeuble(), (LocalDate.now().getYear()-1)+"-05-01", LocalDate.now().getYear()+"-05-31");
                    
                    List<FraisCharges> fraisEtChargesImmeuble = daoImmeuble.getFraisEtChargesParImmeuble(immeuble.getImmeuble(), (LocalDate.now().getYear()-1)+"-05-01", LocalDate.now().getYear()+"-05-31");
                    
                    Proprietes propriete = new Proprietes(
                            immeuble.getImmeuble(),
                            immeuble.getType_immeuble(),
                            immeuble.getPeriodeConstruction(),
                            immeuble.getAdresse(),
                            nombreLocaux,
                            sommeLoyers
                    );
                    proprietes.add(propriete);
                    recettes.add(new InfosRecettes(immeuble.getImmeuble(), "Loyers bruts encaissés", sommeLoyers));
                    fraisEtCharges.addAll(fraisEtChargesImmeuble);
                    
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            ajouterUnSousTitre(document, "Caractéristiques des propriétés");
            ajouterInfosProprietes(document, proprietes);

          
            ajouterUnSousTitre(document, "Recettes");
            ajouterTableRecettes(document, recettes);

            
            ajouterUnSousTitre(document, "Frais et charges");
            ajouterTableFraisCharge(document, fraisEtCharges);
            
            int resultatFoncier = calculerResultatFoncier(recettes, fraisEtCharges);
            ajouterResultatFoncier(document, resultatFoncier);

            // Fin de la déclaration
            ajouterFooter(document, "Fin de la Déclaration");

            document.write(fileOut);
            fileOut.close();
            modele.close();
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static void ajouterUnSousTitre(XWPFDocument document, String txt) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(14);
        run.setText(txt);
        paragraph.setSpacingBefore(12);
        paragraph.setSpacingAfter(6);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
    }

    
    private static void ajouterLigne(XWPFDocument document, String txt, int val) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(txt + "\t" + val);
    }

   
    private static void ajouterUnTitre(XWPFDocument document, String txt) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(16);
        run.setText(txt);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setSpacingBefore(12);
        paragraph.setSpacingAfter(12);
    }

    
    private static void ajouterFooter(XWPFDocument document, String txt) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setItalic(true);
        run.setText(txt);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setSpacingBefore(12);
        paragraph.setSpacingAfter(12);
    }

    
    private static void addTable(XWPFDocument document, List<String> entête, List<List<String>> données) {
        XWPFTable table = document.createTable();

        XWPFTableRow headerRow = table.getRow(0);
        for (int i = 0; i < entête.size(); i++) {
            XWPFTableCell cell = headerRow.getCell(i);
            if (cell == null) {
                cell = headerRow.createCell();
            }
            cell.setText(entête.get(i));
        }
        for (List<String> rowData : données) {
            XWPFTableRow dataRow = table.createRow();
            for (int i = 0; i < entête.size(); i++) {
                XWPFTableCell cell = dataRow.getCell(i);
                if (cell == null) {
                    cell = dataRow.createCell();
                }
                cell.setText(rowData.get(i));
            }
        }
    }

   
    private static void ajouterInfosProprietes(XWPFDocument document, List<Proprietes> propriétés) {
        List<String> headers = List.of("Nom de la Propriété", "Type", "Période de Construction", "Adresse", "Nombre de Locaux");
        List<List<String>> données = new ArrayList<>();

        for (Proprietes propriété : propriétés) {
            List<String> rowData = List.of(propriété.getNom(), propriété.getType(), propriété.getPeriodeConstruction(),
            		propriété.getAdresse(), String.valueOf(propriété.getNombreLocaux()));
            données.add(rowData);
        }

        addTable(document, headers, données);
    }

   
    private static void ajouterTableRecettes(XWPFDocument document, List<InfosRecettes> recettes) {
        List<String> headers = List.of("Nom de la Propriété", "Description", "Montant");
        List<List<String>> données = new ArrayList<>();

        for (InfosRecettes recette : recettes) {
            List<String> rowData = List.of(recette.getNom(), recette.getDescription(), String.valueOf(recette.getMontant()));
            données.add(rowData);
        }

        addTable(document, headers, données);
    }

   
    private static void ajouterTableFraisCharge(XWPFDocument document, List<FraisCharges> fraisCharges) {
        List<String> headers = List.of("Nom de la Propriété", "Description", "Montant");
        List<List<String>> données = new ArrayList<>();

        for (FraisCharges fg : fraisCharges) {
            List<String> rowData = List.of(fg.getNom(), fg.getDescription(), String.valueOf(fg.getMontant())); 
            données.add(rowData);
        }

        addTable(document, headers, données);
    }
    
    
    private static int calculerResultatFoncier(List<InfosRecettes> recettes, List<FraisCharges> fraisEtCharges) {
        int totalRecettes = recettes.stream().mapToInt(InfosRecettes::getMontant).sum();
        int totalCharges = fraisEtCharges.stream().mapToInt(FraisCharges::getMontant).sum();
        return totalRecettes - totalCharges;
    }
}
