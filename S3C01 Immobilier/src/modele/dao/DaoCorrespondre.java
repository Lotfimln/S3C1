package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Correspondre;
import modele.dao.requetes.create.RequeteCreateCorrespondre;
import modele.dao.requetes.delete.RequeteDeleteCorrespondre;
import modele.dao.requetes.select.RequeteSelectCorrespondre;
import modele.dao.requetes.update.RequeteUpdateCorrespondre;
import modele.dao.requetes.update.RequeteUpdateCorrespondreByContratDeLocation;
import modele.dao.requetes.update.RequeteUpdateCorrespondreByLocataire;

public class DaoCorrespondre implements Dao<Correspondre> {

	private Connection connection;

	public DaoCorrespondre(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Correspondre donnees) throws SQLException {
		RequeteCreateCorrespondre requeteCreate = new RequeteCreateCorrespondre();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Correspondre donnees) throws SQLException {
		RequeteUpdateCorrespondre requeteUpdate = new RequeteUpdateCorrespondre();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
	
	public void updateByLocataire(Correspondre donnees) throws SQLException {
		RequeteUpdateCorrespondreByLocataire requeteUpdate = new RequeteUpdateCorrespondreByLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
	
	public void updateByContratDeLocation(Correspondre donnees) throws SQLException {
		RequeteUpdateCorrespondreByContratDeLocation requeteUpdate = new RequeteUpdateCorrespondreByContratDeLocation();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Correspondre donnees) throws SQLException {
		RequeteDeleteCorrespondre requeteDelete = new RequeteDeleteCorrespondre();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
	
	// Cette methode est inutile, car elle renvoie exactement les parametres de la requete
	@Override
    public Correspondre findById(String... id) throws SQLException {
        return null;
    }

	@Override
	public List<Correspondre> findAll() throws SQLException {
		RequeteSelectCorrespondre requeteSelectAll = new RequeteSelectCorrespondre();
		List<Correspondre> correspondances = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				correspondances.add(new Correspondre(
						rs.getString("Id_Locataire"), 
						rs.getInt("Id_Contrat_de_location")));
			}
		}
		return correspondances;
	}
	
	public List<Correspondre> findByContratDeLocation(String... id) throws SQLException {
	    String sql = "SELECT * FROM Correspondre WHERE Id_Contrat_de_location = ?";
	    List<Correspondre> correspondreList = new ArrayList<>();
	    try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
	        prSt.setInt(1, Integer.parseInt(id[0]));
	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                correspondreList.add(new Correspondre(
	                        rs.getString("Id_Locataire"),
	                        rs.getInt("Id_Contrat_de_location")));
	            }
	        }
	    }
	    return correspondreList;
	}
	
	public List<Correspondre> findByLocataire(String... id) throws SQLException {
	    String sql = "SELECT * FROM Correspondre WHERE Id_Locataire = ?";
	    List<Correspondre> correspondreList = new ArrayList<>();
	    try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
	        prSt.setString(1, id[0]);
	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                correspondreList.add(new Correspondre(
	                        rs.getString("Id_Locataire"),
	                        rs.getInt("Id_Contrat_de_location")));
	            }
	        }
	    }
	    return correspondreList;
	}


}
