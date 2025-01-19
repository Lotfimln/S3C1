package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteDeleteAssocierByLouable implements Requete<Associer> {

    @Override
    public String requete() {
        return "DELETE FROM Associer WHERE Id_Louable = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
        prSt.setString(1, ids[0]);
    }

    @Override
    public void parametres(PreparedStatement prSt, Associer donnee) throws SQLException {
        prSt.setInt(1, donnee.getIdLouable());
    }
}
