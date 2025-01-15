package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Indexer;
import modele.dao.requetes.delete.RequeteDeleteIndexer;
import modele.dao.requetes.select.RequeteSelectIndexer;
import modele.dao.requetes.update.RequeteUpdateIndexer;

public class DaoIndexer implements Dao<Indexer> {

	private Connection connection;

	public DaoIndexer(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Indexer donnees) throws SQLException {
		String sql = "INSERT INTO Indexer (Id_Index_Compteur, Id_Immeuble) VALUES (?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, donnees.getIdIndexCompteur());
			prSt.setInt(2, donnees.getIdImmeuble());
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Indexer donnees) throws SQLException {
		RequeteUpdateIndexer requeteUpdate = new RequeteUpdateIndexer();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Indexer donnees) throws SQLException {
		RequeteDeleteIndexer requeteDelete = new RequeteDeleteIndexer();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	// Cette methode est inutile, car elle renvoie exactement les parametres de la requete
	@Override
	public Indexer findById(String... id) throws SQLException {
		return null;
	}
	

	@Override
	public List<Indexer> findAll() throws SQLException {
		RequeteSelectIndexer requeteSelectAll = new RequeteSelectIndexer();
		List<Indexer> indexers = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				indexers.add(new Indexer(
						rs.getInt("Id_Index_Compteur"), 
						rs.getInt("Id_Immeuble")));
			}
		}
		return indexers;
	}
	
	public List<Indexer> findByImmeuble(String... id) throws SQLException {
		String sql = "SELECT * FROM Indexer WHERE Id_Immeuble = ?";
		List<Indexer> indexers = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, Integer.parseInt(id[0]));
			try (ResultSet rs = prSt.executeQuery()) {
				while (rs.next()) {
					indexers.add(new Indexer(
							rs.getInt("Id_Index_Compteur"), 
							rs.getInt("Id_Immeuble")));
				}
			}
		}
		return null;
	}
	
	public List<Indexer> findByIndexCompteur(String[] params) throws SQLException {
	    List<Indexer> result = new ArrayList<>();
	    String sql = "SELECT * FROM Indexer WHERE Id_Index_Compteur = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, params[0]);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                result.add(new Indexer(
	                    rs.getInt("Id_Index_Compteur"),
	                    rs.getInt("Id_Immeuble")
	                ));
	            }
	        }
	    }
	    return result;
	}

}
