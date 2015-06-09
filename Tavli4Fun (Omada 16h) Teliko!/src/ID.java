
public enum ID {
	
	None(0),
	Player1(1),
	Player2(2),
	Both1(3),
	Both2(4);
	
	private int value;
	 
	private ID(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
}
