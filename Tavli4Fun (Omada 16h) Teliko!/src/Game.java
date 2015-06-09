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

/* A��� ����� � ������� ��� ��� ������ ���������� ����������!
 * ���������� ��� �������� ������� ��������� ��� ����� ��� �������
 * �� �������� "������" ���������������� ������� ��������� ����� ��������.
 * ������ ��� ���� ����������� � ������� ��� ����� ������ �� ������� ��� ���
 * ���� ���� ��������� ������� ��������� ���� ���� ���������� ��� ���������� ���� ���� �����
 * ��� ����� ��������������� (3 �����) ��� ��������� �� ������������� ���� �� ������ ���� ���������� ����!
 * ������ ��� ��������� �� ������������ ��� ���������� �� �� ���� ����� ��� ������� ������� ������� ��' ��� ��������
 * "�����". ����������� �� ��� ���� ����������� ������ �� ������� ������ ����������, ���� �� �� � �������� ������ ���
 * ������ �� ����� � ������� ����� ������������ ��� �� ��������, ���� �� �������� �� "��������" ���� ����� ��� �� 
 * �������� �� ������� � ������� �� �������� �� ����� (������ �� ������� �� ������� ��� �������� �� ����� ������) ���� 
 * ���������� ��� �� �������� �� ����� ���� ������. �� �������� �� ����� ���� �� ��������, ���� ��� ���������� ����� 
 * ������ ��� �������� ����� ������� ��� ��� ������ ��� ����� �� ��������� ��� ������ �� �����. �� ������� ��� ��������� ���
 * ���������� ������ �� ��������� ����� ������ ��������, ������������ ���� �������� ��� ������ Window WIDTH ���� ��� 600, ������
 * ���� ������ ������ (���������� 800 ��� ������� ������). ��� ������������ �� ��������� ���� ��� ������ �� ������� ��� �������
 * �� ���������.
 * 
 * ������ ����������� ����� ����������, �� ���������� �� ������� ����� ���� �� �� ������� ��� ���� ��� ����� 
 * ������ ��� ���� ��� �� �������� ���� ���� �������� ��� ������� �������� ���� ������ (����� ����������) ��� 
 * ���� ������� ���������, ��� ���� ���������� �� ������� ������� � ����� �������. ������ ��������������� ��� 
 * ������� (���� ��� ���� ������) ��� �������� ��� ���� ���� �� ������ �� ������� �� ���� 1,2,3,4,5 � 6. �' ����
 * ������ � ��������� ��� ������ ���� ���������� ����� ����� ����������, ���� �� ������ �� ��������� �� �������� �����
 * ���������� �������� ��� ��� ������ � �� ������ � ��������. ������ �� ���� ������ ��������� �' ���� ��� ������ �� 
 * ������� �� ���������� ��� �� ���� �������� ��������.*/

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
