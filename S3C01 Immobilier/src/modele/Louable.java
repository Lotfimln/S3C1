package modele;

import java.util.Objects;

public abstract class Louable {
    protected int idLouable;
    private String adresse;
    private double superficie;
    private int numeroFiscal;
    private String statut;
    private java.util.Date dateAnniversaire;
    private java.util.Date dateAcquisition;
    private Immeuble immeuble; // Relation avec Immeuble
    private Assureur assurance; // Relation avec Assureur

    public Louable(int idLouable, String adresse, double superficie, int numeroFiscal, String statut,
                   java.util.Date dateAnniversaire, java.util.Date dateAcquisition, Immeuble immeuble, Assureur assurance) {
        this.idLouable = idLouable;
        this.adresse = adresse;
        this.superficie = superficie;
        this.numeroFiscal = numeroFiscal;
        this.statut = statut;
        this.dateAnniversaire = dateAnniversaire;
        this.dateAcquisition = dateAcquisition;
        this.immeuble = immeuble;
        this.assurance = assurance;
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

	public int getNumeroFiscal() {
		return numeroFiscal;
	}

	public void setNumeroFiscal(int numeroFiscal) {
		this.numeroFiscal = numeroFiscal;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
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
	
	public Immeuble getImmeuble() {
		return immeuble;
	}
	
	public void setImmeuble(Immeuble immeuble) {
		this.immeuble = immeuble;
	}

	public java.util.Date getDateAcquisition() {
		return dateAcquisition;
	}

	public void setDateAcquisition(java.util.Date dateAcquisition) {
		this.dateAcquisition = dateAcquisition;
	}

	@Override
	public String toString() {
		return "Louable [idLouable=" + idLouable + ", adresse=" + adresse + ", superficie=" + superficie
				+ ", numeroFiscal=" + numeroFiscal + ", statut=" + statut + ", dateAnniversaire=" + dateAnniversaire
				+ ", assurance=" + assurance + ", immeuble=" + immeuble + "]";
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
