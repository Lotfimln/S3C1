package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;


public class RequeteSelectContratDeLocation implements Requete<ContratDeLocation> {

		@Override
		public String requete() {
			return "SELECT * FROM Locataire";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, ContratDeLocation data) throws SQLException {
		}
	}