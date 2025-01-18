package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteCreateAssocier implements Requete<Associer> {

	@Override
	public String requete() {
		return "INSERT INTO Associer (Id_Index_Compteur, Id_Louable, DateReleve, PrixAbonnement, DateRegularisation) VALUES (?, ?, ?, ?, ?)";
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
	public void parametres(PreparedStatement prSt, Associer donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdIndexCompteur());
		prSt.setInt(2, donnees.getIdLouable());
		
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
