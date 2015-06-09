import javax.swing.JOptionPane;


public class Assoduo extends Tavli{

	private int phase1;
	private int phase2;
	private boolean assoduo;

	public Assoduo(Board b) {
		super(b);	
		assoduo=false;
		setDefaultCheckersSpots();
		setDefaultMoves();
		doubleDice=0;
		/*System.out.println("Moves1");
		printMovesArray(moves1);
		System.out.println("Moves2");
		printMovesArray(moves2);*/
	}

	//Αρχικές θέσεις στις πόρτες
	public void setDefaultCheckersSpots(){
		
		prisoners1=15;
		prisoners2=15;
		
		tempPrisoners1=15;
		tempPrisoners1=15;
		checkPhase(false);
		checkPhase(true);
		
	}

	//Αρχικοποίηση των πινάκων moves1 και moves2
	public void setDefaultMoves(){
		for(int i=0; i<24; i++){
			for (int j=0; j<6; j++){

				if(i-j-1>-1)
					moves1[i][j]=b.checkersSpots[i-j-1][1]!=2 || b.checkersSpots[i-j-1][0]==1;
				else 
					moves1[i][j]=false;
				if(i+j+1<24)
					moves2[i][j]=b.checkersSpots[i+j+1][1]!=1 || b.checkersSpots[i+j+1][0]==1;
				else
					moves2[i][j]=false;
			}
		}
		for(int i=0; i<6; i++){
			moves1[24][i]=b.checkersSpots[23-i][1]!=2 || b.checkersSpots[23-i][0]==1;
			moves2[24][i]=b.checkersSpots[i][1]!=1 || b.checkersSpots[i][0]==1;
		}
		for(int i=0; i<25; i++){
			for (int j=0; j<6; j++){
				tempMoves1[i][j]=moves1[i][j];
				tempMoves2[i][j]=moves2[i][j];
			}
		}
	}

	public void printMovesArray(boolean[][] array){
		for(int i=0; i<25; i++){
			System.out.print(i+":  [ ");
			for(int j=0; j<6; j++){
				System.out.print(array[i][j]+" ");
			}
			System.out.println("]");
		}

	}

	public boolean allowedToMove(int spot1, int spot2) {
		int dice1 = b.dice1;
		int dice2 = b.dice2;

		if(whoseTurnIs()==ID.Player1){
			if(spot1-spot2>=0){

				if(readyForCase() && spot1-spot2==0){
					if(allowedToMoveInCase(spot1))
						return true;
				}else{
					if(allowedToMoveSpecial(spot1, spot2)){
						if(spot1-spot2==dice1 && roundsOfDice1!=0)
							return (moves1[spot1][dice1-1]);
						else if(spot1-spot2==dice2 && roundsOfDice2!=0)
							return (moves1[spot1][dice2-1]);
					}
				}
			}
		}
		else if(spot1-spot2<=0){

			if(readyForCase() && spot1-spot2==0){
				if(allowedToMoveInCase(spot1))
					return true;
			}else{
				if(allowedToMoveSpecial(spot1, spot2)){
					if(spot2-spot1==dice1 && roundsOfDice1!=0){
						if(spot1==-1) spot1=24;
						return (moves2[spot1][dice1-1]);
					}
					else if(spot2-spot1==dice2 && roundsOfDice2!=0){
						if(spot1==-1) spot1=24;
						return (moves2[spot1][dice2-1]);
					}
				}
			}
		}
		return false;
	}

	public boolean allowedToMoveInCase(int spot){
		int dice1 = b.dice1;
		int dice2 = b.dice2;

		if(whoseTurnIs()==ID.Player1){
			if((spot+1==dice1 && roundsOfDice1!=0) || (spot+1==dice2 && roundsOfDice2!=0))
				return true;

		}else{
			if((24-spot==dice1 && roundsOfDice1!=0) || (24-spot==dice2 && roundsOfDice2!=0))
				return true;
		}

		return false;
	}
	
