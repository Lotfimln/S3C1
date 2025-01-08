package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssocierDeleteDAO {
	private Connection connection;

	public AssocierDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idLouable, int idIndexCompteur) {
		String sql = "DELETE FROM associer WHERE Id_Louable = ? AND Id_Index_Compteur = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idLouable);
			statement.setInt(2, idIndexCompteur);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression dans associer : " + e.getMessage());
			return false;
		}
	}
}
