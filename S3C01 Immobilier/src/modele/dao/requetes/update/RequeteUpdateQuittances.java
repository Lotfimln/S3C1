package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Quittances;
import modele.dao.requetes.Requete;

public class RequeteUpdateQuittances implements Requete<Quittances> {

	@Override
	public String requete() {
		return "UPDATE Quittances SET Id_Quittances = ?, DatePaiement = ?, MontantLoyer = ?, MontantProvision = ?, Id_Locataire = ?, Id_Contrat_de_location = ? WHERE Id_Quittances = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Quittances data) throws SQLException {
		prSt.setInt(1, data.getIdQuittances());
		java.util.Date utilDate = data.getDatePaiement();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		prSt.setDouble(3, data.getMontantLoyer());
		prSt.setDouble(4, data.getMontantProvision());
		prSt.setString(5, data.getLocataire().getIdLocataire());
		prSt.setInt(6, data.getContratDeLocation().getIdContratDeLocation());
		prSt.setInt(7, data.getIdQuittances());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}
