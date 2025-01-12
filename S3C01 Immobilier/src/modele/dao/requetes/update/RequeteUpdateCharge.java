package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

public class RequeteUpdateCharge implements Requete<Charge> {

	@Override
	public String requete() {
		return "UPDATE Charge SET Type = ?, Montant = ?, Recuperable = ?, PeriodeDebut = ?, PeriodeFin = ?, Id_Facture = ?, Id_Louable = ? WHERE Id_Charge = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Charge data) throws SQLException {
		prSt.setString(1, data.getTypeCharge());
		prSt.setDouble(2, data.getMontant());
		prSt.setString(3, data.isRecuperable());
		java.util.Date utilDate = data.getPeriodeDebut();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(4, sqlDate);
	    } else {
	        prSt.setNull(4, java.sql.Types.DATE);
	    }
	    java.util.Date utilDate1 = data.getPeriodeFin();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
		prSt.setInt(6, data.getFacture().getIdFacture());
		prSt.setInt(7, data.getLouable().getIdLouable());
		prSt.setInt(8, data.getIdCharge());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
