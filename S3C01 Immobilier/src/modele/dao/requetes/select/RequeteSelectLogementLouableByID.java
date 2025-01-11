package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Logement;
import modele.dao.requetes.Requete;


public class RequeteSelectLogementLouableByID implements Requete<Logement> {

		@Override
		public String requete() {
			return "SELECT LOGEMENT.*, LOUABLE.* FROM LOGEMENT JOIN LOUABLE ON LOUABLE.Id_Louable = LOGEMENT.Id_Louable WHERE LOGEMENT.Id_Louable = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Logement data) throws SQLException {
			prSt.setInt(1, data.getIdLouable());
		}
	}