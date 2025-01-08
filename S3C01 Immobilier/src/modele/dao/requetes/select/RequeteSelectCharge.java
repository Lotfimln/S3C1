package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;


public class RequeteSelectCharge implements Requete<Charge> {

		@Override
		public String requete() {
			return "SELECT * FROM Charge";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Charge data) throws SQLException {
		}
	}