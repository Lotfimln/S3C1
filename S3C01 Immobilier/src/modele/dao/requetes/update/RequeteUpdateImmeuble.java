package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Immeuble;
import modele.dao.requetes.Requete;

public class RequeteUpdateImmeuble implements Requete<Immeuble> {

	@Override
	public String requete() {
		return "UPDATE Immeuble SET Id_Immeuble = ?, Adresse = ? WHERE Id_Immeuble = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Immeuble data) throws SQLException {
		prSt.setInt(1, data.getIdImmeuble());
		prSt.setString(2, data.getAdresse());
		prSt.setInt(3, data.getIdImmeuble());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
