package modele.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modele.Assureur;
import modele.dao.requetes.delete.RequeteDeleteAssureur;
import modele.dao.requetes.select.RequeteSelectAssureur;
import modele.dao.requetes.select.RequeteSelectAssureurByID;
import modele.dao.requetes.update.RequeteUpdateAssureur;

public class DaoAssureur implements Dao<Assureur> {

    private Connection connection;

    public DaoAssureur(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Assureur donnees) throws SQLException {
        String sql = "INSERT INTO Assureur (Id_Entreprise, Nom, DateAssurance, Prime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement prSt = connection.prepareStatement(sql)) {
            prSt.setInt(1, donnees.getIdAssurance());
            prSt.setString(2, donnees.getNom());
            java.util.Date utilDate = donnees.getDateAssurance();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Convertir en java.sql.Date
            prSt.setDate(3, sqlDate);
            prSt.setInt(4, donnees.getPrime());
            prSt.executeUpdate();
        }
    }

    @Override
    public void update(Assureur donnees) throws SQLException {
        RequeteUpdateAssureur requeteUpdate = new RequeteUpdateAssureur();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(Assureur donnees) throws SQLException {
        RequeteDeleteAssureur requeteDelete = new RequeteDeleteAssureur();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public Assureur findById(String... id) throws SQLException {
        RequeteSelectAssureurByID requeteSelectById = new RequeteSelectAssureurByID();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectById.requete())) {
            requeteSelectById.parametres(prSt, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    return new Assureur(
                        rs.getInt("Id_Assurance"),
                        rs.getString("Nom"), 
                        rs.getDate("DateAssurance"),
                        rs.getInt("Prime")
                    );
                }
            }
        }
        return null; // Retourne null si aucun immeuble trouvé
    }

    @Override
    public List<Assureur> findAll() throws SQLException {
        RequeteSelectAssureur requeteSelectAll = new RequeteSelectAssureur();
        List<Assureur> assureur = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                assureur.add(new Assureur(
                    rs.getInt("Id_Assurance"),
                    rs.getString("Nom"), 
                    rs.getDate("DateAssurance"),
                    rs.getInt("Prime")
                ));
            }
        }
        return assureur;
    }
}
