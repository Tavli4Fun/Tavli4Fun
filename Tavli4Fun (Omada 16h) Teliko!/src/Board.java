import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Board extends JLabel implements Serializable{
	
	private Game game;
	private Window window = new Window("TAVLI 4 FUN");
	protected int[][] spots = new int[24][2];;//Θέσεις τριγώνων
	protected int[][] checkersSpots = new int [24][2];
	protected int[][] temp = new int [24][2];
	BoardListener boardlsn;
	
	public transient BufferedImage[] diceImages = new BufferedImage[6]; 
	public transient BufferedImage[] buttonImages = new BufferedImage[2];
	
	private int doubleDiceX, doubleDiceY; 

	private Tavli tavli;
	
	public int dice1, dice2;
	private String doubleDiceString;
	private Random r = new Random();
	
	public transient BufferedImage img1 = null;
	public transient BufferedImage img2 = null;
	public transient BufferedImage imgDice1 = null;
	public transient BufferedImage imgDice2 = null;
	
	private Color checker1Color, checker2Color, checkerNoneColor, boardColor;
	private Color outlineColor, triangle1Color, triangle2Color;
	
	protected int w = window.WIDTH;
	protected int h = window.HEIGHT;
	
	private double bWidth, bHeight; //Μήκος και Ύψος ενδικτικού ταμπλού
	protected double lCol, mCol, rCol, row; //Περιγραμμα Ταμπλού (Πλάτος Αριστερής, Μεσαίας, Δεξιάς Στήλης, Γραμμών)
	protected  double triangleWidth, triangleHeight; //Πλάτος και Ύψος τριγώνων
	private double caseBlock; //Στήλη που θα έχει τις θήκες για τα πούλια (checkers).
	
	public Board (Game g){
		game=g;
		window.getFrame().add(this);
		triangle1Color = Color.red;
		triangle2Color = Color.black;
		boardColor = Color.orange;
		int bcolor=Settings.getBoardcolor();
		if(bcolor==0){
			outlineColor = new Color(139,69,19);
		}
		else if(bcolor==1){
			outlineColor = new Color(46,46,31);
		}
		
		bWidth = 56; //Μήκος Ταμπλό 
		bHeight = 48; //Ύψος Ταμπλό
		
		dice1=1;
		dice2=3;

		caseBlock = 3/bWidth;
		lCol = 1.2/bWidth;
		mCol = 2.5/bWidth;
		rCol = lCol + caseBlock;
		row = 1.3/bHeight;
		triangleWidth = 4/bWidth;
		triangleHeight = 20/bHeight;
		int chkcolor1=Settings.getPlayer1();
		int chkcolor2=Settings.getPlayer2();
		if(chkcolor1==1){
			checker1Color = new Color(102,0,102);
		}
		else if(chkcolor1==2){
			checker1Color = new Color(255,255,255);
		}
		else if(chkcolor1==3){
			checker1Color =Color.yellow; 
		}
		else if(chkcolor1==4){
			checker1Color =Color.blue; 
		}
		
		
		if(chkcolor2==1){
			checker2Color = new Color(102,0,102);
		}
		else if(chkcolor2==2){
			checker2Color = new Color(255,255,255);
		}
		else if(chkcolor2==3){
			checker2Color =Color.yellow; 
		}
		else if(chkcolor2==4){
			checker2Color =Color.blue; 
		}
		
		
		checkerNoneColor = Color.black;
		
		readImages();
		img1=buttonImages[0];
		img2=buttonImages[0];
		imgDice1=diceImages[dice1-1];
		imgDice2=diceImages[dice2-1];
		
		doubleDiceString=" 2 ";
		doubleDiceX=(int)Math.round(w*(lCol+6*triangleWidth)+3);
		doubleDiceY=(int)Math.round(h*(row+6.3*triangleWidth));
		window.getFrame().pack();
	}
	
	public void drawing(){
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		//Δημιουργία Ταμπλό
		g.setColor(outlineColor);
		g.fillRect(0, 0, w+24, h+72);
		g.setColor(boardColor);
		g.fillRect((int)Math.round(w*lCol),(int)Math.round(h*row) , (int)Math.round(w*6*triangleWidth), (int)Math.round(h*(1-2*row)));
		g.fillRect((int)Math.round((w-w*caseBlock)*1/2+w*lCol),(int)Math.round(h*row) , (int)Math.round(w*6*triangleWidth), (int)Math.round(h*(1-2*row)));
		
		//Σχεδίαση τριγώνων κάτω δεξιά και τοποθέτηση των θέσεων 0-5
		for(int i=0; i<6; i++){
			if(i%2==0) g.setColor(triangle1Color);
			else g.setColor(triangle2Color);
			g.fillPolygon(new int[]{(int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*lCol), (int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*(triangleWidth+lCol)), (int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*lCol+w*triangleWidth/2)}, 
				new int[]{(int)Math.round(h*(1-row)), (int)Math.round(h*(1-row)), (int)Math.round(h*(1-row-triangleHeight) )}, 3);
			spots[5-i][0]=(int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*lCol-1);
			spots[5-i][1]=(int)Math.round(h*(1-row));
		}	
			
		//Σχεδίαση τριγώνων κάτω αριστερά και τοποθέτηση των θέσεων 6-11
		for(int i=0; i<6; i++){
			if(i%2==0) g.setColor(triangle1Color);
			else g.setColor(triangle2Color);
			g.fillPolygon(new int[]{(int)Math.round(i*w*triangleWidth+w*lCol), (int)Math.round(i*w*triangleWidth+w*(triangleWidth+lCol)), (int)Math.round(i*w*triangleWidth+w*lCol+w*triangleWidth/2)}, 
				new int[]{(int)Math.round(h*(1-row)), (int)Math.round(h*(1-row)), (int)Math.round(h*(1-row-triangleHeight) )}, 3);
			spots[11-i][0]=(int)Math.round(i*w*triangleWidth+w*lCol-1);
			spots[11-i][1]=(int)Math.round(h*(1-row));
		}
		
		//Σχεδίαση τριγώνων πάνω αριστερά και τοποθέτηση των θέσεων 12-17
		for(int i=0; i<6; i++){
			if(i%2==0) g.setColor(triangle1Color);
			else g.setColor(triangle2Color);
			g.fillPolygon(new int[]{(int)Math.round(i*w*triangleWidth+w*lCol), (int)Math.round(i*w*triangleWidth+w*(triangleWidth+lCol)), (int)Math.round(i*w*triangleWidth+w*lCol+w*triangleWidth/2)}, 
				new int[]{(int)Math.round(h*row), (int)Math.round(h*row), (int)Math.round(h*(triangleHeight+row) )}, 3);
			spots[i+12][0]=(int)Math.round(i*w*triangleWidth+w*lCol-1);
			spots[i+12][1]=(int)Math.round(h*row);
		}
				
		//Σχεδίαση τριγώνων πάνω δεξιά και τοποθέτηση των θέσεων 18-23
		for(int i=0; i<6; i++){
			if(i%2==0) g.setColor(triangle1Color);
			else g.setColor(triangle2Color);
			g.fillPolygon(new int[]{(int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*lCol), (int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*(triangleWidth+lCol)), (int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*lCol+w*triangleWidth/2)}, 
					new int[]{(int)Math.round(h*row), (int)Math.round(h*row), (int)Math.round(h*(triangleHeight+row) )}, 3);
			spots[i+18][0]=(int)Math.round(w*mCol+(6+i)*w*triangleWidth+w*lCol-1);
			spots[i+18][1]=(int)Math.round(h*row);
		}
				
		//Σχεδίαση θηκών
		g.setColor(Color.BLACK);
		g.drawRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*row), (int)Math.round(w*caseBlock), (int)Math.round(h/3));
		g.drawRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*(1-row)-h/3), (int)Math.round(w*caseBlock), (int)Math.round(h/3));
		
		//Σχεδίαση Κουμπί MENU και UNDO
		g.drawRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*3.3*row+h/3), (int)Math.round(w*1.5*caseBlock), (int)Math.round(w*caseBlock));
		g.drawRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*4.6*row+h/3+w*caseBlock), (int)Math.round(w*1.5*caseBlock), (int)Math.round(w*caseBlock));
		g.setColor(new Color(0,90,90));
		g.fillRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*3.3*row+h/3), (int)Math.round(w*1.5*caseBlock), (int)Math.round(w*caseBlock));
		g.fillRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*4.6*row+h/3+w*caseBlock), (int)Math.round(w*1.5*caseBlock), (int)Math.round(w*caseBlock));
		Font f = new Font("ARIAL", 1,Window.WIDTH/43);
		g.setFont(f);
		g.setColor(new Color(90,180,180));
		g.drawString("MENU", (int)Math.round(w*(1-rCol*6/8)), (int)Math.round(h*4.75*row+h/3));
		g.drawString("UNDO", (int)Math.round(w*(1-rCol*6/8)), (int)Math.round(h*6.1*row+h/3+w*caseBlock));

		//Σχεδίαση Πούλιων
		drawCheckers(g,checkersSpots);
		
		//Σχεδίαση Κουμπιών
		g.drawImage(img1, (int)Math.round(w*(lCol+mCol+6.7*triangleWidth)), (int)Math.round(h*(1.7*row+triangleHeight)), (int)Math.round(w*(2*triangleWidth)), (int)Math.round(h*(triangleWidth)),this);
		g.setColor(Color.blue);
		g.setFont(f);
		g.drawString("Roll Dices", (int)Math.round(w*(lCol+mCol+6.95*triangleWidth)), (int)Math.round(h*(3.3*row+triangleHeight)));
		if(tavli.getDoubleDice()>0 && (tavli.whoHasDoubleDice()==tavli.getTurn() || tavli.getDoubleDice()==1)){
			g.drawImage(img2, (int)Math.round(w*(lCol+mCol+9.3*triangleWidth)), (int)Math.round(h*(1.7*row+triangleHeight)), (int)Math.round(w*(2*triangleWidth)), (int)Math.round(h*(triangleWidth)),this);
			g.drawString("Double", (int)Math.round(w*(lCol+mCol+9.78*triangleWidth)), (int)Math.round(h*(3.3*row+triangleHeight)));
		}
		//Σχεδίαση Ζαριών
		g.drawImage(imgDice1, (int)Math.round(w*(lCol+1.8*triangleWidth)), (int)Math.round(h*(row+5.95*triangleWidth)), (int)Math.round(w*(1.2*triangleWidth)), (int)Math.round(w*(1.2*triangleWidth)),this);
		g.drawImage(imgDice2, (int)Math.round(w*(lCol+3.3*triangleWidth)), (int)Math.round(h*(row+5.95*triangleWidth)), (int)Math.round(w*(1.2*triangleWidth)), (int)Math.round(w*(1.2*triangleWidth)),this);
		
		//Σχεδίαση Ζαριού Double
		if(tavli.getDoubleDice()>0){
			g.setColor(Color.black);
			g.drawRect(doubleDiceX, doubleDiceY, (int)Math.round(w*mCol-6), (int)Math.round(w*mCol-6));
			g.setColor(Color.white);
			g.fillRect(doubleDiceX, doubleDiceY, (int)Math.round(w*mCol-6), (int)Math.round(w*mCol-6));
			g.setColor(Color.red);
			g.drawString(doubleDiceString, doubleDiceX+4, (int)Math.round((doubleDiceY+1.05*row*h)));
		}
		drawCheckersInCase(true, g);
		drawCheckersInCase(false, g);
		
		
	}
	
	public void drawCheckers(Graphics g, int[][] c){

		Graphics2D g2 = (Graphics2D) g;

		double width, height;
		Color c1 = null;
		Color c2 = null;
		
		Ellipse2D checkerShape = null;
		
		for(int i=0; i<24; i++){
			int n = c[i][0];
			width = spots[i][0];
			height = spots[i][1];
			
			
			if(c[i][1]==ID.Player1.getValue()){
				c1= checker1Color;
				c2=c1;
			}
			else if(c[i][1]==ID.Player2.getValue()){
				c1=checker2Color;
				c2=c1;
			}
			else if(c[i][1]==ID.Both1.getValue()){
				c1=checker2Color;
				c2=checker1Color;
			}
			else if(c[i][1]==ID.Both2.getValue()){
				c1=checker1Color;
				c2=checker2Color;
			}
			else if(c[i][1]==ID.None.getValue()){
				c1=checkerNoneColor;
				c2=checkerNoneColor;
			}
		
			double x,y;
			if(i>11){
				if (n>0){
					
					
					g2.setColor(c1);
					checkerShape = new Ellipse2D.Double(width,height,w*triangleWidth,w*triangleWidth);
					g2.fill(checkerShape);
					if(n>5){
						if(n<10) x= checkerShape.getCenterX()-4;
						else x=checkerShape.getCenterX()-8;
						y= checkerShape.getCenterY()+5;
						g.setColor(Color.BLACK);
						Font f = new Font("ARIAL", 14,14);
						g.setFont(f);
						g.drawString(String.valueOf(n), (int)Math.round(x), (int)Math.round(y));
						
						for(int j=1; j<5; j++){
							g2.setColor(c2);
							checkerShape = new Ellipse2D.Double(width,height+j*w*triangleWidth,w*triangleWidth,w*triangleWidth);
							g2.fill(checkerShape);
						}
					}else{
						for(int j=1; j<n; j++){
							g2.setColor(c2);
							checkerShape = new Ellipse2D.Double(width,height+j*w*triangleWidth,w*triangleWidth,w*triangleWidth);
							g2.fill(checkerShape);
						}
					}
				}
			}else{
				if (n>0){
					g2.setColor(c1);
					checkerShape = new Ellipse2D.Double(width,height-1*w*triangleWidth,w*triangleWidth,w*triangleWidth);
					g2.fill(checkerShape);
					if(n>5){
						if(n<10) x= checkerShape.getCenterX()-4;
						else x=checkerShape.getCenterX()-8;
						y= checkerShape.getCenterY()+5;
						g.setColor(Color.BLACK);
						Font f = new Font("ARIAL", 14,14);
						g.setFont(f);
						g.drawString(String.valueOf(n), (int)Math.round(x), (int)Math.round(y));
						
						for(int j=1; j<5; j++){
							g2.setColor(c2);
							checkerShape = new Ellipse2D.Double(width,height-(j+1)*w*triangleWidth,w*triangleWidth,w*triangleWidth);
							g2.fill(checkerShape);
						}
					}else{
						for(int j=1; j<n; j++){
							g2.setColor(c2);
							checkerShape = new Ellipse2D.Double(width,height-(j+1)*w*triangleWidth,w*triangleWidth,w*triangleWidth);
							g2.fill(checkerShape);
						}
					}
				}
			}
		}
		drawPrisoners(g2);
		if(BoardListener.doubleClick){
			drawSelectedCheckers(g);
			drawAllowedMoves(g);
		}
			
	}
	
	public void readImages(){
		
		for(int i=0; i<6; i++)
			importImage(diceImages, i, "Dices/"+(i+1)+".png");
		
		importImage(buttonImages, 0, "ButtonFree.png");
		importImage(buttonImages, 1, "ButtonPressed.png");
		
	}
	
	public void importImage(BufferedImage[] array, int i, String fileName){
		try{	
			array[i]=ImageIO.read(new File(fileName));
		}
		catch(IOException e){
			System.out.println("There is no image with name: '"+fileName);
		}
	}
	
	public void rollDices(){

		long t= System.currentTimeMillis();
		long end = t+1000;
		
		while(System.currentTimeMillis() < end) {

			/*String d,d2;
			d = JOptionPane.showInputDialog("Pls Give a number for dice1(allowed:1-6): ");
			d2 = JOptionPane.showInputDialog("Pls Give a number for dice1(allowed:1-6): ");

			dice1=Integer.parseInt(d);
			dice2=Integer.parseInt(d2);*/
			
			dice1=r.nextInt(6)+1;
			dice2=r.nextInt(6)+1;
			
			imgDice1=diceImages[dice1-1];
			imgDice2=diceImages[dice2-1];	
			paintImmediately((int)Math.round(w*(lCol+1.8*triangleWidth)), (int)Math.round(h*(row+5.95*triangleWidth)), (int)Math.round(w*(3*triangleWidth)), (int)Math.round(w*(3*triangleWidth)));
		}
		
		imgDice1=diceImages[dice1-1];	
		imgDice2=diceImages[dice2-1];
		
		tavli.setRoundsOfDices();
		tavli.switchTurn();
		tavli.endTurn=!tavli.hasMoves();
		if(tavli.endTurn)
			if(!tavli.turn)
				JOptionPane.showMessageDialog(null, game.getName1()+" can't move!");
			else if(!game.getName2().equals("Computer"))
				JOptionPane.showMessageDialog(null,game.getName2()+" can't move!");
		
	}
	
	//Κίνηση του ζαριού Double
	public void doubleDice(){
		if(!(tavli.getDoubleDice()==64)){
			Object[] options = {"Yes","No","Cancel"};
			int n = JOptionPane.showOptionDialog(null,
		    "Your opponent wants to double! Do you want to double?",
		    "Double Dice",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[2]);
			if(n==0){
				if(tavli.getDoubleDice()==1 ){
					if(tavli.whoseTurnIs()==ID.Player1)
						tavli.setHasDoubleDice();
				}
				else
					tavli.setHasDoubleDice();
				if (!tavli.whoHasDoubleDice()){
					while(doubleDiceY<=(int)w*(1-7*row)){	
						setDoubleDiceY(1);
						paintImmediately((int)Math.round(w*(lCol+6*triangleWidth)), 0, (int)Math.round(w*mCol), h);
					}
	
				}
				else{
					while(doubleDiceY>=(int)w*row){	
						setDoubleDiceY(-1);
						paintImmediately((int)Math.round(w*(lCol+6*triangleWidth)), 0, (int)Math.round(w*mCol), h);
					}
	
				}
				tavli.nextDoubledice();
				repaint();
			}else if(n==1){
				if(!tavli.getTurn())
					tavli.giveResults(false);
				else
					tavli.giveResults(true);
			}
		}else if((tavli.getDoubleDice()==64)){
			JOptionPane.showMessageDialog(null, "Double dice is in the maximum level!");
		}
	}
	
	//Απεικονίζει τα πούλια που έχουν μαζευτεί. Παίρνει παράμετρο true αν είναι να απεικονίσει του 1ου παίκτη και false του 2ου.
	public void drawCheckersInCase(boolean Player1, Graphics g){
		
		if(Player1){
			for(int i=0; i<tavli.getCheckersInCase1(); i++){	
				
				g.setColor(checker1Color);
				g.fillRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*(1-row)-h/3+(h/45)*(i)+i), (int)Math.round(w*caseBlock), (int)Math.round(h/45));
				g.setColor(Color.black);
				g.drawRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*((1-row)-h/3)+(h/45)*(i)+i), (int)Math.round(w*caseBlock), (int)Math.round(h/45));
			}
		}
		else
			for(int i=0; i<tavli.getCheckersInCase2(); i++){	
				g.setColor(checker2Color);
				g.fillRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*row+(h/45)*(i)+i), (int)Math.round(w*caseBlock), (int)Math.round(h/45));
				g.setColor(Color.black);
				g.drawRect((int)Math.round(w*(1-rCol*7/8)), (int)Math.round(h*row+(h/45)*(i)+i), (int)Math.round(w*caseBlock), (int)Math.round(h/45));
			}

	}
	
	public void drawPrisoners(Graphics2D g2){
		
		double x,y;
		
		if(tavli.getPrisoners1()>0){
			g2.setColor(checker1Color);
			Ellipse2D checkerShape1 = new Ellipse2D.Double((int)Math.round(w*(6*triangleWidth+row/1.3)),(int)Math.round(h*(triangleHeight/2)),(int)Math.round(w*triangleWidth/1.7),(int)Math.round(w*triangleWidth/1.7));
			g2.fill(checkerShape1);
			
			if(tavli.getPrisoners1()>1){
				if(tavli.getPrisoners1()<10) x= checkerShape1.getCenterX()-4;
				else x=checkerShape1.getCenterX()-8;
				y= checkerShape1.getCenterY()+5;
				g2.setColor(Color.BLACK);
				Font f = new Font("ARIAL", 14,14);
				g2.setFont(f);
				g2.drawString(String.valueOf(tavli.getPrisoners1()), (int)Math.round(x), (int)Math.round(y));
			}
		}
		if(tavli.getPrisoners2()>0){
			g2.setColor(checker2Color);
			Ellipse2D checkerShape2 = new Ellipse2D.Double((int)Math.round(w*(6*triangleWidth+row/1.3)),(int)Math.round(h*(2*triangleHeight-1.2*triangleWidth)),(int)Math.round(w*triangleWidth/1.7),(int)Math.round(w*triangleWidth/1.7));
			g2.fill(checkerShape2);
			
			if(tavli.getPrisoners2()>1){
				if(tavli.getPrisoners2()<10) x= checkerShape2.getCenterX()-4;
				else x=checkerShape2.getCenterX()-8;
				y= checkerShape2.getCenterY()+5;
				g2.setColor(Color.BLACK);
				Font f = new Font("ARIAL", 14,14);
				g2.setFont(f);
				g2.drawString(String.valueOf(tavli.getPrisoners2()), (int)Math.round(x), (int)Math.round(y));
			}
		}
		
	}
	
	public void drawSelectedCheckers(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D checkerShape = null;
		g2.setColor(Color.green);
		
		if(tavli.getPrisoners1()>0 && tavli.getTurn()==false){
			g.setColor(Color.green);
			checkerShape = new Ellipse2D.Double((int)Math.round(w*(6*triangleWidth+row/1.3)),(int)Math.round(h*(triangleHeight/2)),(int)Math.round(w*triangleWidth/1.7),(int)Math.round(w*triangleWidth/1.7));
			g2.fill(checkerShape);
		}
		else if(tavli.getPrisoners2()>0 && tavli.getTurn()==true){
			checkerShape = new Ellipse2D.Double((int)Math.round(w*(6*triangleWidth+row/1.3)),(int)Math.round(h*(2*triangleHeight-1.2*triangleWidth)),(int)Math.round(w*triangleWidth/1.7),(int)Math.round(w*triangleWidth/1.7));
			g2.fill(checkerShape);
		}
		else if(BoardListener.spot>-1 && BoardListener.spot<24){
			int i =BoardListener.spot;
			int j = checkersSpots[i][0];
			
			double width = spots[i][0];
			double height = spots[i][1];
			
			if (j>5) j=5;
			
			if(i>11){
				checkerShape = new Ellipse2D.Double(width,height+(j-1)*w*triangleWidth,w*triangleWidth,w*triangleWidth);
				g2.fill(checkerShape);
			}	
			else{
				checkerShape = new Ellipse2D.Double(width,height-(j)*w*triangleWidth,w*triangleWidth,w*triangleWidth);
				g2.fill(checkerShape);
			}
		}
	}
	
	public void drawAllowedMoves(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D checkerShape = null;
		
		for(int i=BoardListener.spot-1; i>-1; i--){
			if(tavli.allowedToMove(BoardListener.spot,i)){
				if(i>11)
					checkerShape = new Ellipse2D.Double(spots[i][0]+w*triangleWidth/3,spots[i][1]+w*triangleWidth/3,w*triangleWidth/3,w*triangleWidth/3);
				else
					checkerShape = new Ellipse2D.Double(spots[i][0]+w*triangleWidth/3,spots[i][1]-w*triangleWidth*2/3,w*triangleWidth/3,w*triangleWidth/3);
				g2.setColor(Color.gray);
				g2.fill(checkerShape);
				g2.setColor(Color.black);
				g2.draw(checkerShape);
			}	
		}
	
		for(int i=BoardListener.spot+1; i<24; i++){
			if(tavli.allowedToMove(BoardListener.spot,i)){
				if(i>11)
					checkerShape = new Ellipse2D.Double(spots[i][0]+w*triangleWidth/3,spots[i][1]+w*triangleWidth/3,w*triangleWidth/3,w*triangleWidth/3);
				else
					checkerShape = new Ellipse2D.Double(spots[i][0]+w*triangleWidth/3,spots[i][1]-w*triangleWidth*2/3,w*triangleWidth/3,w*triangleWidth/3);
				g2.setColor(Color.gray);
				g2.fill(checkerShape);
				g2.setColor(Color.black);
				g2.draw(checkerShape);
			}	
		}
	}
	
	public  boolean isInSpot(int x, int y, int i){
	if(i>11)
		return (x>spots[i][0] && x<spots[i][0]+Window.WIDTH*triangleWidth) && (y>spots[i][1] && y<spots[i][1]+Window.HEIGHT*(5.7*triangleWidth));
	else
		return (x>spots[i][0] && x<spots[i][0]+Window.WIDTH*triangleWidth) && (y<spots[i][1] && y>spots[i][1]-Window.HEIGHT*(5.7*triangleWidth));

	}
	
	public boolean isInRollDiceButton(int x, int y){
		
		int width = (int)Math.round(w*(2*triangleWidth)); 
		int height = (int)Math.round(h*(triangleWidth));
		
		
		int xspot=(int)Math.round(w*(lCol+mCol+6.7*triangleWidth)); 
		int yspot=(int)Math.round(h*(1.5*row+triangleHeight));
		
		return (x >= xspot && x <= xspot+width && y >= yspot && y <= yspot+height);

	}
	
	public boolean isInDoubleDiceButton(int x, int y){
		
		int width = (int)Math.round(w*(2*triangleWidth)); 
		int height = (int)Math.round(h*(triangleWidth));
		
		
		int xspot=(int)Math.round(w*(lCol+mCol+9.3*triangleWidth)); 
		int yspot=(int)Math.round(h*(1.7*row+triangleHeight));
		
		return (x >= xspot && x <= xspot+width && y >= yspot && y <= yspot+height);

	}
	
	public boolean isInPrisoners(int x, int y){
		
		if(tavli.getTurn()==false && tavli.getPrisoners1()>0){
			int width = (int)Math.round(w*triangleWidth/1.7); 
			int height = (int)Math.round(w*triangleWidth/1.7);
			
			int xspot=(int)Math.round(w*(6*triangleWidth+row/1.3)); 
			int yspot=(int)Math.round(h*(triangleHeight/2));
			
			BoardListener.spot=24;
			return (x >= xspot && x <= xspot+width && y >= yspot && y <= yspot+height);
		}
		else if (tavli.getTurn()==true && tavli.getPrisoners2()>0){
			int width = (int)Math.round(w*triangleWidth/1.7); 
			int height = (int)Math.round(w*triangleWidth/1.7);
			
			int xspot=(int)Math.round(w*(6*triangleWidth+row/1.3)); 
			int yspot=(int)Math.round(h*(2*triangleHeight-1.2*triangleWidth));
			
			BoardListener.spot=-1;
			return (x >= xspot && x <= xspot+width && y >= yspot && y <= yspot+height);
		}
		return false;	
	}
	
	public boolean isInMenuButton(int x, int y){
		
		int width = (int)Math.round(w*1.5*caseBlock); 
		int height = (int)Math.round(w*caseBlock);
		
		int xspot=(int)Math.round(w*(1-rCol*7/8)); 
		int yspot=(int)Math.round(h*3.3*row+h/3);
		
		return (x >= xspot && x <= xspot+width && y >= yspot && y <= yspot+height);

	}
	
	public boolean isInUndoButton(int x, int y){
		
		int width = (int)Math.round(w*1.5*caseBlock); 
		int height = (int)Math.round(w*caseBlock);
		
		int xspot=(int)Math.round(w*(1-rCol*7/8)); 
		int yspot=(int)Math.round(h*4.6*row+h/3+w*caseBlock);
		
		return (x >= xspot && x <= xspot+width && y >= yspot && y <= yspot+height);

	}
	
	public int getBoardWidth(){
		return this.getWidth();
	}
	
	public int getBoardHeight(){
		return this.getHeight();
	}
	
	public int[][] getSpots(){
		return spots;
	}

	public double getTriangleWidth() {
		return triangleWidth;
	}

	public double getTriangleHeight() {
		return triangleHeight;
	}

	public void setW(int w) {
		this.w = w;
	}
	

	public void setH(int h) {
		this.h = h;
	}
	
	public void getTemp() {
		for(int i=0; i<24; i++){
			for(int j=0; j<2; j++){
				checkersSpots[i][j]=temp[i][j];
			}
		}
	}

	
	
	public BufferedImage getImg1() {
		return img1;
	}

	public void setImg1(BufferedImage img1) {
		this.img1 = img1;
	}

	public BufferedImage getImg2() {
		return img2;
	}

	public void setImg2(BufferedImage img2) {
		this.img2 = img2;
	}

	public BufferedImage getImgDice1() {
		return imgDice1;
	}

	public void setImgDice1(BufferedImage imgDice1) {
		this.imgDice1 = imgDice1;
	}

	public BufferedImage getImgDice2() {
		return imgDice2;
	}

	public void setImgDice2(BufferedImage imgDice2) {
		this.imgDice2 = imgDice2;
	}

	public void setDiceImages(BufferedImage[] diceImages) {
		this.diceImages = diceImages;
	}

	public void setButtonImages(BufferedImage[] buttonImages) {
		this.buttonImages = buttonImages;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setTemp() {
		for(int i=0; i<24; i++){
			for(int j=0; j<2; j++){
				temp[i][j]=checkersSpots[i][j];
			}
		}
	}

	public void setDice2(int i) {
		this.dice2=i;
	}
	public void setDice1(int i) {
		this.dice1=i;
	}
	
	public void setImgDice1(int n) {
		imgDice1=diceImages[n-1];
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public void setImgDice2(int n) {
		imgDice2 = diceImages[n-1];
	}

	public void setImg1(int i) {
		this.img1 = buttonImages[i];
	}

	public void setImg2(int i) {
		this.img2 = buttonImages[i];
	}

	public BufferedImage[] getDiceImages() {
		return diceImages;
	}

	public BufferedImage[] getButtonImages() {
		return buttonImages;
	}

	
	public void setDoubleDiceY(int doubleDiceY) {
		this.doubleDiceY += doubleDiceY;
	}

	public void setTavli(Tavli tavli) {
		this.tavli = tavli;
	}

	
	
	public BoardListener getBoardlsn() {
		return boardlsn;
	}

	public Tavli getTavli() {
		return(tavli);
	}
	
	public void setDoubleDiceString(String doubleDiceString) {
		this.doubleDiceString = doubleDiceString;
	}
	
	
}

class BoardListener implements MouseListener, Serializable {
	
	private Board b;
	protected static boolean doubleClick = false;
	
	protected static int spot =-1;
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		boolean flag=true;
		int i=0;
		
		while(flag && i<24){
		
			if(b.isInSpot(mx,my,i)){
					if(doubleClick){
						doubleClick=false;	
						b.getTavli().round(spot, i);
					}
					else if(b.checkersSpots[i][0]>0 && (b.getTavli().whoseTurnIs().getValue()==b.checkersSpots[i][1] || b.getTavli().whoseTurnIs().getValue()+2==b.checkersSpots[i][1])
							&&((b.getTavli().getTurn()==false && b.getTavli().getPrisoners1()==0) || (b.getTavli().getTurn()==true && b.getTavli().getPrisoners2()==0))){
						if(!b.getGame().name2.equals("Computer") || !b.getTavli().turn)
						doubleClick=true;
						spot=i;
					}
				flag=false;
			}
			else{
				i++;
			}
		}
		
		if(flag && b.isInRollDiceButton(mx, my)){
			b.setImg1(1);
			b.repaint();	
		}
		else if(flag && b.isInDoubleDiceButton(mx, my)){
			b.setImg2(1);
			b.repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		boolean flag = true;
		
		if(b.isInRollDiceButton(mx, my)){
			doubleClick=false;
			b.setTemp();
			b.getTavli().setTempCase1(b.getTavli().getCheckersInCase1());
			b.getTavli().setTempCase2(b.getTavli().getCheckersInCase2());
			b.getTavli().setTempPrisoners1(b.getTavli().getPrisoners1());
			b.getTavli().setTempPrisoners2(b.getTavli().getPrisoners2());
			for(int i=0; i<25; i++){
				for (int j=0; j<6; j++){
					b.getTavli().tempMoves1[i][j]=b.getTavli().moves1[i][j];
					b.getTavli().tempMoves2[i][j]=b.getTavli().moves2[i][j];
				}
			}
			if(!b.getTavli().endTurn){
				JOptionPane.showMessageDialog(null, b.getTavli().getMessageRounds());
			}else{
				b.rollDices();
				flag=false;
			}
		}
		if(flag && b.getTavli().getDoubleDice()>0 && b.isInDoubleDiceButton(mx, my)
				&& (b.getTavli().whoHasDoubleDice()==b.getTavli().getTurn() || b.getTavli().getDoubleDice()==1)){
			b.doubleDice();
			flag=false;
		}
		if(flag && b.isInPrisoners(mx, my)){
			doubleClick=true;
			flag=false;
		}
		if(flag && b.isInMenuButton(mx, my)){
			 
			
			if(b.getGame().getName2().equals("Computer")){
			    int result = JOptionPane.showConfirmDialog(null, "Do you want to save your game before returning to \n the main menu?");
				  if(result==1){
					b.getWindow().getFrame().dispose();
					Mode.getGamelist().clear();
					new Menu();
					Mode.names.clear();
				  }
				  else if(result==0) {
					try {						
						b.getGame().Serialize();
						
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					b.getWindow().getFrame().dispose();
					Mode.getGamelist().clear();
					  new Menu();
					  Mode.names.clear();
				  }
			}else{
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","MENU BUTTON", JOptionPane.YES_NO_OPTION);
		        if (result == JOptionPane.YES_OPTION) {
		        	b.getWindow().getFrame().dispose();
					Mode.getGamelist().clear();
					new Menu();
					Mode.names.clear();
		        }
		        else {
		           
		        }
			}
			flag=false;
		}
		if(flag && b.isInUndoButton(mx, my) && (!b.getTavli().getTurn() || !b.getGame().getName2().equals("Computer"))){
			b.getTemp();
			b.getTavli().setCheckersInCase1(b.getTavli().getTempCase1());
			b.getTavli().setCheckersInCase2(b.getTavli().getTempCase2());
			b.getTavli().setPrisoners1(b.getTavli().getTempPrisoners1());
			b.getTavli().setPrisoners2(b.getTavli().getTempPrisoners2());
			b.getTavli().setRoundsOfDices();
			b.getTavli().setEndTurn(false);
			for(int i=0; i<25; i++){
				for (int j=0; j<6; j++){
					b.getTavli().moves1[i][j]=b.getTavli().tempMoves1[i][j];
					b.getTavli().moves2[i][j]=b.getTavli().tempMoves2[i][j];
				}
			}
			b.repaint();
			flag=false;
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {
		b.setImg1(0);
		b.setImg2(0);
		b.paintImmediately((int)Math.round(b.w*(b.lCol+b.mCol+6.7*b.triangleWidth)), (int)Math.round(b.h*(1.7*b.row+b.triangleHeight)), (int)Math.round(b.w*(5*b.triangleWidth)), (int)Math.round(b.h*(b.triangleWidth)));
		b.repaint();
	}
	
	public void setB(Board b) {
		this.b = b;
	}
}