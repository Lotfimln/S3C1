package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface définissant les méthodes requête(), parametres(PreparedStatement, String...) 
 * et parametres(PreparedStatement, T) pour les objets de requête utilisés dans les DAO.
 * @param <T> Type de données associé à la requête.
 */
public interface Requete<T> {

    /**
     * Retourne la requête SQL associée à l'objet de requête.
     *
     * @return Requête SQL sous forme de chaîne de caractères.
     */
    public String requete();

    /**
     * Paramètre une PreparedStatement avec les valeurs fournies sous forme de tableau de chaînes.
     *
     * @param prSt PreparedStatement à paramétrer.
     * @param id   Valeurs à utiliser dans la requête.
     * @throws SQLException Si une erreur SQL survient lors du paramétrage de la requête.
     */
    public void parametres(PreparedStatement prSt, String... id) throws SQLException;

    /**
     * Paramètre une PreparedStatement avec les valeurs fournies dans l'objet de données.
     *
     * @param prSt PreparedStatement à paramétrer.
     * @param data Données à utiliser dans la requête.
     * @throws SQLException Si une erreur SQL survient lors du paramétrage de la requête.
     */
    public void parametres(PreparedStatement prSt, T data) throws SQLException;
}
