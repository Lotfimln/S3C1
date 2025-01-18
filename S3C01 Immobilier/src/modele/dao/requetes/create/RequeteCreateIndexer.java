package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteCreateIndexer implements Requete<Indexer> {

	@Override
	public String requete() {
		return "INSERT INTO Indexer (Id_Index_Compteur, Id_Immeuble, DateReleve, PrixAbonnement, DateRegularisation) VALUES (?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
		prSt.setString(3, ids[2]);
		prSt.setString(4, ids[3]);
		prSt.setString(5, ids[4]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Indexer donnee) throws SQLException {
		prSt.setInt(1, donnee.getIdIndexCompteur());
		prSt.setInt(2, donnee.getIdImmeuble());
		java.util.Date utilDate = donnee.getDateReleve();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
        prSt.setDate(3, sqlDate);
        prSt.setDouble(4, donnee.getPrixAbonnement());
        java.util.Date utilDate1 = donnee.getDateRegularisation();
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime()); 
        prSt.setDate(5, sqlDate1);
	}
}
