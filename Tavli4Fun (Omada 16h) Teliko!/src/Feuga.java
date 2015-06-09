import javax.swing.JOptionPane;


public class Feuga extends Tavli{

	private boolean phase1,phase2;

	public Feuga(Board b) {
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
	}

	//Αρχικές θέσεις στις πόρτες
	public void setDefaultCheckersSpots(){
		b.checkersSpots[11][0]=15;
		b.checkersSpots[11][1]=2;
		b.checkersSpots[23][0]=15;
		b.checkersSpots[23][1]=1;
		
		b.temp[11][0]=15;
		b.temp[11][1]=2;
		b.temp[23][0]=15;
		b.temp[23][1]=1;
	}

	//Αρχικοποίηση των πινάκων moves1 και moves2
	public void setDefaultMoves(){
		for(int i=0; i<24; i++){
			for (int j=0; j<6; j++){

				if(i-j-1>-1)
					moves1[i][j]=b.checkersSpots[i-j-1][1]!=2;
				else 
					moves1[i][j]=false;
			}
		}
		for(int i=0; i<12; i++){
			for (int j=0; j<6; j++){
				int position=i-j-1;
				if (position<0) position+=24;
					moves2[i][j]=b.checkersSpots[position][1]!=1;
			}
		}
		for(int i=12; i<24; i++){
			for (int j=0; j<6; j++){

				if(i-j-1>11)
					moves2[i][j]=b.checkersSpots[i-j-1][1]!=1;
				else 
					moves2[i][j]=false;
			}
		}
		for(int i=0; i<6; i++){
			moves1[24][i]=false;
			moves2[24][i]=false;
		}
		
		for(int i=0; i<25; i++){
			for (int j=0; j<6; j++){
				tempMoves1[i][j]=moves1[i][j];
				tempMoves2[i][j]=moves2[i][j];
			}
		}
	}

