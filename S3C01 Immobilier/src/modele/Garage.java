package modele;

public class Garage extends Louable {

    public Garage(int idLouable, String adresse, double superficie, int numeroFiscal, String statut,
            java.util.Date dateAnniversaire, Assureur assurance, Louable louable) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance);
    }
    
    public int getIdGarage() {
    	return idLouable;
    }
    
    public void setIdGarage(int idLouable) {
        this.idLouable = idLouable;
    }
}
