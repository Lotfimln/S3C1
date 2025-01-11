package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

public class RequeteUpdateDiagnostic implements Requete<Diagnostic> {

	@Override
	public String requete() {
		return "UPDATE Diagnostic SET Type = ?, DateDiagnostic = ?, Id_Louable = ? WHERE Id_Diagnostic = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic data) throws SQLException {
		prSt.setString(1, data.getType());
		java.util.Date utilDate = data.getDateDiagnostic();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		prSt.setInt(3, data.getLouable().getIdLouable());
		prSt.setInt(4, data.getIdDiagnostic());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
