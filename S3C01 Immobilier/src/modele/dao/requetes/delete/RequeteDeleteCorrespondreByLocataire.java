package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;

public class RequeteDeleteCorrespondreByLocataire implements Requete<Correspondre> {

    @Override
    public String requete() {
        return "DELETE FROM Correspondre WHERE Id_Locataire = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
        prSt.setString(1, ids[0]);
    }

    @Override
    public void parametres(PreparedStatement prSt, Correspondre donnee) throws SQLException {
        prSt.setString(1, donnee.getIdLocataire());
    }
}
