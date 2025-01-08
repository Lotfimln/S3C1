package modele;

import java.util.Objects;

public class Contenir {
    private int idLouable;
    private int idImmeuble;

    public Contenir(int idLouable, int idImmeuble) {
        this.idLouable = idLouable;
        this.idImmeuble = idImmeuble;
    }

    // Getters et Setters
    public int getIdLouable() {
        return idLouable;
    }

    public void setIdLouable(int idLouable) {
        this.idLouable = idLouable;
    }

    public int getIdImmeuble() {
        return idImmeuble;
    }

    public void setIdImmeuble(int idImmeuble) {
        this.idImmeuble = idImmeuble;
    }

	@Override
	public String toString() {
		return "Contenir [idLouable=" + idLouable + ", idImmeuble=" + idImmeuble + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idImmeuble, idLouable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Contenir)) {
			return false;
		}
		Contenir other = (Contenir) obj;
		return idImmeuble == other.idImmeuble && idLouable == other.idLouable;
	}
}
