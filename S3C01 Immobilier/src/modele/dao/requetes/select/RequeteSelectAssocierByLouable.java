package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;


public class RequeteSelectAssocierByLouable implements Requete<Associer> {

		@Override
		public String requete() {
			return "SELECT * FROM Associer where Id_Louable = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Associer data) throws SQLException {
			prSt.setInt(1, data.getIdLouable());
		}
	}