import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Classe principal du jeu
 * Il d'agit du pattern entier d'hexagonne mais également là  où se déroule la partie
 * @author Max
 *
 */
public class HexagonePattern extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    private static final int ROWS = 13;
    private static final int COLUMNS = 13;
    private HexagonButton[][] hexButton = new HexagonButton[ROWS][COLUMNS];

    private int nbClic = 0;
    private HexagonButton b1,b2;
    
    private ListeTerrain ter = new ListeTerrain();
    private int terRestant = 39;
    
    //Phase de placement pions
    private int placement = 5;
    private boolean fin = false;
    
    private ArrayList<String> listCoul = new ArrayList<String>();
    
    
    
    private String coulCour;
    
    public HexagonePattern() {
        setLayout(null); 
        remplissageArrayCoul();
        coulCour = listCoul.get(0);
        initGUI();
    }


    /***
     * Classe mettant en place le plateau de jeu, placement des boutons et des élements de base
     */
    public void initGUI() {
        int offsetX = -5;
        int offsetY = 5;

        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLUMNS; col++){
                hexButton[row][col] = new HexagonButton(row, col);
                hexButton[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        HexagonButton clickedButton = (HexagonButton) e.getSource();
                        System.out.println("Button clicked: [" + clickedButton.getRow() + "][" + clickedButton.getCol() + "]");
                        
                    }
                });
                
                
                // Là où le placement des élement se fait aléatoirement
                if(zoneMilieu(row, col)){
                	int alea = 0 + (int)(Math.random() * ((terRestant - 0) + 1));
                	String terCourant = ter.getTerrain(alea);
                	System.out.println(" " + alea);
                	System.out.print(terCourant);
                	if (terRestant > 0){
                		ter.dropTerrain(alea);
                	}
                	
                	System.out.println("Il reste: " + terRestant + "terrains");
                	
                	if (terCourant == "Foret"){
                		hexButton[row][col].setBackground(Color.green);
                		hexButton[row][col].setTerrain("Foret");
                		hexButton[row][col].setIcon(new ImageIcon("image/Foret.png"));
                	}else if(terCourant == "Plage"){
                		hexButton[row][col].setBackground(Color.yellow);
                		hexButton[row][col].setTerrain("Plage");
                		hexButton[row][col].setIcon(new ImageIcon("image/Plage.png"));
                	}else if(terCourant == "Montagne"){
                		hexButton[row][col].setBackground(Color.gray);
                		hexButton[row][col].setTerrain("Montagne");
                		hexButton[row][col].setIcon(new ImageIcon("image/Montagne.png"));
                	}
                	
                	terRestant --;
                	
                	
                }
                /* Placement des serpent */
                if((row == 1) && (col == 2) || (row == 0) && (col == 11) || (row == 12) && (col == 11) || (row == 12) && (col == 2) || (row == 6) && (col == 6)){
                	
					
					hexButton[row][col].setIcon(new ImageIcon("image/serpent.png"));
					hexButton[row][col].setOccupe(true);
					
                	System.out.println("Passe la");
                }
                
                add(hexButton[row][col]);
                hexButton[row][col].setBounds(offsetY, offsetX, 50, 50);
                offsetX += 43;
                if(row%2 == 1 && col == 0){
                	hexButton[row][col].setVisible(false);
                }
                hexButton[row][col].addActionListener(this);
            }
            if(row%2 == 0) {
                offsetX = -26;
            } else {
                offsetX = -5;
            }
            
            offsetY += 38;
            
        }
    }
    
    
    


	/**
	 * Classe interagissant avec le jouer lkà où il joue et le déroulement de la partie
	 * 
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		
		
		b1 = (HexagonButton) arg0.getSource();
		
		// V2 systeme de placement de pion
		if(!fin){
			if(placement > 0){
				System.out.println("Placement des pions" + coulCour);
				if( zoneMilieu(b1.getRow(), b1.getCol())){
					if(b1.getOccupe()){
						System.out.println("Deja un pion");
					}else{
						Pion p = new Pion(coulCour, 1, b1.getCol(), b1.getRow());
						b1.setPionPresent(p);
						b1.setIcon(new ImageIcon("image/pion" + coulCour + ".png"));
						b1.setOccupe(true);
						placement --;
						System.out.println("reste " + placement);
					}
					
				}
				else{
					System.out.println("Pas bonne zone");
				}
			}else{
				System.out.println("Tout est placé pour" + coulCour);
				if(listCoul.indexOf(coulCour) < listCoul.size() - 1){
					coulCour = listCoul.get(listCoul.indexOf(coulCour) + 1);
					placement = 5;
				}else{
					System.out.println("Tout est placé la partie commence");
					fin = true;
				}
					
				
			}
		}
		
		System.out.println("Partie en cour");
		
		
		// Deplacement pion v1 
		if(fin){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0 && b1.getOccupe() == true){
				b1 = (HexagonButton) arg0.getSource();
				System.out.println("Col 1= " + b1.getCol());
				System.out.println("Row 1 = " + b1.getRow());
				nbClic ++;
			}else if(nbClic == 1 ){
				System.out.println("Clic num: " + nbClic);
				b2 = (HexagonButton) arg0.getSource();
				System.out.println("Col 2 = " + b2.getCol());
				System.out.println("row 2 = " + b2.getRow());
				
				//int rapRow = (b2.getRow() - b1.getRow()),rapCol = (b2.getCol() - b1.getCol());
				
				System.out.println("rapport row = " + (b2.getRow() - b1.getRow()));
				System.out.println("rapport col = " + (b2.getCol() - b1.getCol()));
				
				if((((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 )) && b2.getOccupe() == false){
					// Probléme pas d'accés au pion present dans le bouton 
					System.out.print(b1.getPionPresent().toString());
					b1.setIcon(new ImageIcon("image/" + b1.getTerrain() + ".png"));
					//Pion p = b1.getPionPresent();
					//b2.setPionPresent(p);
					//System.out.println("Pion set sur b2");
					//System.out.println(b2.getPionPresent().toString());
					//b2.setOccupe(true);
					//b1.setPionPresent(null);
					//b2.setIcon(new ImageIcon("image/pion" + b2.getPionPresent().getCouleur() + ".png"));
				}else{
					System.out.println("pas ok");
				}
				
				b1.setBackground(Color.blue);
				nbClic = 0;
			}
		}
		
		
		
	}
	
	
	private void remplissageArrayCoul(){
		listCoul.add("Bleu");
		listCoul.add("Jaune");
		listCoul.add("Vert");
		listCoul.add("Rouge");
		
	}
	
	public boolean zoneMilieu(int row, int col){
		if((col >= 3 && col <= 10) && (row >= 3 && row <= 9) && !(row == 3 && col == 3) && !(row == 9 && col == 3) && !(row == 4 && col == 3) && !(row == 8 && col == 3) && !(row == 3 && col == 4) && !(row == 9 && col == 4) && !(row == 4 && col == 9) && !(row == 6 && col == 10) && !(row == 4 && col == 10) && !(row == 3 && col == 9) && !(row == 3 && col == 10) && !(row == 8 && col == 10) && !(row == 8 && col == 9) && !(row == 9 && col == 10) && !(row == 9 && col == 9)){
			return true;
		}else{
			return false;
		}
	}
}