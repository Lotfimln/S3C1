package modele;

import java.util.List;
import java.util.Objects;

public abstract class Louable {
    private int idLouable;
    private String adresse;
    private double superficie;
    private String numeroFiscal;
    private boolean statut;
    private java.util.Date dateAnniversaire;

    private Assureur assurance; // Relation avec Assureur
    private List<Facture> factures; // Relation avec Facture
    private List<Diagnostic> diagnostics; // Relation avec Diagnostic
    private List<ContratDeLocation> contratsDeLocation; // Relation avec Contrat_de_location

    public Louable(int idLouable, String adresse, double superficie, String numeroFiscal, boolean statut,
                   java.util.Date dateAnniversaire, Assureur assurance, List<Facture> factures,
                   List<Diagnostic> diagnostics, List<ContratDeLocation> contratsDeLocation) {
        this.idLouable = idLouable;
        this.adresse = adresse;
        this.superficie = superficie;
        this.numeroFiscal = numeroFiscal;
        this.statut = statut;
        this.dateAnniversaire = dateAnniversaire;
        this.assurance = assurance;
        this.factures = factures;
        this.diagnostics = diagnostics;
        this.contratsDeLocation = contratsDeLocation;
    }

	public int getIdLouable() {
		return idLouable;
	}

	public void setIdLouable(int idLouable) {
		this.idLouable = idLouable;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}

	public String getNumeroFiscal() {
		return numeroFiscal;
	}

	public void setNumeroFiscal(String numeroFiscal) {
		this.numeroFiscal = numeroFiscal;
	}

	public boolean isStatut() {
		return statut;
	}

	public void setStatut(boolean statut) {
		this.statut = statut;
	}

	public java.util.Date getDateAnniversaire() {
		return dateAnniversaire;
	}

	public void setDateAnniversaire(java.util.Date dateAnniversaire) {
		this.dateAnniversaire = dateAnniversaire;
	}

	public Assureur getAssurance() {
		return assurance;
	}

	public void setAssurance(Assureur assurance) {
		this.assurance = assurance;
	}

	public List<Facture> getFactures() {
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	public List<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(List<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public List<ContratDeLocation> getContratsDeLocation() {
		return contratsDeLocation;
	}

	public void setContratsDeLocation(List<ContratDeLocation> contratsDeLocation) {
		this.contratsDeLocation = contratsDeLocation;
	}

	@Override
	public String toString() {
		return "Louable [idLouable=" + idLouable + ", adresse=" + adresse + ", superficie=" + superficie
				+ ", numeroFiscal=" + numeroFiscal + ", statut=" + statut + ", dateAnniversaire=" + dateAnniversaire
				+ ", assurance=" + assurance + ", factures=" + factures + ", diagnostics=" + diagnostics
				+ ", contratsDeLocation=" + contratsDeLocation + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLouable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Louable)) {
			return false;
		}
		Louable other = (Louable) obj;
		return idLouable == other.idLouable;
	}
 
}
