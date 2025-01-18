package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Assureur;
import modele.dao.requetes.create.RequeteCreateAssureur;
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
        RequeteCreateAssureur requeteCreate = new RequeteCreateAssureur();
        try (PreparedStatement prSt = connection.prepareStatement(requeteCreate.requete())) {
        	requeteCreate.parametres(prSt, donnees);
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
                        rs.getInt("Id_Assureur"),
                        rs.getString("Nom"),
                        rs.getDate("DateAssurance"),
                        rs.getInt("Prime"),
                        rs.getString("TypeAssureur")
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
                    rs.getInt("Id_Assureur"),
                    rs.getString("Nom"),
                    rs.getDate("DateAssurance"),
                    rs.getInt("Prime"),
                    rs.getString("TypeAssureur")
                ));
            }
        }
        return assureur;
    }
}
