package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Quittances;
import modele.dao.requetes.Requete;


public class RequeteSelectQuittancesByLocataire implements Requete<Quittances> {

		@Override
		public String requete() {
			return "SELECT * FROM Quittances where Id_Locataire = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Quittances data) throws SQLException {
			prSt.setString(1, data.getLocataire().getIdLocataire());
		}
	}