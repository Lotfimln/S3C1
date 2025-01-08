package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Contenir;
import modele.dao.requetes.Requete;

public class RequeteDeleteContenir implements Requete<Contenir> {

	@Override
	public String requete() {
		return "DELETE FROM Contenir WHERE Id_Facture = ? AND Id_Charge = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Contenir donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdFacture());
		prSt.setInt(2, donnee.getIdCharge());
	}
}