	public void printMovesArray(boolean[][] array){
		for(int i=0; i<24; i++){
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
				if(spot1-spot2==0 && readyForCase()){
					if(allowedToMoveInCase(spot1))
						return true;
				}else{
					if(spot1-spot2==dice1 && roundsOfDice1!=0)
						return (moves1[spot1][dice1-1]);
					else if(spot1-spot2==dice2 && roundsOfDice2!=0)
						return (moves1[spot1][dice2-1]);
					else if(spot1-spot2==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
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
		else{
			if(spot1-spot2==0 && readyForCase()){
				if(allowedToMoveInCase(spot1))
					return true;
			}else{
				int distance=spot1-spot2; //Για την περίπτωση που έχουμε κίνηση από τη 0 θέση στην 23
				if(distance<0 && spot1<12 && spot2>11){
					distance=24+distance;
				}

				if(distance==dice1 && roundsOfDice1!=0)
					return (moves2[spot1][dice1-1]);
				else if(distance==dice2 && roundsOfDice2!=0)
					return (moves2[spot1][dice2-1]);
				else{ 
					int position1=spot1-dice1;
					if(position1<0) position1+=24;					
					if(distance==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0){
						int position2=spot1-dice2;
						if(position2<0) position2+=24;	
						return ((moves2[spot1][dice1-1] && moves2[position1][dice2-1]) || (moves2[spot1][dice2-1] && moves2[position2][dice1-1]));
					}else if (distance==2*dice1  && roundsOfDice1>1){
						return (moves2[spot1][dice1-1] && moves2[position1][dice1-1]);
					}else if(distance==3*dice1  && roundsOfDice1>2){
						int position2=spot1-2*dice1;
						if(position2<0) position2+=24;
						return (moves2[spot1][dice1-1] && moves2[position1][dice1-1] && moves2[position2][dice1-1]);
					}else if(distance==4*dice1  && roundsOfDice1>3){
						int position2=spot1-2*dice1;
						if(position2<0) position2+=24;
						int position3=spot1-3*dice1;
						if(position3<0) position3+=24;
						return (moves2[spot1][dice1-1] && moves2[position1][dice1-1] && moves2[position2][dice1-1] && moves2[position3][dice1-1]);
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
			if((spot-11==dice1 && roundsOfDice1!=0) || (spot-11==dice2 && roundsOfDice2!=0))
				return true;
			else if(spot-11==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
				return (moves2[spot][dice1-1] || moves2[spot][dice2-1]);
			else if (spot-11==2*dice1  && roundsOfDice1>1)
				return (moves2[spot][dice1-1]);
			else if(spot-11==3*dice1  && roundsOfDice1>2)
				return (moves2[spot][dice1-1] && moves2[spot-dice1][dice1-1]);
			else if(spot-11==4*dice1  && roundsOfDice1>3)
				return (moves2[spot][dice1-1] && moves2[spot-dice1][dice1-1] && moves2[spot-2*dice1][dice1-1]);
			else if((spot-11<dice1*roundsOfDice1 && spot-11<dice1) || spot-11<dice2*roundsOfDice2){
				int sum=0;
				for(int i=spot+1; i<18; i++){
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

		//Aν είναι κίνηση για να μαζέψει
		if(spot1==spot2){
			removeChecker(spot1);
			addCheckerInCase(getTurn());
		}else{
			//’μα είναι μια κανονική κίνηση
			removeChecker(spot1);
			addChecker(spot2);
			if(whoseTurnIs()==ID.Player1){
				if(!phase1){
					phase1=checkPhase();
					for(int i=0; i<6; i++){
						moves1[23][i]=phase1 && b.checkersSpots[22-i][1]!=ID.Player2.getValue();
					}
				}
			}else{
				if(!phase2){
					phase2=checkPhase();
					for(int i=0; i<6; i++){
						moves2[11][i]=phase2 && b.checkersSpots[10-i][1]!=ID.Player1.getValue();
					}
				}
			}

		}
	}

	public void addChecker(int spot){
		int player = whoseTurnIs().getValue();
		if(b.checkersSpots[spot][1]==ID.None.getValue()){
			b.checkersSpots[spot][1]=player;
			if(player==1){
				int count=0;
				int position;
				for(int i=spot+1; i<spot+7; i++){
					position=i;
					if(position>23) position=Math.abs(24-position);
					moves2[position][count]=false;
					count++;
				}
			}else{
				int count=0;
				for(int i=spot+1; i<spot+7 && i<24; i++){
					moves1[i][count]=false;
					count++;
				}
			}
		}
		b.checkersSpots[spot][0]++;
	}

	public void removeChecker(int spot){
		b.checkersSpots[spot][0]--;
		int player = whoseTurnIs().getValue();
		if(b.checkersSpots[spot][0]==0){
			b.checkersSpots[spot][1]=ID.None.getValue();
			if(player==1){
				int count=0;
				int position;
				for(int i=spot+1; i<spot+7 && (spot>11 || i<12); i++){
					position=i;
					if(24-position<=0) position=Math.abs(24-position);
					moves2[position][count]=true;
					count++;
				}
			}else{
				int count=0;
				for(int i=spot+1; i<spot+7 && i<24; i++){
					moves1[i][count]=true;
					count++;
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
					if(spot1-11==dice1 || spot1-11==2*dice1 || spot1-11==3*dice1 || spot1-11==4*dice1)
						roundsOfDice1-=(24-spot1)/dice1;
					else
						roundsOfDice1--;
				}else if(dice1==spot1-11 && roundsOfDice1>0)
					roundsOfDice1--;
				else if(dice2==spot1-11 && roundsOfDice2>0){
					roundsOfDice2--;
				}
				else if(dice1+dice2==spot1-11 && roundsOfDice1>0 && roundsOfDice2>0){
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
			if(whoseTurnIs()==ID.Player2 && spot1<12 && spot2>11){
				num=24-num;
			}
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
		endTurn=!b.getTavli().hasMoves();
		if(roundsOfDice1+roundsOfDice2>0 && endTurn){
			b.repaint();
			if(!turn)
				JOptionPane.showMessageDialog(null, b.getGame().name1+" can't move any more!");
			else if(!b.getGame().name2.equals("Computer"))
				JOptionPane.showMessageDialog(null, b.getGame().name2+" can't move any more!");
		}
	}

	//Ελέγχει αν ο παίκτης έχει άλλες κινήσεις
		public boolean hasMoves(){
			if(roundsOfDice1!=0 || roundsOfDice2!=0){
				if(whoseTurnIs()==ID.Player1){
					if(roundsOfDice1!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][0]>0 && b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMove(i, i-b.dice1) && i-b.dice1>-1 )
								return true;
						}
					}
					if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							if(b.checkersSpots[i][0]>0 && b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMove(i, i-b.dice2) && i-b.dice2>-1)
								return true;
						}
					}
					if(readyForCase()){
						for(int i=0; i<6; i++){
							if (b.checkersSpots[i][0]>0 && b.checkersSpots[i][1]==ID.Player1.getValue() && allowedToMoveInCase(i))
								return true;
						}
					}
				}else{
					if(roundsOfDice1!=0){
						for(int i=0; i<24; i++){
							int position=i-b.dice1;
							if (position<0) position=24+position;
							if(b.checkersSpots[i][0]>0 && b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMove(i, position))
								return true;
						}
					}
					if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							int position=i-b.dice2;
							if (position<0) position=24+position;
							if(b.checkersSpots[i][0]>0 && b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMove(i, position))
								return true;
						}
					}
					if(readyForCase() ){
						for(int i=12; i<18; i++){
							if (b.checkersSpots[i][0]>0 && b.checkersSpots[i][1]==ID.Player2.getValue() && allowedToMoveInCase(i))
								return true;
						}
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

	public boolean checkPhase(){
		int sum = 0;

		if(whoseTurnIs()==ID.Player1){
			for(int i=12;i<24;i++){
				if(b.checkersSpots[i][1]==ID.Player1.getValue()){
					sum= b.checkersSpots[i][0]+sum ;
				}
			}
		}
		else{
			for(int i=0;i<12;i++){
				if(b.checkersSpots[i][1]==ID.Player2.getValue()){
					sum= b.checkersSpots[i][0]+sum ;
				}
			}
		}
		if(sum==15)
			return (false) ;
		else
			return (true);
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
			for(int i=12;i<18;i++){
				if(b.checkersSpots[i][1]==2)
					rdyToPick2=b.checkersSpots[i][0]+rdyToPick2;
			}
			if (rdyToPick2 + checkersInCase2==15) 
				return(true);
			else 
				return (false);
		}
	}

	public boolean opponentInΗomeBoard(boolean player2){
		if(!player2){
			for(int i=0; i<6; i++){
				if(b.checkersSpots[i][1]==ID.Player2.getValue())
					return true;	
			}	
		}else{
			for(int i=12; i<18; i++){
				if(b.checkersSpots[i][1]==ID.Player1.getValue())
					return true;	
			}
		}
		return false;
	}
	
}
