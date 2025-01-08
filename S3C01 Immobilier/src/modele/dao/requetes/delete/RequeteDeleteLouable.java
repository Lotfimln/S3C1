package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;

public class RequeteDeleteLouable implements Requete<Louable> {

	@Override
	public String requete() {
		return "DELETE FROM Louable WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Louable donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdLouable());
	}
}
