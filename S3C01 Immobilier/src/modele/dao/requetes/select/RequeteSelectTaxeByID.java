package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Taxe;
import modele.dao.requetes.Requete;


public class RequeteSelectTaxeByID implements Requete<Taxe> {

		@Override
		public String requete() {
			return "SELECT * FROM Taxe where Id_Taxe = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Taxe data) throws SQLException {
			prSt.setInt(1, data.getIdTaxe());
		}
	}