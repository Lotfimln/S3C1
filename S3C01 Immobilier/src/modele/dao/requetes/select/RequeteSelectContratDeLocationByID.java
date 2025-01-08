package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ContratDeLocation;
import modele.dao.requetes.Requete;


public class RequeteSelectContratDeLocationByID implements Requete<ContratDeLocation> {

		@Override
		public String requete() {
			return "SELECT * FROM Locataire where Id_Contrat_De_Location = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, ContratDeLocation data) throws SQLException {
	        prSt.setInt(1, data.getIdContratDeLocation());
		}
	}