package modele;

import java.util.Objects;

public class IndexCompteur {
    private int idIndexCompteur;
    private String typeCompteur;
    private double valeurCourante;
    private double ancienneValeur;

    public IndexCompteur(int idIndexCompteur, String typeCompteur, double valeurCourante, double ancienneValeur) {
        this.idIndexCompteur = idIndexCompteur;
        this.typeCompteur = typeCompteur;
        this.valeurCourante = valeurCourante;
        this.ancienneValeur = ancienneValeur;
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


	@Override
	public String toString() {
		return "" + idIndexCompteur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ancienneValeur, idIndexCompteur, typeCompteur, valeurCourante);
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
				&& idIndexCompteur == other.idIndexCompteur
				&& Objects.equals(typeCompteur, other.typeCompteur)
				&& Double.doubleToLongBits(valeurCourante) == Double.doubleToLongBits(other.valeurCourante);
	}

}
