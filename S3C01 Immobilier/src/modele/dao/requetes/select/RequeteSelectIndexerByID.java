package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Indexer;
import modele.dao.requetes.Requete;


public class RequeteSelectIndexerByID implements Requete<Indexer> {

		@Override
		public String requete() {
			return "SELECT * FROM Index_Compteur where Id_Index_Compteur = ? and Id_Immeuble = ?";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
			prSt.setString(1, id[0]);
			prSt.setString(2, id[1]);
		}

		@Override
		public void parametres(PreparedStatement prSt, Indexer data) throws SQLException {
			prSt.setInt(1, data.getIdIndexCompteur());
			prSt.setInt(1, data.getIdImmeuble());
		}
	}