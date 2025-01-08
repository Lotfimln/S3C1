package rapport;

public class FraisCharges {
	
    private String nom;
    private String description;
    private int montant;

    // Constructeur prenant en parametre le nom, la description et le montant des frais & charges
    public FraisCharges(String nom, String description, int montant) {
        this.nom = nom;
        this.description = description;
        this.montant = montant;
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

	public void setMontant(int montant) {
		this.montant = montant;
	}
    
    
}