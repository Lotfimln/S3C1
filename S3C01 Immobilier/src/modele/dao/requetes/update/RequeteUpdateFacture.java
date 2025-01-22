package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Facture;
import modele.dao.requetes.Requete;

public class RequeteUpdateFacture implements Requete<Facture> {

	@Override
	public String requete() {
		return "UPDATE Facture SET Id_Facture = ?, Montant = ?, DateFacture = ?, ReferenceDevis = ?, DatePaiement = ?, Id_Entreprise = ?, Id_Louable = ? WHERE Id_Facture = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Facture data) throws SQLException {
		prSt.setInt(1, data.getIdFacture());
		prSt.setDouble(2, data.getMontant());
		
		java.util.Date utilDate = data.getDateFacture();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
	    
		prSt.setString(4, data.getReferenceDevis());
		
		java.util.Date utilDate1 = data.getDatePaiement();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
	    
		prSt.setInt(6, data.getEntreprise().getIdEntreprise());
		prSt.setInt(7, data.getLouable().getIdLouable());
		prSt.setInt(8, data.getIdFacture());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}
