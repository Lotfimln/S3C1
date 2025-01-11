package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Assureur;
import modele.Garage;
import modele.Immeuble;
import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteGarage;
import modele.dao.requetes.delete.RequeteDeleteLouable;
import modele.dao.requetes.select.RequeteSelectGarage;
import modele.dao.requetes.select.RequeteSelectGarageByID;
import modele.dao.requetes.select.RequeteSelectGarageLouable;
import modele.dao.requetes.select.RequeteSelectGarageLouableByID;
import modele.dao.requetes.update.RequeteUpdateGarage;

public class DaoGarage implements Dao<Garage> {

	private Connection connection;

	public DaoGarage(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Garage donnees) throws SQLException {
		String sql = "INSERT INTO Garage (Id_Louable) VALUES (?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, donnees.getIdLouable());
			prSt.executeUpdate();
		}
			String sqlLouable = "INSERT INTO Louable (Id_Louable, Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, Id_Immeuble, Id_Assurance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prStLouable = this.connection.prepareStatement(sqlLouable)) {
			prStLouable.setInt(1, donnees.getIdLouable());
			prStLouable.setString(2, donnees.getAdresse());
			prStLouable.setDouble(3, donnees.getSuperficie());
			prStLouable.setInt(4, donnees.getNumeroFiscal());
			prStLouable.setString(5, donnees.getStatut());
			java.util.Date utilDate = donnees.getDateAnniversaire();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  
            prStLouable.setDate(6, sqlDate);
			prStLouable.setInt(7, donnees.getImmeuble().getIdImmeuble());
			prStLouable.setInt(8, donnees.getAssurance().getIdAssurance());
			prStLouable.executeUpdate();
		}
			
	}
	@Override
	public void update(Garage donnees) throws SQLException {
		RequeteUpdateGarage requeteUpdate = new RequeteUpdateGarage();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Garage donnees) throws SQLException {
		RequeteDeleteGarage requeteDelete = new RequeteDeleteGarage();
		RequeteDeleteLouable requeteDeleteLouable = new RequeteDeleteLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDeleteLouable.requete())) {
			requeteDeleteLouable.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Garage findById(String... id) throws SQLException {
		RequeteSelectGarageLouableByID requeteSelectById = new RequeteSelectGarageLouableByID();
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);
	    
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
					String idImmeuble = rs.getString("Id_Immeuble");
	                int idAssureur = rs.getInt("Id_Assurance");
	                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));
	                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));
	                
					return new Garage(rs.getInt("Id_Louable"), rs.getString("Adresse"), 
							  rs.getDouble("Superficie"), rs.getInt("NumeroFiscal"), 
							  rs.getString("Statut"), rs.getDate("DateAnniversaire"), 
							  rs.getDate("DateAcqui"), immeuble, assureur);
				}
			}
		}
		return null;
	}
	@Override
	public List<Garage> findAll() throws SQLException {
		RequeteSelectGarageLouable requeteSelectAll = new RequeteSelectGarageLouable();
		List<Garage> garages = new ArrayList<>();
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);
	    
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				String idImmeuble = rs.getString("Id_Immeuble");
                int idAssureur = rs.getInt("Id_Assurance");
                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));
                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));
                
				garages.add(new Garage(rs.getInt("Id_Louable"), rs.getString("Adresse"), 
						  rs.getDouble("Superficie"), rs.getInt("NumeroFiscal"), 
						  rs.getString("Statut"), rs.getDate("DateAnniversaire"), 
						  rs.getDate("DateAcqui"), immeuble, assureur));
			}
		return garages;
		}
	}
}