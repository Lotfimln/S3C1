package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Logement;
import modele.dao.requetes.Requete;


public class RequeteSelectLogement implements Requete<Logement> {

		@Override
		public String requete() {
			return "SELECT * FROM LOGEMENT";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Logement data) throws SQLException {
			
		}
	}