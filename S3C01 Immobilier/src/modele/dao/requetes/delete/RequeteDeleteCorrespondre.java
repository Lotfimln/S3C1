package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;

public class RequeteDeleteCorrespondre implements Requete<Correspondre> {

	@Override
	public String requete() {
		return "DELETE FROM correspondre WHERE Id_Locataire = ? AND Id_Contrat_de_location = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Correspondre donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
		prSt.setInt(2, donnee.getIdContratDeLocation());
	}
}