	public boolean allowedToMoveSpecial(int spot1, int spot2){
		
		int dice1=b.dice1;
		int dice2=b.dice2;
		
		if(whoseTurnIs()==ID.Player1){
			if(phase1==0 && (dice1==dice2 || dice1+dice2==3))
				return true;
			else if(isInArea(spot1,5-phase1) && (isInArea(spot2,5-phase1) || isInArea(spot2,4-phase1)) && phase1>0 && phase1<4 )
				return true;
		}else{
			if(phase2==0 && (dice1==dice2 || dice1+dice2==3))
				return true;
			else if(isInArea(spot1,phase2) && (isInArea(spot2,phase2) || isInArea(spot2,phase2+1)) && phase2>0 && phase2<4)
				return true;
		}
		return false;
	}
	
	public void makeaMove(int spot1, int spot2) {
		if(whoseTurnIs()==ID.Player1){
			//Aν είναι κίνηση για να μαζέψει
			if(spot1==spot2){
				removeChecker(spot1);
				addCheckerInCase(getTurn());
				
			}else{
				//’μα είναι μια κανονική κίνηση
				if(prisoners1==0){
					removeChecker(spot1);
				}//Αν η κίνηση που κάνει είναι να ελευθερώσει ένα φυλακισμένο πούλι
				else{
					prisoners1--;
				}
				addChecker(spot2);
			}
		}else{
			//Aν είναι κίνηση για να μαζέψει
			if(prisoners2==0 && readyForCase() && spot1==spot2){
				removeChecker(spot1);
				addCheckerInCase(getTurn());
			}else{
				//’μα είναι μια κανονική κίνηση
				if(prisoners2==0){
					removeChecker(spot1);
				}//Αν η κίνηση που κάνει είναι να ελευθερώσει ένα φυλακισμένο πούλι
				else{
					prisoners2--;
				}
				addChecker(spot2);
			}
		}

	}
	
	public void removeChecker(int spot){
		b.checkersSpots[spot][0]--;
		if(whoseTurnIs()==ID.Player1 && b.checkersSpots[spot][0]==1){
			int count=0;
			for(int i=spot-1; i>spot-7 && i>-2; i--){
				int position=i;
				if(i==-1) position=24;
				moves2[position][count]=true;
				count++;
			}
		}else if(b.checkersSpots[spot][0]==1){
			int count=0;
			for(int i=spot+1; i<spot+7 && i<25; i++){
				moves1[i][count]=true;
				count++;
			}
		}else if(b.checkersSpots[spot][0]==0){
			b.checkersSpots[spot][1]=ID.None.getValue();
		}
	}
	
	public void addChecker(int spot){
		int player = whoseTurnIs().getValue();
		if(b.checkersSpots[spot][1]==ID.None.getValue()){
			b.checkersSpots[spot][1]=player;
			b.checkersSpots[spot][0]++;
		}else if(player==1 && b.checkersSpots[spot][0]==1){
			if(b.checkersSpots[spot][1]==ID.Player2.getValue()){
				prisoners2++;
				b.checkersSpots[spot][1]=ID.Player1.getValue();
			}
			else{
				b.checkersSpots[spot][0]++;
				int count=0;
				for(int i=spot-1; i>spot-7 && i>-2; i--){
					int position=i;
					if(i==-1) position=24;
					moves2[position][count]=false;
					count++;
				}
			}
		}else if(b.checkersSpots[spot][0]==1){
			if(b.checkersSpots[spot][1]==ID.Player1.getValue()){
				prisoners1++;
				b.checkersSpots[spot][1]=ID.Player2.getValue();
			}
			else{
				b.checkersSpots[spot][0]++;
				int count=0;
				for(int i=spot+1; i<spot+7 && i<25; i++){
					moves1[i][count]=false;
					count++;
				}
			}
		}else{
			b.checkersSpots[spot][0]++;
		}
	}
	
	//Επιστρέφει πόσες κινήσεις μπορεί να κάνει ο παίκτης
	public int maximumMoves(){
		int dice1,dice2;
		dice1 = b.dice1;
		dice2 = b.dice2;

		if(dice1==dice2) return(4);
		else return(2);
	}

	//Ξεκινά ο γύρος του παίκτη που έχει σειρά
	public void round(int spot1, int spot2) {

		checkPhase(getTurn());
		if(roundsOfDice1>0 || roundsOfDice2>0){
			if(allowedToMove(spot1,spot2)){
				makeaMove(spot1, spot2);
				if(checkersInCase1==15)
					giveResults(false);
				else if(checkersInCase2==15)
					giveResults(true);
				else
					removePlayedDices(spot1,spot2);
			}
		}

	}

