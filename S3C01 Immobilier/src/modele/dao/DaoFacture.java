package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Entreprise;
import modele.Facture;
import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteFacture;
import modele.dao.requetes.select.RequeteSelectFacture;
import modele.dao.requetes.select.RequeteSelectFactureByID;
import modele.dao.requetes.update.RequeteUpdateFacture;

public class DaoFacture implements Dao<Facture> {

	private Connection connection;

	public DaoFacture(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Facture donnees) throws SQLException {
		String sql = "INSERT INTO Facture (Montant, DateFacture, ReferenceDevis, Entreprise, DatePaiement, Id_Entreprise, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setDouble(1, donnees.getMontant());
			java.util.Date utilDate = donnees.getDateFacture();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            prSt.setDate(2, sqlDate);
			prSt.setString(3, donnees.getReferenceDevis());
			prSt.setString(4, donnees.getEntreprise());
			java.util.Date utilDate1 = donnees.getDatePaiement();
            java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
            prSt.setDate(5, sqlDate1);
			prSt.setInt(6, donnees.getEntrepriseAssociee().getIdEntreprise());
			prSt.setInt(7, donnees.getLouable().getIdLouable());
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
	    DaoEntreprise daoFacture = new DaoEntreprise(this.connection);
	    DaoLouable daoEntreprise = new DaoLouable(this.connection);
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
	                int idEntreprise = rs.getInt("Id_Facture");
	                int idLouable = rs.getInt("Id_Louable");

	                Entreprise entreprise = daoFacture.findById(String.valueOf(idEntreprise));
	                Louable louable = daoEntreprise.findById(String.valueOf(idLouable));

					return new Facture(
							rs.getInt("Id_Facture"), 
							rs.getDouble("Montant"), 
							rs.getDate("DateFacture"),
							rs.getString("ReferenceDevis"), 
							rs.getString("Entreprise"), 
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
						rs.getString("Entreprise"), 
						rs.getDate("DatePaiement"),
						entreprise, 
						louable));
			}
		}
		return factures;
	}
}
