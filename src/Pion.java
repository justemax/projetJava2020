
public class Pion {

	private String couleur;
	private int valeur, posX, posY;
	

	
	
	
	
	
	public Pion(String couleur, int valeur, int posX, int posY) {
		super();
		this.couleur = couleur;
		this.valeur = valeur;
		this.posX = posX;
		this.posY = posY;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setJoueur(String joueur) {
		this.couleur = joueur;
	}

	
	
	
	
}
