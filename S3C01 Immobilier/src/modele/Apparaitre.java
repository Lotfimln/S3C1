package modele;

import java.util.Objects;

public class Apparaitre {
    private int idCharge;
    private int idIndexCompteur;

    public Apparaitre(int idCharge, int idIndexCompteur) {
        this.idCharge = idCharge;
        this.idIndexCompteur = idIndexCompteur;
    }

    // Getters et Setters
    public int getIdCharge() {
        return idCharge;
    }

    public void setIdCharge(int idCharge) {
        this.idCharge = idCharge;
    }

    public int getIdIndexCompteur() {
        return idIndexCompteur;
    }

    public void setIdIndexCompteur(int idIndexCompteur) {
        this.idIndexCompteur = idIndexCompteur;
    }

	@Override
	public String toString() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCharge, idIndexCompteur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Apparaitre)) {
			return false;
		}
		Apparaitre other = (Apparaitre) obj;
		return idCharge == other.idCharge && idIndexCompteur == other.idIndexCompteur;
	}
}
