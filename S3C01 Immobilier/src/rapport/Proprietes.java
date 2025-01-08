package rapport;

public class Proprietes {
    private String nom;
    private String type;
    private String periodeConstruction;
    private String adresse;
    private int nombreLocaux;
    private int sommeLoyers;

    // Constructeur prenant en parametre le nom, le type, la période de construction
    // l'adresse, le nombre de locaux, la somme des loyers d'une propriété
    public Proprietes(String nom, String type, String periodeConstruction, String adresse, int nombreLocaux, int sommeLoyers) {
        this.nom = nom;
        this.type = type;
        this.periodeConstruction = periodeConstruction;
        this.adresse = adresse;
        this.nombreLocaux = nombreLocaux;
        this.sommeLoyers = sommeLoyers;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String n) {
		this.nom = n;
	}

	public String getType() {
		return type;
	}

	public void setType(String t) {
		this.type = t;
	}

	public String getPeriodeConstruction() {
		return periodeConstruction;
	}

	public void setPeriodeConstruction(String periodeConstruction) {
		this.periodeConstruction = periodeConstruction;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getNombreLocaux() {
		return nombreLocaux;
	}

	public void setNombreLocaux(int nombreLocaux) {
		this.nombreLocaux = nombreLocaux;
	}

	public int getSommeLoyers() {
		return sommeLoyers;
	}

	public void setSommeLoyers(int sommeLoyers) {
		this.sommeLoyers = sommeLoyers;
	}
    
    
}