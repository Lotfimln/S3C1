package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Colocataire;
import modele.dao.requetes.Requete;

public class RequeteCreateColocataire implements Requete<Colocataire> {

	@Override
	public String requete() {
		return "INSERT INTO Colocataire (Id_Locataire, Id_Locataire_1) VALUES (?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Colocataire donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
		prSt.setString(2, donnee.getIdLocataire1());
	}
}
