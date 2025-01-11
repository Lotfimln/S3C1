package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;

public class RequeteUpdateAssureur implements Requete<Assureur> {

	@Override
	public String requete() {
		return "UPDATE Assureur SET Prime = ?, DateAssurance = ?, Nom = ? WHERE Id_Assurance = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void parametres(PreparedStatement prSt, Assureur data) throws SQLException {
	    prSt.setInt(1, data.getPrime());
	    java.util.Date utilDate = data.getDateAssurance();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
	    prSt.setString(3, data.getNom());
	    prSt.setInt(4, data.getIdAssurance());
	}

}
