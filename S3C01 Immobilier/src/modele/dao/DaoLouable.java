package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteLouable;
import modele.dao.requetes.update.RequeteUpdateLouable;

public class DaoLouable implements Dao<Louable> {

	private Connection connection;

	public DaoLouable(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Louable donnees) throws SQLException {
		String sql = "INSERT INTO Louable (Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, Id_Immeuble, Id_Assurance) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setString(1, donnees.getAdresse());
			prSt.setDouble(2, donnees.getSuperficie());
			prSt.setLong(3, donnees.getNumeroFiscal());
			prSt.setString(4, donnees.getStatut());
			java.util.Date utilDate = donnees.getDateAnniversaire();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Convertir en java.sql.Date
            prSt.setDate(5, sqlDate);
			prSt.setInt(6, donnees.getImmeuble().getIdImmeuble());
			prSt.setInt(7, donnees.getAssurance().getIdAssurance());
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Louable donnees) throws SQLException {
		RequeteUpdateLouable requeteUpdate = new RequeteUpdateLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Louable donnees) throws SQLException {
		RequeteDeleteLouable requeteDelete = new RequeteDeleteLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Louable findById(String... id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Louable> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
