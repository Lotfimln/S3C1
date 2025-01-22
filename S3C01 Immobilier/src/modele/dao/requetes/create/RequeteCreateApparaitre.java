package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Apparaitre;
import modele.dao.requetes.Requete;

public class RequeteCreateApparaitre implements Requete<Apparaitre> {

	@Override
	public String requete() {
		return "INSERT INTO Apparaitre (Id_Charge, Id_Index_Compteur) VALUES (?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Apparaitre donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdCharge());
		prSt.setInt(2, donnee.getIdIndexCompteur());
	}
}
