package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IndexCompteur;
import modele.dao.requetes.Requete;

public class RequeteDeleteIndexCompteur implements Requete<IndexCompteur> {

	@Override
	public String requete() {
		return "DELETE FROM Index_Compteur WHERE Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, IndexCompteur donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdIndexCompteur());
	}
}
