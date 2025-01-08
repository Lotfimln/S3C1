package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Facture;
import modele.dao.requetes.Requete;


public class RequeteSelectFacture implements Requete<Facture> {

		@Override
		public String requete() {
			return "SELECT * FROM Facture";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Facture data) throws SQLException {
		}
	}