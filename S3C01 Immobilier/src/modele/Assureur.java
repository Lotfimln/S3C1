package modele;

import java.util.Objects;

public class Assureur {
    private int idAssurance;
    private String nom;
    private java.util.Date dateAssurance;
    private String prime;

    public Assureur(int idAssurance, String nom, java.util.Date dateAssurance, String prime) {
        this.idAssurance = idAssurance;
        this.nom = nom;
        this.dateAssurance = dateAssurance;
        this.prime = prime;
    }

	public int getIdAssurance() {
		return idAssurance;
	}

	public void setIdAssurance(int idAssurance) {
		this.idAssurance = idAssurance;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public java.util.Date getDateAssurance() {
		return dateAssurance;
	}

	public void setDateAssurance(java.util.Date dateAssurance) {
		this.dateAssurance = dateAssurance;
	}

	public String getPrime() {
		return prime;
	}

	public void setPrime(String prime) {
		this.prime = prime;
	}

	@Override
	public String toString() {
		return "Assureur [idAssurance=" + idAssurance + ", nom=" + nom + ", dateAssurance=" + dateAssurance + ", prime="
				+ prime + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateAssurance, idAssurance, nom, prime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Assureur)) {
			return false;
		}
		Assureur other = (Assureur) obj;
		return Objects.equals(dateAssurance, other.dateAssurance) && idAssurance == other.idAssurance
				&& Objects.equals(nom, other.nom) && Objects.equals(prime, other.prime);
	}
}
