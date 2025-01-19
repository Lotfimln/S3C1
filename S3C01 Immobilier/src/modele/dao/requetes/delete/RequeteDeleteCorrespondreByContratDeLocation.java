package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;

public class RequeteDeleteCorrespondreByContratDeLocation implements Requete<Correspondre> {

    @Override
    public String requete() {
        return "DELETE FROM Correspondre WHERE Id_Contrat_de_location = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
        prSt.setString(1, ids[0]);
    }

    @Override
    public void parametres(PreparedStatement prSt, Correspondre donnee) throws SQLException {
        prSt.setInt(1, donnee.getIdContratDeLocation());
    }
}