	//Αφαιρεί τα ζάρια που έχουν παιχτεί
	public void removePlayedDices(int spot1, int spot2){
		int dice1=b.dice1;
		int dice2=b.dice2;		

		if(spot1==spot2){
			if(whoseTurnIs()==ID.Player1){
				if(maximumMoves()==4){
					if(spot1+1==dice1 || spot1+1==2*dice1 || spot1+1==3*dice1 || spot1+1==4*dice1)
						roundsOfDice1-=(spot1+1)/dice1;
					else
						roundsOfDice1--;
				}else if(dice1==spot1+1 && roundsOfDice1>0)
					roundsOfDice1--;
				else if(dice2==spot1+1 && roundsOfDice2>0){
					roundsOfDice2--;
				}
				else if(dice1+dice2==spot1+1 && roundsOfDice1>0 && roundsOfDice2>0){
					roundsOfDice1=0;
					roundsOfDice2=0;
				}
				else{
					if(dice1*roundsOfDice1>dice2*roundsOfDice2){
						roundsOfDice1=0;
					}else
						roundsOfDice2=0;
				}
			}else{
				if(maximumMoves()==4){
					if(24-spot1==dice1 || 24-spot1==2*dice1 || 24-spot1==3*dice1 || 24-spot1==4*dice1)
						roundsOfDice1-=(24-spot1)/dice1;
					else
						roundsOfDice1--;
				}else if(dice1==24-spot1 && roundsOfDice1>0)
					roundsOfDice1--;
				else if(dice2==24-spot1 && roundsOfDice2>0){
					roundsOfDice2--;
				}
				else if(dice1+dice2==24-spot1 && roundsOfDice1>0 && roundsOfDice2>0){
					roundsOfDice1=0;
					roundsOfDice2=0;
				}
				else{
					if(dice1*roundsOfDice1>dice2*roundsOfDice2){
						roundsOfDice1=0;
					}else
						roundsOfDice2=0;
				}
			}
		}else{
			int num=Math.abs(spot1-spot2);
			if(maximumMoves()==4){
				roundsOfDice1-=num/dice1;
			}
			else if(num==dice1){
				roundsOfDice1=0;
			}
			else if(num==dice2){
				roundsOfDice2=0;
			}
			else{
				roundsOfDice1=0;
				roundsOfDice2=0;
			}
		}
		checkPhase(getTurn());
		endTurn=!hasMoves();
		System.out.println(phase1+ " L " + phase2);
		if(roundsOfDice1+roundsOfDice2>0 && endTurn){
			b.repaint();
			JOptionPane.showMessageDialog(null, "You can't move any more!");
		}
		if(assoduo && endTurn){
			if(b.dice1+b.dice2==3){
				b.repaint();
				giveDices();
				setRoundsOfDices();
				assoduo=false;
			}
			else{
				getBoard().setDice1(1);
				getBoard().setDice2(2);
				b.setImgDice1(1);
				b.setImgDice2(2);
				b.repaint();
				checkPhase(getTurn());
				endTurn=!hasMoves();
				if(roundsOfDice1+roundsOfDice2>0 && endTurn){
					b.repaint();
					JOptionPane.showMessageDialog(null, "You can't move!");
				}
				setRoundsOfDices();
				assoduo=false;
			}
		}
	}
	
