package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;

public class RequeteCreateAssureur implements Requete<Assureur> {

	@Override
	public String requete() {
		return "INSERT INTO Assureur (Id_Assureur, Nom, DateAssurance, Prime, TypeAssureur) VALUES (?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Assureur donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdAssureur());
        prSt.setString(2, donnees.getNom());

        java.util.Date utilDate = donnees.getDateAssurance();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
        
        prSt.setInt(4, donnees.getPrime());
        prSt.setString(5, donnees.getTypeAssureur());
	}
}
