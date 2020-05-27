import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class Bateau extends Pion {

	
	private HashMap<String,ArrayList> pionInterne = new HashMap<String,ArrayList>();
	private ArrayList<Pion> pionMemeR = new ArrayList<Pion>();
	private ArrayList<Pion> pionMemeV = new ArrayList<Pion>();
	private ArrayList<Pion> pionMemeB = new ArrayList<Pion>();
	private ArrayList<Pion> pionMemeJ = new ArrayList<Pion>();
	private int placeDispo = 3;
	
	public Bateau(String couleur, int valeur, int posX, int posY, ImageIcon img) {
		super(couleur, valeur, posX, posY, img);
		pionInterne.put("Rouge", pionMemeR);
		pionInterne.put("Jaune", pionMemeJ);
		pionInterne.put("Vert", pionMemeV);
		pionInterne.put("Bleu", pionMemeB);
		
	}
	
	@SuppressWarnings("unchecked")
	public void addPion(Pion p){
		if(placeDispo > 0){
			pionInterne.get(p.getCouleur()).add(p);
			placeDispo --;
		}
	}
	
	public boolean placeDispo(){
		if(placeDispo > 0){
			return true;
		}
		return false;
	}
	
	public int getNbPionCoul(String coul){
		return pionInterne.get(coul).size();
	}
	
	public int nbPion(){
		return 3-nbPion();
	}
	
	
	
	
	public Pion getPionCoul(int i, String coul){
		return (Pion) pionInterne.get(coul).get(i);
	}
}
