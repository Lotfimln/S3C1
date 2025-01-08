package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImmeubleDeleteDAO {
	private Connection connection;

	public ImmeubleDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idImmeuble) {
		String sql = "DELETE FROM Immeuble WHERE Id_Immeuble = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idImmeuble);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression de l'immeuble : " + e.getMessage());
			return false;
		}
	}
}
