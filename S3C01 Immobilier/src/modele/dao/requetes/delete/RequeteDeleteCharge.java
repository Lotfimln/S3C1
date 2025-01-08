package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

public class RequeteDeleteCharge implements Requete<Charge> {

	@Override
	public String requete() {
		return "DELETE FROM Charge WHERE Id_Charge = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Charge donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdCharge());
	}
}
