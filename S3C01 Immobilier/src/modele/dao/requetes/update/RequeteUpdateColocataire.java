package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Colocataire;
import modele.dao.requetes.Requete;

public class RequeteUpdateColocataire implements Requete<Colocataire> {

	@Override
	public String requete() {
		return "UPDATE Colocataire SET Id_Locataire = ?, Id_Locataire = 2 WHERE Id_Locataire = 1 AND Id_Contrat_de_location = 2";
	}

	@Override
	public void parametres(PreparedStatement prSt, Colocataire data) throws SQLException {
		prSt.setString(1, data.getIdLocataire());
		prSt.setString(2, data.getIdLocataire1());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
