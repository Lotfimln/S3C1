package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;
import modele.dao.requetes.Requete;

public class RequeteUpdateEntreprise implements Requete<Entreprise> {

	@Override
	public String requete() {
		return "UPDATE Entreprise SET Id_Entreprise = ?, Nom = ?, SIREN = ?, Adresse = ? WHERE Id_Entreprise = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Entreprise data) throws SQLException {
		prSt.setInt(1, data.getIdEntreprise());
		prSt.setString(2, data.getNom());
		prSt.setString(3, data.getSiren());
	    prSt.setString(4, data.getAdresse());
		prSt.setInt(5, data.getIdEntreprise());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}