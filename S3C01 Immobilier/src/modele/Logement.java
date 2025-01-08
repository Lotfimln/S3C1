package modele;

import java.util.List;

public class Logement extends Louable {
    private String nbPieces;

    public Logement(int idLouable, String adresse, double superficie, String numeroFiscal, boolean statut,
            java.util.Date dateAnniversaire, Assureur assurance, List<Facture> factures,
            List<Diagnostic> diagnostics, List<ContratDeLocation> contratsDeLocation, String nbPieces) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance, factures
        		, diagnostics, contratsDeLocation);
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
