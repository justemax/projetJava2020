import javax.swing.ImageIcon;

public class Pion {

	private String couleur;
	private int valeur, posX, posY;
	private boolean nageur = false;
	private ImageIcon img;
	
	
	
	
	


	public Pion(String couleur, int valeur, int posX, int posY, ImageIcon img) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.posX = posX;
		this.posY = posY;
		this.img = img;
	}

	public int getValeur() {
		return valeur;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
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

	public boolean isNageur() {
		return nageur;
	}

	public void setNageur(boolean nageur) {
		this.nageur = nageur;
	}

	@Override
	public String toString() {
		return "Pion [couleur=" + couleur + ", valeur=" + valeur + ", posX=" + posX + ", posY=" + posY + "]";
	}
	
	
	
}
