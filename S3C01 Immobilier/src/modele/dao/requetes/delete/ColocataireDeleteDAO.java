package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ColocataireDeleteDAO {
	private Connection connection;

	public ColocataireDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idLocataire, int idColocataire) {
		String sql = "DELETE FROM Colocataire WHERE Id_Locataire = ? AND Id_Locataire_1 = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idLocataire);
			statement.setInt(2, idColocataire);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression dans colocataire : " + e.getMessage());
			return false;
		}
	}
}
