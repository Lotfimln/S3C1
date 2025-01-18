package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Apparaitre;
import modele.dao.requetes.create.RequeteCreateApparaitre;
import modele.dao.requetes.delete.RequeteDeleteApparaitre;
import modele.dao.requetes.select.RequeteSelectApparaitre;
import modele.dao.requetes.update.RequeteUpdateApparaitre;
import modele.dao.requetes.update.RequeteUpdateApparaitreByCharge;
import modele.dao.requetes.update.RequeteUpdateApparaitreByIndexCompteur;

public class DaoApparaitre implements Dao<Apparaitre> {

    private Connection connection;

    public DaoApparaitre(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Apparaitre donnees) throws SQLException {
        RequeteCreateApparaitre requeteCreate = new RequeteCreateApparaitre();
        try (PreparedStatement prSt = connection.prepareStatement(requeteCreate.requete())) {
        	requeteCreate.parametres(prSt, donnees);
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
    
    public void updateByCharge(Apparaitre donnees) throws SQLException {
        RequeteUpdateApparaitreByCharge requeteUpdate = new RequeteUpdateApparaitreByCharge();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }
    
    public void updateByIndexCompteur(Apparaitre donnees) throws SQLException {
        RequeteUpdateApparaitreByIndexCompteur requeteUpdate = new RequeteUpdateApparaitreByIndexCompteur();
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

	// Cette methode est inutile, car elle renvoie exactement les parametres de la requete
    @Override
    public Apparaitre findById(String... id) throws SQLException {
        return null;
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
    
    public List<Apparaitre> findByCharge(String... id) throws SQLException {
        String sql = "SELECT * FROM Apparaitre WHERE Id_Charge = ?";
        List<Apparaitre> apparaitreList = new ArrayList<>();
        try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
            prSt.setInt(1, Integer.parseInt(id[0])); // Conversion de l'ID en int
            try (ResultSet rs = prSt.executeQuery()) {
                while (rs.next()) {
                    apparaitreList.add(new Apparaitre(
                            rs.getInt("Id_Charge"),
                            rs.getInt("Id_Index_Compteur")));
                }
            }
        }
        return apparaitreList;
    }
    
    public List<Apparaitre> findByIndex(String... id) throws SQLException {
        String sql = "SELECT * FROM Apparaitre WHERE Id_Index_Compteur = ?";
        List<Apparaitre> apparaitreList = new ArrayList<>();
        try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
            prSt.setInt(1, Integer.parseInt(id[0]));
            try (ResultSet rs = prSt.executeQuery()) {
                while (rs.next()) {
                    apparaitreList.add(new Apparaitre(
                            rs.getInt("Id_Charge"),
                            rs.getInt("Id_Index_Compteur")));
                }
            }
        }
        return apparaitreList;
    }

    
}
