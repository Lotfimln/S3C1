package modele;

import java.util.Objects;

public class Indexer {
    private int idIndexCompteur;
    private int idImmeuble;

    public Indexer(int idIndexCompteur, int idImmeuble) {
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

	@Override
	public String toString() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idImmeuble, idIndexCompteur);
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
		return idImmeuble == other.idImmeuble && idIndexCompteur == other.idIndexCompteur;
	}
}
