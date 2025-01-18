package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;

public class RequeteCreateAssureur implements Requete<Assureur> {

	@Override
	public String requete() {
		return "INSERT INTO Assureur (Id_Assureur, Nom, DateAssurance, Prime) VALUES (?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Assureur donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdAssureur());
        prSt.setString(2, donnee.getNom());
        java.util.Date utilDate = donnee.getDateAssurance();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  
        prSt.setDate(3, sqlDate);
        prSt.setInt(4, donnee.getPrime());
	}
}
