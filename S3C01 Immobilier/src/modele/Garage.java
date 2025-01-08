package modele;

import java.util.List;

public class Garage extends Louable {

    public Garage(int idLouable, String adresse, double superficie, String numeroFiscal, boolean statut,
            java.util.Date dateAnniversaire, Assureur assurance, List<Facture> factures,
            List<Diagnostic> diagnostics, List<ContratDeLocation> contratsDeLocation) {
        super(idLouable, adresse, superficie, numeroFiscal, statut, dateAnniversaire, assurance, factures
        		, diagnostics, contratsDeLocation);
    }

}
