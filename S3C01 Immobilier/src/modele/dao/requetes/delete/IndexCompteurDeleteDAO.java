package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IndexCompteurDeleteDAO {
	private Connection connection;

	public IndexCompteurDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idIndexCompteur) {
		String sql = "DELETE FROM Index_Compteur WHERE Id_Index_Compteur = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idIndexCompteur);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression de l'index compteur : " + e.getMessage());
			return false;
		}
	}
}
