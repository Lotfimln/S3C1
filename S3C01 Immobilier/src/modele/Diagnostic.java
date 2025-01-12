package modele;

import java.util.Objects;

public class Diagnostic {
    private int idDiagnostic;
    private String typeDiagnostic;
    private java.util.Date dateDiagnostic;
    private Louable louable; // Relation avec Louable

    public Diagnostic(int idDiagnostic, String typeDiagnostic, java.util.Date dateDiagnostic, Louable louable) {
        this.idDiagnostic = idDiagnostic;
        this.typeDiagnostic = typeDiagnostic;
        this.dateDiagnostic = dateDiagnostic;
        this.louable = louable;
    }

	public int getIdDiagnostic() {
		return idDiagnostic;
	}

	public void setIdDiagnostic(int idDiagnostic) {
		this.idDiagnostic = idDiagnostic;
	}

	public String getTypeDiagnostic() {
		return typeDiagnostic;
	}

	public void setTypeDiagnostic(String typeDiagnostic) {
		this.typeDiagnostic = typeDiagnostic;
	}

	public java.util.Date getDateDiagnostic() {
		return dateDiagnostic;
	}

	public void setDateDiagnostic(java.util.Date dateDiagnostic) {
		this.dateDiagnostic = dateDiagnostic;
	}

	public Louable getLouable() {
		return louable;
	}

	public void setLouable(Louable louable) {
		this.louable = louable;
	}

	@Override
	public String toString() {
		return "Diagnostic [idDiagnostic=" + idDiagnostic + ", type=" + typeDiagnostic + ", dateDiagnostic=" + dateDiagnostic
				+ ", louable=" + louable + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateDiagnostic, idDiagnostic, louable, typeDiagnostic);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Diagnostic)) {
			return false;
		}
		Diagnostic other = (Diagnostic) obj;
		return Objects.equals(dateDiagnostic, other.dateDiagnostic) && idDiagnostic == other.idDiagnostic
				&& Objects.equals(louable, other.louable) && Objects.equals(typeDiagnostic, other.typeDiagnostic);
	}
}
