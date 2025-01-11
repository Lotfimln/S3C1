package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Taxe;
import modele.dao.requetes.Requete;


public class RequeteSelectTaxe implements Requete<Taxe> {

		@Override
		public String requete() {
			return "SELECT * FROM Taxe";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Taxe data) throws SQLException {

		}
	}