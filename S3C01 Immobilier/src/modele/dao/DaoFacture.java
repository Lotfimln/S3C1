package modele.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Entreprise;
import modele.Facture;
import modele.Louable;
import modele.dao.requetes.create.RequeteCreateFacture;
import modele.dao.requetes.delete.RequeteDeleteFacture;
import modele.dao.requetes.select.RequeteSelectFacture;
import modele.dao.requetes.select.RequeteSelectFactureByID;
import modele.dao.requetes.update.RequeteUpdateFacture;
import oracle.jdbc.OracleTypes;

public class DaoFacture implements Dao<Facture> {

	private Connection connection;

	public DaoFacture(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Facture donnees) throws SQLException {
		RequeteCreateFacture requeteCreate = new RequeteCreateFacture();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Facture donnees) throws SQLException {
		RequeteUpdateFacture requeteUpdate = new RequeteUpdateFacture();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Facture donnees) throws SQLException {
		RequeteDeleteFacture requeteDelete = new RequeteDeleteFacture();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Facture findById(String... id) throws SQLException {
		RequeteSelectFactureByID requeteSelectById = new RequeteSelectFactureByID();
	    DaoEntreprise daoEntreprise = new DaoEntreprise(this.connection);
	    DaoLouable daoLouable = new DaoLouable(this.connection);
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
	                int idEntreprise = rs.getInt("Id_Facture");
	                int idLouable = rs.getInt("Id_Louable");

	                Entreprise entreprise = daoEntreprise.findById(String.valueOf(idEntreprise));
	                Louable louable = daoLouable.findById(String.valueOf(idLouable));

					return new Facture(
							rs.getInt("Id_Facture"), 
							rs.getDouble("Montant"), 
							rs.getDate("DateFacture"),
							rs.getString("ReferenceDevis"), 
							rs.getDate("DatePaiement"),
							entreprise, 
							louable);
				}
			}
		}
		return null;
	}

	@Override
	public List<Facture> findAll() throws SQLException {
		RequeteSelectFacture requeteSelectAll = new RequeteSelectFacture();
		List<Facture> factures = new ArrayList<>();
	    DaoEntreprise daoEntreprise = new DaoEntreprise(this.connection);
	    DaoLouable daoLouable = new DaoLouable(this.connection);
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
                int idEntreprise = rs.getInt("Id_Entreprise");
                int idLouable = rs.getInt("Id_Louable");

                Entreprise entreprise = daoEntreprise.findById(String.valueOf(idEntreprise));
                Louable louable = daoLouable.findById(String.valueOf(idLouable));

				factures.add(new Facture(
						rs.getInt("Id_Facture"), 
						rs.getDouble("Montant"), 
						rs.getDate("DateFacture"),
						rs.getString("ReferenceDevis"), 
						rs.getDate("DatePaiement"),
						entreprise, 
						louable));
			}
		}
		return factures;
	}
	
	public List<Facture> facturesImpayeParImmeuble() throws SQLException {
	    String sql = "{ call FacturesImpayeParImmeuble(?) }";
	    List<Facture> factures = new ArrayList<>();
	    DaoEntreprise daoEntreprise = new DaoEntreprise(this.connection);
	    DaoLouable daoLouable = new DaoLouable(this.connection);
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, OracleTypes.CURSOR);
	        stmt.execute();
	        
	        try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
	            while (rs.next()) {
	            	
	            	int idEntreprise = rs.getInt("Id_Facture");
	                int idLouable = rs.getInt("Id_Louable");
	                
	                Entreprise entreprise = daoEntreprise.findById(String.valueOf(idEntreprise));
	                Louable louable = daoLouable.findById(String.valueOf(idLouable));
	            	
	                Facture facture = new Facture(
							rs.getInt("Id_Facture"), 
							rs.getDouble("Montant"), 
							rs.getDate("DateFacture"),
							rs.getString("ReferenceDevis"), 
							rs.getDate("DatePaiement"),
							entreprise, 
							louable);
	                factures.add(facture);
	            }
	        }
	    }
	    return factures;
	}

}
