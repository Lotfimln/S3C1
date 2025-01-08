package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Colocataire;
import modele.dao.requetes.Requete;

public class RequeteDeleteColocataire implements Requete<Colocataire> {

	@Override
	public String requete() {
		return "DELETE FROM Colocataire WHERE Id_Locataire = ? AND Id_Locataire_1 = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Colocataire donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdLocataire());
		prSt.setInt(2, donnee.getIdLocataire1());
	}
}
