import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HexagonePattern extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;
    private HexagonButton[][] hexButton = new HexagonButton[ROWS][COLUMNS];


    public HexagonePattern() {
        setLayout(null);
        initGUI();
    }


    public void initGUI() {
        int offsetX = -10;
        int offsetY = 0;

        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLUMNS; col++){
                hexButton[row][col] = new HexagonButton(row, col);
                hexButton[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        HexagonButton clickedButton = (HexagonButton) e.getSource();
                        System.out.println("Button clicked: [" + clickedButton.getRow() + "][" + clickedButton.getCol() + "]");
                        
                    }
                });
                add(hexButton[row][col]);
                hexButton[row][col].setBounds(offsetY, offsetX, 105, 95);
                offsetX += 87;
            }
            if(row%2 == 0) {
                offsetX = -52;
            } else {
                offsetX = -10;
            }
            offsetY += 76;
        }
    }
}