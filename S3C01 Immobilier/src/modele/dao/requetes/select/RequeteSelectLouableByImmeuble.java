package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louable;
import modele.dao.requetes.Requete;


public class RequeteSelectLouableByImmeuble implements Requete<Louable> {

		@Override
		public String requete() {
			return "SELECT * FROM Louable where Id_Immeuble = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Louable data) throws SQLException {
			prSt.setInt(1, data.getImmeuble().getIdImmeuble());
		}
	}