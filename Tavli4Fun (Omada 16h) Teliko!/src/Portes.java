import javax.swing.JOptionPane;


public class Portes extends Tavli{

	private AI ai;
	
	public Portes(Board b) {
		super(b);		
		setDefaultCheckersSpots();
		setDefaultMoves();
		if(Settings.doubledice)
			doubleDice=1;
		else
			doubleDice=0;
		/*System.out.println("Moves1");
		printMovesArray(moves1);
		System.out.println("Moves2");
		printMovesArray(moves2);*/
		if(b.getGame().name2.equals("Computer")){
			ai = new AI(Mode.level,b,this);
			doubleDice=0;
			if(whoseTurnIs()==ID.Player2)
				ai.playAMoveInPortes();
		}
	}

	//Αρχικές θέσεις στις πόρτες
	public void setDefaultCheckersSpots(){		
		
		b.checkersSpots[0][0]=2;
		b.checkersSpots[0][1]=2;
		b.checkersSpots[5][0]=5;
		b.checkersSpots[5][1]=1;
		b.checkersSpots[7][0]=3;
		b.checkersSpots[7][1]=1;
		b.checkersSpots[11][0]=5;
		b.checkersSpots[11][1]=2;
		b.checkersSpots[12][0]=5;
		b.checkersSpots[12][1]=1;
		b.checkersSpots[16][0]=3;
		b.checkersSpots[16][1]=2;
		b.checkersSpots[18][0]=5;
		b.checkersSpots[18][1]=2;
		b.checkersSpots[23][0]=2;
		b.checkersSpots[23][1]=1;
		
		b.temp[0][0]=2;
		b.temp[0][1]=2;
		b.temp[5][0]=5;
		b.temp[5][1]=1;
		b.temp[7][0]=3;
		b.temp[7][1]=1;
		b.temp[11][0]=5;
		b.temp[11][1]=2;
		b.temp[12][0]=5;
		b.temp[12][1]=1;
		b.temp[16][0]=3;
		b.temp[16][1]=2;
		b.temp[18][0]=5;
		b.temp[18][1]=2;
		b.temp[23][0]=2;
		b.temp[23][1]=1;
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
				if(prisoners1==0 || spot1==24){
					if(readyForCase() && spot1-spot2==0){
						if(allowedToMoveInCase(spot1))
							return true;
					}else{
						if(spot1-spot2==dice1 && roundsOfDice1!=0)
							return (moves1[spot1][dice1-1]);
						else if(spot1-spot2==dice2 && roundsOfDice2!=0)
							return (moves1[spot1][dice2-1]);
						else if(prisoners1<=1){
							 if(spot1-spot2==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
								return ((moves1[spot1][dice1-1] && moves1[spot1-dice1][dice2-1]) || (moves1[spot1][dice2-1] && moves1[spot1-dice2][dice1-1]));
							else if (spot1-spot2==2*dice1  && roundsOfDice1>1)
								return (moves1[spot1][dice1-1] && moves1[spot1-dice1][dice1-1]);
							else if(spot1-spot2==3*dice1  && roundsOfDice1>2)
								return (moves1[spot1][dice1-1] && moves1[spot1-dice1][dice1-1] && moves1[spot1-2*dice1][dice1-1]);
							else if(spot1-spot2==4*dice1  && roundsOfDice1>3)
								return (moves1[spot1][dice1-1] && moves1[spot1-dice1][dice1-1] && moves1[spot1-2*dice1][dice1-1] && moves1[spot1-3*dice1][dice1-1]);
						}
					}
				}
			}
		}
		else if(spot1-spot2<=0){

			if(prisoners2==0 || spot1==-1){
				if(readyForCase() && spot1-spot2==0){
					if(allowedToMoveInCase(spot1))
						return true;
				}else{
					int position=spot1; //Για την περίπτωση που έχουμε prisoner (δεν θα μπορούσαμε να έχουμε θέση -1 στον πίνακα)
					//κι έτσι η spot2 θα δείχνει στη θέση 24.
					if(spot1==-1) position=24;

					if(spot2-spot1==dice1 && roundsOfDice1!=0)
						return (moves2[position][dice1-1]);
					else if(spot2-spot1==dice2 && roundsOfDice2!=0)
						return (moves2[position][dice2-1]);
					else if(prisoners2<=1){
						if(spot2-spot1==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
							return ((moves2[position][dice1-1] && moves2[spot1+dice1][dice2-1]) || (moves2[position][dice2-1] && moves2[spot1+dice2][dice1-1]));
						else if (spot2-spot1==2*dice1  && roundsOfDice1>1)
							return (moves2[position][dice1-1] && moves2[spot1+dice1][dice1-1]);
						else if(spot2-spot1==3*dice1  && roundsOfDice1>2)
							return (moves2[position][dice1-1] && moves2[spot1+dice1][dice1-1] && moves2[spot1+2*dice1][dice1-1]);
						else if(spot2-spot1==4*dice1  && roundsOfDice1>3)
							return (moves2[position][dice1-1] && moves2[spot1+dice1][dice1-1] && moves2[spot1+2*dice1][dice1-1] && moves2[spot1+3*dice1][dice1-1]);
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
			else if(spot+1==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
				return (moves1[spot][dice1-1] || moves1[spot][dice2-1]);
			else if (spot+1==2*dice1  && roundsOfDice1>1)
				return (moves1[spot][dice1-1]);
			else if(spot+1==3*dice1  && roundsOfDice1>2)
				return (moves1[spot][dice1-1] && moves1[spot-dice1][dice1-1]);
			else if(spot+1==4*dice1  && roundsOfDice1>3)
				return (moves1[spot][dice1-1] && moves1[spot-dice1][dice1-1] && moves1[spot-2*dice1][dice1-1]);
			else if((spot+1<dice1*roundsOfDice1 && spot+1<dice1) || spot+1<dice2*roundsOfDice2){
				int sum=0;
				for(int i=spot+1; i<6; i++){
					if(b.checkersSpots[i][1]==ID.Player1.getValue())
						sum+=b.checkersSpots[i][0];
				}
				if(sum==0){
					return true;
				}
			}

		}else{
			if((24-spot==dice1 && roundsOfDice1!=0) || (24-spot==dice2 && roundsOfDice2!=0))
				return true;
			else if(24-spot==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
				return (moves2[spot][dice1-1] || moves2[spot][dice2-1]);
			else if (24-spot==2*dice1  && roundsOfDice1>1)
				return (moves2[spot][dice1-1]);
			else if(24-spot==3*dice1  && roundsOfDice1>2)
				return (moves2[spot][dice1-1] && moves2[spot+dice1][dice1-1]);
			else if(24-spot==4*dice1  && roundsOfDice1>3)
				return (moves2[spot][dice1-1] && moves2[spot+dice1][dice1-1] && moves2[spot+2*dice1][dice1-1]);
			else if((24-spot<dice1*roundsOfDice1 && 24-spot<dice1) || 24-spot<dice2*roundsOfDice2){
				int sum=0;
				for(int i=spot-1; i>17; i--){
					if(b.checkersSpots[i][1]==ID.Player2.getValue())
						sum+=b.checkersSpots[i][0];
				}
				if(sum==0){
					return true;
				}
			}
		}

		return false;
	}

	public void makeaMove(int spot1, int spot2) {
		if(whoseTurnIs()==ID.Player1){
			//Aν είναι κίνηση για να μαζέψει
			if(prisoners1==0 && readyForCase() && spot1==spot2){
				b.checkersSpots[spot1][0]--;
				addCheckerInCase(getTurn());
				if(b.checkersSpots[spot1][0]==1){
					int count=0;
					for(int i=spot1-1; i>spot1-7 && i>-2; i--){
						int position=i;
						if(i==-1) position=24;
						moves2[position][count]=true;
						count++;
					}
				}else if(b.checkersSpots[spot1][0]==0){
					b.checkersSpots[spot1][1]=ID.None.getValue();
				}
			}else{
				//’μα είναι μια κανονική κίνηση
				if(prisoners1==0){
					b.checkersSpots[spot1][0]--;
					if(b.checkersSpots[spot1][0]==0){
						b.checkersSpots[spot1][1]=ID.None.getValue();
					}else if(b.checkersSpots[spot1][0]==1){
						int count=0;
						for(int i=spot1-1; i>spot1-7 && i>-2; i--){
							int position=i;
							if(i==-1) position=24;
							moves2[position][count]=true;
							count++;
						}
					}
				}//Αν η κίνηση που κάνει είναι να ελευθερώσει ένα φυλακισμένο πούλι
				else{
					prisoners1--;
				}
				if(b.checkersSpots[spot2][1]==ID.None.getValue()){
					b.checkersSpots[spot2][1]=ID.Player1.getValue();
					b.checkersSpots[spot2][0]++;
				}else if(b.checkersSpots[spot2][0]==1){
					if(b.checkersSpots[spot2][1]==ID.Player2.getValue()){
						prisoners2++;
						b.checkersSpots[spot2][1]=ID.Player1.getValue();
					}
					else{
						b.checkersSpots[spot2][0]++;
						int count=0;
						for(int i=spot2-1; i>spot2-7 && i>-2; i--){
							int position=i;
							if(i==-1) position=24;
							moves2[position][count]=false;
							count++;
						}

					}
				}else{
					b.checkersSpots[spot2][0]++;
				}
			}
		}else{
			//Aν είναι κίνηση για να μαζέψει
			if(prisoners2==0 && readyForCase() && spot1==spot2){
				b.checkersSpots[spot1][0]--;
				addCheckerInCase(getTurn());
				if(b.checkersSpots[spot1][0]==1){
					int count=0;
					for(int i=spot1+1; i<spot1+7 && i<25; i++){
						moves1[i][count]=true;
						count++;
					}
				}else if(b.checkersSpots[spot1][0]==0){
					b.checkersSpots[spot1][1]=ID.None.getValue();
				}
			}else{
				//’μα είναι μια κανονική κίνηση
				if(prisoners2==0){
					b.checkersSpots[spot1][0]--;
					if(b.checkersSpots[spot1][0]==0){
						b.checkersSpots[spot1][1]=ID.None.getValue();
					}else if(b.checkersSpots[spot1][0]==1){
						int count=0;
						for(int i=spot1+1; i<spot1+7 && i<25; i++){
							moves1[i][count]=true;
							count++;
						}
					}
				}//Αν η κίνηση που κάνει είναι να ελευθερώσει ένα φυλακισμένο πούλι
				else{
					prisoners2--;
				}
				if(b.checkersSpots[spot2][1]==ID.None.getValue()){
					b.checkersSpots[spot2][1]=ID.Player2.getValue();
					b.checkersSpots[spot2][0]++;
				}else if(b.checkersSpots[spot2][0]==1){
					if(b.checkersSpots[spot2][1]==ID.Player1.getValue()){
						prisoners1++;
						b.checkersSpots[spot2][1]=ID.Player2.getValue();
					}
					else{
						b.checkersSpots[spot2][0]++;
						int count=0;
						for(int i=spot2+1; i<spot2+7 && i<25; i++){
							moves1[i][count]=false;
							count++;
						}

					}
				}else{
					b.checkersSpots[spot2][0]++;
				}
			}
		}
	}
	
	public void checkForPrisoners(int spot1, int spot2){
		int dice1 = b.dice1;
		int dice2 = b.dice2;
		if(whoseTurnIs()==ID.Player1){
			if(dice1!=dice2){
				//Έλεγχος για το εάν είναι ένας απ' τους δύο συνδιασμούς απαγορευμένος
				int position=spot1;
				if (position==-1) position=24;
				if((moves1[position][dice1-1]==false && moves1[position][dice2-1]==true) || (moves1[position][dice1-1]==true && moves1[position][dice2-1]==false)){
					if(b.checkersSpots[spot1-dice1][1]==2 && b.checkersSpots[spot1-dice1][0]==1){
						prisoners2++;
						b.checkersSpots[spot1-dice1][0]--;
						b.checkersSpots[spot1-dice1][1]=ID.None.getValue();
					}else if(b.checkersSpots[spot1-dice2][1]==2 && b.checkersSpots[spot1-dice2][0]==1){
						prisoners2++;
						b.checkersSpots[spot1-dice2][0]--;
						b.checkersSpots[spot1-dice2][1]=ID.None.getValue();
					}
				}
			}else if(spot1-2*dice1==spot2){
				if(b.checkersSpots[spot1-dice1][1]==2 && b.checkersSpots[spot1-dice1][0]==1){
					prisoners2++;
					b.checkersSpots[spot1-dice1][0]--;
					b.checkersSpots[spot1-dice1][1]=ID.None.getValue();
				}
			}else if(spot1-3*dice1==spot2){
				if(b.checkersSpots[spot1-dice1][1]==2 && b.checkersSpots[spot1-dice1][0]==1){
					prisoners2++;
					b.checkersSpots[spot1-dice1][0]--;
					b.checkersSpots[spot1-dice1][1]=ID.None.getValue();
				}
				if(b.checkersSpots[spot1-2*dice1][1]==2 && b.checkersSpots[spot1-2*dice1][0]==1){
					prisoners2++;
					b.checkersSpots[spot1-2*dice1][0]--;
					b.checkersSpots[spot1-2*dice1][1]=ID.None.getValue();
				}
			}else if(spot1-4*dice1==spot2){
				if(b.checkersSpots[spot1-dice1][1]==2 && b.checkersSpots[spot1-dice1][0]==1){
					prisoners2++;
					b.checkersSpots[spot1-dice1][0]--;
					b.checkersSpots[spot1-dice1][1]=ID.None.getValue();
				}
				if(b.checkersSpots[spot1-2*dice1][1]==2 && b.checkersSpots[spot1-2*dice1][0]==1){
					prisoners2++;
					b.checkersSpots[spot1-2*dice1][0]--;
					b.checkersSpots[spot1-2*dice1][1]=ID.None.getValue();
				}
				if(b.checkersSpots[spot1-3*dice1][1]==2 && b.checkersSpots[spot1-3*dice1][0]==1){
					prisoners2++;
					b.checkersSpots[spot1-3*dice1][0]--;
					b.checkersSpots[spot1-3*dice1][1]=ID.None.getValue();
				}
			}
		}
		else{
			if(dice1!=dice2){
				//Έλεγχος για το εάν είναι ένας απ' τους δύο συνδιασμούς απαγορευμένος
				int position=spot1;
				if (position==-1) position=24;
				if((moves2[position][dice1-1]==true && moves2[position][dice2-1]==false)||(moves2[position][dice1-1]==false && moves2[position][dice2-1]==true)){
					if(b.checkersSpots[spot1+dice1][1]==1 && b.checkersSpots[spot1+dice1][0]==1){
						prisoners1++;
						b.checkersSpots[spot1+dice1][0]--;
						b.checkersSpots[spot1+dice1][1]=ID.None.getValue();
					}else if(b.checkersSpots[spot1+dice2][1]==1 && b.checkersSpots[spot1+dice2][0]==1){
						prisoners1++;
						b.checkersSpots[spot1+dice2][0]--;
						b.checkersSpots[spot1+dice2][1]=ID.None.getValue();
					}
				}
			}else if(spot1+2*dice1==spot2){
				if(b.checkersSpots[spot1+dice1][1]==1 && b.checkersSpots[spot1+dice1][0]==1){
					prisoners1++;
					b.checkersSpots[spot1+dice1][0]--;
					b.checkersSpots[spot1+dice1][1]=ID.None.getValue();
				}
			}else if(spot1+3*dice1==spot2){
				if(b.checkersSpots[spot1+dice1][1]==1 && b.checkersSpots[spot1+dice1][0]==1){
					prisoners1++;
					b.checkersSpots[spot1+dice1][0]--;
					b.checkersSpots[spot1+dice1][1]=ID.None.getValue();
				}
				if(b.checkersSpots[spot1+2*dice1][1]==1 && b.checkersSpots[spot1+2*dice1][0]==1){
					prisoners1++;
					b.checkersSpots[spot1+2*dice1][0]--;
					b.checkersSpots[spot1+2*dice1][1]=ID.None.getValue();
				}
			}else if(spot1+4*dice1==spot2){
				if(b.checkersSpots[spot1+dice1][1]==1 && b.checkersSpots[spot1+dice1][0]==1){
					prisoners1++;
					b.checkersSpots[spot1+dice1][0]--;
					b.checkersSpots[spot1+dice1][1]=ID.None.getValue();
				}
				if(b.checkersSpots[spot1+2*dice1][1]==1 && b.checkersSpots[spot1+2*dice1][0]==1){
					prisoners1++;
					b.checkersSpots[spot1+2*dice1][0]--;
					b.checkersSpots[spot1+2*dice1][1]=ID.None.getValue();
				}
				if(b.checkersSpots[spot1+3*dice1][1]==1 && b.checkersSpots[spot1+3*dice1][0]==1){
					prisoners1++;
					b.checkersSpots[spot1+3*dice1][0]--;
					b.checkersSpots[spot1+3*dice1][1]=ID.None.getValue();
				}
			}
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

		/*System.out.println("Moves1");
		printMovesArray(moves1);
		System.out.println("Moves2");
		printMovesArray(moves2);*/

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
				checkForPrisoners(spot1,spot2);
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
				checkForPrisoners(spot1,spot2);
			}
		}
		endTurn=!b.getTavli().hasMoves();
		if(roundsOfDice1+roundsOfDice2>0 && endTurn){
			b.repaint();
			if(!turn)
				JOptionPane.showMessageDialog(null, b.getGame().name1+" can't move any more!");
			else
				JOptionPane.showMessageDialog(null, b.getGame().name2+" can't move any more!");
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
					}if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMove(i, i-b.dice2) && i-b.dice2>-1)
								return true;
						}
					}if(readyForCase()){
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
					}if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMove(i, i+b.dice2) && i+b.dice2<24)
								return true;
						}
						
					}if(readyForCase()){
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
		if(maximumMoves()==4){
			roundsOfDice1=4;
			roundsOfDice2=0;
		}
		else{
			roundsOfDice1=1;
			roundsOfDice2=1;
		}
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
	
	public boolean readyForCase(){
		if(whoseTurnIs()==ID.Player1){
			int rdyToPick1=0;
			for(int i=0;i<6;i++){
				if(b.checkersSpots[i][1]==1)
					rdyToPick1= b.checkersSpots[i][0] + rdyToPick1;
			}	
			if (rdyToPick1 + checkersInCase1==15)
				return(true);
			else
				return (false);
		}else {
			int rdyToPick2=0;
			for(int i=18;i<24;i++){
				if(b.checkersSpots[i][1]==2)
					rdyToPick2=b.checkersSpots[i][0]+rdyToPick2;
			}
			if (rdyToPick2 + checkersInCase2==15) 
				return(true);
			else 
				return (false);
		}
	}
	
	public void switchTurn(){
		turn=!turn;
		if(turn && b.getGame().name2.equals("Computer")){
			if(hasMoves())
				ai.playAMoveInPortes();
			else
				JOptionPane.showMessageDialog(null, "Computer can't move!");
		}
	}

	public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}

	
	
	
}
