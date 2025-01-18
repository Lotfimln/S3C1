package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Indexer;
import modele.dao.requetes.create.RequeteCreateIndexer;
import modele.dao.requetes.delete.RequeteDeleteIndexer;
import modele.dao.requetes.select.RequeteSelectIndexer;
import modele.dao.requetes.select.RequeteSelectIndexerByImmeuble;
import modele.dao.requetes.update.RequeteUpdateIndexer;
import modele.dao.requetes.update.RequeteUpdateIndexerByImmeuble;
import modele.dao.requetes.update.RequeteUpdateIndexerByIndexCompteur;

public class DaoIndexer implements Dao<Indexer> {

	private Connection connection;

	public DaoIndexer(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Indexer donnees) throws SQLException {
		RequeteCreateIndexer requeteCreate = new RequeteCreateIndexer();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
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
	

	public void updateByIndexCompteur(Indexer donnees) throws SQLException {
		RequeteUpdateIndexerByIndexCompteur requeteUpdate = new RequeteUpdateIndexerByIndexCompteur();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
	

	public void updateByImmeuble(Indexer donnees) throws SQLException {
		RequeteUpdateIndexerByImmeuble requeteUpdate = new RequeteUpdateIndexerByImmeuble();
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
		RequeteSelectIndexerByImmeuble requeteSelectImmeuble = new RequeteSelectIndexerByImmeuble();
		List<Indexer> indexers = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectImmeuble.requete())) {
			try (ResultSet rs = prSt.executeQuery()) {
				while (rs.next()) {
					indexers.add(new Indexer(
							rs.getInt("Id_Index_Compteur"), 
							rs.getInt("Id_Immeuble")));
				}
			}
		}
		return indexers;
	}
	
	public List<Indexer> findByIndexCompteur(String[] params) throws SQLException {
	    List<Indexer> result = new ArrayList<>();
	    RequeteSelectIndexerByImmeuble requeteSelectImmeuble = new RequeteSelectIndexerByImmeuble();
	    try (PreparedStatement pstmt = connection.prepareStatement(requeteSelectImmeuble.requete())) {
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
