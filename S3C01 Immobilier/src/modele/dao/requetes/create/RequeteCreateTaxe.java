package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Taxe;
import modele.dao.requetes.Requete;

public class RequeteCreateTaxe implements Requete<Taxe> {

	@Override
	public String requete() {
		return "INSERT INTO Taxe (Id_Taxe, MontantTaxeFoncieres, DateTaxe, Id_Immeuble) VALUES (?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Taxe donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdTaxe());
		prSt.setDouble(2, donnees.getMontantTaxeFoncieres());
		java.util.Date utilDate = donnees.getDateTaxe();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prSt.setDate(3, sqlDate);
		prSt.setInt(4, donnees.getImmeuble().getIdImmeuble());
	}
}
