package modele;

import java.util.Date;
import java.util.Objects;

public class Taxe {
    private int idTaxe;
    private double montantTaxeFoncieres;
    private java.util.Date dateTaxe;
    private Immeuble immeuble; // Relation avec Immeuble

    public Taxe(int idTaxe, double montantTaxeFoncieres, java.util.Date dateTaxe, Immeuble immeuble) {
        this.idTaxe = idTaxe;
        this.montantTaxeFoncieres = montantTaxeFoncieres;
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

	@Override
	public String toString() {
		return "Taxe [idTaxe=" + idTaxe + ", montantTaxeFoncieres=" + montantTaxeFoncieres + ", dateTaxe=" + dateTaxe
				+ ", immeuble=" + immeuble + "]";
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
