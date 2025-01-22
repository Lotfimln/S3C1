package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;

public class RequeteUpdateLouable implements Requete<Louable> {

	@Override
	public String requete() {
		return "UPDATE Louable SET Id_Louable = ?, Adresse = ?, Superficie  = ?, NumeroFiscal = ?, Statut = ?, DateAnniversaire  = ?, DateAcquisition = ?, Id_Immeuble = ?, Id_Assureur = ? WHERE Id_Louable = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Louable data) throws SQLException {

		prSt.setInt(1, data.getIdLouable());
		prSt.setString(2, data.getAdresse());
		prSt.setDouble(3, data.getSuperficie());
		prSt.setString(4, data.getNumeroFiscal());
		prSt.setString(5, data.getStatut());
		
		java.util.Date dateAnniversaire = data.getDateAnniversaire();
	    if (dateAnniversaire != null) {
	        java.sql.Date sqlDate = new java.sql.Date(dateAnniversaire.getTime());
	        prSt.setDate(6, sqlDate);
	    } else {
	        prSt.setNull(6, java.sql.Types.DATE);
	    }
	    
	    java.util.Date dateAcquisition = data.getDateAnniversaire();
	    if (dateAcquisition != null) {
	        java.sql.Date sqlDate = new java.sql.Date(dateAcquisition.getTime());
	        prSt.setDate(7, sqlDate);
	    } else {
	        prSt.setNull(7, java.sql.Types.DATE);
	    }
	    
	    prSt.setInt(8, data.getImmeuble().getIdImmeuble());
	    prSt.setInt(9, data.getAssureur().getIdAssureur());
	    prSt.setInt(10, data.getIdLouable());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}
}