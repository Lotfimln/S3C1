package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteUpdateIndexer implements Requete<Indexer> {

	@Override
	public String requete() {
		return "UPDATE indexer SET Id_Index_Compteur = ?, Id_Immeuble = ?, DateReleve = ?, PrixAbonnement = ?, DateRegularisation = ? WHERE Id_Index_Compteur = ? AND Id_Immeuble = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Indexer data) throws SQLException {
		prSt.setInt(1, data.getIdIndexCompteur());
		prSt.setInt(2, data.getIdImmeuble());
		
		java.util.Date utilDate = data.getDateReleve();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
        
        prSt.setDouble(4, data.getPrixAbonnement());
        
        java.util.Date utilDate1 = data.getDateRegularisation();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
        
		prSt.setInt(6, data.getIdIndexCompteur());
		prSt.setInt(7, data.getIdImmeuble());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}
