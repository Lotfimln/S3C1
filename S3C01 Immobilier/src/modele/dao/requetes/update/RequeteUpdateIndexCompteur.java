package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IndexCompteur;
import modele.dao.requetes.Requete;

public class RequeteUpdateIndexCompteur implements Requete<IndexCompteur> {

	@Override
	public String requete() {
		return "UPDATE Index_Compteur SET Id_Index_Compteur = ?, TypeCompteur = ?, ValeurCourante = ?, AncienneValeur = ? WHERE Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, IndexCompteur data) throws SQLException {
		prSt.setInt(1, data.getIdIndexCompteur());
		prSt.setString(2, data.getTypeCompteur());
		prSt.setDouble(3, data.getValeurCourante());
		prSt.setDouble(4, data.getAncienneValeur());
		prSt.setInt(5, data.getIdIndexCompteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}
