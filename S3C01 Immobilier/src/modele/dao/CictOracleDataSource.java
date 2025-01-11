package modele.dao;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;


public class CictOracleDataSource extends OracleDataSource {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static Connection connection;

	/**
	 * @param nom (String) : correspond au nom de l'utilisateur pour la connexion à la BD
	 * @param mdp (String) : correspond au mot de passe de l'utilisateur pour la connexion à la BD
	 * @throws SQLException
	 */
	private CictOracleDataSource(String nom, String mdp) throws SQLException {
		this.setURL("jdbc:oracle:thin:@telline.univ-tlse3.fr:1521:etupre");
		this.setUser(nom);
		this.setPassword(mdp);
	}

	// Méthode pour créer ou récupérer la connexion unique (Singleton)
	/**
	 * @param nom (String) : correspond au nom de l'utilisateur pour la connexion à la BD
	 * @param mdp (String) : correspond au mot de passe de l'utilisateur pour la connexion à la BD
	 * @return la connexion avec le bon accès pour le bon utilisateur à la base de donnée
	 * @throws SQLException
	 */
	public static Connection creerAcces(String nom, String mdp) throws SQLException {
		if (connection == null || connection.isClosed()) {
			// Si la connexion n'existe pas ou est fermée, on la crée
			CictOracleDataSource dataSource = new CictOracleDataSource(nom, mdp);
			connection = dataSource.getConnection();
		}
		return connection;
	}

	// Méthode pour récupérer la connexion
	/**
	 * @return la connexion actuelle à la base de donnée
	 */
	public static Connection getConnectionBD() {
		return connection;
	}

	// Méthode pour déconnecter
	/**
	 * @throws SQLException
	 */
	public static void deconnecter() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
		connection = null;
	}
}