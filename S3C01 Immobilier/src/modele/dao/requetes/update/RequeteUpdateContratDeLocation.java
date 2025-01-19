package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;

public class RequeteUpdateContratDeLocation implements Requete<ContratDeLocation> {

	@Override
	public String requete() {
		return "UPDATE Contrat_de_location SET Id_Contrat_de_location = ?, DateDebut = ?, DateFin = ?, MontantLoyer = ?, ProvisionsCharges = ?, TypeContrat = ?, DateAnniversaire = ?, DateDerniereRegularisation = ?, IndiceICC = ?, MontantCaution = ?, Id_Louable = ? WHERE Id_Contrat_de_location = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, ContratDeLocation donnees) throws SQLException {
prSt.setInt(1, donnees.getIdContratDeLocation());
		
		java.util.Date utilDate = donnees.getDateDebut();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }

	    java.util.Date utilDate1 = donnees.getDateFin();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }

		prSt.setDouble(4, donnees.getMontantLoyer());
		prSt.setDouble(5, donnees.getProvisionsCharges());
		prSt.setString(6, donnees.getTypeContrat());
		
		java.util.Date utilDate2 = donnees.getDateAnniversaire();
	    if (utilDate2 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate2.getTime());
	        prSt.setDate(7, sqlDate);
	    } else {
	        prSt.setNull(7, java.sql.Types.DATE);
	    }
	    
	    java.util.Date utilDate3 = donnees.getDateDerniereRegularisation();
	    if (utilDate3 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate3.getTime());
	        prSt.setDate(7, sqlDate);
	    } else {
	        prSt.setNull(7, java.sql.Types.DATE);
	    }
		
		prSt.setDouble(8, donnees.getIndiceICC());
		prSt.setDouble(9, donnees.getMontantCaution());
		prSt.setInt(10, donnees.getLouable().getIdLouable());
		prSt.setInt(11, donnees.getIdContratDeLocation());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	}
}
