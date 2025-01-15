package modele;

import java.util.Objects;

public class Associer {
    private int idLouable;
    private int idIndexCompteur;

    public Associer(int idLouable, int idIndexCompteur) {
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

	@Override
	public String toString() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idIndexCompteur, idLouable);
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
		return idIndexCompteur == other.idIndexCompteur && idLouable == other.idLouable;
	}
}
