package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Diagnostic;
import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteDiagnostic;
import modele.dao.requetes.select.RequeteSelectDiagnostic;
import modele.dao.requetes.select.RequeteSelectDiagnosticByID;
import modele.dao.requetes.update.RequeteUpdateDiagnostic;

public class DaoDiagnostic implements Dao<Diagnostic> {

	private Connection connection;

	public DaoDiagnostic(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Diagnostic donnees) throws SQLException {
		String sql = "INSERT INTO Diagnostic (Type, DateDiagnostic, Id_Louable) VALUES (?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setString(1, donnees.getType());
			prSt.setDate(2, new java.sql.Date(donnees.getDateDiagnostic().getTime()));
			prSt.setInt(3, donnees.getLouable().getIdLouable());
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Diagnostic donnees) throws SQLException {
		RequeteUpdateDiagnostic requeteUpdate = new RequeteUpdateDiagnostic();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Diagnostic donnees) throws SQLException {
		RequeteDeleteDiagnostic requeteDelete = new RequeteDeleteDiagnostic();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public Diagnostic findById(String... id) throws SQLException {
		RequeteSelectDiagnosticByID requeteSelectById = new RequeteSelectDiagnosticByID();
	    DaoLouable daoLouable = new DaoLouable(this.connection);
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
	                int idLouable = rs.getInt("Id_Louable");
	                Louable louable = daoLouable.findById(String.valueOf(idLouable));
	                
					return new Diagnostic(rs.getInt("Id_Diagnostic"), rs.getString("Type_Diag"),
							rs.getDate("DateDiagnostic"), louable);
				}
			}
		}
		return null;
	}

	@Override
	public List<Diagnostic> findAll() throws SQLException {
		RequeteSelectDiagnostic requeteSelectAll = new RequeteSelectDiagnostic();
		List<Diagnostic> diagnostics = new ArrayList<>();
	    DaoLouable daoLouable = new DaoLouable(this.connection);
	    
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				int idLouable = rs.getInt("Id_Louable");
                Louable louable = daoLouable.findById(String.valueOf(idLouable));
                
				diagnostics.add(new Diagnostic(rs.getInt("Id_Diagnostic"), rs.getString("Type_Diag"),
						rs.getDate("DateDiagnostic"), louable));
			}
		}
		return diagnostics;
	}
}
