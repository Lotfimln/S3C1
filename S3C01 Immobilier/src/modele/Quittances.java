package modele;

import java.util.Objects;

public class Quittances {
    private int idQuittances;
    private java.util.Date datePaiement;
    private double montantLoyer;
    private double montantProvision;
    private Locataire locataire; // Relation avec Locataire
    private ContratDeLocation contratDeLocation; // Relation avec Contrat_de_location

    public Quittances(int idQuittances, java.util.Date datePaiement, double montantLoyer, double montantProvision,
                      Locataire locataire, ContratDeLocation contratDeLocation) {
        this.idQuittances = idQuittances;
        this.datePaiement = datePaiement;
        this.montantLoyer = montantLoyer;
        this.montantProvision = montantProvision;
        this.locataire = locataire;
        this.contratDeLocation = contratDeLocation;
    }

	public int getIdQuittances() {
		return idQuittances;
	}

	public void setIdQuittances(int idQuittances) {
		this.idQuittances = idQuittances;
	}

	public java.util.Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(java.util.Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public double getMontantLoyer() {
		return montantLoyer;
	}

	public void setMontantLoyer(double montantLoyer) {
		this.montantLoyer = montantLoyer;
	}

	public double getMontantProvision() {
		return montantProvision;
	}

	public void setMontantProvision(double montantProvision) {
		this.montantProvision = montantProvision;
	}

	public Locataire getLocataire() {
		return locataire;
	}

	public void setLocataire(Locataire locataire) {
		this.locataire = locataire;
	}

	public ContratDeLocation getContratDeLocation() {
		return contratDeLocation;
	}

	public void setContratDeLocation(ContratDeLocation contratDeLocation) {
		this.contratDeLocation = contratDeLocation;
	}

	@Override
	public String toString() {
		return "Quittances [idQuittances=" + idQuittances + ", datePaiement=" + datePaiement + ", montantLoyer="
				+ montantLoyer + ", montantProvision=" + montantProvision + ", locataire=" + locataire
				+ ", contratDeLocation=" + contratDeLocation + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contratDeLocation, datePaiement, idQuittances, locataire, montantLoyer, montantProvision);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Quittances)) {
			return false;
		}
		Quittances other = (Quittances) obj;
		return Objects.equals(contratDeLocation, other.contratDeLocation)
				&& Objects.equals(datePaiement, other.datePaiement) && idQuittances == other.idQuittances
				&& Objects.equals(locataire, other.locataire)
				&& Double.doubleToLongBits(montantLoyer) == Double.doubleToLongBits(other.montantLoyer)
				&& Double.doubleToLongBits(montantProvision) == Double.doubleToLongBits(other.montantProvision);
	}
}
