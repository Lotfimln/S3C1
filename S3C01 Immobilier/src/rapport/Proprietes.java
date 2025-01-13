package rapport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.dao.CictOracleDataSource;

public class Proprietes {
    private String nom;
    private String type;
    private String periodeConstruction;
    private String adresse;
    private int nombreLocaux;
    private int sommeLoyers;

    // Constructeur
    public Proprietes(String nom, String type, String periodeConstruction, String adresse, int nombreLocaux, int sommeLoyers) {
        this.nom = nom;
        this.type = type;
        this.periodeConstruction = periodeConstruction;
        this.adresse = adresse;
        this.nombreLocaux = nombreLocaux;
        this.sommeLoyers = sommeLoyers;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getPeriodeConstruction() {
        return periodeConstruction;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getNombreLocaux() {
        return nombreLocaux;
    }

    public int getSommeLoyers() {
        return sommeLoyers;
    }

    // Méthode pour récupérer les propriétés depuis la base de données
    public static List<Proprietes> recupererProprietes() throws SQLException {
        Connection connection = CictOracleDataSource.getConnectionBD();  // Connexion correcte
        List<Proprietes> proprietes = new ArrayList<>();

        String query = "SELECT i.nom, i.type, i.periode_construction, i.adresse, COUNT(a.id) AS nombre_locaux, " +
                       "COALESCE(SUM(l.montant), 0) AS somme_loyers " +
                       "FROM Immeuble i " +
                       "LEFT JOIN Appartement a ON i.id = a.immeuble_id " +
                       "LEFT JOIN Loyer l ON a.id = l.appartement_id " +
                       "GROUP BY i.nom, i.type, i.periode_construction, i.adresse";

        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            proprietes.add(new Proprietes(
                rs.getString("nom"),
                rs.getString("type"),
                rs.getString("periode_construction"),
                rs.getString("adresse"),
                rs.getInt("nombre_locaux"),
                rs.getInt("somme_loyers")
            ));
        }
        return proprietes;
    }
}
