import java.io.Serializable;
import java.util.Random;


public class AI implements Serializable{
	
	private int level;
	private Board b;
	private Tavli tavli;
	
	public AI(int level, Board b, Tavli tavli) {
		super();
		this.level = level;
		this.b = b;
		this.tavli = tavli;
	}

	public void easyMode(){
		boolean flag;
		//Αν έχει κάποιο φυλακισμένο πούλι
		playPrisoners();
		while(tavli.hasMoves()){
			//Αν έχει πούλι για να το μαζέψει
			flag=pickChecker();
			if(flag)
				makePossibleMove();
		}
	}
	
	public void mediumMode(){
		boolean flag;
		playPrisoners();
		
		while(tavli.hasMoves()){
			flag=eatChecker();
			if(flag)
				flag=pickChecker();
			if(flag)
				makePossibleMove();
		}
		
		
		
	}
	
	public void hardMode(){
		boolean flag;
		playPrisoners();
		
		while(tavli.hasMoves()){	
			flag=eatChecker();
			if(flag)
				flag=pickChecker();
			if(flag)
				flag=coverChecker();
			if(flag){
				makePossibleMove();
			}
		}
		
	}
	
	public void playPrisoners(){
		boolean flag=true;
		int i=0;
		int end=tavli.getPrisoners2();
		while(flag && i<end){
			
			if(b.checkersSpots[b.dice1-1][0]==1 && b.checkersSpots[b.dice1-1][1]==ID.Player1.getValue() && tavli.getRoundsOfDice1()>0){
					roundForComputer(-1, b.dice1-1);
			}else if(b.checkersSpots[b.dice2-1][0]==1 && b.checkersSpots[b.dice2-1][1]==ID.Player1.getValue() && tavli.getRoundsOfDice2()>0){
				roundForComputer(-1, b.dice2-1);
			}	
			else{
				if(tavli.allowedToMove(-1, -1+b.dice1)){
					roundForComputer(-1,-1+b.dice1);
				}
				else if(tavli.allowedToMove(-1, -1+b.dice2)){
					roundForComputer(-1,-1+b.dice2);
				}	
			}
			if(tavli.isEndTurn()) flag=false;
			i++;
		}
	}
	
	public boolean pickChecker(){
		boolean flag=true;
		if(tavli.readyForCase()){
			int i=18;
			while(flag && i<24){
				if(b.checkersSpots[i][1]==ID.Player2.getValue() && tavli.allowedToMoveInCase(i)){
					roundForComputer(i,i);
					flag=false;
				}
				i++;
			}
		}
		return flag;
	}
	
	public boolean eatChecker(){
		boolean flag=true;
		int i=0;
		
		while(i<24 && flag){
			if(b.checkersSpots[i][0]==1 && b.checkersSpots[i][1]==ID.Player1.getValue()){
				if(i-b.dice1>-1 && b.checkersSpots[i-b.dice1][1]==ID.Player2.getValue() && 
					(level!=3 ||  b.checkersSpots[i-b.dice1][0]!=2 || i<15 ) && tavli.getRoundsOfDice1()>0){
						roundForComputer(i-b.dice1, i);
						flag=false;
				}else if (i-b.dice2>-1 && b.checkersSpots[i-b.dice2][1]==ID.Player2.getValue() &&
					(level!=3 ||  b.checkersSpots[i-b.dice2][0]!=2 || i<15 ) && tavli.getRoundsOfDice2()>0){
						roundForComputer(i-b.dice2, i);
						flag=false;
				}else if(i-b.dice1-b.dice2>-1 && b.checkersSpots[i-b.dice1-b.dice2][1]==ID.Player2.getValue() &&
					(level!=3 || b.checkersSpots[i-b.dice1-b.dice2][0]!=2 || i<15 ) && tavli.allowedToMove(i-b.dice1-b.dice2, i)){
						if(tavli.allowedToMove(i-b.dice1-b.dice2, i-b.dice1)){
							roundForComputer(i-b.dice1-b.dice2, i-b.dice1);
							roundForComputer(i-b.dice1, i);
							
						}else{
							roundForComputer(i-b.dice1-b.dice2, i-b.dice2);
							roundForComputer(i-b.dice2, i);
						}
						flag=false;
				}else if(tavli.maximumMoves()==4){
					if(i-2*b.dice1>-1 && b.checkersSpots[i-2*b.dice1][1]==ID.Player2.getValue() &&
							(level!=3 ||  b.checkersSpots[i-2*b.dice1][0]!=2 || i<15) && tavli.allowedToMove(i-2*b.dice1,i)){
								roundForComputer(i-2*b.dice1, i-b.dice1);
								roundForComputer(i-b.dice1, i);
								flag=false;
					}else if(i-3*b.dice1>-1 && b.checkersSpots[i-3*b.dice1][1]==ID.Player2.getValue() && 
						(level!=3 ||  b.checkersSpots[i-3*b.dice1][0]!=2 || i<15) && tavli.allowedToMove(i-3*b.dice1,i)){
							roundForComputer(i-3*b.dice1, i-2*b.dice1);
							roundForComputer(i-2*b.dice1, i-b.dice1);
							roundForComputer(i-b.dice1, i);
							flag=false;
					}else if(i-4*b.dice1>-1 && b.checkersSpots[i-4*b.dice1][1]==ID.Player2.getValue() && 
						(level!=3 ||  b.checkersSpots[i-4*b.dice1][0]!=2 || i<15) && tavli.allowedToMove(i-4*b.dice1,i)){
							roundForComputer(i-4*b.dice1, i-3*b.dice1);
							roundForComputer(i-3*b.dice1, i-2*b.dice1);
							roundForComputer(i-2*b.dice1, i-b.dice1);
							roundForComputer(i-b.dice1, i);
							flag=false;
						
					}
				}
			}
			i++;
		}
		return flag;
	}
	
