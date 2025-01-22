package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Charge;
import modele.Facture;
import modele.Louable;
import modele.dao.requetes.create.RequeteCreateCharge;
import modele.dao.requetes.delete.RequeteDeleteCharge;
import modele.dao.requetes.select.RequeteSelectCharge;
import modele.dao.requetes.select.RequeteSelectChargeByID;
import modele.dao.requetes.update.RequeteUpdateCharge;

public class DaoCharge implements Dao<Charge> {

	private Connection connection;

	public DaoCharge(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Charge donnees) throws SQLException {
		RequeteCreateCharge requeteCreate = new RequeteCreateCharge();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Charge donnees) throws SQLException {
		RequeteUpdateCharge requeteUpdate = new RequeteUpdateCharge();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(String... id) throws SQLException {
		RequeteDeleteCharge requeteDelete = new RequeteDeleteCharge();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, id);
			prSt.executeUpdate();
		}
	}

	@Override
	public Charge findById(String... id) throws SQLException {
	    RequeteSelectChargeByID requeteSelectById = new RequeteSelectChargeByID();
	    DaoFacture daoFacture = new DaoFacture(this.connection);
	    DaoLouable daoLouable = new DaoLouable(this.connection);

	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
	        requeteSelectById.parametres(prSt, id);
	        try (ResultSet rs = prSt.executeQuery()) {
	            if (rs.next()) {
	                int idFacture = rs.getInt("Id_Facture");
	                int idLouable = rs.getInt("Id_Louable");

	                Facture facture = daoFacture.findById(String.valueOf(idFacture));
	                Louable louable = daoLouable.findById(String.valueOf(idLouable));

	                return new Charge(
	                    rs.getInt("Id_Charge"),
	                    rs.getString("TypeCharge"),
	                    rs.getDouble("Montant"),
	                    rs.getString("Recuperable"),
	                    rs.getDate("PeriodeDebut"),
	                    rs.getDate("PeriodeFin"),
	                    facture,
	                    louable
	                );
	            }
	        }
	    }
	    return null; // Retourne null si aucune charge trouvée
	}

	@Override
	public List<Charge> findAll() throws SQLException {
	    RequeteSelectCharge requeteSelectAll = new RequeteSelectCharge();
	    List<Charge> charges = new ArrayList<>();
	    DaoFacture daoFacture = new DaoFacture(this.connection);
	    DaoLouable daoLouable = new DaoLouable(this.connection);

	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
	         ResultSet rs = prSt.executeQuery()) {
	        while (rs.next()) {
	            int idFacture = rs.getInt("Id_Facture");
	            int idLouable = rs.getInt("Id_Louable");

	            Facture facture = daoFacture.findById(String.valueOf(idFacture));
	            Louable louable = daoLouable.findById(String.valueOf(idLouable));

	            charges.add(new Charge(
	                rs.getInt("Id_Charge"),
	                rs.getString("TypeCharge"),
	                rs.getDouble("Montant"),
	                rs.getString("Recuperable"),
	                rs.getDate("PeriodeDebut"),
	                rs.getDate("PeriodeFin"),
	                facture,
	                louable
	            ));
	        }
	    }
	    return charges;
	}
}
