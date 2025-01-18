package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;

public class RequeteCreateContratDeLocation implements Requete<ContratDeLocation> {

	@Override
	public String requete() {
		return "INSERT INTO Contrat_de_location (Id_Contrat_de_location, DateDebut, DateFin, MontantLoyer, ProvisionsCharges, TypeContrat, DateAnniversaire, IndiceICC, MontantCaution, Id_Louable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
		prSt.setString(4, id[3]);
		prSt.setString(5, id[4]);
		prSt.setString(6, id[5]);
		prSt.setString(7, id[6]);
		prSt.setString(8, id[7]);
		prSt.setString(9, id[8]);
		prSt.setString(10, id[9]);
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
		
		prSt.setDouble(8, donnees.getIndiceICC());
		prSt.setDouble(9, donnees.getMontantCaution());
		prSt.setInt(10, donnees.getLouable().getIdLouable());
	}
}