	public boolean coverChecker(){
		//Έλεγχος για το εάν βρίσκεται ένα πούλι του μόνο του ώστε να το καλύψουν πούλια που βρίσκονται
		//πίσω από αυτό ή να καλυφθεί πηγαίνοντας σε κάποια άλλη πιο μπροστά
		boolean flag=true;
		int i=23;
		while(i>0 && flag){
			if(b.checkersSpots[i][0]==1 && b.checkersSpots[i][1]==ID.Player2.getValue()){
				if(i-b.dice1>-1 && b.checkersSpots[i-b.dice1][1]==ID.Player2.getValue() 
					&&  b.checkersSpots[i-b.dice1][0]!=2 && tavli.getRoundsOfDice1()>0){
						roundForComputer(i-b.dice1, i);
						flag=false;
				}else if (i-b.dice2>-1 && b.checkersSpots[i-b.dice2][1]==ID.Player2.getValue() 
					&& b.checkersSpots[i-b.dice2][0]!=2 && tavli.getRoundsOfDice2()>0){
						roundForComputer(i-b.dice2, i);
						flag=false;
				}else if(i+b.dice1<24 && b.checkersSpots[i+b.dice1][1]==ID.Player2.getValue() 
					&& tavli.allowedToMove(i, i+b.dice1)){
						roundForComputer(i, i+b.dice1);
						flag=false;
				}else if (i+b.dice2<24 && b.checkersSpots[i+b.dice2][1]==ID.Player2.getValue() && tavli.allowedToMove(i, i+b.dice2)){
						roundForComputer(i, i+b.dice2);
						flag=false;
				}else if(i-b.dice1-b.dice2>-1 && b.checkersSpots[i-b.dice1-b.dice2][1]==ID.Player2.getValue() 
					&& b.checkersSpots[i-b.dice1-b.dice2][0]!=2 && tavli.allowedToMove(i-b.dice1-b.dice2, i)){
						if(tavli.allowedToMove(i-b.dice1-b.dice2, i-b.dice1)){
							roundForComputer(i-b.dice1-b.dice2, i-b.dice1);
							roundForComputer(i-b.dice1, i);
						}else{
							roundForComputer(i-b.dice1-b.dice2, i-b.dice2);
							roundForComputer(i-b.dice2, i);
						}
						flag=false;
				}else if(i+b.dice1+b.dice2<24 && b.checkersSpots[i+b.dice1+b.dice2][1]==ID.Player2.getValue()
					&& tavli.allowedToMove(i, i+b.dice1+b.dice2)){
						if(tavli.allowedToMove(i, i+b.dice1)){
							roundForComputer(i, i+b.dice1);
							roundForComputer(i+b.dice1, i+b.dice1+b.dice2);
						}else{
							roundForComputer(i, i+b.dice2);
							roundForComputer(i+b.dice2, i+b.dice1+b.dice2);
						}
						flag=false;
				}else if(tavli.maximumMoves()==4){
					if(i-2*b.dice1>-1 && b.checkersSpots[i-2*b.dice1][1]==ID.Player2.getValue() 
						&& b.checkersSpots[i-2*b.dice1][0]!=2 && tavli.allowedToMove(i-2*b.dice1, i)){
							roundForComputer(i-2*b.dice1, i-b.dice1);
							roundForComputer(i-b.dice1, i);
							flag=false;
					}else if(i+2*b.dice1<24 && b.checkersSpots[i+2*b.dice1][1]==ID.Player2.getValue() 
						&& tavli.allowedToMove(i, i+2*b.dice1)){
							roundForComputer(i, i+b.dice1);
							roundForComputer( i+b.dice1, i+2*b.dice1);
							flag=false;
					}
					else if(i-3*b.dice1>-1 && b.checkersSpots[i-3*b.dice1][1]==ID.Player2.getValue() 
						&& b.checkersSpots[i-3*b.dice1][0]!=2 && tavli.allowedToMove(i-3*b.dice1, i)){
							roundForComputer(i-3*b.dice1, i-2*b.dice1);
							roundForComputer(i-2*b.dice1, i-b.dice1);
							roundForComputer(i-b.dice1, i);
							flag=false;
					}else if(i+3*b.dice1<24 && b.checkersSpots[i+3*b.dice1][1]==ID.Player2.getValue() 
							&& tavli.allowedToMove(i, i+3*b.dice1)){
							roundForComputer(i, i+b.dice1);
							roundForComputer(i+b.dice1,  i+2*b.dice1);
							roundForComputer(i+2*b.dice1, i+3*b.dice1);
							flag=false;
					}else if(i-4*b.dice1>-1 && b.checkersSpots[i-4*b.dice1][1]==ID.Player2.getValue() 
						&& b.checkersSpots[i-4*b.dice1][0]!=2 && tavli.allowedToMove(i-4*b.dice1, i)){
							roundForComputer(i-4*b.dice1, i-3*b.dice1);
							roundForComputer(i-3*b.dice1, i-2*b.dice1);
							roundForComputer(i-2*b.dice1, i-b.dice1);
							roundForComputer(i-b.dice1, i);
							flag=false;
					}else if(i+4*b.dice1<24 && b.checkersSpots[i+4*b.dice1][1]==ID.Player2.getValue() 
						&& b.checkersSpots[i+4*b.dice1][0]!=2 && tavli.allowedToMove(i, i+4*b.dice1)){
							roundForComputer(i, i+b.dice1);
							roundForComputer(i+b.dice1,  i+2*b.dice1);
							roundForComputer(i+2*b.dice1, i+3*b.dice1);
							roundForComputer(i+3*b.dice1, i+4*b.dice1);
							flag=false;
					}
				}
			}
			i--;
		}
		return flag;
	}
	
