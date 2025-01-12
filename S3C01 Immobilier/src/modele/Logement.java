package modele;

public class Logement extends Louable {
    private int nbPieces;

    public Logement(int idLouable, String adresse, double superficie, String numeroFiscal, String statut,
            java.util.Date dateAnniversaire, java.util.Date dateAcquisition, Immeuble immeuble, Assureur assurance, Louable louable, int nbPieces) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, dateAcquisition, immeuble, assurance);
        this.nbPieces = nbPieces;
    }

    public void setIdLogement(int idLouable) {
        this.idLouable = idLouable;
    }

    public int getIdLogement() {
    	return idLouable;
    }
    
    public int getNbPieces() {
        return nbPieces;
    }

    public void setNbPieces(int nbPieces) {
        this.nbPieces = nbPieces;
    }

    @Override
    public String toString() {
        return "Logement{" +
                "nbPieces='" + nbPieces + '\'' +
                ", " + super.toString() +
                '}';
    }
}
