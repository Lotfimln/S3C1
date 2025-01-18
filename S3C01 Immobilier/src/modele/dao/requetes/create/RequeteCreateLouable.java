package modele.dao.requetes.create;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;

public class RequeteCreateLouable implements Requete<Louable> {

	@Override
	public String requete() {
		return "INSERT INTO Louable (Id_Louable, TypeLouable, Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, Id_Immeuble, Id_Assureur, NbPieces) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
	public void parametres(PreparedStatement prSt, Louable donnees) throws SQLException {
		prSt.setInt(1, donnees.getIdLouable());
		prSt.setString(2, donnees.getTypeLouable());
		prSt.setString(3, donnees.getAdresse());
		prSt.setDouble(4, donnees.getSuperficie());
		prSt.setString(5, donnees.getNumeroFiscal());
		prSt.setString(6, donnees.getStatut());
		java.util.Date utilDate = donnees.getDateAnniversaire();	            
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		prSt.setDate(7, sqlDate);
		prSt.setInt(8, donnees.getImmeuble().getIdImmeuble());
		prSt.setInt(9, donnees.getAssureur().getIdAssureur());
		prSt.setInt(10, donnees.getNbPieces());
	}
}
