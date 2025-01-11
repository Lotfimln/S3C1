package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;


public class RequeteSelectLogementLouable implements Requete<Garage> {

		@Override
		public String requete() {
			return "SELECT LOGEMENT.*, LOUABLE.* FROM LOGEMENT JOIN LOUABLE ON LOUABLE.Id_Louable = LOGEMENT.Id_Louable";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Garage data) throws SQLException {

		}
	}