package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;


public class RequeteSelectCorrespondreByLocataire implements Requete<Correspondre> {

		@Override
		public String requete() {
			return "SELECT * FROM Correspondre where Id_Locataire = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Correspondre data) throws SQLException {
			prSt.setString(1, data.getIdLocataire());
		}
	}