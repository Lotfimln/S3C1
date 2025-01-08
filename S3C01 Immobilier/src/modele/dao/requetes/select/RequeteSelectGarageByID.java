package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;


public class RequeteSelectGarageByID implements Requete<Garage> {

		@Override
		public String requete() {
			return "SELECT * FROM Garage where Id_Louable = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Garage data) throws SQLException {
			prSt.setInt(1, data.getIdLouable());
		}
	}