package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

public class RequeteCreateLocataire implements Requete<Locataire> {

	@Override
	public String requete() {
		return "INSERT INTO Locataire (Id_Locataire, Nom, Prenom, Mail, Telephone, DateNaissance, DateDepart) VALUES (?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
		prSt.setString(6, id[5]);
		prSt.setString(7, id[6]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire donnees) throws SQLException {
		prSt.setString(1, donnees.getIdLocataire());
		prSt.setString(2, donnees.getNom());
		prSt.setString(3, donnees.getPrenom());
		prSt.setString(4, donnees.getMail());
		prSt.setString(5, donnees.getTelephone());
		java.util.Date utilDate = donnees.getDateDepart();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prSt.setDate(6, sqlDate);
		java.util.Date utilDate1 = donnees.getDateDepart();
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
        prSt.setDate(7, sqlDate1);
	}
}
