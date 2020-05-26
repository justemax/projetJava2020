import java.awt.*;

import javax.swing.*;

/**
 * Classe redifinissant les boutons de façons a ce qu'ils est une forme d'hexagonne
 * @author Max
 *
 */
class HexagonButton extends JButton {
        private static final long serialVersionUID = 1L;
        private int row = 0;
        private int col = 0;
        private int n=6;
        private int x[]= new int[n];
        private int y[]= new int[n];
        private double angle = 2*Math.PI/n;
        
        private boolean occupe = false;
        private boolean boat = false;
        private Pion pionPresent;
        private Bateau bateauPresent;
        private String terrain = "Mer";
        private FaceCachee faceCachee = null; 

        


		public HexagonButton(int row, int col) {
        	Dimension size = getPreferredSize();
        	size.width = size.height = Math.max(size.width, size.height);
        	setPreferredSize(size);
        	setContentAreaFilled(false);
            this.row = row;
            this.col = col;
            this.setBackground(Color.blue.brighter());
            this.setText(String.valueOf(col)+ "." + String.valueOf(row));
            this.setIcon(new ImageIcon("image/Mer.png"));
        }

        
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(Color.lightGray);
            } else {
                g.setColor(getBackground());
            }
            int x0 = getSize().width/2;
            int y0 = getSize().height/2;
            for(int i=0; i<n; i++) {
                double v = i*angle;
                x[i] = x0 + (int)Math.round((getWidth()/2)*Math.cos(v));
                y[i] = y0 + (int)Math.round((getHeight()/2)*Math.sin(v));
            }
            g.fillPolygon(x, y, n);
            super.paintComponent(g);
        }
         
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            int x0 = getSize().width/2;
            int y0 = getSize().height/2;
            for(int i=0; i<n; i++) {
                double v = i*angle;
                x[i] = x0 + (int)Math.round((getWidth()/2)*Math.cos(v));
                y[i] = y0 + (int)Math.round((getHeight()/2)*Math.sin(v));
            }
            g.drawPolygon(x, y, n);
        }
        
        Polygon polygon;
        public boolean contains(int x1, int y1) {
            if (polygon == null || !polygon.getBounds().equals(getBounds())) {
                int x0 = getSize().width/2;
                int y0 = getSize().height/2;
                for(int i=0; i<n; i++) {
                    double v = i*angle;
                    x[i] = x0 + (int)Math.round((getWidth()/2)*Math.cos(v));
                    y[i] = y0 + (int)Math.round((getHeight()/2)*Math.sin(v));
                }
                polygon = new Polygon(x,y,n);
            }
            return polygon.contains(x1, y1);
        }
        
        public Pion getPionPresent() {
			return pionPresent;
		}


		public void setPionPresent(Pion pionPresent) {
			this.pionPresent = pionPresent;
		}


		public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
        
        public String getTerrain() {
			return terrain;
		}


        public FaceCachee getFaceCachee() {
			return faceCachee;
		}


		public void setFaceCachee(FaceCachee faceCachee) {
			this.faceCachee = faceCachee;
		}


		public void setOccupe(boolean b){
        	this.occupe = b;
        }
        
        public boolean getOccupe(){
        	return occupe;
        }
        
		public void setTerrain(String terrain) {
			this.terrain = terrain;
		}
		
		
		
		public boolean isBoat() {
			return boat;
		}


		public void setBoat(boolean boat) {
			this.boat = boat;
		}


		public Bateau getBateauPresent() {
			return bateauPresent;
		}


		public void setBateauPresent(Bateau bateauPresent) {
			this.bateauPresent = bateauPresent;
		}


		public void majAff(){
			
			if(occupe){
				if(pionPresent.getCouleur().equals("Noir")){
					this.setIcon(new ImageIcon("image/serpent.png"));
				}else{
					this.setIcon(new ImageIcon("image/pion" + pionPresent.getCouleur() + ".png"));
				}
				
			}else{
				this.setIcon(new ImageIcon("image/" + terrain + ".png"));
			}
			
			
			if (terrain == "Foret"){
        		this.setBackground(Color.green);
        	}else if(terrain == "Plage"){
        		this.setBackground(Color.yellow);
        	}else if(terrain == "Montagne"){
        		this.setBackground(Color.gray);
        	}else{
        		this.setBackground(Color.blue.brighter());
        	}
		}
}
