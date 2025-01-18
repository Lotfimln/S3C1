package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;
import modele.dao.requetes.Requete;

public class RequeteCreateEntreprise implements Requete<Entreprise> {

	@Override
	public String requete() {
		return "INSERT INTO Entreprise (Id_Entreprise, Nom, SIREN, Adresse) VALUES (?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[0]);
		prSt.setString(3, id[1]);
		prSt.setString(4, id[2]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Entreprise donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdEntreprise());
		prSt.setString(2, donnees.getNom());
		prSt.setString(3, donnees.getSiren());
		prSt.setString(4, donnees.getAdresse());
	}
}
