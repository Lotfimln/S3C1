package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;
import modele.dao.requetes.Requete;

public class RequeteUpdateEntreprise implements Requete<Entreprise> {

	@Override
	public String requete() {
		return "UPDATE Entreprise SET Nom = ?, SIREN = ?, Adresse = ? WHERE Id_Entreprise = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Entreprise data) throws SQLException {

		prSt.setString(1, data.getNom());
		prSt.setString(2, data.getSiren());
	    prSt.setString(3, data.getAdresse());
		prSt.setDouble(4, data.getIdEntreprise());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
	}
}