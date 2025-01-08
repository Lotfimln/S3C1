package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Correspondre;
import modele.dao.requetes.Requete;


public class RequeteSelectCorrespondre implements Requete<Correspondre> {

		@Override
		public String requete() {
			return "SELECT * FROM Correspondre";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Correspondre data) throws SQLException {
		}
	}