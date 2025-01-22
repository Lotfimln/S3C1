package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;
import modele.dao.requetes.Requete;

public class RequeteDeleteEntreprise implements Requete<Entreprise> {

	@Override
	public String requete() {
		return "DELETE FROM Entreprise WHERE Id_Entreprise = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Entreprise donnee) throws SQLException {
		return;
	}
}
