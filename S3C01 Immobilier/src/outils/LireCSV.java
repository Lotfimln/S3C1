package outils;

import modele.Quittances;
import modele.dao.CictOracleDataSource;
import modele.dao.DaoContratDeLocation;
import modele.dao.DaoCorrespondre;
import modele.dao.DaoLocataire;
import modele.dao.DaoQuittances;
import modele.Locataire;
import modele.Louable;
import modele.ContratDeLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class LireCSV {

    public static void importerQuittances(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            int numeroLigne = 1; // Utilisé pour générer l'ID de quittance
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy"); // Format pour Mo/AA

            while ((ligne = br.readLine()) != null) {
                if (numeroLigne == 1) { // Ignorer l'entête du CSV
                    numeroLigne++;
                    continue;
                }

                String[] elements = ligne.split(","); // Séparation par virgule

                // Validation des données
                if (elements.length != 5) {
                    System.err.println("Erreur dans la ligne " + numeroLigne + ": nombre de colonnes incorrect.");
                    numeroLigne++;
                    continue;
                }

                try {
                    // Récupération des informations depuis le CSV
                    int idLouable = Integer.parseInt(elements[0].trim());
                    String idLocataire = elements[1].trim();
                    Date datePaiement = dateFormat.parse(elements[2].trim());
                    double montantLoyer = Double.parseDouble(elements[3].trim());
                    double montantProvision = Double.parseDouble(elements[4].trim());
                    
                    DaoLocataire daoLocataire = new DaoLocataire(CictOracleDataSource.getConnectionBD());
                    DaoContratDeLocation daoContratDeLocation = new DaoContratDeLocation(CictOracleDataSource.getConnectionBD());

                    // Création d'objets nécessaires
                    Locataire locataire = daoLocataire.findById(idLocataire); 
                    ContratDeLocation contrat = daoContratDeLocation.findById(String.valueOf(idLouable));

                    // Création de l'objet Quittances
                    Quittances quittance = new Quittances(numeroLigne, datePaiement, montantLoyer, montantProvision, locataire, contrat);
                    System.out.print(quittance);
                    DaoQuittances daoQuittances = new DaoQuittances(CictOracleDataSource.getConnectionBD());
                    daoQuittances.create(quittance);


                    System.out.println("Quittance créée : " + quittance);
                } catch (Exception e) {
                    System.err.println("Erreur dans la ligne " + numeroLigne + ": " + e.getMessage());
                }

                numeroLigne++;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}
