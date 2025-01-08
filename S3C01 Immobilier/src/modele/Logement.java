package modele;

public class Logement extends Louable {
    private String nbPieces;

    public Logement(int idLouable, String adresse, double superficie, String numeroFiscal, boolean statut,
            java.util.Date dateAnniversaire, Assureur assurance, String nbPieces) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance);
        this.nbPieces = nbPieces;
    }

    // Getter et Setter pour nbPieces
    public String getNbPieces() {
        return nbPieces;
    }

    public void setNbPieces(String nbPieces) {
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
