package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteDeleteIndexerByImmeuble implements Requete<Indexer> {

    @Override
    public String requete() {
        return "DELETE FROM Indexer WHERE Id_Immeuble = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
        prSt.setString(1, ids[0]);
    }

    @Override
    public void parametres(PreparedStatement prSt, Indexer donnee) throws SQLException {
		return;
    }
}
