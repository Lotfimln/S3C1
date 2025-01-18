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
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
        prSt.setDate(3, sqlDate);
        prSt.setDouble(4, data.getPrixAbonnement());
        java.util.Date utilDate1 = data.getDateRegularisation();
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime()); 
        prSt.setDate(5, sqlDate1);
		prSt.setInt(6, data.getIdLouable());
		prSt.setInt(7, data.getIdIndexCompteur());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
		prSt.setString(6, id[5]);
		prSt.setString(7, id[5]);
	}
}
