package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;

public class RequeteCreateCorrespondre implements Requete<Correspondre> {

	@Override
	public String requete() {
		return "INSERT INTO Correspondre (Id_Locataire, Id_Contrat_de_location) VALUES (?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Correspondre donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
		prSt.setInt(2, donnee.getIdContratDeLocation());
	}
}
