package modele;

import java.util.Objects;

public class Charge {
    private int idCharge;
    private String type;
    private double montant;
    private boolean recuperable;
    private java.util.Date periodeDebut;
    private java.util.Date periodeFin;

    private Facture facture; // Relation avec Facture
    private Louable louable; // Relation avec Louable

    public Charge(int idCharge, String type, double montant, boolean recuperable, java.util.Date periodeDebut,
                  java.util.Date periodeFin, Facture facture, Louable louable) {
        this.idCharge = idCharge;
        this.type = type;
        this.montant = montant;
        this.recuperable = recuperable;
        this.periodeDebut = periodeDebut;
        this.periodeFin = periodeFin;
        this.facture = facture;
        this.louable = louable;
    }

	public int getIdCharge() {
		return idCharge;
	}

	public void setIdCharge(int idCharge) {
		this.idCharge = idCharge;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public boolean isRecuperable() {
		return recuperable;
	}

	public void setRecuperable(boolean recuperable) {
		this.recuperable = recuperable;
	}

	public java.util.Date getPeriodeDebut() {
		return periodeDebut;
	}

	public void setPeriodeDebut(java.util.Date periodeDebut) {
		this.periodeDebut = periodeDebut;
	}

	public java.util.Date getPeriodeFin() {
		return periodeFin;
	}

	public void setPeriodeFin(java.util.Date periodeFin) {
		this.periodeFin = periodeFin;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Louable getLouable() {
		return louable;
	}

	public void setLouable(Louable louable) {
		this.louable = louable;
	}

	@Override
	public String toString() {
		return "Charge [idCharge=" + idCharge + ", type=" + type + ", montant=" + montant + ", recuperable="
				+ recuperable + ", periodeDebut=" + periodeDebut + ", periodeFin=" + periodeFin + ", facture=" + facture
				+ ", louable=" + louable + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(facture, idCharge, louable, montant, periodeDebut, periodeFin, recuperable, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Charge)) {
			return false;
		}
		Charge other = (Charge) obj;
		return Objects.equals(facture, other.facture) && idCharge == other.idCharge
				&& Objects.equals(louable, other.louable)
				&& Double.doubleToLongBits(montant) == Double.doubleToLongBits(other.montant)
				&& Objects.equals(periodeDebut, other.periodeDebut) && Objects.equals(periodeFin, other.periodeFin)
				&& recuperable == other.recuperable && Objects.equals(type, other.type);
	}
}
