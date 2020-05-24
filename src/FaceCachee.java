
public class FaceCachee {

	private String nom,momentJeu, description;

	
	
	
	public FaceCachee(String nom, String momentJeu, String descri) {
		this.nom = nom;
		this.momentJeu = momentJeu;
		this.description = descri;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "FaceCachee [nom=" + nom + ", momentJeu=" + momentJeu + ", description=" + description + "]";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMomentJeu() {
		return momentJeu;
	}

	public void setMomentJeu(String momentJeu) {
		this.momentJeu = momentJeu;
	}
	
	
	
}
