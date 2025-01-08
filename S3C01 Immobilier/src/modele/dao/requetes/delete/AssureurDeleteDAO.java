package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssureurDeleteDAO {
	private Connection connection;

	public AssureurDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idAssurance) {
		String sql = "DELETE FROM Assureur WHERE Id_Assurance = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idAssurance);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression de l'assureur : " + e.getMessage());
			return false;
		}
	}
}
