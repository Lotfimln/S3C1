package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;

public class RequeteUpdateGarage implements Requete<Garage> {

	@Override
	public String requete() {
		return "UPDATE Garage SET Id_Louable = ? WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Garage data) throws SQLException {
		prSt.setInt(1, data.getIdLouable());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
