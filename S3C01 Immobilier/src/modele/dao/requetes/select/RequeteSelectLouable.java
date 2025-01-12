package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;


public class RequeteSelectLouable implements Requete<Louable> {

		@Override
		public String requete() {
			return "SELECT * FROM Louable";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Louable data) throws SQLException {

		}
	}