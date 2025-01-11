package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;


public class RequeteSelectGarageLouable implements Requete<Garage> {

		@Override
		public String requete() {
			return "SELECT GARAGE.*, LOUABLE.* FROM GARAGE JOIN LOUABLE ON LOUABLE.Id_Louable = GARAGE.Id_Louable";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Garage data) throws SQLException {

		}
	}