package modele;

import java.util.Objects;

public class Taxe {
    private int idTaxe;
    private double montantTaxeFoncieres;
    private java.util.Date datePaiement;
    private java.util.Date dateTaxe;
    private Immeuble immeuble; // Relation avec Immeuble

    public Taxe(int idTaxe, double montantTaxeFoncieres, java.util.Date datePaiement, java.util.Date dateTaxe, Immeuble immeuble) {
        this.idTaxe = idTaxe;
        this.montantTaxeFoncieres = montantTaxeFoncieres;
        this.datePaiement = datePaiement;
        this.dateTaxe = dateTaxe;
        this.immeuble = immeuble;
    }

	public int getIdTaxe() {
		return idTaxe;
	}

	public void setIdTaxe(int idTaxe) {
		this.idTaxe = idTaxe;
	}

	public double getMontantTaxeFoncieres() {
		return montantTaxeFoncieres;
	}

	public void setMontantTaxeFoncieres(double montantTaxeFoncieres) {
		this.montantTaxeFoncieres = montantTaxeFoncieres;
	}

	public java.util.Date getDateTaxe() {
		return dateTaxe;
	}

	public void setDateTaxe(java.util.Date dateTaxe) {
		this.dateTaxe = dateTaxe;
	}

	public Immeuble getImmeuble() {
		return immeuble;
	}

	public void setImmeuble(Immeuble immeuble) {
		this.immeuble = immeuble;
	}

	public java.util.Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(java.util.Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	@Override
	public String toString() {
		return "" + idTaxe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTaxe, idTaxe, immeuble, montantTaxeFoncieres);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Taxe)) {
			return false;
		}
		Taxe other = (Taxe) obj;
		return Objects.equals(dateTaxe, other.dateTaxe) && idTaxe == other.idTaxe
				&& Objects.equals(immeuble, other.immeuble)
				&& Double.doubleToLongBits(montantTaxeFoncieres) == Double.doubleToLongBits(other.montantTaxeFoncieres);
	}
}
