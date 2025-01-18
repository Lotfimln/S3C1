package modele;

import java.sql.Date;
import java.util.Objects;

public class Associer {
    private int idLouable;
    private int idIndexCompteur;
    private java.util.Date dateReleve;
    private double prixAbonnement;
    private java.util.Date dateRegularisation;
    
    public Associer(int idLouable, int idIndexCompteur, Date dateReleve, double prixAbonnement, Date dateRegularisation) {
        this.idLouable = idLouable;
        this.idIndexCompteur = idIndexCompteur;
    }

    // Getters et Setters
    public int getIdLouable() {
        return idLouable;
    }

    public void setIdLouable(int idLouable) {
        this.idLouable = idLouable;
    }

    public int getIdIndexCompteur() {
        return idIndexCompteur;
    }

    public void setIdIndexCompteur(int idIndexCompteur) {
        this.idIndexCompteur = idIndexCompteur;
    }
	
	public java.util.Date getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(java.util.Date dateReleve) {
        this.dateReleve = dateReleve;
    }
    
    public double getPrixAbonnement() {
        return prixAbonnement;
    }

    public void setPrixAbonnement(double prixAbonnement) {
        this.prixAbonnement = prixAbonnement;
    }

    public java.util.Date getDateRegularisation() {
        return dateRegularisation;
    }

    public void setDateRegularisation(java.util.Date dateRegularisation) {
        this.dateRegularisation = dateRegularisation;
    }
    
	@Override
	public String toString() {
		return null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idIndexCompteur, idLouable, dateReleve, prixAbonnement, dateRegularisation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Associer)) {
			return false;
		}
		Associer other = (Associer) obj;
		return idIndexCompteur == other.idIndexCompteur && idLouable == other.idLouable 
				&& Objects.equals(dateReleve, other.dateReleve) && Objects.equals(dateReleve, other.dateReleve)
				&& Double.doubleToLongBits(prixAbonnement) == Double.doubleToLongBits(other.prixAbonnement)
				&& Objects.equals(dateRegularisation, other.dateRegularisation) && Objects.equals(dateRegularisation, other.dateRegularisation);
	}
}
