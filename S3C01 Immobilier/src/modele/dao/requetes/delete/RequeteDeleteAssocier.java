package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteDeleteAssocier implements Requete<Associer> {

	@Override
	public String requete() {
		return "INSERT INTO Assureur (Id_Louable, Id_Index_Compteur) VALUES (?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Associer donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdLouable());
		prSt.setInt(2, donnee.getIdIndexCompteur());
	}
}
