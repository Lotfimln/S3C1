package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

public class RequeteCreateLocataire implements Requete<Locataire> {

	@Override
	public String requete() {
		return "INSERT INTO Locataire (Id_Locataire, Nom, Prenom, Mail, Telephone, DateNaissance, DateDepart) VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire donnees) throws SQLException {
		prSt.setString(1, donnees.getIdLocataire());
		prSt.setString(2, donnees.getNom());
		prSt.setString(3, donnees.getPrenom());
		prSt.setString(4, donnees.getMail());
		prSt.setString(5, donnees.getTelephone());
		
		java.util.Date utilDate = donnees.getDateNaissance();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(6, sqlDate);
	    } else {
	        prSt.setNull(6, java.sql.Types.DATE);
	    }
	    
	    java.util.Date utilDate1 = donnees.getDateDepart();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(7, sqlDate);
	    } else {
	        prSt.setNull(7, java.sql.Types.DATE);
	    }
	}
}
