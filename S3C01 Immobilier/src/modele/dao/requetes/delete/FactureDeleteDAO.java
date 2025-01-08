package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FactureDeleteDAO {
	private Connection connection;

	public FactureDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idFacture) {
		String sql = "DELETE FROM Facture WHERE Id_Facture = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idFacture);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression de la facture : " + e.getMessage());
			return false;
		}
	}
}
