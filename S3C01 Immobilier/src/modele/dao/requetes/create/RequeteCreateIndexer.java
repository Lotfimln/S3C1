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
	public void parametres(PreparedStatement prSt, Indexer donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdIndexCompteur());
		prSt.setInt(2, donnees.getIdImmeuble());

		java.util.Date utilDate = donnees.getDateReleve();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
		
        prSt.setDouble(4, donnees.getPrixAbonnement());

        java.util.Date utilDate1 = donnees.getDateRegularisation();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
	}
}
