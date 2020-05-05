

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
        HexagonePattern hexPattern = new HexagonePattern();
        JFrame frame = new JFrame();
        frame.setTitle("AOE eco+");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(new Point(700, 300));
        frame.add(hexPattern);
        frame.setSize(520, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
}

