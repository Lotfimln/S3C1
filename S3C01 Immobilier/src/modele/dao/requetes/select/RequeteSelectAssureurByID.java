package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;


public class RequeteSelectAssureurByID implements Requete<Assureur> {

		@Override
		public String requete() {
			return "SELECT * FROM Assureur where Id_Assurance = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Assureur data) throws SQLException {
			prSt.setInt(1, data.getIdAssurance());
		}
	}