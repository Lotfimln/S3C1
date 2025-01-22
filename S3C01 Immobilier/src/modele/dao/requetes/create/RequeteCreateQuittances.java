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
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Quittances donnees) throws SQLException {
		prSt.setDouble(1, donnees.getIdQuittances());

		java.util.Date utilDate = donnees.getDatePaiement();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		
		prSt.setDouble(3, donnees.getMontantLoyer());
		prSt.setDouble(4, donnees.getMontantProvision());
		prSt.setString(5, donnees.getLocataire().getIdLocataire());
		prSt.setInt(6, donnees.getContratDeLocation().getIdContratDeLocation());
	}
}
