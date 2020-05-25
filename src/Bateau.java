import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Bateau extends Pion {

	
	private ArrayList<Pion> pionInterne = new ArrayList<Pion>();
	private int placeDispo = 3;
	
	public Bateau(String couleur, int valeur, int posX, int posY, ImageIcon img) {
		super(couleur, valeur, posX, posY, img);
	}
	
	public void addPion(Pion p){
		if(placeDispo > 0){
			pionInterne.add(p);
		}
	}
	
	public boolean placeDispo(){
		if(placeDispo > 0){
			return true;
		}
		return false;
	}
}
