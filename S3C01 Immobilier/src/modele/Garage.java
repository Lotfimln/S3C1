package modele;

public class Garage extends Louable {

    public Garage(int idLouable, String adresse, double superficie, int numeroFiscal, String statut,
            java.util.Date dateAnniversaire, Assureur assurance) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance);
    }

}
