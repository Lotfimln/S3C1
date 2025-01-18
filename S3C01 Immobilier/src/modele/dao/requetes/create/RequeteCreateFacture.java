package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Facture;
import modele.dao.requetes.Requete;

public class RequeteCreateFacture implements Requete<Facture> {

	@Override
	public String requete() {
		return "INSERT INTO Facture (Montant, DateFacture, ReferenceDevis, Entreprise, DatePaiement, Id_Entreprise, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
		prSt.setString(6, id[5]);
		prSt.setString(7, id[6]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Facture donnees) throws SQLException {
		prSt.setDouble(1, donnees.getMontant());
		java.util.Date utilDate = donnees.getDateFacture();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prSt.setDate(2, sqlDate);
		prSt.setString(3, donnees.getReferenceDevis());
		java.util.Date utilDate1 = donnees.getDatePaiement();
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
        prSt.setDate(5, sqlDate1);
		prSt.setInt(6, donnees.getEntreprise().getIdEntreprise());
		prSt.setInt(7, donnees.getLouable().getIdLouable());
	}
}
