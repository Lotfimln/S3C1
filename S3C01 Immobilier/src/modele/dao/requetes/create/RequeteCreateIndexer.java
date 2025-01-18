package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteCreateIndexer implements Requete<Indexer> {

	@Override
	public String requete() {
		return "INSERT INTO Indexer (Id_Index_Compteur, Id_Immeuble) VALUES (?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Indexer donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdIndexCompteur());
		prSt.setInt(2, donnee.getIdImmeuble());
	}
}
