package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

public class RequeteCreateCharge implements Requete<Charge> {

	@Override
	public String requete() {
		return "INSERT INTO Charge (Id_Charge, TypeCharge, Montant, Recuperable, PeriodeDebut, PeriodeFin, Id_Facture, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Charge donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdCharge());
		prSt.setString(2, donnees.getTypeCharge());
		prSt.setDouble(3, donnees.getMontant());
		prSt.setString(4, donnees.isRecuperable());
		
		java.util.Date utilDate = donnees.getPeriodeDebut();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
	    
	    java.util.Date utilDate1 = donnees.getPeriodeFin();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(6, sqlDate);
	    } else {
	        prSt.setNull(6, java.sql.Types.DATE);
	    }
		
		
		prSt.setInt(7, donnees.getFacture().getIdFacture());
		prSt.setInt(8, donnees.getLouable().getIdLouable());
	}
}
