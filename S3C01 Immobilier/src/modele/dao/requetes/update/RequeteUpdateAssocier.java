package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteUpdateAssocier implements Requete<Associer> {

	@Override
	public String requete() {
		return "UPDATE associer SET Id_Louable = ?, Id_Index_Compteur = ?, DateReleve = ?, PrixAbonnement = ?, DateRegularisation = ? WHERE Id_Louable = ? AND Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Associer data) throws SQLException {
		prSt.setInt(1, data.getIdLouable());
		prSt.setInt(2, data.getIdIndexCompteur());
		
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
        
		prSt.setInt(6, data.getIdLouable());
		prSt.setInt(7, data.getIdIndexCompteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}
