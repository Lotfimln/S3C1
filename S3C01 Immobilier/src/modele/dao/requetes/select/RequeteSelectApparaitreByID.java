package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Apparaitre;
import modele.dao.requetes.Requete;


public class RequeteSelectApparaitreByID implements Requete<Apparaitre> {

		@Override
		public String requete() {
			return "SELECT * FROM Apparaitre where Id_Charge = ? and Id_Index = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Apparaitre data) throws SQLException {
			prSt.setInt(1, data.getIdCharge());
			prSt.setInt(2, data.getIdIndexCompteur());
		}
	}