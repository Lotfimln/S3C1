package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApparaitreDeleteDAO {
	private Connection connection;

	public ApparaitreDeleteDAO() {
		try {
			this.connection = CictOracleDataSource.getConnectionBD();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
		}
	}

	public boolean supprimer(int idCharge, int idIndexCompteur) {
		String sql = "DELETE FROM apparaitre WHERE Id_Charge = ? AND Id_Index_Compteur = ?";
		try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
			statement.setInt(1, idCharge);
			statement.setInt(2, idIndexCompteur);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression dans apparaitre : " + e.getMessage());
			return false;
		}
	}
}
