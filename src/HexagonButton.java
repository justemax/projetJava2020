import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;



class HexagonButton extends JButton {
        private static final long serialVersionUID = 1L;
        private static final int SIDES = 6;
        private static final int SIDE_LENGTH = 50;
        public static final int LENGTH = 95;
        public static final int WIDTH = 105;
        private int row = 0;
        private int col = 0;

        public HexagonButton(int row, int col) {
            setContentAreaFilled(false);
            setFocusPainted(true);
            setBorderPainted(false);
            setPreferredSize(new Dimension(WIDTH, LENGTH));
            this.row = row;
            this.col = col;
            //this.setBackground(Color.blue);
        }

        // Dessine mon hexagone
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Polygon hex = new Polygon();
            for (int i = 0; i < SIDES; i++) {
                hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
            }     
            g.setColor(Color.blue);
            g.drawPolygon(hex);
            
        }
        
        
        
        public void paintBorder(Graphics g) {
            super.paintComponent(g);
            Polygon hex = new Polygon();
            for (int i = 0; i < SIDES; i++) {
                hex.addPoint((int) (50 + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                (int) (50 + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
            }       
            g.drawPolygon(hex);
            
        }
        Shape shape;
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                 shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
            }
            return shape.contains(x, y);
       }
        
        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
}
