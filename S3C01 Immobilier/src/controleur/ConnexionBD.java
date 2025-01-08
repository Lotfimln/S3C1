package controleur;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class ConnexionBD {
    private static ConnexionBD instance;
    private Connection connection;

    private ConnexionBD(String username, String password) throws SQLException {
        OracleDataSource bd = new OracleDataSource();
        bd.setURL("jdbc:oracle:thin:@telline.univ-tlse3.fr:1521:etupre");
        bd.setUser(username);
        bd.setPassword(password);
        this.connection = bd.getConnection();
    }

    public static ConnexionBD getInstance(String username, String password) throws SQLException {
        if (instance == null) {
            instance = new ConnexionBD(username, password);
        }
        return instance;
    }

    public static ConnexionBD getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ConnexionBD n'a pas encore été initialisée. Appelez getInstance(username, password) d'abord.");
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            instance = null; // Permet de réinitialiser l'instance pour une nouvelle connexion
        }
    }
}
