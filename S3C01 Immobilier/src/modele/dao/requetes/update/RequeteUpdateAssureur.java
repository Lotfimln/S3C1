package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;

public class RequeteUpdateAssureur implements Requete<Assureur> {

	@Override
	public String requete() {
		return "UPDATE Assureur SET Id_Assureur = ?, Nom = ?, DateAssurance = ?, Prime = ?, TypeAssureur = ? WHERE Id_Assureur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void parametres(PreparedStatement prSt, Assureur data) throws SQLException {
		prSt.setInt(1, data.getIdAssureur());
	    prSt.setString(2, data.getNom());
	    java.util.Date utilDate = data.getDateAssurance();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	    prSt.setInt(4, data.getPrime());
	    prSt.setString(5, data.getTypeAssureur());
	    prSt.setInt(6, data.getIdAssureur());
	}

}
