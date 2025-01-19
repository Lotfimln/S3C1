package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Colocataire;
import modele.dao.requetes.Requete;


public class RequeteSelectColocataireByLocataire1 implements Requete<Colocataire> {

		@Override
		public String requete() {
			return "SELECT * FROM Colocataire where Id_Locataire_1 = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Colocataire data) throws SQLException {
			prSt.setString(1, data.getIdLocataire());

		}
	}