import java.io.Serializable;

import javax.swing.JOptionPane;


public abstract class  Tavli implements Serializable{
	
	protected Board b;
	protected boolean turn; //Όταν είναι false είναι σειρά του 1ου παίκτη, ενώ όταν είναι true του 2ου παίκτη
	protected int doubleDice;
	protected int checkersInCase1, checkersInCase2;
	protected boolean endTurn;
	protected boolean[][] moves1= new boolean[25][6];
	protected boolean[][] moves2= new boolean[25][6];
	protected boolean[][] tempMoves1= new boolean[25][6];
	protected boolean[][] tempMoves2= new boolean[25][6];
	private int tempCase1, tempCase2;
	protected int tempPrisoners1, tempPrisoners2;
	protected int roundsOfDice1,roundsOfDice2;
	protected int prisoners1, prisoners2;
	
	protected boolean hasDoubleDice;

	public Tavli(Board b) {
		hasDoubleDice=false;
		prisoners1=0;
		prisoners2=0;
		this.b = b;
		turn=false;
		checkersInCase1=0;
		checkersInCase2=0;
		endTurn=true;
	}
	
	//Επιστρέφει τον παίκτη που είναι η σειρά του να παίξει
	public ID whoseTurnIs(){
		if(!turn) return ID.Player1;
		else return ID.Player2;
	}
	
	//Εναλλάσει τη σειρά
	public void switchTurn(){
		turn=!turn;
	}
	
	public void nextDoubledice(){
		if (doubleDice == 1)
			doubleDice = 2;
		else if (doubleDice <5){
			doubleDice *= 2;
			b.setDoubleDiceString(" "+doubleDice+" ");
		}
		else if(doubleDice <64){
			doubleDice *= 2;
			b.setDoubleDiceString(doubleDice+" ");
		}
			
	}
	
	public ID checkIfCaseIsFull(){
		if (checkersInCase1==15){
			return ID.Player1;
		}
		else if (checkersInCase2==15){
			return ID.Player2;
		}
		else 
			return ID.None;
	}
	
	public void addCheckerInCase(boolean player2){
		if (!player2 && checkersInCase1<16) checkersInCase1++;
		else if (player2 && checkersInCase2<16) checkersInCase2++;
	}
	
	public void giveResults(boolean player2){
		int score=1;
		if(checkersInCase2==15 || checkersInCase1==15){
			if(opponentInΗomeBoard(player2) || (prisoners1>0 && !player2) || (prisoners2>0 && player2))
				score=3;
			else if((!player2 && checkersInCase2==0) || (player2 && checkersInCase1==0))
					score=2;
		}
		if(doubleDice>0)
			score*=doubleDice;
		if(!player2){
			JOptionPane.showMessageDialog(null, "The winner of this game is "+ b.getGame().name1+" with "+score+" points!");
			b.getGame().scoreP1+=score;
			b.getGame().nextTavli();
		}else{
			JOptionPane.showMessageDialog(null, "The winner of this game is "+ b.getGame().name2 +" with "+score+" points!");
			b.getGame().scoreP2+=score;
			b.getGame().nextTavli();
		}
			
	}
	
	public String getMessageRounds(){
		String str="You haven't played all dice! You have to play:\n\n";
		
		if(roundsOfDice1>0){
			str+=roundsOfDice1 +"x time(s) of the number: " +b.dice1+"\n"; 
		}
		if(roundsOfDice2>0){
			str+=roundsOfDice2 +"x time(s) of the number: " +b.dice2; 
		}
		return str;
	}
	

	
	
	public int getTempPrisoners1() {
		return tempPrisoners1;
	}

	public void setTempPrisoners1(int tempPrisoners1) {
		this.tempPrisoners1 = tempPrisoners1;
	}

	public int getTempPrisoners2() {
		return tempPrisoners2;
	}

	public void setTempPrisoners2(int tempPrisoners2) {
		this.tempPrisoners2 = tempPrisoners2;
	}

	public int getTempCase1() {
		return tempCase1;
	}

	public void setTempCase1(int tempCase1) {
		this.tempCase1 = tempCase1;
	}

	public int getTempCase2() {
		return tempCase2;
	}

	public void setTempCase2(int tempCase2) {
		this.tempCase2 = tempCase2;
	}

	public int getDoubleDice() {
		return doubleDice;
	}

	public int getCheckersInCase1() {
		return checkersInCase1;
	}

	public void setCheckersInCase1(int checkersInCase1) {
		this.checkersInCase1 = checkersInCase1;
	}

	public int getCheckersInCase2() {
		return checkersInCase2;
	}

	public void setCheckersInCase2(int checkersInCase2) {
		this.checkersInCase2 = checkersInCase2;
	}
	
	public Board getBoard() {
		return b;
	}
	public void setBoard(Board board) {
		this.b =board;
	}
	
	public boolean[][] getTempMoves1() {
		return tempMoves1;
	}

	public void setTempMoves1(boolean[][] tempMoves1) {
		this.tempMoves1 = tempMoves1;
	}

	public boolean[][] getTempMoves2() {
		return tempMoves2;
	}

	public void setTempMoves2(boolean[][] tempMoves2) {
		this.tempMoves2 = tempMoves2;
	}

	public boolean getTurn(){
		return(turn);
	}
	public abstract boolean opponentInΗomeBoard(boolean player2);
	public abstract boolean allowedToMove(int spot,int i);
	public abstract void makeaMove(int spot,int i);
	public abstract int maximumMoves();
	public abstract void round(int spot,int i);
	public abstract boolean hasMoves();
	public abstract void removePlayedDices(int spot1, int spot2);
	public abstract boolean readyForCase();
	public abstract boolean allowedToMoveInCase(int spot);
	public abstract void setRoundsOfDices();
	
	public int getPrisoners1() {
		return prisoners1;
	}

	public void setPrisoners1(int prisoners1) {
		this.prisoners1 = prisoners1;
	}

	public int getPrisoners2() {
		return prisoners2;
	}

	
	
	public void setPrisoners2(int prisoners2) {
		this.prisoners2 = prisoners2;
	}

	public boolean isEndTurn() {
		return endTurn;
	}

	public void setEndTurn(boolean endTurn) {
		this.endTurn = endTurn;
	}


	public boolean whoHasDoubleDice() {
		return hasDoubleDice;
	}

	public void setHasDoubleDice() {
		this.hasDoubleDice = !hasDoubleDice;
	}

	public int getRoundsOfDice1() {
		return roundsOfDice1;
	}

	public int getRoundsOfDice2() {
		return roundsOfDice2;
	}

	public void setDoubleDice(int doubleDice) {
		this.doubleDice = doubleDice;
	}
	
	
}
