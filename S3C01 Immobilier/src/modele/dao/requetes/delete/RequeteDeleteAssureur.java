package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;

public class RequeteDeleteAssureur implements Requete<Assureur> {

	@Override
	public String requete() {
		return "DELETE FROM Assureur WHERE Id_Assureur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Assureur donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdAssureur());
	}
}
