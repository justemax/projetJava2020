import java.util.ArrayList;

public class ListeDePions {

	private ArrayList<Pion> listePion = new ArrayList<Pion>();
	private String couleur;
	
	
	public ListeDePions(String couleur) {
		this.couleur = couleur;
	}
	
	public void addPion(Pion p){
		listePion.add(p);
	}
	
	
	
}
