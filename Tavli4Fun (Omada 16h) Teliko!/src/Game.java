import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/* Aυτή είναι η εργασία μας στο μάθημα Τεχνολογία Λογισμικού!
 * Καταφέραμε και φτιάξαμε τέσσερα παιχνίδια του τάβλι και μάλιστα
 * το παιχνίδι "Πόρτες" συμπεριλαμβάνουν τεχνιτή νοημοσύνη τριών επιπέδων.
 * Ωστώσο δεν έχει ολοκληρωθεί η δουλειά μας καθώς θέλαμε να βάλουμε και στα
 * άλλα τρια παιχνίδια τεχνιτή νοημοσύνη αλλά λόγω απροθυμίας για συνεργασία μέσα στην ομάδα
 * και λίγων προγραμματιστών (3 άτομα) δεν μπορέσαμε να ολοκληρώσουμε αυτό το μεγάλο αλλά ενδιαφέρον έργο!
 * Επίσης δεν προλάβαμε να υλοποιήσουμε την λειτουργία με τα σκορ καθώς και κάποιοι κανόνες λείπουν απ' την κλασσική
 * "Φεύγα". Προτιμήσαμε να μην τους προσθέσουμε επειδή θα υπήρχαν κάποια προβλήματα, όπως το αν η μοναδική κινηση που
 * μπορεί να κάνει ο παίκτης είναι απαγορευμένη από το παιχνίδι, τότε το παιχνίδι θα "κολλούσε" εκεί γιατί δεν θα 
 * μπορούσε να πατήσει ο παίκτης να αλλάξουν τα ζάρια (επειδή το σύστημα θα έδειχνε ότι υπάρχουν κι άλλες ζαριές) αλλά 
 * ταυτόχρονα δεν θα μπορούσε να κάνει άλλη κίνηση. Θα μπορούσε να λυθεί αυτό το πρόβλημα, αλλά τις τελευταίες μέρες 
 * είδαμε ότι υπάρχουν έξτρα κανόνες και δεν είχαμε τον χρόνο να σκεφτούμε πως μπορεί να γίνει. Το μέγεθος του παραθύρου του
 * παιχνιδιού μπορεί να μεγαλώσει χωρίς κανένα πρόβλημα, τοποθετώντας στην ιδιότητα της κλάσης Window WIDTH αντί για 600, όποιον
 * άλλο αριθμό θέλετε (συνηστάται 800 για μεγάλες οθόνες). Όλα υλοποιήθηκαν με αναλογίες ώστε στο μέλλον να βάλουμε και ανάλυση
 * αν θελήσουμε.
 * 
 * Γενικά συναντήσαμε πολλά προβλήματα, με αποτέλεσμα να χάσουμε χρόνο ώστε να τα λύσουμε και προς στο τέλος 
 * υπήρξε μια ιδέα που θα βοηθούσε πολύ στην εμφάνιση των πιθανών κινήσεων στον παίκτη (έξτρα λειτουργία) και 
 * στην τεχνιτή νοημοσύνη, που όμως χρειάστηκε να αλλάξει περίπου ο μισός κώδικας. Τελικά χρησιμοποιήσαμε δύο 
 * πίνακες (έναν για κάθε παίκτη) που δείχνουν για κάθε θέση αν μπορεί να παιχτεί το ζάρι 1,2,3,4,5 ή 6. Μ' αυτό
 * άλλαξε η νοοτροπία του κώδικα αλλά ταυτόχρονα έλυσε πολλά προβλήματα, όπως να βλέπει το πρόγραμμα αν υπάρχουν άλλες
 * διαθέσιμες κινήσεις για τον παίκτη ή να παίξει ο επόμενος. Πάντως αν όλοι είχαμε ασχοληθεί μ' αυτά που έπρεπε να 
 * κάνουμε το αποτέλεσμα μας θα ήταν διπλάσια καλύτερο.*/

public class Game extends Canvas implements Serializable{
	
	public String name1,name2;
	public int scoreP1, scoreP2;
	
