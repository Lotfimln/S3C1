package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Facture;
import modele.dao.requetes.Requete;

public class RequeteUpdateFacture implements Requete<Facture> {

	@Override
	public String requete() {
		return "UPDATE Facture SET Montant = ?, DateFacture = ?, ReferenceDevis = ?, DatePaiement = ?, Id_Entreprise = ?, Id_Louable = ? WHERE Id_Facture = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Facture data) throws SQLException {
		prSt.setDouble(1, data.getMontant());
		java.util.Date utilDate = data.getDateFacture();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		prSt.setString(3, data.getReferenceDevis());
		java.util.Date utilDate1 = data.getDatePaiement();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(4, sqlDate);
	    } else {
	        prSt.setNull(4, java.sql.Types.DATE);
	    }
		prSt.setInt(5, data.getEntreprise().getIdEntreprise());
		prSt.setInt(6, data.getLouable().getIdLouable());
		prSt.setInt(7, data.getIdFacture());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
