package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;

public class RequeteUpdateLouable implements Requete<Louable> {

	@Override
	public String requete() {
		return "UPDATE Louable SET Adresse = ?, Superficie  = ?, NumeroFiscal = ?, Statut = ?, DateAnniversaire  = ?, Id_Immeuble = ?,Id_Assurance =? WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Louable data) throws SQLException {

		prSt.setDouble(1, data.getSuperficie());
		prSt.setString(2, data.getAdresse());
		prSt.setInt(3, data.getNumeroFiscal());
		prSt.setString(4, data.getStatut());
		java.util.Date utilDate = data.getDateAnniversaire();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(5, sqlDate);
	    } else {
	        prSt.setNull(5, java.sql.Types.DATE);
	    }
	    prSt.setInt(6, data.getIdLouable());
	    prSt.setInt(7, data.getIdLouable());
	    prSt.setInt(8, data.getIdLouable());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
	}
}