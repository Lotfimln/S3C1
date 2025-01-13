package modele;

import java.util.Date;
import java.util.Objects;

public class Facture {
    private int idFacture;
    private double montant;
    private java.util.Date dateFacture;
    private String referenceDevis;
    private String entreprise;
    private Date datePaiement;

    private Entreprise entrepriseAssociee; // Relation avec Entreprise
    private Louable louable; // Relation avec Louable

    public Facture(int idFacture, double montant, java.util.Date dateFacture, String referenceDevis,
                   String entreprise, Date datePaiement, Entreprise entrepriseAssociee, Louable louable) {
        this.idFacture = idFacture;
        this.montant = montant;
        this.dateFacture = dateFacture;
        this.referenceDevis = referenceDevis;
        this.entreprise = entreprise;
        this.datePaiement = datePaiement;
        this.entrepriseAssociee = entrepriseAssociee;
        this.louable = louable;
    }

	public int getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public java.util.Date getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(java.util.Date dateFacture) {
		this.dateFacture = dateFacture;
	}

	public String getReferenceDevis() {
		return referenceDevis;
	}

	public void setReferenceDevis(String referenceDevis) {
		this.referenceDevis = referenceDevis;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public java.util.Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Entreprise getEntrepriseAssociee() {
		return entrepriseAssociee;
	}

	public void setEntrepriseAssociee(Entreprise entrepriseAssociee) {
		this.entrepriseAssociee = entrepriseAssociee;
	}

	public Louable getLouable() {
		return louable;
	}

	public void setLouable(Louable louable) {
		this.louable = louable;
	}

	@Override
	public String toString() {
		return datePaiement.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateFacture, datePaiement, entreprise, entrepriseAssociee, idFacture, louable, montant,
				referenceDevis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Facture)) {
			return false;
		}
		Facture other = (Facture) obj;
		return Objects.equals(dateFacture, other.dateFacture) && Objects.equals(datePaiement, other.datePaiement)
				&& Objects.equals(entreprise, other.entreprise)
				&& Objects.equals(entrepriseAssociee, other.entrepriseAssociee) && idFacture == other.idFacture
				&& Objects.equals(louable, other.louable)
				&& Double.doubleToLongBits(montant) == Double.doubleToLongBits(other.montant)
				&& Objects.equals(referenceDevis, other.referenceDevis);
	}
}
