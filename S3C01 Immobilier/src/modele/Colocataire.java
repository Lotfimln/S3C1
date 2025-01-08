package modele;

import java.util.Objects;

public class Colocataire {
    private int idLocataire;
    private int idLocataire1;

    public Colocataire(int idLocataire, int idLocataire1) {
        this.idLocataire = idLocataire;
        this.idLocataire1 = idLocataire1;
    }

    // Getters et Setters
    public int getIdLocataire() {
        return idLocataire;
    }

    public void setIdLocataire(int idLocataire) {
        this.idLocataire = idLocataire;
    }

    public int getIdLocataire1() {
        return idLocataire1;
    }

    public void setIdLocataire1(int idLocataire1) {
        this.idLocataire1 = idLocataire1;
    }

	@Override
	public String toString() {
		return "Colocataire [idLocataire=" + idLocataire + ", idLocataire1=" + idLocataire1 + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLocataire, idLocataire1);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Colocataire)) {
			return false;
		}
		Colocataire other = (Colocataire) obj;
		return idLocataire == other.idLocataire && idLocataire1 == other.idLocataire1;
	}
}
