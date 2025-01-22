package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

public class RequeteCreateDiagnostic implements Requete<Diagnostic> {

	@Override
	public String requete() {
		return "INSERT INTO Diagnostic (Id_Diagnostic, TypeDiagnostic, DateDiagnostic, Id_Louable) VALUES (?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdDiagnostic());
		prSt.setString(2, donnees.getTypeDiagnostic());

		java.util.Date utilDate = donnees.getDateDiagnostic();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
		
		prSt.setInt(4, donnees.getLouable().getIdLouable());
	}
}
