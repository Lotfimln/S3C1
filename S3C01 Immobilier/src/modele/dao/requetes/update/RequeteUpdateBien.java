package modele.dao.requetes.update;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;

public class RequeteUpdateBien implements Requete<Louable> {

	@Override
	public String requete() {
		return "UPDATE Bien SET Superficie  = ?, DateAnniversaire  = ?, type_bien = ? ,Id_Immeuble = ? WHERE Id_Bien = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Louable data) throws SQLException {

		prSt.setDouble(1, data.getSuperficie());                                     
		prSt.setDate(4, Date.valueOf(data.getDateAnniversaire()));  
		prSt.setString(5, data.getType_bien());     
		prSt.setString(6, data.getImmeuble().getImmeuble()); 
		prSt.setString(7, data.getIdBien());              
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub
	}
}