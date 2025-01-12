package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.ContratDeLocation;
import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteContratDeLocation;
import modele.dao.requetes.select.RequeteSelectContratDeLocation;
import modele.dao.requetes.select.RequeteSelectContratDeLocationByID;
import modele.dao.requetes.update.RequeteUpdateContratDeLocation;

public class DaoContratDeLocation implements Dao<ContratDeLocation> {

	private Connection connection;

	public DaoContratDeLocation(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(ContratDeLocation donnees) throws SQLException {
		String sql = "INSERT INTO Contrat_de_location (Id_Contrat_de_location, DateDebut, DateFin, MontantLoyer, ProvisionsCharges, TypeContrat, DateAnniversaire, IndiceICC, MontantCaution, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, donnees.getIdContratDeLocation());
			java.util.Date utilDate = donnees.getDateDebut();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            prSt.setDate(2, sqlDate);
			java.util.Date utilDate1 = donnees.getDateFin();
            java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
            prSt.setDate(3, sqlDate1);
			prSt.setDouble(4, donnees.getMontantLoyer());
			prSt.setDouble(5, donnees.getProvisionsCharges());
			prSt.setString(6, donnees.getTypeContrat());
			prSt.setDate(7, new java.sql.Date(donnees.getDateAnniversaire().getTime()));
			prSt.setDouble(8, donnees.getIndiceICC());
			prSt.setDouble(9, donnees.getMontantCaution());
			prSt.setInt(10, donnees.getLouable().getIdLouable());
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(ContratDeLocation donnees) throws SQLException {
		RequeteUpdateContratDeLocation requeteUpdate = new RequeteUpdateContratDeLocation();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(ContratDeLocation donnees) throws SQLException {
		RequeteDeleteContratDeLocation requeteDelete = new RequeteDeleteContratDeLocation();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public ContratDeLocation findById(String... id) throws SQLException {
		RequeteSelectContratDeLocationByID requeteSelectById = new RequeteSelectContratDeLocationByID();
	    DaoLouable daoLouable = new DaoLouable(this.connection);
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
	                int idLouable = rs.getInt("Id_Louable");
	                Louable louable = daoLouable.findById(String.valueOf(idLouable));

					return new ContratDeLocation(
							rs.getInt("Id_Contrat_de_location"), 
							rs.getDate("DateDebut"),
							rs.getDate("DateFin"), 
							rs.getDouble("MontantLoyer"), 
							rs.getDouble("ProvisionsCharges"),
							rs.getString("TypeContrat"), 
							rs.getDate("DateAnniversaire"), 
							rs.getDouble("IndiceICC"),
							rs.getDouble("MontantCaution"), 
							rs.getString("NomCaution"),
							rs.getString("AdresseCaution"), 
							louable);
				}
			}
		}
		return null;
	}

	@Override
	public List<ContratDeLocation> findAll() throws SQLException {
		RequeteSelectContratDeLocation requeteSelectAll = new RequeteSelectContratDeLocation();
		List<ContratDeLocation> contrats = new ArrayList<>();
	    DaoLouable daoLouable = new DaoLouable(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				int idLouable = rs.getInt("Id_Louable");
                Louable louable = daoLouable.findById(String.valueOf(idLouable));

				contrats.add(new ContratDeLocation(
						rs.getInt("Id_Contrat_de_location"), 
						rs.getDate("DateDebut"),
						rs.getDate("DateFin"), 
						rs.getDouble("MontantLoyer"), 
						rs.getInt("ProvisionsCharges"),
						rs.getString("TypeContrat"), 
						rs.getDate("DateAnniversaire"), 
						rs.getDouble("IndiceICC"),
						rs.getDouble("MontantCaution"), 
						rs.getString("NomCaution"),
						rs.getString("AdresseCaution"), 
						louable));
			}
		}
		return contrats;
	}
}
