package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.IndexCompteur;
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
		String sql = "INSERT INTO Index_Compteur (TypeCompteur, ValeurCourante, AncienneValeur, DateReleve) VALUES (?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setString(1, donnees.getTypeCompteur());
			prSt.setDouble(2, donnees.getValeurCourante());
			prSt.setDouble(3, donnees.getAncienneValeur());
			prSt.setDate(4, new java.sql.Date(donnees.getDateReleve().getTime()));
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
							rs.getDouble("AncienneValeur"), 
							rs.getDate("DateReleve"));
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
						rs.getDouble("AncienneValeur"), 
						rs.getDate("DateReleve")));
			}
		}
		return compteurs;
	}
}
