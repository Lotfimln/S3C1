package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Apparaitre;
import modele.dao.requetes.Requete;

public class RequeteUpdateApparaitreByCharge implements Requete<Apparaitre> {

	@Override
	public String requete() {
		return "UPDATE apparaitre SET Id_Charge = ?, Id_Index_Compteur = ? WHERE Id_Charge = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Apparaitre data) throws SQLException {
		prSt.setInt(1, data.getIdCharge());
		prSt.setInt(2, data.getIdIndexCompteur());
		prSt.setInt(3, data.getIdCharge());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
