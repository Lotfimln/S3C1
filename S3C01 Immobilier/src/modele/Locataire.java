package modele;

import java.util.Objects;

public class Locataire {
    private String idLocataire;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private java.util.Date dateNaissance;
    private java.util.Date dateDepart;

    public Locataire(String idLocataire, String nom, String prenom, String mail, String telephone, java.util.Date dateNaissance, java.util.Date dateDepart) {
        this.idLocataire = idLocataire;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.dateDepart = dateDepart;
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

	public java.util.Date getDateNaissance() {
		return dateNaissance;
	}
	
	public void setDateNaissance(java.util.Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public java.util.Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(java.util.Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	@Override
	public String toString() {
		return this.nom + this.prenom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateDepart, dateNaissance, idLocataire, mail, nom, prenom, telephone);
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
		return Objects.equals(dateDepart, other.dateDepart) && Objects.equals(dateNaissance, other.dateNaissance)
				&& Objects.equals(idLocataire, other.idLocataire) && Objects.equals(mail, other.mail)
				&& Objects.equals(nom, other.nom) && Objects.equals(prenom, other.prenom)
				&& Objects.equals(telephone, other.telephone);
	}



}
