package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Immeuble;
import modele.dao.requetes.Requete;

public class RequeteCreateImmeuble implements Requete<Immeuble> {

	@Override
	public String requete() {
		return "INSERT INTO Immeuble (Id_Immeuble, Adresse) VALUES (?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Immeuble donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdImmeuble());
        prSt.setString(2, donnees.getAdresse());
	}
}
