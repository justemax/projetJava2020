import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HexagonePattern extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    private static final int ROWS = 13;
    private static final int COLUMNS = 13;
    private HexagonButton[][] hexButton = new HexagonButton[ROWS][COLUMNS];

    private int nbClic = 0;
    private HexagonButton b1,b2;
    
    
    
    public HexagonePattern() {
        setLayout(null);
        initGUI();
    }


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
                if((row == 1) && (col == 2) || (row == 0) && (col == 11) || (row == 12) && (col == 11) || (row == 12) && (col == 2)){
                	
					
					hexButton[row][col].setIcon(new ImageIcon("image/serpent.png"));
					
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
    
    
    


	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Selectionnez le premier bouton");
		
		
		/* Selection entre deux case adjacentes */
		if(nbClic == 0){
			b1 = (HexagonButton) arg0.getSource();
			System.out.println("Col 1= " + b1.getCol());
			System.out.println("Row 1 = " + b1.getRow());
			b1.setBackground(Color.green);
			nbClic ++;
		}else if(nbClic == 1){
			System.out.println("Clic num: " + nbClic);
			b2 = (HexagonButton) arg0.getSource();
			System.out.println("Col 2 = " + b2.getCol());
			System.out.println("row 2 = " + b2.getRow());
			
			int rapRow = (b2.getRow() - b1.getRow()),rapCol = (b2.getCol() - b1.getCol());
			
			System.out.println("rapport row = " + (b2.getRow() - b1.getRow()));
			System.out.println("rapport col = " + (b2.getCol() - b1.getCol()));
			
			if((((b2.getCol() - b1.getCol()) == 1 ) || ((b2.getCol() - b1.getCol()) == -1 ) || ((b2.getCol() - b1.getCol()) == 0 )) && (((b2.getRow() - b1.getRow()) == 1 ) || ((b2.getRow() - b1.getRow()) == -1 ) || ((b2.getRow() - b1.getRow()) == 0 ))){
				System.out.print("col +1 ok");
				b2.setBackground(Color.red);
			}else{
				System.out.println("pas ok");
			}
			
			b1.setBackground(Color.blue);
			nbClic = 0;
		}
		
	}
}