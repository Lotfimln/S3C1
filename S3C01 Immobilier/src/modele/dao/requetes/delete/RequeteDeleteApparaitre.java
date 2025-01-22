package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Apparaitre;
import modele.dao.requetes.Requete;

public class RequeteDeleteApparaitre implements Requete<Apparaitre> {

	@Override
	public String requete() {
		return "DELETE FROM apparaitre WHERE Id_Charge = ? AND Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Apparaitre donnee) throws SQLException {
		return;
	}
}
