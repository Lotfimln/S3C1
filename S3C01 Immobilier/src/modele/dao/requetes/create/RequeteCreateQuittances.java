package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Quittances;
import modele.dao.requetes.Requete;

public class RequeteCreateQuittances implements Requete<Quittances> {

	@Override
	public String requete() {
		return "INSERT INTO Quittances (Id_Quittances, DatePaiement, MontantLoyer, MontantProvision, Id_Locataire, Id_Contrat_de_location) VALUES (?, ?, ?, ?, ?, ?)";
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

	@Override
	public void parametres(PreparedStatement prSt, Quittances donnees) throws SQLException {
		prSt.setDouble(1, donnees.getMontantLoyer());
		java.util.Date utilDate = donnees.getDatePaiement();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prSt.setDate(2, sqlDate);
		prSt.setDouble(3, donnees.getMontantLoyer());
		prSt.setDouble(4, donnees.getMontantProvision());
		prSt.setString(5, donnees.getLocataire().getIdLocataire());
		prSt.setInt(6, donnees.getContratDeLocation().getIdContratDeLocation());
	}
}
