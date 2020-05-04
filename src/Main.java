

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
        HexagonePattern hexPattern = new HexagonePattern();
        JFrame frame = new JFrame();
        frame.setTitle("Hexagon Pattern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(new Point(700, 300));
        frame.add(hexPattern);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
}

