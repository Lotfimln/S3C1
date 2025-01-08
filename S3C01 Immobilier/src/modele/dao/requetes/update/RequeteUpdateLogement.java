package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Logement;
import modele.dao.requetes.Requete;

public class RequeteUpdateLogement implements Requete<Logement> {

	@Override
	public String requete() {
		return "UPDATE Logement SET NbPièces = ? WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Logement data) throws SQLException {
		prSt.setString(1, data.getNbPieces());
		prSt.setInt(2, data.getIdLouable());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
