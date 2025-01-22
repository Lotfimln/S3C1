package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteDeleteIndexer implements Requete<Indexer> {

	@Override
	public String requete() {
		return "DELETE FROM Indexer WHERE Id_Index_Compteur = ? AND Id_Immeuble = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Indexer donnee) throws SQLException {
		return;
	}
}
