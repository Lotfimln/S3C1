package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

public class RequeteCreateCharge implements Requete<Charge> {

	@Override
	public String requete() {
		return "INSERT INTO Charge (Id_Charge, TypeCharge, Montant, Recuperable, PeriodeDebut, PeriodeFin, Id_Facture, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
		prSt.setString(6, id[5]);
		prSt.setString(7, id[6]);
		prSt.setString(8, id[7]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Charge donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdCharge());
		prSt.setString(2, donnees.getTypeCharge());
		prSt.setDouble(3, donnees.getMontant());
		prSt.setString(4, donnees.isRecuperable());
		java.util.Date utilDate = donnees.getPeriodeDebut();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
        prSt.setDate(5, sqlDate);
		java.util.Date utilDate1 = donnees.getPeriodeFin();
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime()); 
        prSt.setDate(6, sqlDate1);
		prSt.setInt(7, donnees.getFacture().getIdFacture());
		prSt.setInt(8, donnees.getLouable().getIdLouable());
	}
}
