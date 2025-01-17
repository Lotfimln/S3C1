package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteUpdateAssocierByIndexCompteur implements Requete<Associer> {

	@Override
	public String requete() {
		return "UPDATE associer SET Id_Louable = ?, Id_Index_Compteur = ? WHERE Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Associer data) throws SQLException {
		prSt.setInt(1, data.getIdLouable());
		prSt.setInt(2, data.getIdIndexCompteur());
		prSt.setInt(3, data.getIdIndexCompteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
