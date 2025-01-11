package modele;

public class Garage extends Louable {

    public Garage(int idLouable, String adresse, double superficie, int numeroFiscal, String statut,
            java.util.Date dateAnniversaire, java.util.Date dateAcquisition, Immeuble immeuble, Assureur assurance) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, dateAcquisition, immeuble, assurance);
    }
    
    public int getIdGarage() {
    	return idLouable;
    }
    
    public void setIdGarage(int idLouable) {
        this.idLouable = idLouable;
    }
}
