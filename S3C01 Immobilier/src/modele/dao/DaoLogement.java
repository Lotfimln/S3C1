package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Assureur;
import modele.Logement;
import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteLogement;
import modele.dao.requetes.delete.RequeteDeleteLouable;
import modele.dao.requetes.select.RequeteSelectLogementLouable;
import modele.dao.requetes.select.RequeteSelectLogementLouableByID;
import modele.dao.requetes.update.RequeteUpdateLogement;

public class DaoLogement implements Dao<Logement> {

	private Connection connection;

	public DaoLogement(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Logement donnees) throws SQLException {
		String sql = "INSERT INTO Logement (Id_Louable, NbPièces) VALUES (?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, donnees.getIdLouable());
			prSt.setInt(2, donnees.getNbPieces());
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
	public void update(Logement donnees) throws SQLException {
		RequeteUpdateLogement requeteUpdate = new RequeteUpdateLogement();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Logement donnees) throws SQLException {
		RequeteDeleteLogement requeteDeleteLogement = new RequeteDeleteLogement();
		RequeteDeleteLouable requeteDeleteLouable = new RequeteDeleteLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDeleteLogement.requete())) {
			requeteDeleteLogement.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDeleteLouable.requete())) {
			requeteDeleteLouable.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Logement findById(String... id) throws SQLException {
		RequeteSelectLogementLouableByID requeteSelectById = new RequeteSelectLogementLouableByID();
	    DaoLouable daoLouable = new DaoLouable(this.connection);
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
	                int idLouable = rs.getInt("Id_Louable");
	                int idAssureur = rs.getInt("Is_Assureur");
	                Louable louable = daoLouable.findById(String.valueOf(idLouable));
	                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));

					return new Logement(rs.getInt("Id_Louable"), rs.getString("Adresse"), rs.getDouble("Superficie"),
							   rs.getInt("Numero Fiscal"), rs.getString("Statut"), rs.getDate("DateAnniversaire"),
							   assureur, rs.getInt("NbPiece"), louable);
				}
			}
		}
		return null;
	}

	@Override
	public List<Logement> findAll() throws SQLException {
		RequeteSelectLogementLouable requeteSelectAll = new RequeteSelectLogementLouable();
		List<Logement> logements = new ArrayList<>();
	    DaoLouable daoLouable = new DaoLouable(this.connection);
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
                int idLouable = rs.getInt("Id_Louable");
                int idAssureur = rs.getInt("Is_Assureur");
                Louable louable = daoLouable.findById(String.valueOf(idLouable));
                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));

				logements.add(new Logement(rs.getInt("Id_Louable"), rs.getString("Adresse"), rs.getDouble("Superficie"),
										   rs.getInt("Numero Fiscal"), rs.getString("Statut"), rs.getDate("DateAnniversaire"),
										   assureur, rs.getInt("NbPiece"), louable));
			}
		}
		return logements;
	}
}
