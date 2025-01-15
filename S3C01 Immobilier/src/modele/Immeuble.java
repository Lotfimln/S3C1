package modele;

import java.util.Objects;

public class Immeuble {
    private int idImmeuble;
    private String adresse;

    public Immeuble(int idImmeuble, String adresse) {
        this.idImmeuble = idImmeuble;
        this.adresse = adresse;
    }

	public int getIdImmeuble() {
		return idImmeuble;
	}

	public void setIdImmeuble(int idImmeuble) {
		this.idImmeuble = idImmeuble;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		return "" + idImmeuble;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adresse, idImmeuble);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Immeuble)) {
			return false;
		}
		Immeuble other = (Immeuble) obj;
		return Objects.equals(adresse, other.adresse) && idImmeuble == other.idImmeuble;
	}

}
