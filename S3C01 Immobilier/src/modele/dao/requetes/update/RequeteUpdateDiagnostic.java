package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

public class RequeteUpdateDiagnostic implements Requete<Diagnostic> {

	@Override
	public String requete() {
		return "UPDATE Diagnostic SET Id_Diagnostic = ?, TypeDiagnostic = ?, DateDiagnostic = ?, Id_Louable = ? WHERE Id_Diagnostic = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic data) throws SQLException {
		prSt.setInt(1, data.getIdDiagnostic());
		prSt.setString(2, data.getTypeDiagnostic());
		
		java.util.Date utilDate = data.getDateDiagnostic();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	    
		prSt.setInt(4, data.getLouable().getIdLouable());
		prSt.setInt(5, data.getIdDiagnostic());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}
