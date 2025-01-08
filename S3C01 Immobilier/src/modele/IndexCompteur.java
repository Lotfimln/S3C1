package modele;

import java.util.List;
import java.util.Objects;

public class IndexCompteur {
    private int idIndexCompteur;
    private String typeCompteur;
    private double valeurCourante;
    private double ancienneValeur;
    private java.util.Date dateReleve;

    private List<Charge> charges; // Relation avec Charge (apparaitre)
    private List<Immeuble> immeubles; // Relation avec Immeuble (Indexer)

    public IndexCompteur(int idIndexCompteur, String typeCompteur, double valeurCourante, double ancienneValeur,
                         java.util.Date dateReleve, List<Charge> charges, List<Immeuble> immeubles) {
        this.idIndexCompteur = idIndexCompteur;
        this.typeCompteur = typeCompteur;
        this.valeurCourante = valeurCourante;
        this.ancienneValeur = ancienneValeur;
        this.dateReleve = dateReleve;
        this.charges = charges;
        this.immeubles = immeubles;
    }

	public int getIdIndexCompteur() {
		return idIndexCompteur;
	}

	public void setIdIndexCompteur(int idIndexCompteur) {
		this.idIndexCompteur = idIndexCompteur;
	}

	public String getTypeCompteur() {
		return typeCompteur;
	}

	public void setTypeCompteur(String typeCompteur) {
		this.typeCompteur = typeCompteur;
	}

	public double getValeurCourante() {
		return valeurCourante;
	}

	public void setValeurCourante(double valeurCourante) {
		this.valeurCourante = valeurCourante;
	}

	public double getAncienneValeur() {
		return ancienneValeur;
	}

	public void setAncienneValeur(double ancienneValeur) {
		this.ancienneValeur = ancienneValeur;
	}

	public java.util.Date getDateReleve() {
		return dateReleve;
	}

	public void setDateReleve(java.util.Date dateReleve) {
		this.dateReleve = dateReleve;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public List<Immeuble> getImmeubles() {
		return immeubles;
	}

	public void setImmeubles(List<Immeuble> immeubles) {
		this.immeubles = immeubles;
	}

	@Override
	public String toString() {
		return "IndexCompteur [idIndexCompteur=" + idIndexCompteur + ", typeCompteur=" + typeCompteur
				+ ", valeurCourante=" + valeurCourante + ", ancienneValeur=" + ancienneValeur + ", dateReleve="
				+ dateReleve + ", charges=" + charges + ", immeubles=" + immeubles + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ancienneValeur, charges, dateReleve, idIndexCompteur, immeubles, typeCompteur,
				valeurCourante);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof IndexCompteur)) {
			return false;
		}
		IndexCompteur other = (IndexCompteur) obj;
		return Double.doubleToLongBits(ancienneValeur) == Double.doubleToLongBits(other.ancienneValeur)
				&& Objects.equals(charges, other.charges) && Objects.equals(dateReleve, other.dateReleve)
				&& idIndexCompteur == other.idIndexCompteur && Objects.equals(immeubles, other.immeubles)
				&& Objects.equals(typeCompteur, other.typeCompteur)
				&& Double.doubleToLongBits(valeurCourante) == Double.doubleToLongBits(other.valeurCourante);
	}
}
