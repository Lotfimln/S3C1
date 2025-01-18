package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;

public class RequeteUpdateIndexerByImmeuble implements Requete<Indexer> {

	@Override
	public String requete() {
		return "UPDATE indexer SET Id_Index_Compteur = ?, Id_Immeuble = ?, DateReleve = ?, PrixAbonnement = ?, DateRegularisation = ? WHERE Id_Immeuble = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Indexer data) throws SQLException {
		prSt.setInt(1, data.getIdIndexCompteur());	// Id_Index_Compteur (nouvelle valeur)
		prSt.setInt(2, data.getIdImmeuble());		// Id_Immeuble (nouvelle valeur)
		java.util.Date utilDate = data.getDateReleve();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
        prSt.setDate(3, sqlDate);
        prSt.setDouble(4, data.getPrixAbonnement());
        java.util.Date utilDate1 = data.getDateRegularisation();
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime()); 
        prSt.setDate(5, sqlDate1);
		prSt.setInt(6, data.getIdImmeuble());		// Id_Immeuble (ancienne valeur)
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
		prSt.setString(6, id[5]);
	}
}
