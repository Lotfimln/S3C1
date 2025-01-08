package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Entreprise;
import modele.dao.requetes.Requete;


public class RequeteSelectEntreprise implements Requete<Entreprise> {

		@Override
		public String requete() {
			return "SELECT * FROM Entreprise";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Entreprise data) throws SQLException {
		}
	}