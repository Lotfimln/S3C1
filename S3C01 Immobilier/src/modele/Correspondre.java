package modele;

import java.util.Objects;

public class Correspondre {
    private String idLocataire;
    private int idContratDeLocation;

    public Correspondre(String idLocataire, int idContratDeLocation) {
        this.idLocataire = idLocataire;
        this.idContratDeLocation = idContratDeLocation;
    }

    // Getters et Setters
    public String getIdLocataire() {
        return idLocataire;
    }

    public void setIdLocataire(String idLocataire) {
        this.idLocataire = idLocataire;
    }

    public int getIdContratDeLocation() {
        return idContratDeLocation;
    }

    public void setIdContratDeLocation(int idContratDeLocation) {
        this.idContratDeLocation = idContratDeLocation;
    }

	@Override
	public String toString() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idContratDeLocation, idLocataire);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Correspondre)) {
			return false;
		}
		Correspondre other = (Correspondre) obj;
		return idContratDeLocation == other.idContratDeLocation && idLocataire == other.idLocataire;
	}
}
