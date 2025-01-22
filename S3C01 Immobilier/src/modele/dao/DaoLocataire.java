package modele.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Locataire;
import modele.dao.requetes.create.RequeteCreateLocataire;
import modele.dao.requetes.delete.RequeteDeleteLocataire;
import modele.dao.requetes.select.RequeteSelectLocataire;
import modele.dao.requetes.select.RequeteSelectLocataireByID;
import modele.dao.requetes.update.RequeteUpdateLocataire;
import oracle.jdbc.OracleTypes;

public class DaoLocataire implements Dao<Locataire> {

	private Connection connection;

	public DaoLocataire(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Locataire donnees) throws SQLException {
		RequeteCreateLocataire requeteCreate = new RequeteCreateLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Locataire donnees) throws SQLException {
		RequeteUpdateLocataire requeteUpdate = new RequeteUpdateLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(String... id) throws SQLException {
		RequeteDeleteLocataire requeteDelete = new RequeteDeleteLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, id);
			prSt.executeUpdate();
		}
	}

	@Override
	public Locataire findById(String... id) throws SQLException {
		RequeteSelectLocataireByID requeteSelectById = new RequeteSelectLocataireByID();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
					return new Locataire(
							rs.getString("Id_Locataire"), 
							rs.getString("Nom"), 
							rs.getString("Prenom"),
							rs.getString("Mail"), 
							rs.getString("Telephone"), 
							rs.getDate("DateNaissance"), 
							rs.getDate("DateDepart"));
				}
			}
		}
		return null;
	}

	@Override
	public List<Locataire> findAll() throws SQLException {
		RequeteSelectLocataire requeteSelectAll = new RequeteSelectLocataire();
		List<Locataire> locataires = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				locataires.add(new Locataire(
						rs.getString("Id_Locataire"), 
						rs.getString("Nom"), 
						rs.getString("Prenom"),
						rs.getString("Mail"), 
						rs.getString("Telephone"), 
						rs.getDate("DateNaissance"), 
						rs.getDate("DateDepart")));
			}
		}
		return locataires;
	}
	
	public List<Object[]> detecterLoyersImpayes() throws SQLException {
	    String sql = "{ call DetecterLoyersImpayes(?) }";
	    List<Object[]> loyersImpaye = new ArrayList<>();
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, OracleTypes.CURSOR);
	        stmt.execute();

	        try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
	            while (rs.next()) {
	                Object[] tuple = new Object[2];
	                tuple[0] = rs.getInt("Id_Louable");
	                tuple[1] = rs.getDate("DateImpayee").toLocalDate();
	                loyersImpaye.add(tuple);
	            }
	        }
	    }
	    return loyersImpaye;
	}


}
