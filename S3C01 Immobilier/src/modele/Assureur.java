package modele;

import java.util.Objects;

public class Assureur {
    private int idAssureur;
    private String nom;
    private java.util.Date dateAssurance;
    private int prime;
    private String typeAssurance;

    public Assureur(int idAssureur, String nom, java.util.Date dateAssurance, int prime, String typeAssurance) {
        this.idAssureur = idAssureur;
        this.nom = nom;
        this.dateAssurance = dateAssurance;
        this.prime = prime;
        this.typeAssurance = typeAssurance;
    }

	public int getIdAssureur() {
		return idAssureur;
	}

	public void setIdAssureur(int idAssureur) {
		this.idAssureur = idAssureur;
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

	public int getPrime() {
		return prime;
	}

	public void setPrime(int prime) {
		this.prime = prime;
	}
	
	public String getTypeAssurance() {
		return nom;
	}

	public void setTypeAssurance(String typeAssurance) {
		this.typeAssurance = typeAssurance;
	}

	@Override
	public String toString() {
		return "" + idAssureur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateAssurance, idAssureur, nom, prime, typeAssurance);
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
		return Objects.equals(dateAssurance, other.dateAssurance) && idAssureur == other.idAssureur
				&& Objects.equals(nom, other.nom) && Objects.equals(prime, other.typeAssurance) && Objects.equals(typeAssurance, other.typeAssurance);
	}
}
