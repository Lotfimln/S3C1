package rapport;

public class InfosRecettes{
    private String nom;
    private String description;
    private int montant;

    // Constructeur prenant en parametre le nom, la description et le montant des recettes
    public InfosRecettes(String nom, String description, int m) {
        this.nom = nom;
        this.description = description;
        this.montant = m;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public int getMontant() {
		return montant;
	}

	public void setMontant(int m) {
		this.montant = m;
	}
    
    
}