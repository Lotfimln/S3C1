package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;

public class RequeteCreateLouable implements Requete<Louable> {

	@Override
	public String requete() {
		return "INSERT INTO Louable (Id_Louable, TypeLouable, Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, DateAcquisition, Id_Immeuble, Id_Assureur, NbPieces) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		return;
	}

	@Override
	public void parametres(PreparedStatement prSt, Louable donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdLouable());
		prSt.setString(2, donnees.getTypeLouable());
		prSt.setString(3, donnees.getAdresse());
		prSt.setDouble(4, donnees.getSuperficie());
		prSt.setString(5, donnees.getNumeroFiscal());
		prSt.setString(6, donnees.getStatut());

		java.util.Date utilDate = donnees.getDateAnniversaire();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(7, sqlDate);
	    } else {
	        prSt.setNull(7, java.sql.Types.DATE);
	    }
	    
	    java.util.Date utilDate1 = donnees.getDateAcquisition();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(8, sqlDate);
	    } else {
	        prSt.setNull(8, java.sql.Types.DATE);
	    }
		
		prSt.setInt(9, donnees.getImmeuble().getIdImmeuble());
		prSt.setInt(10, donnees.getAssureur().getIdAssureur());
		prSt.setInt(11, donnees.getNbPieces());
	}
}
