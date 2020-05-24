import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Classe principal du jeu
 * Il d'agit du pattern entier d'hexagonne mais �galement l�  o� se d�roule la partie
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
    private int placement = 5;
    private boolean fin = false;
    private int nbPlage = 0,nbForet = 0, nbMontagne = 0;
    
    //Phase de jeux p1 deplacement
    private int nbDepla = 0; 
    private boolean depla = false;
    
    //phase de retrait d'une tuile terrain
    private boolean retrait = false;
    
    private ArrayList<String> listCoul = new ArrayList<String>();
    
    
    
    private String coulCour;
    
    public HexagonePattern() {
        setLayout(null); 
        remplissageArrayCoul();
        coulCour = listCoul.get(0);
        initFaceCache();
        initGUI();
        JOptionPane.showMessageDialog(this,"Phase de placement de pion, les pions ont respectivement la valeur 3*1, 2*2, 2*3, 4, 5 et 6. Retenez les l'acc�s au placment vous ne pourrez plus y acc�der");
        JOptionPane.showMessageDialog(this,coulCour + " Commence a placer");
    }


    /***
     * Classe mettant en place le plateau de jeu, placement des boutons et des �lements de base
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
                
                
                // L� o� le placement des �lement se fait al�atoirement
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
                /* Placement des serpent */
                if((row == 1) && (col == 2) || (row == 0) && (col == 11) || (row == 12) && (col == 11) || (row == 12) && (col == 2) || (row == 6) && (col == 6)){
                	
					
					hexButton[row][col].setIcon(new ImageIcon("image/serpent.png"));
					hexButton[row][col].setOccupe(true);
					Pion s = new Pion("Noir", 1, col, row);
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
	 * Classe interagissant avec le jouer lk� o� il joue et le d�roulement de la partie
	 * 
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		
		
		
		
		// V2 systeme de placement de pion
		if(!fin){
			b1 = (HexagonButton) arg0.getSource();
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
						System.out.println(b1.getPionPresent().toString());
						System.out.println("reste " + placement);
					}
					
				}
				else{
					System.out.println("Pas bonne zone");
				}
			}else{
				System.out.println("Tout est plac� pour" + coulCour);
				if(listCoul.indexOf(coulCour) < listCoul.size() - 1){
					coulCour = listCoul.get(listCoul.indexOf(coulCour) + 1);
					JOptionPane.showMessageDialog(this,"Tous les pion de " + listCoul.get(listCoul.indexOf(coulCour) - 1) + " sont plac� au tour de " + coulCour + " de placer");
					placement = 5;
				}else{
					System.out.println("Tout est plac� la partie commence");
					fin = true;
					depla = true;
				}
					
				
			}
		}
		
		System.out.println("Partie en cour");
		
		
		// Deplacement pion v1 
		/**
		 * @author Max HOFFER
		 * 
		 * Boucle de d�placement de pion
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
				
				if((((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 )) && b2.getOccupe() == false){
					if(b1.getPionPresent().isNageur()){
						System.out.print(b1.getPionPresent().toString());
						b1.setIcon(new ImageIcon("image/" + b1.getTerrain() + ".png"));
						Pion p = b1.getPionPresent();
						b2.setPionPresent(p);
						System.out.println("Pion set sur b2");
						System.out.println(b2.getPionPresent().toString());
						b2.setOccupe(true);
						b1.setPionPresent(null);
						b1.setOccupe(false);
						b2.setIcon(new ImageIcon("image/pion" + b2.getPionPresent().getCouleur() + ".png"));
						nbClic = 0;
						nbDepla = 3;
					}else{
						deplace(b1,b2);
						nbClic = 0;
						nbDepla ++;
					}
					
					
					if(nbDepla == 3){
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
		b2.setPionPresent(p);
		System.out.println("Pion set sur b2");
		System.out.println(b2.getPionPresent().toString());
		b2.setOccupe(true);
		b1.setPionPresent(null);
		b1.setOccupe(false);
		b2.setIcon(new ImageIcon("image/pion" + b2.getPionPresent().getCouleur() + ".png"));
		
	}
	
	public void retraitTerrain(HexagonButton b){
		System.out.println("Ok retrai");
		b1.setTerrain("Mer");
		if(b1.getOccupe()){
			b1.getPionPresent().setNageur(true);
		}
		System.out.println(b1.getFaceCachee().toString());
		b1.majAff();
		retrait = false;
		depla = true;
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
	 * Fonction de cr�ation des face cach�e de chaque terrain
	 */
	public void initFaceCache(){
		
		// Jouable immediatement
		FaceCachee f1 = new FaceCachee("Requin", "Immediat", "Prenez un pion requin mis de c�t� et placez sur la case de mer qu�occupait la tuile de terrain. Tout nageur occupant cette case de mer est retir� du jeu ");
		FaceCachee f2 = new FaceCachee("Baleine", "Immediat", "Prenez un pion baleine mis de c�t� et placez-le sur la case de mer qu�occupait la tuile de terrain ");
		FaceCachee f3 = new FaceCachee("Bateau","Immediat","Prenez un pion bateau mis de c�t� et placez-le sur la case de mer qu�occupait la tuile de terrain. Si cette case de mer contenait un ou plusieurs nageurs, placez-les � bord du bateau. Si la case de mer contenait plus de trois nageurs, c�est le joueur ayant r�v�l� la tuile de terrain qui choisit lesquels montent � bord ");
		FaceCachee f4 = new FaceCachee("Tourbillon", "Immediat","retirez du jeu tous les nageurs, serpents de mer, requins, baleines, bateaux et explorateurs de la case de mer qu�occupait la tuile de terrain et de toutes les cases mer adjacentes");
		FaceCachee f5 = new FaceCachee("Volcant","Immediat","Fin du jeu");
		
		//Jouable Quand on veux
		FaceCachee f6 = new FaceCachee("Dauphin","AuChoix","Un dauphin vient en aide � l�un de vos nageurs. D�placez un de vos nageurs de 1 � 3 cases de mer.");
		FaceCachee f7 = new FaceCachee("Vent","AuChoix","Les vents vous sont favorables. D�placez un des bateaux que vous contr�lez de 1 � 3 cases de mer");
		FaceCachee f8 = new FaceCachee("Serpent","AuChoix","D�placez le serpent de mer de votre choix d�j� pr�sent sur le plateau de jeu sur n�importe quelle case de mer inoccup�e ");
		FaceCachee f9 = new FaceCachee("RequinV2","AuChoix","D�placez le requin de votre choix d�j� pr�sent sur le plateau de jeu sur n�importe quelle case de mer inoccup�e");
		FaceCachee f10 = new FaceCachee("BaleineV2","AuChoix","D�placez la baleine de votre choix d�j� pr�sente sur le plateau de jeu sur n�importe quelle case de mer inoccup�e");
		
		
		// Jouable en d�fence
		FaceCachee f11 = new FaceCachee("Tue requin","Defence","Quand un autre joueur d�place un requin dans une case de mer occup�e par l�un de vos nageurs, vous pouvez jouer cette tuile de terrain pour retirer le requin du jeu. Tous les nageurs demeurent dans la case mer. ");
		FaceCachee f12 = new FaceCachee("Tue baleine","Defence","Quand un autre joueur d�place une baleine dans une case de mer occup�e par un bateau que vous contr�lez, vous pouvez jouer cette tuile de terrain pour retirer la baleine du jeu. Votre bateau demeure dans la case de mer. ");
		
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
}

