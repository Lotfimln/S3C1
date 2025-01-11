package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IndexCompteur;
import modele.dao.requetes.Requete;


public class RequeteSelectIndexCompteur implements Requete<IndexCompteur> {

		@Override
		public String requete() {
			return "SELECT * FROM Index_Compteur";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, IndexCompteur data) throws SQLException {
		}
	}