package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IndexCompteur;
import modele.dao.requetes.Requete;

public class RequeteCreateIndexCompteur implements Requete<IndexCompteur> {

	@Override
	public String requete() {
		return "INSERT INTO Index_Compteur (Id_Index_Compteur, TypeCompteur, ValeurCourante, AncienneValeur) VALUES (?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, IndexCompteur donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdIndexCompteur());
		prSt.setString(2, donnees.getTypeCompteur());
		prSt.setDouble(3, donnees.getValeurCourante());
		prSt.setDouble(4, donnees.getAncienneValeur());
	}
}