	private int n=0;
	private ArrayList GameList =Mode.getGamelist();
	private Board b;
	private Tavli tavli;
	private BoardListener listener;
	private int numOfRounds;
	
	
	public Game(){
		name1=Mode.names.get(0);
		name2=Mode.names.get(1);
		String game=GameList.get(0).toString();
		b = new Board(this);
		
		int roundOfGames=0;
		for(int i=1; i<Settings.numOfGames; i++){
			roundOfGames+=i;
		}
		
		if(game=="Plakwto")
			tavli = new Plakwto(b);
		else if(game=="Portes"){
			tavli = new Portes(b);
			if(name2.equals("Computer")) {
				Portes portes =new Portes(b);
				roundOfGames=1;
				System.out.println("epipedo "+ portes.getAi().getLevel());
			}
			else {
				tavli = new Portes(b);
			}
		}else if(game=="Assoduo")
			tavli = new Assoduo(b);
		else if(game=="Feuga")
			tavli = new Feuga(b);
		
		b.setTavli(tavli);
		b.paintImmediately(0, 0, Window.WIDTH+24,Window.HEIGHT+72);
		if(!name2.equals("Computer")){
			b.rollDices();
		}
		listener = new BoardListener();
		listener.setB(b);
		b.addMouseListener(listener);
		if(!tavli.getTurn())
			JOptionPane.showMessageDialog(null, name1 +" plays first!");
		else
			JOptionPane.showMessageDialog(null, name2 +" plays first!");
		
		
	}
	
	public  void nextTavli(){

		if(!name2.equals("Computer")){
			boolean flag1=true;
			int i=0;
			while(flag1){
				if(Mode.names.get(i).equals(name2)){
					flag1=false;
					if (i+1 < Mode.names.size())
						name2=Mode.names.get(i+1);
					else{
						boolean flag2=true;
						int j=0;
						while(flag2){
							if(Mode.names.get(j).equals(name1)){
								flag2=false;
								if (j+2 < Mode.names.size()){
									name1=Mode.names.get(j+1);
									name2=Mode.names.get(j+2);
								}
								else{
									n++;
									name1=Mode.names.get(0);
									name2=Mode.names.get(1);
								}
							}else
								j++;
						}
					}
				}
				i++;
			}
		}else{
			n++;
		}
		if(n<GameList.size()){
			
		
			String game=GameList.get(n).toString();
			b.getWindow().getFrame().dispose();
			b = new Board(this);
			
			for(int i=0; i<24; i++){
				b.checkersSpots[i][0]=0;
				b.checkersSpots[i][1]=0;		
			}
			
			if(game=="Plakwto")
				tavli = new Plakwto(b);
			else if(game=="Portes")
				tavli = new Portes(b);
			else if(game=="Assoduo")
				tavli = new Assoduo(b);
			else if(game=="Feuga")
				tavli = new Feuga(b);
			
			b.setTavli(tavli);
			b.getWindow().getFrame().setContentPane(b);
			listener = new BoardListener();
			listener.setB(b);
			b.addMouseListener(listener);
			JOptionPane.showMessageDialog(null, "New "+ game +" game begins!");
			if(tavli.getTurn())
				JOptionPane.showMessageDialog(null, name1 +" plays first!");
			else
				JOptionPane.showMessageDialog(null, name2 +" plays first!");
		}else{
			JOptionPane.showMessageDialog(null, "Game Over");
			b.getWindow().getFrame().dispose();
			Mode.getGamelist().clear();
			new Menu();
		}
	}
	
	public void Serialize() throws IOException{
		FileOutputStream outstream=new FileOutputStream("savedgame.ser");
		ObjectOutputStream out=new ObjectOutputStream(outstream);
		out.writeObject(this);
		out.close();
		outstream.close();
		
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        
    }
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

    }
	
	public static void main(String[] args) {
		new Menu();
	}
	
	public void setTavli(Tavli tavli) {
		this.tavli = tavli;
	}
	
	public Board getB() {
		return b;
	}

	public void setB(Board b) {
		this.b = b;
	}

	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}

	public ArrayList getGameList() {
		return GameList;
	}

	
	
	public void setName1(String name1) {
		this.name1 = name1;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public void setGameList(ArrayList gameList) {
		GameList = gameList;
	}
}
