package rapport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modele.dao.CictOracleDataSource;

public class InfosRecettes {
    private String nom;
    private String description;
    private int montant;

    // Constructeur
    public InfosRecettes(String nom, String description, int montant) {
        this.nom = nom;
        this.description = description;
        this.montant = montant;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public int getMontant() {
        return montant;
    }

    // Méthode pour récupérer les recettes (loyers) depuis la base de données
    public static List<InfosRecettes> recupererRecettes() throws SQLException {
        Connection connection = CictOracleDataSource.getConnectionBD();  // Connexion correcte
        List<InfosRecettes> recettes = new ArrayList<>();

        String query = "SELECT i.nom, 'Loyers bruts encaissés' AS description, SUM(l.montant) AS montant " +
                       "FROM Loyer l " +
                       "JOIN Appartement a ON l.appartement_id = a.id " +
                       "JOIN Immeuble i ON a.immeuble_id = i.id " +
                       "WHERE l.date_paiement BETWEEN ? AND ? " +
                       "GROUP BY i.nom";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, LocalDate.now().minusYears(1).toString());  // Début de la période
        stmt.setString(2, LocalDate.now().toString());                // Fin de la période
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            recettes.add(new InfosRecettes(
                rs.getString("nom"),
                rs.getString("description"),
                rs.getInt("montant")
            ));
        }
        return recettes;
    }
}
