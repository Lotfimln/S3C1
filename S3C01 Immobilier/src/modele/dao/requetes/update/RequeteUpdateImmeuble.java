package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Immeuble;
import modele.dao.requetes.Requete;

public class RequeteUpdateImmeuble implements Requete<Immeuble> {

	@Override
	public String requete() {
		return "UPDATE Immeuble SET Adresse = ? WHERE Id_Immeuble = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Immeuble data) throws SQLException {
		prSt.setString(1, data.getAdresse());
		prSt.setInt(2, data.getIdImmeuble());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
