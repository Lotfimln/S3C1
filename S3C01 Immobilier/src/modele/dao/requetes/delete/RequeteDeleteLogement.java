package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Logement;
import modele.dao.requetes.Requete;

public class RequeteDeleteLogement implements Requete<Logement> {

	@Override
	public String requete() {
		return "DELETE FROM Logement WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Logement donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdLouable());
	}
}
