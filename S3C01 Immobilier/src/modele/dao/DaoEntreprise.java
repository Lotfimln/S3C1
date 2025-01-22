package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Entreprise;
import modele.dao.requetes.create.RequeteCreateEntreprise;
import modele.dao.requetes.delete.RequeteDeleteEntreprise;
import modele.dao.requetes.select.RequeteSelectEntreprise;
import modele.dao.requetes.select.RequeteSelectEntrepriseByID;
import modele.dao.requetes.update.RequeteUpdateEntreprise;

public class DaoEntreprise implements Dao<Entreprise> {

	private Connection connection;

	public DaoEntreprise(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Entreprise donnees) throws SQLException {
		RequeteCreateEntreprise requeteCreate = new RequeteCreateEntreprise();	
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Entreprise donnees) throws SQLException {
		RequeteUpdateEntreprise requeteUpdate = new RequeteUpdateEntreprise();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(String... id) throws SQLException {
		RequeteDeleteEntreprise requeteDelete = new RequeteDeleteEntreprise();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, id);
			prSt.executeUpdate();
		}
	}

	@Override
	public Entreprise findById(String... id) throws SQLException {
		RequeteSelectEntrepriseByID requeteSelectById = new RequeteSelectEntrepriseByID();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
					return new Entreprise(
							rs.getInt("Id_Entreprise"), 
							rs.getString("Nom"), 
							rs.getString("SIREN"),
							rs.getString("Adresse"));
				}
			}
		}
		return null;
	}

	@Override
	public List<Entreprise> findAll() throws SQLException {
		RequeteSelectEntreprise requeteSelectAll = new RequeteSelectEntreprise();
		List<Entreprise> entreprises = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				entreprises.add(new Entreprise(
						rs.getInt("Id_Entreprise"), 
						rs.getString("Nom"), 
						rs.getString("SIREN"),
						rs.getString("Adresse")));
			}
		}
		return entreprises;
	}
}