	public void playAMoveInPortes(){
		if(level==1){
			easyMode();
		}else if(level==2){
			mediumMode();
		}else{
			hardMode();
		}
	}
	
	public boolean makePossibleMove(){
		int i=0;
		boolean flag=true;
		while(flag && i<24){
			if(b.checkersSpots[i][1]==ID.Player2.getValue()){
				if(tavli.allowedToMove(i, i+b.dice1)){
					roundForComputer(i,i+b.dice1);
					flag=false;
				}
				else if(tavli.allowedToMove(i, i+b.dice2)){
					roundForComputer(i,i+b.dice2);
					flag=false;
				}
			}
			i++;
		}
		if(tavli.isEndTurn()) flag=false;
		
		return flag;
	}
	
	public void roundForComputer(int spot1, int spot2){
		
		waitAMoment();
		tavli.makeaMove(spot1, spot2);
		if(tavli.checkersInCase1==15)
			tavli.giveResults(false);
		else if(tavli.checkersInCase2==15)
			tavli.giveResults(true);
		else
			tavli.removePlayedDices(spot1,spot2);
		b.paintImmediately(0,0,Window.WIDTH,Window.HEIGHT);
	}
	
	public void waitAMoment(){
		
		long t= System.currentTimeMillis();
		long end = t+1000;
		
		while(System.currentTimeMillis() < end) {
			
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
