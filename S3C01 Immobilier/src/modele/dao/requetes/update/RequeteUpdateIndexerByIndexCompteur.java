package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteUpdateIndexerByIndexCompteur implements Requete<Indexer> {

	@Override
	public String requete() {
		return "UPDATE indexer SET Id_Index_Compteur = ?, Id_Immeuble = ? WHERE Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Indexer data) throws SQLException {
		prSt.setInt(1, data.getIdIndexCompteur());
		prSt.setInt(2, data.getIdImmeuble());
		prSt.setInt(3, data.getIdIndexCompteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
