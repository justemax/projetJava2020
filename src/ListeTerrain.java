
import java.util.ArrayList;

public class ListeTerrain {

	private ArrayList<String> terrains = new ArrayList();
	
	ListeTerrain(){
		for(int i = 0; i<16; i++){
			terrains.add("Plage");
		}
		
		for(int i = 0; i<16; i++){
			terrains.add("Foret");
		}
		
		for(int i = 0; i<8; i++){
			terrains.add("Montagne");
		}
	}
	
	public String getTerrain(int d){
		return terrains.get(d);
	}
	
	public void dropTerrain(int i){
		terrains.remove(i);
	}
	
	
}
