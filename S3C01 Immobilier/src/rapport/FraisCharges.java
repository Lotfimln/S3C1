package rapport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modele.dao.CictOracleDataSource;

public class FraisCharges {
    private String nom;
    private String description;
    private int montant;

    // Constructeur
    public FraisCharges(String nom, String description, int montant) {
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

    // Méthode pour récupérer les frais et charges depuis la base de données
    public static List<FraisCharges> recupererFraisCharges() throws SQLException {
        Connection connection = CictOracleDataSource.getConnectionBD();
        List<FraisCharges> fraisCharges = new ArrayList<>();

        String query = "SELECT i.nom, fc.description, SUM(fc.montant) AS montant " +
                       "FROM FraisCharges fc " +
                       "JOIN Immeuble i ON fc.immeuble_id = i.id " +
                       "WHERE fc.date BETWEEN ? AND ? " +
                       "GROUP BY i.nom, fc.description";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, LocalDate.now().minusYears(1).toString());
        stmt.setString(2, LocalDate.now().toString());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            fraisCharges.add(new FraisCharges(
                rs.getString("nom"),
                rs.getString("description"),
                rs.getInt("montant")
            ));
        }
        return fraisCharges;
    }
}
