package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.IndexCompteur;
import modele.dao.requetes.create.RequeteCreateIndexCompteur;
import modele.dao.requetes.delete.RequeteDeleteIndexCompteur;
import modele.dao.requetes.select.RequeteSelectIndexCompteur;
import modele.dao.requetes.select.RequeteSelectIndexCompteurByID;
import modele.dao.requetes.update.RequeteUpdateIndexCompteur;

public class DaoIndexCompteur implements Dao<IndexCompteur> {

	private Connection connection;

	public DaoIndexCompteur(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(IndexCompteur donnees) throws SQLException {
		RequeteCreateIndexCompteur requeteCreate = new RequeteCreateIndexCompteur();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(IndexCompteur donnees) throws SQLException {
		RequeteUpdateIndexCompteur requeteUpdate = new RequeteUpdateIndexCompteur();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(IndexCompteur donnees) throws SQLException {
		RequeteDeleteIndexCompteur requeteDelete = new RequeteDeleteIndexCompteur();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public IndexCompteur findById(String... id) throws SQLException {
		RequeteSelectIndexCompteurByID requeteSelectById = new RequeteSelectIndexCompteurByID();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
					return new IndexCompteur(
							rs.getInt("Id_Index_Compteur"), 
							rs.getString("TypeCompteur"),
							rs.getDouble("ValeurCourante"), 
							rs.getDouble("AncienneValeur")
						);
				}
			}
		}
		return null;
	}

	@Override
	public List<IndexCompteur> findAll() throws SQLException {
		RequeteSelectIndexCompteur requeteSelectAll = new RequeteSelectIndexCompteur();
		List<IndexCompteur> compteurs = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
			ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				compteurs.add(new IndexCompteur(
						rs.getInt("Id_Index_Compteur"), 
						rs.getString("TypeCompteur"),
						rs.getDouble("ValeurCourante"), 
						rs.getDouble("AncienneValeur")
					));
			}
		}
		return compteurs;
	}
}
