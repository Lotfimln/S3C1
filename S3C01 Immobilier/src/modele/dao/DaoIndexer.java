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
import modele.dao.requetes.delete.RequeteDeleteIndexerByImmeuble;
import modele.dao.requetes.delete.RequeteDeleteIndexerByIndexCompteur;
import modele.dao.requetes.select.RequeteSelectIndexer;
import modele.dao.requetes.select.RequeteSelectIndexerByImmeuble;
import modele.dao.requetes.select.RequeteSelectIndexerByIndexCompteur;
import modele.dao.requetes.update.RequeteUpdateIndexer;

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

	@Override
	public void delete(String... id) throws SQLException {
		RequeteDeleteIndexer requeteDelete = new RequeteDeleteIndexer();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, id);
			prSt.executeUpdate();
		}
	}
	
	public void deleteById(String... id) throws SQLException {
		RequeteDeleteIndexer requeteDelete = new RequeteDeleteIndexer();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, id);
			prSt.executeUpdate();
		}
	}
	
	public void deleteByIndexCompteur(String... id) throws SQLException {
	    RequeteDeleteIndexerByIndexCompteur requeteDelete = new RequeteDeleteIndexerByIndexCompteur();
	    try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
	        requeteDelete.parametres(prSt, id);
	        prSt.executeUpdate();
	    }
	}
	
	public void deleteByImmeuble(String... id) throws SQLException {
	    RequeteDeleteIndexerByImmeuble requeteDelete = new RequeteDeleteIndexerByImmeuble();
	    try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
	        requeteDelete.parametres(prSt, id);
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
						rs.getInt("Id_Immeuble"),
						rs.getDate("DateReleve"),
                        rs.getDouble("PrixAbonnement"),
                        rs.getDate("DateRegularisation")));
			}
		}
		return indexers;
	}
	
	public List<Indexer> findByImmeuble(String... id) throws SQLException {
	    RequeteSelectIndexerByImmeuble requeteSelectIndexer = new RequeteSelectIndexerByImmeuble();
	    List<Indexer> indexers = new ArrayList<>();
	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectIndexer.requete())) {
	    	requeteSelectIndexer.parametres(prSt, id);
	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                indexers.add(new Indexer(
	                    rs.getInt("Id_Index_Compteur"), 
	                    rs.getInt("Id_Immeuble"),
	                    rs.getDate("DateReleve"),
	                    rs.getDouble("PrixAbonnement"),
	                    rs.getDate("DateRegularisation")));
	            }
	        }
	    }
	    return indexers;
	}
	
	public List<Indexer> findByIndexCompteur(String... id) throws SQLException {
	    List<Indexer> result = new ArrayList<>();
	    RequeteSelectIndexerByIndexCompteur requeteSelectIndexCompteur = new RequeteSelectIndexerByIndexCompteur();
	    try (PreparedStatement prSt = connection.prepareStatement(requeteSelectIndexCompteur.requete())) {
	    	requeteSelectIndexCompteur.parametres(prSt, id);
	    	try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                result.add(new Indexer(
	                    rs.getInt("Id_Index_Compteur"),
	                    rs.getInt("Id_Immeuble"),
	                    rs.getDate("DateReleve"),
                        rs.getDouble("PrixAbonnement"),
                        rs.getDate("DateRegularisation")
	                ));
	            }
	        }
	    }
	    return result;
	}

}
