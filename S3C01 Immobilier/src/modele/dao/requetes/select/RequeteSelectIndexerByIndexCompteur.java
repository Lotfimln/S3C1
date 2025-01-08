package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;


public class RequeteSelectIndexerByIndexCompteur implements Requete<Indexer> {

		@Override
		public String requete() {
			return "SELECT * FROM Indexer where Id_Index_Compteur = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Indexer data) throws SQLException {
			prSt.setInt(1, data.getIdIndexCompteur());
		}
	}