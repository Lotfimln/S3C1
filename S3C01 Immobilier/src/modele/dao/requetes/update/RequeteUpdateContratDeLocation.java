package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;

public class RequeteUpdateContratDeLocation implements Requete<ContratDeLocation> {

	@Override
	public String requete() {
		return "UPDATE Contrat_de_location SET Id_Contrat_de_location = ?, DateDebut = ?, DateFin = ?, MontantLoyer = ?, ProvisionsCharges = ?, TypeContrat = ?, DateAnniversaire = ?, IndiceICC = ?, MontantCaution = ?, Id_Louable = ? WHERE Id_Contrat_de_location = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, ContratDeLocation data) throws SQLException {
		prSt.setInt(1, data.getIdContratDeLocation());
		java.util.Date utilDate = data.getDateDebut();
	    if (utilDate != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        prSt.setDate(2, sqlDate);
	    } else {
	        prSt.setNull(2, java.sql.Types.DATE);
	    }
		java.util.Date utilDate1 = data.getDateFin();
	    if (utilDate1 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
	        prSt.setDate(3, sqlDate);
	    } else {
	        prSt.setNull(3, java.sql.Types.DATE);
	    }
		prSt.setDouble(4, data.getMontantLoyer());
		prSt.setDouble(5, data.getProvisionsCharges());
		prSt.setString(6, data.getTypeContrat());
		java.util.Date utilDate2 = data.getDateAnniversaire();
		if (utilDate2 != null) {
	        java.sql.Date sqlDate = new java.sql.Date(utilDate2.getTime());
	        prSt.setDate(7, sqlDate);
	    } else {
	        prSt.setNull(7, java.sql.Types.DATE);
	    }
		prSt.setDouble(8, data.getIndiceICC());
		prSt.setDouble(9, data.getMontantCaution());
		prSt.setInt(10, data.getLouable().getIdLouable());
		prSt.setInt(11, data.getIdContratDeLocation());
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
