package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;


public class RequeteSelectCorrespondreByContratdelocation implements Requete<Correspondre> {

		@Override
		public String requete() {
			return "SELECT * FROM Correspondre where Id_Contrat_de_location = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Correspondre data) throws SQLException {
			prSt.setInt(1, data.getIdContratDeLocation());
		}
	}