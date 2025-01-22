package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;

public class RequeteDeleteContratDeLocation implements Requete<ContratDeLocation> {

	@Override
	public String requete() {
		return "DELETE FROM Contrat_de_location WHERE Id_Contrat_de_location = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, ContratDeLocation donnee) throws SQLException {
		return;
	}
}
