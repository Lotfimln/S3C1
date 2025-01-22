package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Quittances;
import modele.dao.requetes.Requete;

public class RequeteDeleteQuittances implements Requete<Quittances> {

	@Override
	public String requete() {
		return "DELETE FROM Quittances WHERE Id_Quittances = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Quittances donnee) throws SQLException {
		return;
	}
}
