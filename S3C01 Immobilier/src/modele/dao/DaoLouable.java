package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Assureur;
import modele.Immeuble;
import modele.Louable;
import modele.dao.requetes.delete.RequeteDeleteLouable;
import modele.dao.requetes.update.RequeteUpdateLouable;
import modele.dao.requetes.select.RequeteSelectLouableByID;
import modele.dao.requetes.select.RequeteSelectLouable;

public class DaoLouable implements Dao<Louable> {

	private Connection connection;

	public DaoLouable(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Louable donnees) throws SQLException {
		String sql = "INSERT INTO Louable (Id_Louable, TypeLouable, Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, Id_Immeuble, Id_Assurance, NbPieces) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prSt = this.connection.prepareStatement(sql)) {
			prSt.executeUpdate();
				
			// Insertion dans la table Louable
			prSt.setInt(1, donnees.getIdLouable());
			prSt.setString(2, donnees.getTypeLouable());
			prSt.setString(3, donnees.getAdresse());
			prSt.setDouble(4, donnees.getSuperficie());
			prSt.setString(5, donnees.getNumeroFiscal());
			prSt.setString(6, donnees.getStatut());
			java.util.Date utilDate = donnees.getDateAnniversaire();	            
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			prSt.setDate(7, sqlDate);
			prSt.setInt(8, donnees.getImmeuble().getIdImmeuble());
			prSt.setInt(9, donnees.getAssurance().getIdAssurance());
			prSt.setInt(10, donnees.getNbPieces());
			prSt.executeUpdate();
			}
		catch (SQLException e) {
	            String messageErreur = "Erreur lors de la création du logement avec Id_Louable = " + donnees.getIdLouable() + 
                        ". Détails : " + e.getMessage();
	            System.err.println(messageErreur);
	            throw new SQLException(messageErreur, e);
		}
	}

	@Override
	public void update(Louable donnees) throws SQLException {
		RequeteUpdateLouable requeteUpdate = new RequeteUpdateLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		} catch (SQLException e) {
            String messageErreur = "Erreur lors de la mise à jour du logement avec Id_Louable = " + donnees.getIdLouable() + 
                    ". Détails : " + e.getMessage();
            System.err.println(messageErreur);
            throw new SQLException(messageErreur, e);
		}
	}

	@Override
	public void delete(Louable donnees) throws SQLException {
		RequeteDeleteLouable requeteDeleteLogement = new RequeteDeleteLouable();
		RequeteDeleteLouable requeteDeleteLouable = new RequeteDeleteLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDeleteLogement.requete())) {
			requeteDeleteLogement.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDeleteLouable.requete())) {
			requeteDeleteLouable.parametres(prSt, donnees);
			prSt.executeUpdate();
		} catch (SQLException e) {
            String messageErreur = "Erreur lors de la suppression du logement avec Id_Louable = " + donnees.getIdLouable() +
                    ". Détails : " + e.getMessage();
            System.err.println(messageErreur);
            throw new SQLException(messageErreur, e);
		}
	}

	@Override
	public Louable findById(String... id) throws SQLException {
		RequeteSelectLouableByID requeteSelectById = new RequeteSelectLouableByID();
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
			requeteSelectById.parametres(prSt, id);
			try (ResultSet rs = prSt.executeQuery()) {
				if (rs.next()) {
	                int idAssureur = rs.getInt("Id_Assureur");
	                int idImmeuble = rs.getInt("Id_Immeuble");
	                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));
	                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));
	                
					return new Louable(
							rs.getInt("Id_Louable"), 
							rs.getString("TypeLouable"), 
							rs.getString("Adresse"), 
							rs.getDouble("Superficie"), 
							rs.getString("NumeroFiscal"), 
							rs.getString("Statut"), 
							rs.getDate("DateAnniversaire"),
							rs.getDate("DateAcquisition"), 
							rs.getInt("NbPieces"),
							immeuble, 
							assureur);
				} else {
                    throw new SQLException("Aucun louable trouvé avec Id_Louable = " + id[0]);
                }
            }
        } catch (SQLException e) {
            String messageErreur = "Erreur lors de la récupération du logement avec Id_Louable = " + id[0] +
                                    ". Détails : " + e.getMessage();
            System.err.println(messageErreur);
            throw new SQLException(messageErreur, e);
        }
    }

	@Override
	public List<Louable> findAll() throws SQLException {
		RequeteSelectLouable requeteSelectAll = new RequeteSelectLouable();
		List<Louable> logements = new ArrayList<>();
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				int idImmeuble = rs.getInt("Id_Immeuble");
                int idAssureur = rs.getInt("Id_Assurance");
                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));
                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));

				logements.add(new Louable(
						rs.getInt("Id_Louable"), 
						rs.getString("TypeLouable"), 
						rs.getString("Adresse"), 
						rs.getDouble("Superficie"), 
						rs.getString("NumeroFiscal"), 
						rs.getString("Statut"), 
						rs.getDate("DateAnniversaire"),
						rs.getDate("DateAcquisition"), 
						rs.getInt("NbPieces"),
						immeuble, 
						assureur));
			}
        } catch (SQLException e) {
            String messageErreur = "Erreur lors de la récupération de la liste des logements. Détails : " + e.getMessage();
            System.err.println(messageErreur);
            throw new SQLException(messageErreur, e);
        }
        return logements;
    }
}