package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteDeleteAssocierByIndexCompteur implements Requete<Associer> {

    @Override
    public String requete() {
        return "DELETE FROM Associer WHERE Id_Index_Compteur = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
        prSt.setString(1, ids[0]);
    }

    @Override
    public void parametres(PreparedStatement prSt, Associer donnee) throws SQLException {
		return;
    }
}
