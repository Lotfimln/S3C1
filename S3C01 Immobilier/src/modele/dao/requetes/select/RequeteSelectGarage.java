package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;


public class RequeteSelectGarage implements Requete<Garage> {

		@Override
		public String requete() {
			return "SELECT * FROM Garage";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Garage data) throws SQLException {

		}
	}