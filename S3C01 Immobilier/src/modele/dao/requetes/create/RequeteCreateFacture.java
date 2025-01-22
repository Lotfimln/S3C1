package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Facture;
import modele.dao.requetes.Requete;

public class RequeteCreateFacture implements Requete<Facture> {

	@Override
	public String requete() {
		return "INSERT INTO Facture (Id_Facture, Montant, DateFacture, ReferenceDevis, DatePaiement, Id_Entreprise, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Facture donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdFacture());
		prSt.setDouble(2, donnees.getMontant());

		java.util.Date utilDate = donnees.getDateFacture();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
		
		prSt.setString(4, donnees.getReferenceDevis());

		java.util.Date utilDate1 = donnees.getDatePaiement();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
		
		prSt.setInt(6, donnees.getEntreprise().getIdEntreprise());
		prSt.setInt(7, donnees.getLouable().getIdLouable());
	}
}
