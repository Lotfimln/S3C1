package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Correspondre;
import modele.dao.requetes.delete.RequeteDeleteCorrespondre;
import modele.dao.requetes.select.RequeteSelectCorrespondre;
import modele.dao.requetes.select.RequeteSelectCorrespondreByID;
import modele.dao.requetes.update.RequeteUpdateCorrespondre;

public class DaoCorrespondre implements Dao<Correspondre> {

	private Connection connection;

	public DaoCorrespondre(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Correspondre donnees) throws SQLException {
		String sql = "INSERT INTO Correspondre (Id_Locataire, Id_Contrat_de_location) VALUES (?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.setInt(1, donnees.getIdLocataire());
			prSt.setInt(2, donnees.getIdContratDeLocation());
			prSt.executeUpdate();
		}
	}

	@Override
	public void update(Correspondre donnees) throws SQLException {
		RequeteUpdateCorrespondre requeteUpdate = new RequeteUpdateCorrespondre();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(Correspondre donnees) throws SQLException {
		RequeteDeleteCorrespondre requeteDelete = new RequeteDeleteCorrespondre();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
    public Correspondre findById(String... id) throws SQLException {
        RequeteSelectCorrespondreByID requeteSelectById = new RequeteSelectCorrespondreByID();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectById.requete())) {
            requeteSelectById.parametres(prSt, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    return new Correspondre(
                        rs.getInt("Id_Contrat_de_location"),
                        rs.getInt("Id_Locataire")
                    );
                }
            }
        }
        return null; // Retourne null si aucun immeuble trouvé
    }

	@Override
	public List<Correspondre> findAll() throws SQLException {
		RequeteSelectCorrespondre requeteSelectAll = new RequeteSelectCorrespondre();
		List<Correspondre> correspondances = new ArrayList<>();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				correspondances.add(new Correspondre(rs.getInt("Id_Locataire"), rs.getInt("Id_Contrat_de_location")));
			}
		}
		return correspondances;
	}
}
