package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Immeuble;
import modele.dao.requetes.Requete;


public class RequeteSelectImmeuble implements Requete<Immeuble> {

		@Override
		public String requete() {
			return "SELECT * FROM Immeuble";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Immeuble data) throws SQLException {

		}
	}