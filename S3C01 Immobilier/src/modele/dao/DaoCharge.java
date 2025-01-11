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
		String sql = "INSERT INTO Charge (Id_Charge, Type, Montant, Recuperable, PeriodeDebut, PeriodeFin, Id_Facture, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, donnees.getIdCharge());
			prSt.setString(2, donnees.getType());
			prSt.setDouble(3, donnees.getMontant());
			prSt.setString(4, donnees.isRecuperable());
			java.util.Date utilDate = donnees.getPeriodeDebut();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Convertir en java.sql.Date
            prSt.setDate(5, sqlDate);
			java.util.Date utilDate1 = donnees.getPeriodeFin();
            java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());  // Convertir en java.sql.Date
            prSt.setDate(6, sqlDate1);
			prSt.setInt(7, donnees.getFacture().getIdFacture());
			prSt.setInt(8, donnees.getLouable().getIdLouable());
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
	public void delete(Charge donnees) throws SQLException {
		RequeteDeleteCharge requeteDelete = new RequeteDeleteCharge();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
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
	                    rs.getString("Type_Charge"),
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
	                rs.getString("Type_Charge"),
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
