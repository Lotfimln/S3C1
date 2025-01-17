package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Colocataire;
import modele.Correspondre;
import modele.Locataire;
import modele.dao.requetes.delete.RequeteDeleteColocataire;
import modele.dao.requetes.delete.RequeteDeleteLocataire;
import modele.dao.requetes.select.RequeteSelectColocataire;
import modele.dao.requetes.select.RequeteSelectColocataireByID;
import modele.dao.requetes.update.RequeteUpdateColocataire;

public class DaoColocataire implements Dao<Colocataire> {

	private Connection connection;

	public DaoColocataire(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Colocataire donnees) throws SQLException {
		String sql = "INSERT INTO Colocataire (Id_Locataire, Id_Locataire_1) VALUES (?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setString(1, donnees.getIdLocataire());
			prSt.setString(2, donnees.getIdLocataire1());
			prSt.executeUpdate();
		}
	}
	@Override
    public void update(Colocataire donnees) throws SQLException {
        RequeteUpdateColocataire requeteUpdate = new RequeteUpdateColocataire();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(Colocataire donnees) throws SQLException {
        RequeteDeleteColocataire requeteDelete = new RequeteDeleteColocataire();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    // fonction inutile
    @Override
    public Colocataire findById(String... id) throws SQLException {
        return null;
    }

    @Override
    public List<Colocataire> findAll() throws SQLException {
        RequeteSelectColocataire requeteSelectAll = new RequeteSelectColocataire();
        List<Colocataire> immeubles = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                immeubles.add(new Colocataire(
                    rs.getString("Id_Locataire"),
                    rs.getString("Id_Locataire")
                ));
            }
        }
        return immeubles;
    }
    
    public List<Colocataire> findByLocataire(String... id) throws SQLException {
	    String sql = "SELECT * FROM Colocataire WHERE Id_Locataire = ?";
	    List<Colocataire> colocataires = new ArrayList<>();
	    try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
	        prSt.setString(1, id[0]);
	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                colocataires.add(new Colocataire(
	                        rs.getString("Id_Locataire"),
	                        rs.getString("Id_Locataire_1")));
	            }
	        }
	    }
	    return colocataires;
	}
    
	public void deleteByLocataire(Locataire donnees) throws SQLException {
		RequeteDeleteLocataire requeteDelete = new RequeteDeleteLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
}
