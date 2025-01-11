package modele;

public class Logement extends Louable {
    private int nbPieces;

    public Logement(int idLouable, String adresse, double superficie, int numeroFiscal, String statut,
            java.util.Date dateAnniversaire, Assureur assurance, int nbPieces, Louable louable) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance);
        this.nbPieces = nbPieces;
    }
    
    public void setIdLogement(int idLouable) {
        this.idLouable = idLouable;
    }
    
    public int getIdLogement() {
    	return idLouable;
    }
    // Getter et Setter pour nbPieces
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
