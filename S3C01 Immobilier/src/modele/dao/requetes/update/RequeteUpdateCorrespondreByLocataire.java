package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;

public class RequeteUpdateCorrespondreByLocataire implements Requete<Correspondre> {

	@Override
	public String requete() {
		return "UPDATE correspondre SET Id_Locataire = ?, Id_Contrat_de_location = ? WHERE Id_Locataire = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Correspondre data) throws SQLException {
		prSt.setString(1, data.getIdLocataire());
		prSt.setInt(2, data.getIdContratDeLocation());
		prSt.setString(3, data.getIdLocataire());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
