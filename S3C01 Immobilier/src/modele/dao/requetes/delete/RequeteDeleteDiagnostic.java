package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

public class RequeteDeleteDiagnostic implements Requete<Diagnostic> {

	@Override
	public String requete() {
		return "DELETE FROM Diagnostic WHERE Id_Diagnostic = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdDiagnostic());
	}
}
