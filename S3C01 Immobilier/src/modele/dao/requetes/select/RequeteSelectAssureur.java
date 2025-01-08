package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assureur;
import modele.dao.requetes.Requete;


public class RequeteSelectAssureur implements Requete<Assureur> {

		@Override
		public String requete() {
			return "SELECT * FROM Assureur";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Assureur data) throws SQLException {
		}
	}