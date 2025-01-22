package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

public class RequeteUpdateLocataire implements Requete<Locataire> {

    @Override
    public String requete() {
        return "UPDATE Locataire SET Id_Locataire = ?, Nom = ?, Prenom = ?, Mail = ?, Telephone = ?, DateNaissance = ?, DateDepart = ? WHERE Id_Locataire = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Locataire data) throws SQLException {
    	prSt.setString(1, data.getIdLocataire());
        prSt.setString(2, data.getNom());
        prSt.setString(3, data.getPrenom());
        prSt.setString(4, data.getMail());
        prSt.setString(5, data.getTelephone());

        java.util.Date dateNaissance = data.getDateNaissance();
        if (dateNaissance != null) {
            java.sql.Date sqlDate = new java.sql.Date(dateNaissance.getTime());
            prSt.setDate(6, sqlDate);
        } else {
            prSt.setNull(6, java.sql.Types.DATE);
        }

        java.util.Date dateDepart = data.getDateDepart();
        if (dateDepart != null) {
            java.sql.Date sqlDate = new java.sql.Date(dateDepart.getTime());
            prSt.setDate(7, sqlDate);
        } else {
            prSt.setNull(7, java.sql.Types.DATE);
        }

        prSt.setString(8, data.getIdLocataire());
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
    }
}
