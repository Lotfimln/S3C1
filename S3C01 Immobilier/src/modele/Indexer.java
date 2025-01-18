package modele;

import java.sql.Date;
import java.util.Objects;

public class Indexer {
    private int idIndexCompteur;
    private int idImmeuble;
    private java.util.Date dateReleve;
    private double prixAbonnement;
    private java.util.Date dateRegularisation;

    public Indexer(int idIndexCompteur, int idImmeuble, Date dateReleve, double prixAbonnement, Date dateRegularisation) {
        this.idIndexCompteur = idIndexCompteur;
        this.idImmeuble = idImmeuble;
    }

    // Getters et Setters
    public int getIdIndexCompteur() {
        return idIndexCompteur;
    }

    public void setIdIndexCompteur(int idIndexCompteur) {
        this.idIndexCompteur = idIndexCompteur;
    }

    public int getIdImmeuble() {
        return idImmeuble;
    }

    public void setIdImmeuble(int idImmeuble) {
        this.idImmeuble = idImmeuble;
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
		return Objects.hash(idImmeuble, idIndexCompteur, dateReleve, prixAbonnement, dateRegularisation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Indexer)) {
			return false;
		}
		Indexer other = (Indexer) obj;
		return idImmeuble == other.idImmeuble && idIndexCompteur == other.idIndexCompteur
				&& Objects.equals(dateReleve, other.dateReleve) && Objects.equals(dateReleve, other.dateReleve)
				&& Double.doubleToLongBits(prixAbonnement) == Double.doubleToLongBits(other.prixAbonnement)
				&& Objects.equals(dateRegularisation, other.dateRegularisation) && Objects.equals(dateRegularisation, other.dateRegularisation);
	}
}
