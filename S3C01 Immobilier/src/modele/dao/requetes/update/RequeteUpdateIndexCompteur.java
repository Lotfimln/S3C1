package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IndexCompteur;
import modele.dao.requetes.Requete;

public class RequeteUpdateIndexCompteur implements Requete<IndexCompteur> {

	@Override
	public String requete() {
		return "UPDATE Index_Compteur SET TypeCompteur = ?, ValeurCourante = ?, AncienneValeur = ?, DateReleve = ? WHERE Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, IndexCompteur data) throws SQLException {
		prSt.setString(1, data.getTypeCompteur());
		prSt.setDouble(2, data.getValeurCourante());
		prSt.setDouble(3, data.getAncienneValeur());
		java.util.Date utilDate = data.getDateReleve();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(4, sqlDate);
	    } else {
	        prSt.setNull(4, java.sql.Types.DATE);
	    }
		prSt.setInt(5, data.getIdIndexCompteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
