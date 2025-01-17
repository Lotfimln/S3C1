package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Taxe;
import modele.dao.requetes.Requete;

public class RequeteUpdateTaxe implements Requete<Taxe> {

	@Override
	public String requete() {
		return "UPDATE Taxe SET Id_Taxe = ?, MontantTaxeFoncieres = ?, DateTaxe = ?, Id_Immeuble = ? WHERE Id_Taxe = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Taxe data) throws SQLException {
		prSt.setInt(1, data.getIdTaxe());
		prSt.setDouble(2, data.getMontantTaxeFoncieres());
		
		java.util.Date utilDate = data.getDateTaxe();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	    
		prSt.setInt(4, data.getImmeuble().getIdImmeuble());
		prSt.setInt(5, data.getIdTaxe());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
