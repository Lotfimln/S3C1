package modele;

import java.util.Objects;

public class Colocataire {
    private String idLocataire;
    private String idLocataire1;

    public Colocataire(String idLocataire, String idLocataire1) {
        this.idLocataire = idLocataire;
        this.idLocataire1 = idLocataire1;
    }

    // Getters et Setters
    public String getIdLocataire() {
        return idLocataire;
    }

    public void setIdLocataire(String idLocataire) {
        this.idLocataire = idLocataire;
    }

    public String getIdLocataire1() {
        return idLocataire1;
    }

    public void setIdLocataire1(String idLocataire1) {
        this.idLocataire1 = idLocataire1;
    }

	@Override
	public String toString() {
		return "" + idLocataire + " " + idLocataire1;
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
