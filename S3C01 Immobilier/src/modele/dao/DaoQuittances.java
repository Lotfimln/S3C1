package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ContratDeLocation;
import modele.Locataire;
import modele.Quittances;
import modele.dao.requetes.delete.RequeteDeleteQuittances;
import modele.dao.requetes.select.RequeteSelectQuittances;
import modele.dao.requetes.select.RequeteSelectQuittancesByID;
import modele.dao.requetes.update.RequeteUpdateQuittances;

public class DaoQuittances implements Dao<Quittances> {

	private Connection connection;

	public DaoQuittances(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Quittances donnees) throws SQLException {
		String sql = "INSERT INTO Quittances (Id_Quittances, DatePaiement, MontantLoyer, MontantProvision, Id_Locataire, Id_Contrat_de_location) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setDouble(1, donnees.getMontantLoyer());
			java.util.Date utilDate = donnees.getDatePaiement();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            prSt.setDate(2, sqlDate);
			prSt.setDouble(3, donnees.getMontantLoyer());
			prSt.setDouble(4, donnees.getMontantProvision());
			prSt.setString(5, donnees.getLocataire().getIdLocataire());
			prSt.setInt(6, donnees.getContratDeLocation().getIdContratDeLocation());
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Quittances donnees) throws SQLException {
		RequeteUpdateQuittances requeteUpdate = new RequeteUpdateQuittances();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Quittances donnees) throws SQLException {
		RequeteDeleteQuittances requeteDelete = new RequeteDeleteQuittances();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Quittances findById(String... id) throws SQLException {
		RequeteSelectQuittancesByID requeteSelectById = new RequeteSelectQuittancesByID();
	    DaoLocataire daoLocataire = new DaoLocataire(this.connection);
	    DaoContratDeLocation daoContratDeLocation = new DaoContratDeLocation(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
					String idLocataire = rs.getString("Id_Locataire");
	                int idContratDeLocation = rs.getInt("Id_Contrat_de_location");
	                Locataire locataire = daoLocataire.findById(String.valueOf(idLocataire));
	                ContratDeLocation contratDeLocation = daoContratDeLocation.findById(String.valueOf(idContratDeLocation));

					return new Quittances(
							rs.getInt("Id_Quittances"), 
							rs.getDate("DatePaiement"),
							rs.getDouble("MontantLoyer"), 
							rs.getDouble("MontantProvision"), 
							locataire,
							contratDeLocation);
				}
			}
		}
		return null;
	}

	@Override
	public List<Quittances> findAll() throws SQLException {
		RequeteSelectQuittances requeteSelectAll = new RequeteSelectQuittances();
		List<Quittances> quittances = new ArrayList<>();
	    DaoLocataire daoLocataire = new DaoLocataire(this.connection);
	    DaoContratDeLocation daoContratDeLocation = new DaoContratDeLocation(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				String idLocataire = rs.getString("Id_Locataire");
                int idContratDeLocation = rs.getInt("Id_Contrat_de_location");
                Locataire locataire = daoLocataire.findById(String.valueOf(idLocataire));
                ContratDeLocation contratDeLocation = daoContratDeLocation.findById(String.valueOf(idContratDeLocation));

				quittances.add(new Quittances(
						rs.getInt("Id_Quittances"), 
						rs.getDate("DatePaiement"),
						rs.getDouble("MontantLoyer"), 
						rs.getDouble("MontantProvision"), 
						locataire,
						contratDeLocation));
			}
		}
		return quittances;
	}
}
