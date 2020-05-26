import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    //Initialisation du terrain
    private ListeTerrain ter = new ListeTerrain();
    private int terRestant = 39;
    private ArrayList<FaceCachee> listeFCPlage = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> listeFCForet = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> listeFCMontagne = new ArrayList<FaceCachee>();
    
    //Phase de placement pions
    private int placement = 10;
    private boolean placementPion = false;
    private int nbPlage = 0,nbForet = 0, nbMontagne = 0;
    
    //Phase de jeux p1 deplacement
    private int nbDepla = 0; 
    private boolean depla = false;
    
    //phase de retrait d'une tuile terrain
    private boolean retrait = false;
    
    //Tuile de chaque joueurs
    private HashMap<String,ArrayList<FaceCachee>> tuileFCJoueur = new HashMap<String,ArrayList<FaceCachee>>();
    private ArrayList<FaceCachee> listeFCRouge = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> listeFCBleu = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> listeFCVert = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> listeFCJaune = new ArrayList<FaceCachee>();
    
    //Tuile de défence de chaque joueur
    private HashMap<String,ArrayList<FaceCachee>> defence = new HashMap<String,ArrayList<FaceCachee>>();
    private ArrayList<FaceCachee> defRouge = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> defBleu = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> defVert = new ArrayList<FaceCachee>();
    private ArrayList<FaceCachee> defJaune = new ArrayList<FaceCachee>();
    
    //Face Caché jouet immediatement
    private boolean immediat = false;
    private FaceCachee fCCoura = null;
    
    
    //Deplacement dé
    private boolean deSerpent = false;
    private boolean deRequin = false;
    private boolean deBaleine = false;
    
    private int deDepla = 0;
    
    //Gestion des monstre et bateaux
    private int nbBateaux = 0;
    private int nbRequin = 0;
    private int nbBaleine = 0;
    private HashMap<String, Integer> nbNageur = new HashMap<String,Integer>();
    
    //FaceCachee a jouer quand on veux
    private HashMap<String, Boolean> quandOnveux = new HashMap<String,Boolean>();
    
    //Gestion des deplacement en fonction des FC
    private boolean deplaMonstre = false;
    private boolean deplaBat = false;
    private boolean deplaNag = false;
    
    private int nbDeplaFC = 0;
    private String typeFC = "";
    
    //Fin de partie
    private boolean FinDePartie = false;
    private HashMap<String,Integer> score = new HashMap<String,Integer>();
    
    private ArrayList<String> listCoul = new ArrayList<String>();
    
    
    
    private String coulCour;
    
    public HexagonePattern() {
        setLayout(null); 
        remplissageArrayCoul();
        coulCour = listCoul.get(0);
        initHashMap();
        initFaceCache();
        initGUI();
        JOptionPane.showMessageDialog(this,"Phase de placement de pion, les pions ont respectivement la valeur 3*1, 2*2, 2*3, 4, 5 et 6. Retenez les l'accés au placment vous ne pourrez plus y accéder");
        JOptionPane.showMessageDialog(this,coulCour + " Commence a placer");
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
                	int alea = random(0,terRestant);
                	String terCourant = ter.getTerrain(alea);
                	System.out.println(" " + alea);
                	System.out.print(terCourant);
                	if (terRestant > 0){
                		ter.dropTerrain(alea);
                	}
                	
                	System.out.println("Il reste: " + terRestant + "terrains");
                	
                	if (terCourant == "Foret"){
                		nbForet ++;
                		hexButton[row][col].setFaceCachee(listeFCForet.get(random(0, listeFCForet.size()-1)));
                		hexButton[row][col].setBackground(Color.green);
                		hexButton[row][col].setTerrain("Foret");
                		hexButton[row][col].setIcon(new ImageIcon("image/Foret.png"));
                	}else if(terCourant == "Plage"){
                		nbPlage ++;
                		hexButton[row][col].setFaceCachee(listeFCPlage.get(random(0, listeFCPlage.size()-1)));
                		hexButton[row][col].setBackground(Color.yellow);
                		hexButton[row][col].setTerrain("Plage");
                		hexButton[row][col].setIcon(new ImageIcon("image/Plage.png"));
                	}else if(terCourant == "Montagne"){
                		nbMontagne ++;
                		hexButton[row][col].setFaceCachee(listeFCMontagne.get(random(0, listeFCMontagne.size()-1)));
                		hexButton[row][col].setBackground(Color.gray);
                		hexButton[row][col].setTerrain("Montagne");
                		hexButton[row][col].setIcon(new ImageIcon("image/Montagne.png"));
                	}
                	
                	terRestant --;
                	
                	
                }
                
                /* Placement des safeZone */
                
                if((row == 0) && (col == 2) || (row == 11) && (col == 2) || (row == 1) && (col == 12) || (row == 11) && (col == 12)){
                	hexButton[row][col].setTerrain("SafeZone");
                	hexButton[row][col].setIcon(null);
                	hexButton[row][col].setBackground(Color.orange);
                }
                
                
                /* Placement des serpent */
                if((row == 1) && (col == 2) || (row == 0) && (col == 11) || (row == 12) && (col == 11) || (row == 12) && (col == 2) || (row == 6) && (col == 6)){

					hexButton[row][col].setOccupe(true);
					Pion s = new Pion("Noir", 1, col, row,new ImageIcon("image/serpent.png"));
					hexButton[row][col].setIcon(s.getImg());
					hexButton[row][col].setPionPresent(s);
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
	 * @author Max HOFFER
	 * Classe interagissant avec le jouer lkà où il joue et le déroulement de la partie
	 * 
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		if(FinDePartie){
			
		}
		
		
		
		// V2 systeme de placement de pion
		if(!placementPion){
			b1 = (HexagonButton) arg0.getSource();
			if(placement > 0){
				System.out.println("Placement des pions" + coulCour);
				if( zoneMilieu(b1.getRow(), b1.getCol())){
					if(b1.getOccupe()){
						System.out.println("Deja un pion");
					}else{
						int valPion = 0;
						if(placement == 10 || placement == 9 || placement == 8){
							valPion = 1;
						}else if(placement == 7 || placement == 6){
							valPion = 2;
						}else if(placement == 5 || placement == 4){
							valPion = 3;
						}else if(placement == 3){
							valPion = 4;
						}else if(placement == 2){
							valPion = 5;
						}else if(placement == 1){
							valPion = 6;
						}
						
						Pion p = new Pion(coulCour, valPion, b1.getCol(), b1.getRow(),new ImageIcon("image/pion" + coulCour + ".png"));
						b1.setPionPresent(p);
						b1.setIcon(p.getImg());
						b1.setOccupe(true);
						placement --;
						System.out.println(b1.getPionPresent().toString());
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
					JOptionPane.showMessageDialog(this,"Tous les pion de " + listCoul.get(listCoul.indexOf(coulCour) - 1) + " sont placé au tour de " + coulCour + " de placer");
					placement = 10;
				}else{
					System.out.println("Tout est placé la partie commence");
					changementJoueur();
					placementPion = true;
					depla = true;
				}
					
				
			}
		}
		
		System.out.println("Partie en cour");
		
		/**
		 * @author Max HOFFER
		 * Gestion de la face caché à action immediate
		 */
		
		if(immediat){
			b1 = (HexagonButton) arg0.getSource();
			if(fCCoura.getNom().equals("Requin")){
				if(b1.getTerrain().equals("Mer")){
					Pion p = new Pion("Requin",0,b1.getCol(),b1.getRow(),new ImageIcon("image/Requin.png"));
					b1.setOccupe(true);
					b1.setPionPresent(p);
					b1.setIcon(p.getImg());
					immediat = false;
					fCCoura = null;
					nbRequin ++;
					lanceDe();
					return;
				}
				else{
					JOptionPane.showMessageDialog(this,"Un requin se joue dans la mer");
					return;
				}
			}
			if(fCCoura.getNom().equals("Baleine")){
				if(b1.getTerrain().equals("Mer") && !b1.getOccupe()){
					Pion p = new Pion("Baleine",0,b1.getCol(),b1.getRow(),new ImageIcon("image/Baleine.png"));
					b1.setOccupe(true);
					b1.setPionPresent(p);
					b1.setIcon(p.getImg());
					immediat = false;
					fCCoura = null;
					nbBaleine ++;
					lanceDe();
					return;
				}
				else{
					JOptionPane.showMessageDialog(this,"CUne baleine se joue dans la mer et sur une case vide");
				}
			}
			if(fCCoura.getNom().equals("Volcant")){
				FinDePartie = true;
				immediat = false;
				fCCoura = null;
				return;
			}
			
			if(fCCoura.getNom().equals("Tourbillon")){
				if(b1.getTerrain().equals("Mer") && b1.getOccupe()){
					if(b1.getPionPresent().equals("Requin")){
						nbRequin --;
					}else if(b1.getPionPresent().equals("Baleine")){
						nbBaleine --;
					}else if(b1.getPionPresent().equals("Bateau")){
						nbBateaux --;
					}
					b1.setOccupe(false);
					b1.setPionPresent(null);
					b1.setIcon(new ImageIcon("image/" + b1.getTerrain() + ".png"));
					immediat = false;
					fCCoura = null;
					lanceDe();
					return;
				}
				else{
					JOptionPane.showMessageDialog(this,"Un topurbillon se joue sur une case mer avec un pion présent");
					return;
				}
			}
			
			if(fCCoura.getNom().equals("Bateau")){
				if(b1.getTerrain().equals("Mer") && !b1.getOccupe()){
					Bateau b = new Bateau("Boat",0,b1.getCol(),b1.getRow(),new ImageIcon("image/bateau.png"));
					b1.setOccupe(true);
					b1.setBoat(true);
					b1.setBateauPresent(b);
					b1.setIcon(b.getImg());
					nbBateaux ++;
					immediat = false;
					fCCoura = null;
					lanceDe();
					return;
				}else{
					JOptionPane.showMessageDialog(this,"Un bateau se joue dans la mer et sur une case vide");
				}
			}
			
			
		}
		
		/**
		 * @author Max HOFFER
		 * 
		 * Jouer une tuile dans son deck
		 */
		
		if(!tuileFCJoueur.get(coulCour).isEmpty() && quandOnveux.get(coulCour)){
			Object[] choix = new String[100];
			choix[0] = "rien";
			for(int i = 0;i < tuileFCJoueur.get(coulCour).size();i++){
				choix[i+1] = tuileFCJoueur.get(coulCour).get(i).getNom();
			}
			ImageIcon icon = new ImageIcon("image/baeine.png","truc");
			String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Choisisser quoi jouer, rien pour ne jouer",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    icon,
                    choix,
                    choix[0]);
			
			if(s.equals("rien")){
				depla = true;
			}else if(s.equals("Vent")){
				if(nbBateaux>0){
					deplaBat = true;
					tuileFCJoueur.get(coulCour).remove(tuileFCJoueur.get(coulCour).indexOf("Vent"));
				}else{
					JOptionPane.showMessageDialog(this,"Impossible pas de bateau sur le terrain");
					depla = true;
				}
			}else if(s.equals("Dauphin")){
				if(nbNageur.get(coulCour)>0){
					deplaNag = true;
					tuileFCJoueur.get(coulCour).remove(tuileFCJoueur.get(coulCour).indexOf("Dauphin"));
				}else{
					JOptionPane.showMessageDialog(this,"Impossible pas de Nageur"+ coulCour +  "sur le terrain");
					depla = true;
				}
				
			}else if(s.equals("RequinV2")){
				if(nbRequin > 0){
					tuileFCJoueur.get(coulCour).remove(tuileFCJoueur.get(coulCour).indexOf("RequinV2"));
					deplaMonstre = true;
					typeFC = s;
				}else{
					JOptionPane.showMessageDialog(this,"Pas de requin en jeu");
					depla = true;
				}
			}else if(s.equals("BaleineV2")){
				if(nbBaleine > 0){
					tuileFCJoueur.get(coulCour).remove(tuileFCJoueur.get(coulCour).indexOf("BaleineV2"));
					deplaMonstre = true;
					typeFC = s;
				}else{
					JOptionPane.showMessageDialog(this,"Pas de requin en jeu");
					depla = true;
				}
			}else{
				tuileFCJoueur.get(coulCour).remove(tuileFCJoueur.get(coulCour).indexOf("Serpent"));
				deplaMonstre = true;
				typeFC = s;
			}
			quandOnveux.put(coulCour, false);
			
			
		}
		
		
		/**
		 * @author Max HOFFER
		 * 
		 * Deplacement d'un monstre ou de negeur ou bateau
		 */
		
		//Deplacement des serpend
		
		if(deplaMonstre &&  typeFC.equals("Serpent")){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Noir")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if(b2.getTerrain().equals("Mer") && b2.getOccupe() == false ){
					deplace(b1, b2);
					depla = true;
					deplaMonstre = false;
					return;
				}
				nbClic = 0;
			}
				
		}
		
		//Deplacement des requins
		
		if(deplaMonstre &&  typeFC.equals("RequinV2")){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Requin")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if(b2.getTerrain().equals("Mer") && b2.getOccupe() == false ){
					deplace(b1, b2);
					depla = true;
					deplaMonstre = false;
					return;
				}
				nbClic = 0;
			}
				
		}
		
		//Deplacement des baleines
		
		if(deplaMonstre &&  typeFC.equals("BaleineV2")){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Baleine")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if(b2.getTerrain().equals("Mer") && b2.getOccupe() == false ){
					deplace(b1, b2);
					depla = true;
					deplaMonstre = false;
					return;
				}
				nbClic = 0;
			}
				
		}
		
		
		//Deplacement Ngeurs
		
		if(deplaNag){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals(coulCour)){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if((((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 )) && b2.getTerrain().equals("Mer") && b2.getOccupe() == false ){
					deplace(b1, b2);
					nbDeplaFC ++;
					if(nbDeplaFC >= 3){
						depla = true;
						deplaNag = false;
						return;
					}
				}
				nbClic = 0;
			}	
		}
		
		if(deplaBat){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Bateau")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if((((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 )) && b2.getTerrain().equals("Mer") && b2.getOccupe() == false ){
					deplace(b1, b2);
					nbDeplaFC ++;
					if(nbDeplaFC >= 3){
						depla = true;
						deplaBat = false;
						return;
					}
				}
				nbClic = 0;
			}
				
		}
		
		
		/**
		 * @author Max HOFEFR
		 * 
		 * Deplacement dûs au dé
		 */
		//Deplacement du serpend en fonction du dé
		if(deSerpent){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Noir")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if(b2.getTerrain().equals("Mer")){
					deplace(b1, b2);
					deSerpent = false;
					changementJoueur();
					return;
				}
				nbClic = 0;
			}		
		}
		
		//Deplacement des requins en focntion du dé
		if(deRequin){
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Requin")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if(b2.getTerrain().equals("Mer") && (((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 ))){
					if(b2.getOccupe() && !b2.isBoat()){
						if(!defence.get(b2.getPionPresent().getCouleur()).isEmpty()){
							int j = defence.get(b2.getPionPresent()).indexOf("TueRequin");
							if(j > 0){
								b1.setPionPresent(null);
								b1.setOccupe(false);
								nbRequin --;
								b1.majAff();
							}
						}else{
							deplace(b1, b2);
						}
					}else{
						deplace(b1, b2);
					}
					
					deDepla ++;
					if(deDepla >= 2){
						deRequin = false;
						deDepla = 0;
						changementJoueur();
						return;
					}
					
				}
				nbClic = 0;
			}
				
		}
		
		
		if(deBaleine){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && b1.getPionPresent().getCouleur().equals("Baleine")){
					nbClic ++;
				}
				else{
					System.out.println("Rien sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				b2 = (HexagonButton) arg0.getSource();
				if(b2.getTerrain().equals("Mer") && (((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 ))){
					if(b2.getOccupe()){
						if(b2.getPionPresent().equals("Bateau")){
							//Faire le truc du bateau
						}
					}
					deplace(b1, b2);
					deDepla ++;
					if(deDepla >= 3){
						deBaleine = false;
						deDepla = 0;
						changementJoueur();
						return;
					}
					
				}
				nbClic = 0;
			}
				
		}
		
		/**
		 * @author Max HOFFER
		 * 
		 * Boucle de déplacement de pion
		 */
		if(depla){
			System.out.println("B1 selectionner en attnte du 2");
			if(nbClic == 0 ){
				b1 = (HexagonButton) arg0.getSource();
				if(b1.getOccupe() == true && !b1.getPionPresent().getCouleur().equals("Noir") && b1.getPionPresent().getCouleur().equals(coulCour)){
					
					nbClic ++;
					System.out.println("clic valu: " + nbClic);
				}
				else{
					System.out.println("Personne sur la case selectionner");
				}
			}else if(nbClic == 1 ){
				System.out.println("Clic num: " + nbClic);
				b2 = (HexagonButton) arg0.getSource();
				
				if((((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 )) && (b2.getOccupe() == false || b2.isBoat() == true) ){
					if(b2.isBoat()){
						
						Bateau b = b2.getBateauPresent();
						if(b.placeDispo()){
							b.addPion(b1.getPionPresent());
							b1.setPionPresent(null);
							b1.setOccupe(false);
							b1.setIcon(new ImageIcon("image/" + b1.getTerrain() + ".png"));
						}else{
							JOptionPane.showMessageDialog(this,"Le bateau est plein");
						} 
						
						
					}else{
						if(b1.getPionPresent().isNageur()){
							deplace(b1,b2);
							nbClic = 0;
							nbDepla = 3;
						}else{
							deplace(b1,b2);
							nbClic = 0;
							nbDepla ++;
						}
						
						
					}
					
					
					if(nbDepla >= 3){
						nbDepla = 0;
						depla = false;
						retrait = true;
						JOptionPane.showMessageDialog(this,"Choisissez une tuile a retirer");
						return;
					}
				}else{
					System.out.println("pas ok");
				}
				
				System.out.println("On remet nbCLic a 0");
				nbClic = 0;
			}
		}
		
		/**
		 * @author Max HOFFER
		 * 
		 * Moment de retrait d'une tuile
		 */
		
		if(retrait){
			b1 = (HexagonButton) arg0.getSource();
			if(!b1.getTerrain().equals("Mer")){

				if(b1.getTerrain().equals("Montagne") && nbPlage == 0 && nbForet == 0){
					retraitTerrain(b1);
					nbMontagne --;
				}else if(b1.getTerrain().equals("Montagne")){
					JOptionPane.showMessageDialog(this,"Il reste des plage ou des foret");
				}
				
				if(b1.getTerrain().equals("Foret") && nbPlage == 0){
					retraitTerrain(b1);
					nbForet --;
				}else if(b1.getTerrain().equals("Foret")){
					JOptionPane.showMessageDialog(this,"Il reste des plage");
				}
				
				if(b1.getTerrain().equals("Plage")){
					retraitTerrain(b1);
					nbPlage --;
				}
				
			}else{
				System.out.println("On ne retire pas la mer!");
			}
		}
	}
	
	
	private void remplissageArrayCoul(){
		listCoul.add("Bleu");
		listCoul.add("Jaune");
		listCoul.add("Vert");
		listCoul.add("Rouge");
		
	}
	
	private void deplace(HexagonButton b1, HexagonButton b2){
		System.out.print(b1.getPionPresent().toString());
		b1.setIcon(new ImageIcon("image/" + b1.getTerrain() + ".png"));
		Pion p = b1.getPionPresent();
		b1.setPionPresent(null);
		b1.setOccupe(false);
		if(b2.getTerrain().equals("Mer")){
			p.setNageur(true);
			nbNageur.put(coulCour, nbNageur.get(coulCour)+1);
		}else if(b1.getTerrain().equals("Mer")){
			return;
		}
		if(b2.getTerrain().equals("SafeZone")){
			if(b1.isBoat()){
				for(int i=0;i<b1.getBateauPresent().getNbPionCoul(coulCour);i++){
					Pion pb = b1.getBateauPresent().getPionCoul(i, coulCour);
					score.put(coulCour, score.get(coulCour) + pb.getValeur());
				}
				JOptionPane.showMessageDialog(this,"Le/Les pions de " + coulCour +" sont mis en sécurité");
				return;
			}
			score.put(coulCour, score.get(coulCour) + p.getValeur());
		}else{
			b2.setPionPresent(p);
			b2.setOccupe(true);
			b2.setIcon(b2.getPionPresent().getImg());
		}

	}
	
	public void retraitTerrain(HexagonButton b){
		System.out.println("Ok retrai");
		b1.setTerrain("Mer");
		retrait = false;
		if(b1.getOccupe()){
			b1.getPionPresent().setNageur(true);
		}
		if(b1.getFaceCachee().getMomentJeu().equals("AuChoix")){
			tuileFCJoueur.get(coulCour).add(b1.getFaceCachee());
			quandOnveux.put(coulCour, false);
			lanceDe();
		}
		
		if(b1.getFaceCachee().getMomentJeu().equals("Immediat")){
			JOptionPane.showMessageDialog(this,"Vous avez optenu:\n" + b1.getFaceCachee().toString()+"\n Vous aller la jouer au prochain clic");
			fCCoura = b1.getFaceCachee();
			immediat = true;
		}
		if(b1.getFaceCachee().getMomentJeu().equals("Defence")){
			defence.get(coulCour).add(b1.getFaceCachee());
			lanceDe();
		}
		b1.majAff();
		
		
	}
	
	public void initHashMap(){
		tuileFCJoueur.put("Rouge", listeFCRouge);
		tuileFCJoueur.put("Bleu", listeFCBleu);
		tuileFCJoueur.put("Jaune", listeFCJaune);
		tuileFCJoueur.put("Vert", listeFCVert);
		
		quandOnveux.put("Rouge", true);
		quandOnveux.put("Bleu", true);
		quandOnveux.put("Jaune", true);
		quandOnveux.put("Vert", true);
		
		score.put("Rouge", 0);
		score.put("Bleu", 0);
		score.put("Jaune", 0);
		score.put("Vert", 0);
		
		nbNageur.put("Rouge", 0);
		nbNageur.put("Bleu", 0);
		nbNageur.put("Jaune", 0);
		nbNageur.put("Vert", 0);
		
		defence.put("Rouge", defRouge);
		defence.put("Bleu", defBleu);
		defence.put("Vert", defVert);
		defence.put("Jaune", defJaune);
		
	}
	
	public boolean zoneMilieu(int row, int col){

		if((col >= 3 && col <= 10) && (row >= 3 && row <= 9) && !(row == 3 && col == 3) && !(row == 9 && col == 3) && !(row == 4 && col == 3) && !(row == 8 && col == 3) && !(row == 3 && col == 4) && !(row == 9 && col == 4) && !(row == 4 && col == 9) && !(row == 6 && col == 10) && !(row == 4 && col == 10) && !(row == 3 && col == 9) && !(row == 3 && col == 10) && !(row == 8 && col == 10) && !(row == 8 && col == 9) && !(row == 9 && col == 10) && !(row == 9 && col == 9)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @author Max HOFFER
	 * Fonction de création des face cachée de chaque terrain
	 */
	
	public void initFaceCache(){
		
		// Jouable immediatement
		FaceCachee f1 = new FaceCachee("Requin", "Immediat", "Prenez un pion requin mis de côté et placez sur la case de mer qu’occupait la tuile de terrain. Tout nageur occupant cette case de mer est retiré du jeu ");
		FaceCachee f2 = new FaceCachee("Baleine", "Immediat", "Prenez un pion baleine mis de côté et placez-le sur la case de mer qu’occupait la tuile de terrain ");
		FaceCachee f3 = new FaceCachee("Bateau","Immediat","Prenez un pion bateau mis de côté et placez-le sur la case de mer qu’occupait la tuile de terrain. Si cette case de mer contenait un ou plusieurs nageurs, placez-les à bord du bateau. Si la case de mer contenait plus de trois nageurs, c’est le joueur ayant révélé la tuile de terrain qui choisit lesquels montent à bord ");
		FaceCachee f4 = new FaceCachee("Tourbillon", "Immediat","retirez du jeu tous les nageurs, serpents de mer, requins, baleines, bateaux et explorateurs de la case de mer qu’occupait la tuile de terrain et de toutes les cases mer adjacentes");
		FaceCachee f5 = new FaceCachee("Volcant","Immediat","Fin du jeu");
		
		//Jouable Quand on veux
		FaceCachee f6 = new FaceCachee("Dauphin","AuChoix","Un dauphin vient en aide à l’un de vos nageurs. Déplacez un de vos nageurs de 1 à 3 cases de mer.");
		FaceCachee f7 = new FaceCachee("Vent","AuChoix","Les vents vous sont favorables. Déplacez un des bateaux que vous contrôlez de 1 à 3 cases de mer");
		FaceCachee f8 = new FaceCachee("Serpent","AuChoix","Déplacez le serpent de mer de votre choix déjà présent sur le plateau de jeu sur n’importe quelle case de mer inoccupée ");
		FaceCachee f9 = new FaceCachee("RequinV2","AuChoix","Déplacez le requin de votre choix déjà présent sur le plateau de jeu sur n’importe quelle case de mer inoccupée");
		FaceCachee f10 = new FaceCachee("BaleineV2","AuChoix","Déplacez la baleine de votre choix déjà présente sur le plateau de jeu sur n’importe quelle case de mer inoccupée");
		
		
		// Jouable en défence
		FaceCachee f11 = new FaceCachee("TueRequin","Defence","Quand un autre joueur déplace un requin dans une case de mer occupée par l’un de vos nageurs, vous pouvez jouer cette tuile de terrain pour retirer le requin du jeu. Tous les nageurs demeurent dans la case mer. ");
		FaceCachee f12 = new FaceCachee("TueBaleine","Defence","Quand un autre joueur déplace une baleine dans une case de mer occupée par un bateau que vous contrôlez, vous pouvez jouer cette tuile de terrain pour retirer la baleine du jeu. Votre bateau demeure dans la case de mer. ");
		
		// Remplissage des faces cachee
		
		//Plage
		
		//Baleine
		listeFCPlage.add(f2);
		listeFCPlage.add(f2);
		listeFCPlage.add(f2);
		//Requin
		listeFCPlage.add(f1);
		listeFCPlage.add(f1);
		listeFCPlage.add(f1);
		//Bateau
		listeFCPlage.add(f3);
		//Vent
		listeFCPlage.add(f7);
		listeFCPlage.add(f7);
		//Dauphin
		listeFCPlage.add(f6);
		listeFCPlage.add(f6);
		listeFCPlage.add(f6);
		//Serpent
		listeFCPlage.add(f8);
		//RequinV2
		listeFCPlage.add(f9);
		//BalienV2
		listeFCPlage.add(f10);
		//Tue requin
		listeFCPlage.add(f11);
		
		//Foret
		
		listeFCForet.add(f2);
		listeFCForet.add(f2);
		
		listeFCForet.add(f1);
		listeFCForet.add(f1);
		
		listeFCForet.add(f3);
		listeFCForet.add(f2);
		listeFCForet.add(f2);
		
		listeFCForet.add(f4);
		listeFCForet.add(f4);
		
		listeFCForet.add(f6);
		
		listeFCForet.add(f8);
		
		listeFCForet.add(f9);
		
		listeFCForet.add(f10);
		
		listeFCForet.add(f11);
		
		listeFCForet.add(f12);
		listeFCForet.add(f12);
		
		//Montagne
		
		listeFCMontagne.add(f1);
		
		listeFCMontagne.add(f4);
		listeFCMontagne.add(f4);
		listeFCMontagne.add(f4);
		listeFCMontagne.add(f4);
		
		listeFCMontagne.add(f5);
		
		listeFCMontagne.add(f11);
		
		listeFCMontagne.add(f12);
		
	}
	
	public int random(int min,int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public void changementJoueur(){
		quandOnveux.put(coulCour, true);
		if(listCoul.indexOf(coulCour) < listCoul.size()-1){
			coulCour = listCoul.get(listCoul.indexOf(coulCour)+1);
		}else{
			coulCour = listCoul.get(0);
		}
		
		if(tuileFCJoueur.get(coulCour).isEmpty()){
			depla = true;
		}
		
		JOptionPane.showMessageDialog(this,"Au tour de " + coulCour);
	}
	
	public void lanceDe(){
		int r = random(1, 3);
		if(r == 1){
			if(nbRequin>0){
				deRequin = true;
				JOptionPane.showMessageDialog(this,"Le de indique que vous avez 2 deplacement disponible sur un requin");
			}else{
				JOptionPane.showMessageDialog(this,"Le de à choisie requin mais aucun n'est disponible");
				changementJoueur();
			}	
		}else if(r == 2){
			if(nbBaleine>0){
				deBaleine = true;
				JOptionPane.showMessageDialog(this,"Le de indique que vous avez 3 deplacement disponible sur une baleine");
			}else{
				JOptionPane.showMessageDialog(this,"Le de à choisie baleine mais aucune n'est disponible");
				changementJoueur();
			}	
		}else{
			
			deSerpent = true;
			JOptionPane.showMessageDialog(this,"Le de indique que vous avez 1 deplacement disponible sur un serpent");
			
				
		}
		
	}
}

