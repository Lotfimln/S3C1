package modele;

public class Garage extends Louable {

    public Garage(int idLouable, String adresse, double superficie, String numeroFiscal, boolean statut,
            java.util.Date dateAnniversaire, Assureur assurance) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance);
    }

}
