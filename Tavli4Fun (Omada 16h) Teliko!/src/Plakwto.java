import javax.swing.JOptionPane;


public class Plakwto extends Tavli{

	public Plakwto(Board b) {
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
		printMovesArray(moves2);
		System.out.println("CheckersSpots");
		printMovesArray2(b.checkersSpots);*/
	}

	//Αρχικές θέσεις στο πλακωτό
	public void setDefaultCheckersSpots(){

		b.checkersSpots[0][0]=15;
		b.checkersSpots[0][1]=ID.Player1.getValue();
		b.checkersSpots[23][0]=15;
		b.checkersSpots[23][1]=ID.Player2.getValue();
		
		b.temp[0][0]=15;
		b.temp[0][1]=ID.Player1.getValue();
		b.temp[23][0]=15;
		b.temp[23][1]=ID.Player2.getValue();
	}

	//Αρχικοποίηση των πινάκων moves1 και moves2
	public void setDefaultMoves(){
		for(int i=0; i<24; i++){
			for (int j=0; j<6; j++){

				if(i+j+1<24)
					moves1[i][j]=(b.checkersSpots[i+j+1][1]!=ID.Player2.getValue() || b.checkersSpots[i+j+1][0]==1) && b.checkersSpots[i+j+1][1]!=ID.Both2.getValue();
				else
					moves1[i][j]=false;
				if(i-j-1>-1)
					moves2[i][j]=(b.checkersSpots[i-j-1][1]!=ID.Player1.getValue() || b.checkersSpots[i-j-1][0]==1) && b.checkersSpots[i-j-1][1]!=ID.Both1.getValue();
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

	//Εκτυπώνει έναν πίνακα boolean
	public void printMovesArray(boolean[][] array){
		for(int i=0; i<24; i++){
			System.out.print(i+":  [ ");
			for(int j=0; j<6; j++){
				System.out.print(array[i][j]+" ");
			}
			System.out.println("]");
		}

	}
	
	public void printMovesArray2(int[][] array){
		for(int i=0; i<24; i++){
			System.out.print(i+":  [ ");
			for(int j=0; j<2; j++){
				System.out.print(array[i][j]+" ");
			}
			System.out.println("]");
		}

	}

	public boolean allowedToMove(int spot1, int spot2) {
		int dice1 = b.dice1;
		int dice2 = b.dice2;

		if(whoseTurnIs()==ID.Player1){
			if(spot1-spot2<=0){
				if(readyForCase() && spot1-spot2==0){
					if(allowedToMoveInCase(spot1))
						return true;
				}else{
					if(spot2-spot1==dice1 && roundsOfDice1!=0)
						return (moves1[spot1][dice1-1]);
					else if(spot2-spot1==dice2 && roundsOfDice2!=0)
						return (moves1[spot1][dice2-1]);
					else if(spot2-spot1==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
						return ((moves1[spot1][dice1-1] && moves1[spot1+dice1][dice2-1]) || (moves1[spot1][dice2-1] && moves1[spot1+dice2][dice1-1]));
					else if (spot2-spot1==2*dice1  && roundsOfDice1>1)
						return (moves1[spot1][dice1-1] && moves1[spot1+dice1][dice1-1]);
					else if(spot2-spot1==3*dice1  && roundsOfDice1>2)
						return (moves1[spot1][dice1-1] && moves1[spot1+dice1][dice1-1] && moves1[spot1+2*dice1][dice1-1]);
					else if(spot2-spot1==4*dice1  && roundsOfDice1>3)
						return (moves1[spot1][dice1-1] && moves1[spot1+dice1][dice1-1] && moves1[spot1+2*dice1][dice1-1] && moves1[spot1+3*dice1][dice1-1]);
				}
			}
		}
		else{
			if(spot1-spot2>=0){
				if(readyForCase() && spot1-spot2==0){
					if(allowedToMoveInCase(spot1))
						return true;
				}else{
					if(spot1-spot2==dice1 && roundsOfDice1!=0)
						return (moves2[spot1][dice1-1]);
					else if(spot1-spot2==dice2 && roundsOfDice2!=0)
						return (moves2[spot1][dice2-1]);
					else if(spot1-spot2==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
						return ((moves2[spot1][dice1-1] && moves2[spot1-dice1][dice2-1]) || (moves2[spot1][dice2-1] && moves2[spot1-dice2][dice1-1]));
					else if (spot1-spot2==2*dice1  && roundsOfDice1>1)
						return (moves2[spot1][dice1-1] && moves2[spot1-dice1][dice1-1]);
					else if(spot1-spot2==3*dice1  && roundsOfDice1>2)
						return (moves2[spot1][dice1-1] && moves2[spot1-dice1][dice1-1] && moves2[spot1-2*dice1][dice1-1]);
					else if(spot1-spot2==4*dice1  && roundsOfDice1>3)
						return (moves2[spot1][dice1-1] && moves2[spot1-dice1][dice1-1] && moves2[spot1-2*dice1][dice1-1] && moves2[spot1-3*dice1][dice1-1]);
				}
			}
		}
		return false;
	}

	public boolean allowedToMoveInCase(int spot){
		int dice1 = b.dice1;
		int dice2 = b.dice2;

		if(whoseTurnIs()==ID.Player1){
			if((24-spot==dice1 && roundsOfDice1!=0) || (24-spot==dice2 && roundsOfDice2!=0))
				return true;
			else if(24-spot==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
				return (moves1[spot][dice1-1] || moves1[spot][dice2-1]);
			else if (24-spot==2*dice1  && roundsOfDice1>1)
				return (moves1[spot][dice1-1]);
			else if(24-spot==3*dice1  && roundsOfDice1>2)
				return (moves1[spot][dice1-1] && moves1[spot+dice1][dice1-1]);
			else if(24-spot==4*dice1  && roundsOfDice1>3)
				return (moves1[spot][dice1-1] && moves1[spot+dice1][dice1-1] && moves1[spot+2*dice1][dice1-1]);
			else if((24-spot<dice1*roundsOfDice1 && 24-spot<dice1) || 24-spot<dice2*roundsOfDice2){
				int sum=0;
				for(int i=spot-1; i>17; i--){
					if(b.checkersSpots[i][1]==ID.Player1.getValue())
						sum+=b.checkersSpots[i][0];
					else if(b.checkersSpots[i][1]==ID.Both1.getValue())
						sum+=b.checkersSpots[i][0]-1;
				}
				if(sum==0){
					return true;
				}
			}
		}
		else{
			if((spot+1==dice1 && roundsOfDice1!=0) || (spot+1==dice2 && roundsOfDice2!=0))
				return true;
			else if(spot+1==dice1+dice2 && roundsOfDice1!=0 && roundsOfDice2!=0)
				return (moves2[spot][dice1-1] || moves2[spot][dice2-1]);
			else if (spot+1==2*dice1  && roundsOfDice1>1)
				return (moves2[spot][dice1-1]);
			else if(spot+1==3*dice1  && roundsOfDice1>2)
				return (moves2[spot][dice1-1] && moves2[spot-dice1][dice1-1]);
			else if(spot+1==4*dice1  && roundsOfDice1>3)
				return (moves2[spot][dice1-1] && moves2[spot-dice1][dice1-1] && moves2[spot-2*dice1][dice1-1]);
			else if((spot+1<dice1*roundsOfDice1 && spot+1<dice1) || spot+1<dice2*roundsOfDice2){
				int sum=0;
				for(int i=spot+1; i<6; i++){
					if(b.checkersSpots[i][1]==ID.Player2.getValue())
						sum+=b.checkersSpots[i][0];
					else if(b.checkersSpots[i][1]==ID.Both2.getValue())
						sum+=b.checkersSpots[i][0]-1;
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
			if(spot1==spot2){
				removeChecker(spot1);
				addCheckerInCase(false);
			}else{
				//’μα είναι μια κανονική κίνηση
				removeChecker(spot1);
				addChecker(spot2);
			}
		}
		else{
			if(spot1==spot2){
				removeChecker(spot1);
				addCheckerInCase(true);
			}else{
				//’μα είναι μια κανονική κίνηση
				removeChecker(spot1);
				addChecker(spot2);
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
			else{
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
		endTurn=!b.getTavli().hasMoves();
		if(roundsOfDice1+roundsOfDice2>0 && endTurn){
			b.repaint();
			JOptionPane.showMessageDialog(null, "You can't move any more!");
		}
	}

	//Ελέγχει αν ο παίκτης έχει άλλες κινήσεις
		public boolean hasMoves(){
			if(roundsOfDice1!=0 || roundsOfDice2!=0){
				if(whoseTurnIs()==ID.Player2){
					if(roundsOfDice1!=0){
						for(int i=0; i<24; i++){
							if((b.checkersSpots[i][1]==ID.Player2.getValue() || b.checkersSpots[i][1]==ID.Both2.getValue()) && allowedToMove(i, i-b.dice1) && i-b.dice1>-1)
								return true;
						}
					}
					if(roundsOfDice2!=0){
						for(int i=0; i<24; i++){
							if((b.checkersSpots[i][1]==ID.Player2.getValue() || b.checkersSpots[i][1]==ID.Both2.getValue()) && allowedToMove(i, i-b.dice2) && i-b.dice2>-1)
								return true;
						}
					}
					if(readyForCase()){
						for(int i=0; i<6; i++){
							if ((b.checkersSpots[i][1]==ID.Player2.getValue() || b.checkersSpots[i][1]==ID.Both2.getValue()) && allowedToMoveInCase(i))
								return true;
						}
					}
				}else{
					if(roundsOfDice1!=0){
						for(int i=0; i<24; i++){ 
							if((b.checkersSpots[i][1]==ID.Player1.getValue() || b.checkersSpots[i][1]==ID.Both1.getValue()) && allowedToMove(i, i+b.dice1) && i+b.dice1<24)
								return true;
						}
					}
					if(roundsOfDice2!=0){
						for(int i=0; i<25; i++){ 
							if((b.checkersSpots[i][1]==ID.Player1.getValue() || b.checkersSpots[i][1]==ID.Both1.getValue()) && allowedToMove(i, i+b.dice2) && i+b.dice2<24 )
								return true;
						}
					}
					if(readyForCase() ){
						for(int i=18; i<24; i++){
							if ((b.checkersSpots[i][1]==ID.Player1.getValue() || b.checkersSpots[i][1]==ID.Both1.getValue()) && allowedToMoveInCase(i))
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

	public void addChecker(int spot){
		int player = whoseTurnIs().getValue();
		if(b.checkersSpots[spot][1]==ID.None.getValue()){
			b.checkersSpots[spot][1]=player;
		}else if(player==1 && b.checkersSpots[spot][0]==1){
			//Φυλακίζεται ένα πούλι του αντιπάλου και κάνει πόρτα ο παίκτης 1
			if(b.checkersSpots[spot][1]==ID.Player2.getValue()){
				b.checkersSpots[spot][1]=ID.Both1.getValue();
			}
			//Επειδή δημιουργείται πόρτα (είτε με δικά του πούλια είτε πλακώνοντας πούλι του αντιπάλου)
			//πρέπει να γίνουν false οι 6 θέσεις που είναι προηγούμενες από αυτήν στον πίνακα moves του αντιπάλου
			//αφού δεν θα έχει τη δυνατότητα να πάει σ' αυτήν τη θέση
			int count=0;
			for(int i=spot+1; i<spot+7 && i<24; i++){
				moves2[i][count]=false;
				count++;
			}
		}else if(b.checkersSpots[spot][0]==1){
			if(b.checkersSpots[spot][1]==ID.Player1.getValue()){
				b.checkersSpots[spot][1]=ID.Both2.getValue();
			}
			int count=0;
			for(int i=spot-1; i>spot-7 && i>-1; i--){
				moves1[i][count]=false;
				count++;
			}

		}
		b.checkersSpots[spot][0]++;
	}

	public void removeChecker(int spot){
		int player = whoseTurnIs().getValue();
		b.checkersSpots[spot][0]--;
		if(player==1 && b.checkersSpots[spot][0]==1){
			if(b.checkersSpots[spot][1]==ID.Both1.getValue()){
				//Ελευθερώνεται ένα πούλι του αντιπάλου
				b.checkersSpots[spot][1]=ID.Player2.getValue();
			}
			//Οι 6 επόμενες θέσεις θα έχουν στο spot true για να πατήσουν
			int count=0;
			for(int i=spot+1; i<spot+7 && i<24; i++){
				moves2[i][count]=true;
				count++;
			}
		}		
		else if(b.checkersSpots[spot][0]==1){

			if(b.checkersSpots[spot][1]==ID.Both2.getValue()){
				//Ελευθερώνεται ένα πούλι του αντιπάλου
				b.checkersSpots[spot][1]=ID.Player1.getValue();
			}
			//Οι 6 προηγούμενες θέσεις θα έχουν στο spot true για να πατήσουν
			int count=0;
			for(int i=spot-1; i>spot-7 && i>-1; i--){
				moves1[i][count]=true;
				count++;
			}
		}
		else if(b.checkersSpots[spot][0]==0){
			b.checkersSpots[spot][1]=ID.None.getValue();
		}
	}

	public boolean readyForCase(){
		if(whoseTurnIs()==ID.Player1){
			int rdyToPick1=0;
			for(int i=18;i<24;i++){
				if(b.checkersSpots[i][1]==ID.Player1.getValue())
					rdyToPick1= b.checkersSpots[i][0] + rdyToPick1;
				else if(b.checkersSpots[i][1]==ID.Both1.getValue())
					rdyToPick1= b.checkersSpots[i][0] + rdyToPick1 - 1;
			}	
			if (rdyToPick1 + checkersInCase1==15)
				return(true);
			else
				return (false);
		}else {
			int rdyToPick2=0;
			for(int i=0;i<6;i++){
				if(b.checkersSpots[i][1]==ID.Player2.getValue())
					rdyToPick2=b.checkersSpots[i][0]+rdyToPick2;
				else if(b.checkersSpots[i][1]==ID.Both2.getValue())
					rdyToPick2= b.checkersSpots[i][0] + rdyToPick2 - 1;
			}
			if (rdyToPick2 + checkersInCase2==15) 
				return(true);
			else 
				return (false);
		}
	}
	
	public boolean opponentInΗomeBoard(boolean player2){
		if(!player2){
			for(int i=18; i<24; i++){
				if(b.checkersSpots[i][1]==ID.Player2.getValue())
					return true;	
			}	
		}else{
			for(int i=0; i<6; i++){
				if(b.checkersSpots[i][1]==ID.Player1.getValue())
					return true;	
			}
		}
		return false;
	}
	
}
