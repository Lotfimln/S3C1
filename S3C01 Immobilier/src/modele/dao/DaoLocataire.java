package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Locataire;
import modele.dao.requetes.delete.RequeteDeleteLocataire;
import modele.dao.requetes.select.RequeteSelectLocataire;
import modele.dao.requetes.select.RequeteSelectLocataireByID;
import modele.dao.requetes.update.RequeteUpdateLocataire;

public class DaoLocataire implements Dao<Locataire> {

	private Connection connection;

	public DaoLocataire(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Locataire donnees) throws SQLException {
		String sql = "INSERT INTO Locataire (Id_Locataire, Nom, Prenom, Mail, Telephone, DateNaissance, DateDepart) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setString(1, donnees.getIdLocataire());
			prSt.setString(2, donnees.getNom());
			prSt.setString(3, donnees.getPrenom());
			prSt.setString(4, donnees.getMail());
			prSt.setString(5, donnees.getTelephone());
			java.util.Date utilDate = donnees.getDateDepart();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            prSt.setDate(6, sqlDate);
			java.util.Date utilDate1 = donnees.getDateDepart();
            java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
            prSt.setDate(7, sqlDate1);
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
	public void delete(Locataire donnees) throws SQLException {
		RequeteDeleteLocataire requeteDelete = new RequeteDeleteLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
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
}
