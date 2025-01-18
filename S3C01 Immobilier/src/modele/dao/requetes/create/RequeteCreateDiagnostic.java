package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

public class RequeteCreateDiagnostic implements Requete<Diagnostic> {

	@Override
	public String requete() {
		return "INSERT INTO Diagnostic (Type, DateDiagnostic, Id_Louable) VALUES (?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic donnees) throws SQLException {
		prSt.setString(1, donnees.getTypeDiagnostic());
		prSt.setDate(2, new java.sql.Date(donnees.getDateDiagnostic().getTime()));
		prSt.setInt(3, donnees.getLouable().getIdLouable());
	}
}
