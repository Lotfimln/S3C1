package modele.dao;

import java.sql.SQLException;
import java.util.List;

// Interface DAO avec les methodes
public interface Dao<T> {

	public void create(T donnees) throws SQLException;

	public void update(T donnees) throws SQLException;

	public void delete(String...id) throws SQLException;

	public T findById(String... id) throws SQLException;

	public List<T> findAll() throws SQLException;
}
