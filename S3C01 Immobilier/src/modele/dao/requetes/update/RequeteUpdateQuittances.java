package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Quittances;
import modele.dao.requetes.Requete;

public class RequeteUpdateQuittances implements Requete<Quittances> {

	@Override
	public String requete() {
		return "UPDATE Quittances SET DatePaiement = ?, MontantLoyer = ?, MontantProvision = ?, Id_Locataire = ?, Id_Contrat_de_location = ? WHERE Id_Quittances = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Quittances data) throws SQLException {
		java.util.Date utilDate = data.getDatePaiement();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(1, sqlDate);
	    } else {
	        prSt.setNull(1, java.sql.Types.DATE);
	    }
		prSt.setDouble(2, data.getMontantLoyer());
		prSt.setDouble(3, data.getMontantProvision());
		prSt.setString(4, data.getLocataire().getIdLocataire());
		prSt.setInt(5, data.getContratDeLocation().getIdContratDeLocation());
		prSt.setInt(6, data.getIdQuittances());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
