package modele.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modele.Apparaitre;
import modele.dao.requetes.delete.RequeteDeleteApparaitre;
import modele.dao.requetes.select.RequeteSelectApparaitre;
import modele.dao.requetes.select.RequeteSelectApparaitreByCharge;
import modele.dao.requetes.select.RequeteSelectApparaitreByIndex;
import modele.dao.requetes.select.RequeteSelectApparaitreByID;
import modele.dao.requetes.update.RequeteUpdateApparaitre;

public class DaoApparaitre implements Dao<Apparaitre> {

    private Connection connection;

    public DaoApparaitre(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Apparaitre donnees) throws SQLException {
        String sql = "INSERT INTO Assureur (Id_Charge, Id_Index_Compteur) VALUES (?, ?)";
        try (PreparedStatement prSt = connection.prepareStatement(sql)) {
            prSt.setInt(1, donnees.getIdCharge());
            prSt.setInt(2, donnees.getIdIndexCompteur());
            prSt.executeUpdate();
        }
    }

    @Override
    public void update(Apparaitre donnees) throws SQLException {
        RequeteUpdateApparaitre requeteUpdate = new RequeteUpdateApparaitre();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(Apparaitre donnees) throws SQLException {
        RequeteDeleteApparaitre requeteDelete = new RequeteDeleteApparaitre();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public Apparaitre findById(String... id) throws SQLException {
        RequeteSelectApparaitreByID requeteSelectById = new RequeteSelectApparaitreByID();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectById.requete())) {
            requeteSelectById.parametres(prSt, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    return new Apparaitre(
                        rs.getInt("Id_Charge"),
                        rs.getInt("Id_Index_Compteur")
                    );
                }
            }
        }
        return null; // Retourne null si aucun immeuble trouvé
    }

    @Override
    public List<Apparaitre> findAll() throws SQLException {
        RequeteSelectApparaitre requeteSelectAll = new RequeteSelectApparaitre();
        List<Apparaitre> apparaitre = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                apparaitre.add(new Apparaitre(
                        rs.getInt("Id_Charge"),
                        rs.getInt("Id_Index_Compteur")
                ));
            }
        }
        return apparaitre;
    }
}