	//Ελέγχει αν ο παίκτης έχει άλλες κινήσεις
	public boolean hasMoves(){
		if(roundsOfDice1!=0 || roundsOfDice2!=0){
			if(whoseTurnIs()==ID.Player1){
				
				if(prisoners1==0){
					if(roundsOfDice1!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMove(i, i-b.dice1) && i-b.dice1>-1)
								return true;
						}
					}
					if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMove(i, i-b.dice2) && i-b.dice2>-1)
								return true;
						}
					}
					if(readyForCase()){
						for(int i=0; i<6; i++){
							if (b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMoveInCase(i))
								return true;
						}
					}
				}else{
					if((allowedToMove(24, 24-b.dice1) && roundsOfDice1>0) || (allowedToMove(24, 24-b.dice2) && roundsOfDice2>0))
						return true;
				}
			}else{
				if(prisoners2==0){	
					if(roundsOfDice1!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMove(i, i+b.dice1) && i+b.dice1<24)
								return true;
						}
					}
					if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMove(i, i+b.dice2) && i+b.dice2<24)
								return true;
						}
					}
					if(readyForCase() ){
						for(int i=18; i<24; i++){
							if (b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMoveInCase(i))
								return true;
						}
					}
				}else{
					if((allowedToMove(-1, -1+b.dice1) && roundsOfDice1>0) || (allowedToMove(-1, -1+b.dice2) && roundsOfDice2>0))
						return true;
				}
			}
		}
		return false;
	}
	
	//Τοποθετεί αρχικές τιμές στον αριθμό των φορών που θα παιχτεί κάθε ζάρι
	public void setRoundsOfDices(){
		checkPhase(getTurn());
		if(b.dice1+b.dice2==3 && !assoduo){
			int reply = JOptionPane.showConfirmDialog(null, "Do you want to play 1-2 now?", "1-2", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	assoduo=true;
	        }
	        else {
	        	assoduo=true;
	        	giveDices();
	        }
		}
		if(maximumMoves()==4){
			roundsOfDice1=4;
			roundsOfDice2=0;
		}
		else{
			roundsOfDice1=1;
			roundsOfDice2=1;
		}
	}

	//Ελέγχει σε ποια φάση βρίσκεται ο παίκτης
	public void checkPhase(boolean player2){
		if(!player2){
			if(prisoners1!=0)
				phase1=0;
			else if(checkersIsInArea(4)!=0)
				phase1=1;
			else if(checkersIsInArea(3)!=0)
				phase1=2;
			else if(checkersIsInArea(2)!=0)
				phase1=3;
		}else{
			if(prisoners2!=0)
				phase2=0;
			else if(checkersIsInArea(1)!=0)
				phase2=1;
			else if(checkersIsInArea(2)!=0)
				phase2=2;
			else if(checkersIsInArea(3)!=0)
				phase2=3;
		}
	}
	
	public boolean isInArea(int spot, int area){
		if(area==0 && (spot==24 || spot==-1))
			return true;
		else if(area!=0){
			for(int i=(area-1)*6; i<(area)*6; i++){
				if(spot==i)
					return true;
			}
		}
		return false;
	}
	
	//Μετράει τα πούλια του παίκτη που είναι σε μία απ' τις τέσσερις περιοχές
	//Η περιοχή καθορίζεται από το όρισμα num
	public int checkersIsInArea(int num){
		int sum=0;
		for(int i=(num-1)*6; i<(num)*6; i++){
			if(b.checkersSpots[i][1]==whoseTurnIs().getValue())
				sum+=b.checkersSpots[i][0];
		}
		return sum;
	}
	
	public void giveDices(){
		int newDice;
		String dice;
		do{
			dice = JOptionPane.showInputDialog("Pls Give a number for dice1(allowed:1-6): ");
		}while(!dice.equals("1") && !dice.equals("2") && !dice.equals("3") && !dice.equals("4") && !dice.equals("5") && !dice.equals("6"));
			
		newDice=Integer.parseInt(dice);
		getBoard().setDice1(newDice);
		getBoard().setDice2(newDice);
		b.setImgDice1(newDice);
		b.setImgDice2(newDice);
		b.repaint();
		if(roundsOfDice1+roundsOfDice2>0 && endTurn){
			b.repaint();
			JOptionPane.showMessageDialog(null, "You can't move!");
		}
	}
	
	public boolean readyForCase(){
		if(whoseTurnIs()==ID.Player1){
			return (checkersIsInArea(1)+checkersInCase1==15);
		}else {
			return (checkersIsInArea(4)+checkersInCase2==15);
		}
	}
	
	public void switchTurn(){
		if(b.dice1!=b.dice2 && b.dice1+b.dice2!=3)
			turn=!turn;
		
	}
	
	public boolean opponentInΗomeBoard(boolean player2){
		if(!player2){
			for(int i=0; i<6; i++){
				if(b.checkersSpots[i][1]==ID.Player2.getValue())
					return true;	
			}	
		}else{
			for(int i=18; i<24; i++){
				if(b.checkersSpots[i][1]==ID.Player1.getValue())
					return true;	
			}
		}
		return false;
	}
	
}

