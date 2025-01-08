package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CorrespondreDeleteDAO {
	private Connection connection;

	public CorrespondreDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idLocataire, int idContrat) {
		String sql = "DELETE FROM correspondre WHERE Id_Locataire = ? AND Id_Contrat_de_location = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idLocataire);
			statement.setInt(2, idContrat);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression dans correspondre : " + e.getMessage());
			return false;
		}
	}
}
