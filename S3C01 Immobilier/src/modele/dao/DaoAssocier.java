package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Associer;
import modele.dao.requetes.delete.RequeteDeleteAssocier;
import modele.dao.requetes.select.RequeteSelectAssocier;
import modele.dao.requetes.select.RequeteSelectAssocierByID;
import modele.dao.requetes.update.RequeteUpdateAssocier;

public class DaoAssocier implements Dao<Associer> {

    private Connection connection;

    public DaoAssocier(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Associer donnees) throws SQLException {
        String sql = "INSERT INTO Assureur (Id_Louable, Id_Index_Compteur) VALUES (?, ?)";
        try (PreparedStatement prSt = connection.prepareStatement(sql)) {
            prSt.setInt(1, donnees.getIdLouable());
            prSt.setInt(2, donnees.getIdIndexCompteur());
            prSt.executeUpdate();
        }
    }

    @Override
    public void update(Associer donnees) throws SQLException {
        RequeteUpdateAssocier requeteUpdate = new RequeteUpdateAssocier();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(Associer donnees) throws SQLException {
        RequeteDeleteAssocier requeteDelete = new RequeteDeleteAssocier();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public Associer findById(String... id) throws SQLException {
        RequeteSelectAssocierByID requeteSelectById = new RequeteSelectAssocierByID();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectById.requete())) {
            requeteSelectById.parametres(prSt, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    return new Associer(
                        rs.getInt("Id_Louable"),
                        rs.getInt("Id_Index_Compteur")
                    );
                }
            }
        }
        return null; // Retourne null si aucun immeuble trouvé
    }


    @Override
    public List<Associer> findAll() throws SQLException {
        RequeteSelectAssocier requeteSelectAll = new RequeteSelectAssocier();
        List<Associer> associer = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                associer.add(new Associer(
                		rs.getInt("Id_Louable"),
                        rs.getInt("Id_Index_Compteur")
                ));
            }
        }
        return associer;
    }
}
