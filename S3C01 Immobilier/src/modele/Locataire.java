package modele;

import java.util.List;
import java.util.Objects;

public class Locataire {
    private String idLocataire;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private java.util.Date dateDepart;

    private List<ContratDeLocation> contratsDeLocation; // Relation avec Contrat_de_location
    private List<Quittances> quittances; // Relation avec Quittances
    private List<Locataire> colocataires; // Relation avec d'autres locataires (N:N)

    public Locataire(String idLocataire, String nom, String prenom, String mail, String telephone, java.util.Date dateDepart,
                     List<ContratDeLocation> contratsDeLocation, List<Quittances> quittances, List<Locataire> colocataires) {
        this.idLocataire = idLocataire;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.dateDepart = dateDepart;
        this.contratsDeLocation = contratsDeLocation;
        this.quittances = quittances;
        this.colocataires = colocataires;
    }

	public String getIdLocataire() {
		return idLocataire;
	}

	public void setIdLocataire(String idLocataire) {
		this.idLocataire = idLocataire;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public java.util.Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(java.util.Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public List<ContratDeLocation> getContratsDeLocation() {
		return contratsDeLocation;
	}

	public void setContratsDeLocation(List<ContratDeLocation> contratsDeLocation) {
		this.contratsDeLocation = contratsDeLocation;
	}

	public List<Quittances> getQuittances() {
		return quittances;
	}

	public void setQuittances(List<Quittances> quittances) {
		this.quittances = quittances;
	}

	public List<Locataire> getColocataires() {
		return colocataires;
	}

	public void setColocataires(List<Locataire> colocataires) {
		this.colocataires = colocataires;
	}

	@Override
	public String toString() {
		return "Locataire [idLocataire=" + idLocataire + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail
				+ ", telephone=" + telephone + ", dateDepart=" + dateDepart + ", contratsDeLocation="
				+ contratsDeLocation + ", quittances=" + quittances + ", colocataires=" + colocataires + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(colocataires, contratsDeLocation, dateDepart, idLocataire, mail, nom, prenom, quittances,
				telephone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Locataire)) {
			return false;
		}
		Locataire other = (Locataire) obj;
		return Objects.equals(colocataires, other.colocataires)
				&& Objects.equals(contratsDeLocation, other.contratsDeLocation)
				&& Objects.equals(dateDepart, other.dateDepart) && idLocataire == other.idLocataire
				&& Objects.equals(mail, other.mail) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom) && Objects.equals(quittances, other.quittances)
				&& Objects.equals(telephone, other.telephone);
	}
}
