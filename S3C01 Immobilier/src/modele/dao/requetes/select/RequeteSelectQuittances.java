package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Quittances;
import modele.dao.requetes.Requete;


public class RequeteSelectQuittances implements Requete<Quittances> {

		@Override
		public String requete() {
			return "SELECT * FROM Quittances";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Quittances data) throws SQLException {

		}
	}