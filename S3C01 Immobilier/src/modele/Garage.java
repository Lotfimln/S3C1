package modele;

public class Garage extends Louable {

    public Garage(int idLouable, String adresse, double superficie, String numeroFiscal, String statut,
            java.util.Date dateAnniversaire, Immeuble immeuble, Assureur assurance, Louable louable) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, immeuble, assurance);
    }

    public int getIdGarage() {
    	return idLouable;
    }

    public void setIdGarage(int idLouable) {
        this.idLouable = idLouable;
    }
}
