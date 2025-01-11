package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

public class RequeteUpdateLocataire implements Requete<Locataire> {

	@Override
	public String requete() {
		return "UPDATE Locataire SET Nom = ?, Prénom = ?, Mail = ?, Téléphone = ?, DateDépart = ? WHERE Id_Locataire = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire data) throws SQLException {
		prSt.setString(1, data.getNom());
		prSt.setString(2, data.getPrenom());
		prSt.setString(3, data.getMail());
		prSt.setString(4, data.getTelephone());
		java.util.Date utilDate = data.getDateDepart();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
		prSt.setString(6, data.getIdLocataire());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
