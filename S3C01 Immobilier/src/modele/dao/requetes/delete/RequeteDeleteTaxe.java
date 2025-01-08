package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Taxe;
import modele.dao.requetes.Requete;

public class RequeteDeleteTaxe implements Requete<Taxe> {

	@Override
	public String requete() {
		return "DELETE FROM Taxe WHERE Id_Taxe = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Taxe donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdTaxe());
	}
}
