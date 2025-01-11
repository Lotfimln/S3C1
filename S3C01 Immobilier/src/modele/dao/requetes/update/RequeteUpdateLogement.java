package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Logement;
import modele.dao.requetes.Requete;

public class RequeteUpdateLogement implements Requete<Logement> {

    @Override
    public String requete() {
        return "UPDATE Louable SET Id_Louable = ?,Adresse = ?, Superficie  = ?, NumeroFiscal = ?, Statut = ?, DateAnniversaire  = ?, Id_Immeuble = ?, Id_Assurance = ? WHERE Id_Louable = ?;"
                + "UPDATE Logement SET Id_Louable = ?, NbPièces = ? WHERE Id_Louable = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Logement data) throws SQLException {
        // Paramètres pour la table Louable
    	prSt.setInt(1, data.getIdLogement());
        prSt.setString(1, data.getAdresse());
        prSt.setDouble(2, data.getSuperficie());
        prSt.setLong(3, data.getNumeroFiscal());
        prSt.setString(4, data.getStatut());
        java.util.Date utilDate = data.getDateAnniversaire();
        if (utilDate != null) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            prSt.setDate(5, sqlDate);
        } else {
            prSt.setNull(5, java.sql.Types.DATE);
        }
        prSt.setInt(6, data.getImmeuble().getIdImmeuble());
        prSt.setInt(7, data.getAssurance().getIdAssurance());
        prSt.setInt(8, data.getIdLouable());

        // Paramètres pour la table Logement
        prSt.setInt(10, data.getIdLouable());
        prSt.setInt(9, data.getNbPieces());
        prSt.setInt(10, data.getIdLouable());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // Si besoin, gérer les paramètres pour une requête basée sur un ID
    }
}
