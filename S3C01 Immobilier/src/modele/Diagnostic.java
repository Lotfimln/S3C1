package modele;

import java.util.Objects;

public class Diagnostic {
    private int idDiagnostic;
    private String type;
    private java.util.Date dateDiagnostic;
    private Louable louable; // Relation avec Louable

    public Diagnostic(int idDiagnostic, String type, java.util.Date dateDiagnostic, Louable louable) {
        this.idDiagnostic = idDiagnostic;
        this.type = type;
        this.dateDiagnostic = dateDiagnostic;
        this.louable = louable;
    }

	public int getIdDiagnostic() {
		return idDiagnostic;
	}

	public void setIdDiagnostic(int idDiagnostic) {
		this.idDiagnostic = idDiagnostic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "Diagnostic [idDiagnostic=" + idDiagnostic + ", type=" + type + ", dateDiagnostic=" + dateDiagnostic
				+ ", louable=" + louable + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateDiagnostic, idDiagnostic, louable, type);
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
				&& Objects.equals(louable, other.louable) && Objects.equals(type, other.type);
	}
}
