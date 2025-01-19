package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Taxe;
import modele.dao.requetes.Requete;

public class RequeteCreateTaxe implements Requete<Taxe> {

	@Override
	public String requete() {
		return "INSERT INTO Taxe (Id_Taxe, MontantTaxeFoncieres, DatePaiement, DateTaxe, Id_Immeuble) VALUES (?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Taxe donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdTaxe());
		prSt.setDouble(2, donnees.getMontantTaxeFoncieres());

		java.util.Date utilDate = donnees.getDateTaxe();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	    
	    java.util.Date utilDate1 = donnees.getDatePaiement();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(4, sqlDate);
	    } else {
	        prSt.setNull(4, java.sql.Types.DATE);
	    }
		
		prSt.setInt(5, donnees.getImmeuble().getIdImmeuble());
	}
}
