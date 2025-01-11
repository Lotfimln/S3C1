package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;

public class RequeteUpdateContratDeLocation implements Requete<ContratDeLocation> {

	@Override
	public String requete() {
		return "UPDATE Contrat_de_location SET DateDebut = ?, DateFin = ?, MontantLoyer = ?, ProvisionsCharges = ?, TypeContrat = ?, DateAnniversaire = ?, IndiceICC = ?, MontantCaution = ?, Id_Louable = ? WHERE Id_Contrat_de_location = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, ContratDeLocation data) throws SQLException {
		java.util.Date utilDate = data.getDateDebut();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(1, sqlDate);
	    } else {
	        prSt.setNull(1, java.sql.Types.DATE);
	    }
		java.util.Date utilDate1 = data.getDateFin();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		prSt.setDouble(3, data.getMontantLoyer());
		prSt.setDouble(4, data.getProvisionsCharges());
		prSt.setString(5, data.getTypeContrat());
		java.util.Date utilDate2 = data.getDateAnniversaire();
		if (utilDate2 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate2.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		prSt.setDouble(7, data.getIndiceICC());
		prSt.setDouble(8, data.getMontantCaution());
		prSt.setInt(9, data.getLouable().getIdLouable());
		prSt.setInt(10, data.getIdContratDeLocation());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
