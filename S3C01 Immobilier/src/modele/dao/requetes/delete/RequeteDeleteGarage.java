package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;

public class RequeteDeleteGarage implements Requete<Garage> {

	@Override
	public String requete() {
		return "DELETE FROM Garage WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Garage donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdLouable());
	}
}
