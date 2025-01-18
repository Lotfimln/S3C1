package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Immeuble;
import modele.Taxe;
import modele.dao.requetes.create.RequeteCreateTaxe;
import modele.dao.requetes.delete.RequeteDeleteTaxe;
import modele.dao.requetes.select.RequeteSelectTaxe;
import modele.dao.requetes.select.RequeteSelectTaxeByID;
import modele.dao.requetes.update.RequeteUpdateTaxe;

public class DaoTaxe implements Dao<Taxe> {

	private Connection connection;

	public DaoTaxe(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Taxe donnees) throws SQLException {
		RequeteCreateTaxe requeteCreate = new RequeteCreateTaxe();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Taxe donnees) throws SQLException {
		RequeteUpdateTaxe requeteUpdate = new RequeteUpdateTaxe();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Taxe donnees) throws SQLException {
		RequeteDeleteTaxe requeteDelete = new RequeteDeleteTaxe();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Taxe findById(String... id) throws SQLException {
		RequeteSelectTaxeByID requeteSelectById = new RequeteSelectTaxeByID();
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
					int idImmeuble = rs.getInt("Id_Immeuble");
	                Immeuble immeuble= daoImmeuble.findById(String.valueOf(idImmeuble));

					return new Taxe(rs.getInt("Id_Taxe"), rs.getDouble("MontantTaxeFoncieres"),
							rs.getDate("DateTaxe"), immeuble);
				}
			}
		}
		return null;
	}

	@Override
	public List<Taxe> findAll() throws SQLException {
		RequeteSelectTaxe requeteSelectAll = new RequeteSelectTaxe();
		List<Taxe> taxes = new ArrayList<>();
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				int idImmeuble = rs.getInt("Id_Immeuble");
                Immeuble immeuble= daoImmeuble.findById(String.valueOf(idImmeuble));

				taxes.add(new Taxe(
						rs.getInt("Id_Taxe"), 
						rs.getDouble("MontantTaxeFoncieres"),
						rs.getDate("DateTaxe"), 
						immeuble));
			}
		}
		return taxes;
	}
}
