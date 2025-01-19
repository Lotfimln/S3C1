package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Apparaitre;
import modele.dao.requetes.Requete;

public class RequeteDeleteApparaitreByIndexCompteur implements Requete<Apparaitre> {

    @Override
    public String requete() {
        return "DELETE FROM Apparaitre WHERE Id_Index_Compteur = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
        prSt.setString(1, ids[0]);
    }

    @Override
    public void parametres(PreparedStatement prSt, Apparaitre donnee) throws SQLException {
        prSt.setInt(1, donnee.getIdIndexCompteur());
    }
}
