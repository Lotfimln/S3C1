package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;


public class RequeteSelectAssocier implements Requete<Associer> {

		@Override
		public String requete() {
			return "SELECT * FROM Associer";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Associer data) throws SQLException {
		}
	}