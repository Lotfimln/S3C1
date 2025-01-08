package modele;

import java.util.List;
import java.util.Objects;

public class Immeuble {
    private int idImmeuble;
    private String adresse;

    private List<Taxe> taxes; // Relation avec Taxe
    private List<Louable> louables; // Relation avec Louable (Contient)
    private List<IndexCompteur> indexCompteurs; // Relation avec IndexCompteur (Indexer)

    public Immeuble(int idImmeuble, String adresse, List<Taxe> taxes, List<Louable> louables, List<IndexCompteur> indexCompteurs) {
        this.idImmeuble = idImmeuble;
        this.adresse = adresse;
        this.taxes = taxes;
        this.louables = louables;
        this.indexCompteurs = indexCompteurs;
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

	public List<Taxe> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Taxe> taxes) {
		this.taxes = taxes;
	}

	public List<Louable> getLouables() {
		return louables;
	}

	public void setLouables(List<Louable> louables) {
		this.louables = louables;
	}

	public List<IndexCompteur> getIndexCompteurs() {
		return indexCompteurs;
	}

	public void setIndexCompteurs(List<IndexCompteur> indexCompteurs) {
		this.indexCompteurs = indexCompteurs;
	}

	@Override
	public String toString() {
		return "Immeuble [idImmeuble=" + idImmeuble + ", adresse=" + adresse + ", taxes=" + taxes + ", louables="
				+ louables + ", indexCompteurs=" + indexCompteurs + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(adresse, idImmeuble, indexCompteurs, louables, taxes);
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
		return Objects.equals(adresse, other.adresse) && idImmeuble == other.idImmeuble
				&& Objects.equals(indexCompteurs, other.indexCompteurs) && Objects.equals(louables, other.louables)
				&& Objects.equals(taxes, other.taxes);
	}
}
