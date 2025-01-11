package modele.dao.requetes.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garage;
import modele.dao.requetes.Requete;

public class RequeteUpdateGarage implements Requete<Garage> {

	@Override
    public String requete() {
        return "UPDATE Louable SET Id_Louable = ?, Adresse = ?, Superficie  = ?, NumeroFiscal = ?, Statut = ?, DateAnniversaire  = ?, Id_Immeuble = ?, Id_Assurance = ? WHERE Id_Louable = ?;"
                + "UPDATE Garage SET Id_Louable = ? WHERE Id_Louable = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, Garage data) throws SQLException {
        // Paramètres pour la table Louable
    	prSt.setInt(1, data.getIdLouable());
        prSt.setString(2, data.getAdresse());
        prSt.setDouble(3, data.getSuperficie());
        prSt.setInt(4, data.getNumeroFiscal());
        prSt.setString(5, data.getStatut());
        java.util.Date utilDate = data.getDateAnniversaire();
        if (utilDate != null) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            prSt.setDate(6, sqlDate);
        } else {
            prSt.setNull(6, java.sql.Types.DATE);
        }
        prSt.setInt(7, data.getImmeuble().getIdImmeuble());
        prSt.setInt(8, data.getAssurance().getIdAssurance());
        prSt.setInt(9, data.getIdLouable());

        // Paramètres pour la table Garage
        prSt.setInt(10, data.getIdLouable());
    }

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		// TODO Auto-generated method stub

	}
}
