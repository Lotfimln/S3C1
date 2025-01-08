package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;


public class RequeteSelectDiagnostic implements Requete<Diagnostic> {

		@Override
		public String requete() {
			return "SELECT * FROM Diagnostic";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Diagnostic data) throws SQLException {
		}
	}