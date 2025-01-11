package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Facture;
import modele.dao.requetes.Requete;

public class RequeteUpdateFacture implements Requete<Facture> {

	@Override
	public String requete() {
		return "UPDATE Facture SET Montant = ?, DateFacture = ?, ReferenceDevis = ?, Entreprise = ?, DatePaiement = ?, Id_Entreprise = ?, Id_Louable = ? WHERE Id_Facture = ?";
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
		prSt.setString(4, data.getEntreprise());
		java.util.Date utilDate1 = data.getDatePaiement();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
		prSt.setString(6, data.getEntreprise());
		prSt.setInt(7, data.getEntrepriseAssociee().getIdEntreprise());
		prSt.setInt(8, data.getIdFacture());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
