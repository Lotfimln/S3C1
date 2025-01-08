package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LouableDeleteDAO {
	private Connection connection;

	public LouableDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idLouable) {
		String sql = "DELETE FROM Louable WHERE Id_Louable = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idLouable);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression du bien louable : " + e.getMessage());
			return false;
		}
	}
}
