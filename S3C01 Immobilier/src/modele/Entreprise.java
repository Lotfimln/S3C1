package modele;

import java.util.List;
import java.util.Objects;

public class Entreprise {
    private int idEntreprise;
    private String nom;
    private String siren;
    private String adresse;

    private List<Facture> factures; // Relation avec Facture

    public Entreprise(int idEntreprise, String nom, String siren, String adresse, List<Facture> factures) {
        this.idEntreprise = idEntreprise;
        this.nom = nom;
        this.siren = siren;
        this.adresse = adresse;
        this.factures = factures;
    }

	public int getIdEntreprise() {
		return idEntreprise;
	}

	public void setIdEntreprise(int idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public List<Facture> getFactures() {
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	@Override
	public String toString() {
		return "Entreprise [idEntreprise=" + idEntreprise + ", nom=" + nom + ", siren=" + siren + ", adresse=" + adresse
				+ ", factures=" + factures + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(adresse, factures, idEntreprise, nom, siren);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Entreprise)) {
			return false;
		}
		Entreprise other = (Entreprise) obj;
		return Objects.equals(adresse, other.adresse) && Objects.equals(factures, other.factures)
				&& idEntreprise == other.idEntreprise && Objects.equals(nom, other.nom)
				&& Objects.equals(siren, other.siren);
	}
}
