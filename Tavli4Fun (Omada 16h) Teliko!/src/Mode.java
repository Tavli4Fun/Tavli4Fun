import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;


public  class Mode extends JFrame {
	public  static ArrayList<String> names= new ArrayList<String>();
	static int level=0;
	private Game load;
	private static int numofplayers =2;
	private Window window=new Window("Mode",600,440);
	private ArrayList list1=new ArrayList();
	private ArrayList list2=new ArrayList();
	private static ArrayList gamelist=new ArrayList();
	static Game game ;
	
	public  Game getgame() {
		return game;
	}

	public void setgame(Game singlegame) {
		this.game = singlegame;
	}

	

	public Mode(){
	/*JFrame frame =new JFrame("Mode");
	frame.setVisible(true);
	frame.setResizable(false);
	frame.getContentPane().setBackground(new Color(255, 153, 0));
	frame.setMaximumSize(new Dimension(600, 440));
	frame.setMinimumSize(new Dimension(600, 440));*/
	window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	/*frame.setLocationRelativeTo(null);
	frame.getContentPane().setLayout(null);*/
	
	JLabel lblNewLabel = new JLabel("Mode");
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
	lblNewLabel.setBounds(205, 11, 212, 45);
	window.getFrame().getContentPane().add(lblNewLabel);
	
	JButton btnNewButton2 = new JButton("Multi Player");
	btnNewButton2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			
			LineNumberReader lnr;
			try {
				lnr = new LineNumberReader(new FileReader(new File("Players.txt")));
				lnr.skip(Long.MAX_VALUE);
				if(lnr.getLineNumber()<2 ){
					JOptionPane.showMessageDialog(null, "You must create at least 2 players in order \n to play Multiplayer");
				}else{
					window.getFrame().dispose();
					MultiPlayer();
				}
				lnr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
		}
	});
	btnNewButton2.setForeground(new Color(255, 255, 204));
	btnNewButton2.setBackground(new Color(0, 102, 153));
	btnNewButton2.setFont(new Font("Verdana", Font.PLAIN, 15));
	btnNewButton2.setBounds(228, 155, 156, 54);
	window.getFrame().getContentPane().add(btnNewButton2);
	
	JButton btnNewButton_1 = new JButton("Single Player");
	btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			window.getFrame().dispose();
			SinglePlayer();
		}
	});
	btnNewButton_1.setForeground(new Color(255, 255, 204));
	btnNewButton_1.setBackground(new Color(0, 102, 153));
	btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
	btnNewButton_1.setBounds(228, 79, 156, 54);
	window.getFrame().getContentPane().add(btnNewButton_1);
	
	JButton btnNewButton_2 = new JButton("Go Back");
	btnNewButton_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			window.getFrame().dispose();
			new Menu();
		}
	});
	btnNewButton_2.setForeground(new Color(255, 255, 204));
	btnNewButton_2.setBackground(new Color(0, 102, 153));
	btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 15));
	btnNewButton_2.setBounds(228, 355, 156, 45);
	window.getFrame().getContentPane().add(btnNewButton_2);
	window.getFrame().setVisible(true);
	
	
	
	}
	
	public static ArrayList getGamelist() {
		return gamelist;
	}

	public static void setGamelist(ArrayList gamelist) {
		Mode.gamelist = gamelist;
	}

	public void SinglePlayer(){
		
		
		Window window2 =new Window("Single Player",600,440);
		
		/*JFrame frame2 =new JFrame("Single Player");
		frame2.setVisible(true);
		frame2.setResizable(false);
		frame2.getContentPane().setBackground(new Color(255, 153, 0));
		frame2.setMaximumSize(new Dimension(600, 440));
		frame2.setMinimumSize(new Dimension(600, 440));*/
		window2.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame2.setLocationRelativeTo(null);
		frame2.getContentPane().setLayout(null);*/
		
		JLabel lblNewLabel = new JLabel("Single Player");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window2.getFrame().getContentPane().add(lblNewLabel);
		
		
		
		JButton btnNewButton_1 = new JButton("New Game");
		btnNewButton_1.setForeground(new Color(255, 255, 204));
		btnNewButton_1.setBackground(new Color(0, 102, 153));
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBounds(228, 79, 156, 54);
		window2.getFrame().getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window2.getFrame().dispose();
				 NewGame();
			}
		});
		
		
		
		
		JButton btnNewButton = new JButton("Resume Game");
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(228, 180, 156, 54);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					FileInputStream instream = new FileInputStream("savedgame.ser");
					ObjectInputStream in= new ObjectInputStream(instream);
					load=(Game)in.readObject();
					instream.close();
					window.getFrame().dispose();
					in.close();
					load.getB().buttonImages= new BufferedImage[2];
					load.getB().diceImages= new BufferedImage[6];
					load.getB().readImages();
					load.getB().img1=load.getB().buttonImages[0];
					load.getB().img2=load.getB().buttonImages[0];
					load.getB().imgDice1=load.getB().diceImages[load.getB().dice1-1];
					load.getB().imgDice2=load.getB().diceImages[load.getB().dice2-1];
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
				load.getB().getWindow().getFrame().setVisible(true);
			}
		});
		
		window2.getFrame().getContentPane().add(btnNewButton);
		JButton btnNewButton_2 = new JButton("Go Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window2.getFrame().dispose();
				new Mode();
			}
		});
		btnNewButton_2.setForeground(new Color(255, 255, 204));
		btnNewButton_2.setBackground(new Color(0, 102, 153));
		btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_2.setBounds(228, 355, 156, 45);
		window2.getFrame().getContentPane().add(btnNewButton_2);
		window2.getFrame().setVisible(true);
		
		
	}
	public void MultiPlayer(){
		
		Window window3=new Window("Multiplayer",600,440);
		
		
		/*JFrame frame3 =new JFrame("Multiplayer");
		frame3.setVisible(true);
		frame3.setResizable(false);
		frame3.getContentPane().setBackground(new Color(255, 153, 0));
		frame3.setMaximumSize(new Dimension(600, 440));
		frame3.setMinimumSize(new Dimension(600, 440));*/
		window3.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame3.setLocationRelativeTo(null);
		frame3.getContentPane().setLayout(null);*/
		
		JLabel lblNewLabel = new JLabel("Multiplayer");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window3.getFrame().getContentPane().add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setBounds(244, 88, 86, 20);
		window3.getFrame().getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("2");
		textField.setEditable(true);
		
		JLabel lblNumberOfPlayers = new JLabel("Number of Players");
		lblNumberOfPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumberOfPlayers.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNumberOfPlayers.setBounds(100, 81, 128, 31);
		window3.getFrame().getContentPane().add(lblNumberOfPlayers);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(351, 74, 89, 44);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Mode.isValid(textField.getText())){
					JOptionPane.showMessageDialog(null, "Number of players must be between 2 and 4!");
				}
				else{
					numofplayers=Integer.parseInt(textField.getText());
				}
				
				
				
			}
		});
		
		window3.getFrame().getContentPane().add(btnNewButton);
		
		JCheckBox rdbtnNewRadioButton = new JCheckBox("Plakoto");
		rdbtnNewRadioButton.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		rdbtnNewRadioButton.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton.setBounds(66, 162, 155, 38);
		window3.getFrame().getContentPane().add(rdbtnNewRadioButton);
		
		JCheckBox rdbtnNewRadioButton_1 = new JCheckBox("Fevga");
		rdbtnNewRadioButton_1.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		rdbtnNewRadioButton_1.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_1.setBounds(66, 219, 155, 38);
		window3.getFrame().getContentPane().add(rdbtnNewRadioButton_1);
		
		JCheckBox rdbtnNewRadioButton_2 = new JCheckBox("Portes");
		rdbtnNewRadioButton_2.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		rdbtnNewRadioButton_2.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_2.setBounds(66, 271, 155, 38);
		window3.getFrame().getContentPane().add(rdbtnNewRadioButton_2);
		
		JCheckBox rdbtnNewRadioButton_3 = new JCheckBox("Asodyo");
		rdbtnNewRadioButton_3.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_3.setFont(new Font("Verdana", Font.PLAIN, 15));
		rdbtnNewRadioButton_3.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_3.setBounds(66, 325, 155, 38);
		
		window3.getFrame().getContentPane().add(rdbtnNewRadioButton_3);
		
		
		 
		JButton btnNewButton_1 = new JButton("Play");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean plakoto,asodyo,portes,fevga;
			if(rdbtnNewRadioButton.isSelected()==false && rdbtnNewRadioButton_2.isSelected()== false && rdbtnNewRadioButton_1.isSelected()==false && rdbtnNewRadioButton_3.isSelected()==false ){
				JOptionPane.showMessageDialog(null, "You must select at least one game in order to play!");
			}
			else{	
			if(rdbtnNewRadioButton.isSelected()){
					plakoto=true;
				}
				else{
					plakoto=false;
				}
				
				if(rdbtnNewRadioButton_2.isSelected()){
					portes=true;
				}
				else{
					portes=false;
				}
				if(rdbtnNewRadioButton_1.isSelected()){
					fevga=true;
				}
				else{
					fevga=false;
				}
				
				if(rdbtnNewRadioButton_3.isSelected()){
					asodyo=true;
				}
				else{
					asodyo=false;
				}
				 list1=Mode.getGames(plakoto, portes, fevga, asodyo);
				list2=Settings.getList();
				
				
					int j,i;
					j=i=0;
					String string=list2.get(1).toString();
					int numberofgames=Integer.parseInt(string);
					for(i=0;i<numberofgames;i++){
						if(j<list1.size()){
							gamelist.add(list1.get(j));
							j++;
						}
						else{
							j=0;
							gamelist.add(list1.get(j));
							j++;
						}
					
					
					if(list2.get(0).equals(3)){
						Collections.shuffle(gamelist);
					}
					
					
					
					
				}
				
				
				
					
					 if(list2.get(0).equals(1)){
							Order();
							window3.getFrame().dispose();
					 }
				
							else{
					window3.getFrame().dispose();
					MultiList();
				}
				}
			}
			
		});
		btnNewButton_1.setBackground(new Color(0, 102, 153));
		btnNewButton_1.setForeground(new Color(255, 255, 204));
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBounds(316, 202, 147, 66);
		window3.getFrame().getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Go Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window3.getFrame().dispose();
				new Mode();
			}
		});
		btnNewButton_2.setForeground(new Color(255, 255, 204));
		btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_2.setBackground(new Color(0, 102, 153));
		btnNewButton_2.setBounds(316, 306, 147, 57);
		window3.getFrame().getContentPane().add(btnNewButton_2);
		window3.getFrame().setVisible(true);
		
		
		
	}
	
	public void NewGame(){
		Window window4 =new Window("New Game",600,440);
		
		
		/*JFrame frame4 =new JFrame("New Game");
		frame4.setVisible(true);
		frame4.setResizable(false);
		frame4.getContentPane().setBackground(new Color(255, 153, 0));
		frame4.setMaximumSize(new Dimension(600, 440));
		frame4.setMinimumSize(new Dimension(600, 440));*/
		window4.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame4.setLocationRelativeTo(null);
		frame4.getContentPane().setLayout(null);*/
		
		JLabel lblNewLabel = new JLabel("New Game");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window4.getFrame().getContentPane().add(lblNewLabel);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Easy");
		rdbtnNewRadioButton.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton.setFont(new Font("Verdana", Font.PLAIN, 13));
		rdbtnNewRadioButton.setBounds(250, 112, 124, 23);
		window4.getFrame().getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Normal");
		rdbtnNewRadioButton_1.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_1.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		rdbtnNewRadioButton_1.setBounds(250, 152, 124, 23);
		rdbtnNewRadioButton_1.setSelected(true);
		window4.getFrame().getContentPane().add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Hard");
		rdbtnNewRadioButton_2.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_2.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_2.setFont(new Font("Verdana", Font.PLAIN, 13));
		rdbtnNewRadioButton_2.setBounds(250, 189, 124, 23);
		window4.getFrame().getContentPane().add(rdbtnNewRadioButton_2);
		
		 ButtonGroup group=new ButtonGroup();
		 
		 group.add(rdbtnNewRadioButton);
		 group.add(rdbtnNewRadioButton_1);
		 group.add(rdbtnNewRadioButton_2);
		 
		JLabel lblSelectDifficulty = new JLabel("Select Difficulty");
		lblSelectDifficulty.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblSelectDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectDifficulty.setBounds(250, 69, 135, 23);
		window4.getFrame().getContentPane().add(lblSelectDifficulty);
		
		
		
		
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(rdbtnNewRadioButton.isSelected()){
				level=1;
			}
			else if(rdbtnNewRadioButton_1.isSelected()){
				level=2;
			}
			else if(rdbtnNewRadioButton_2.isSelected()){
				level=3;
			}
				 list1=Mode.getGames(false, true, false, false);
				list2=Settings.getList();

				
				
					int j,i;
					j=i=0;
					String string=list2.get(1).toString();
					int numberofgames=Integer.parseInt(string);
					for(i=0;i<numberofgames;i++){
						if(j<list1.size()){
							gamelist.add(list1.get(j));
							j++;
						}
						else{
							j=0;
							gamelist.add(list1.get(j));
							j++;
						}
					
					
					if(list2.get(0).equals(3)){
						Collections.shuffle(gamelist);
					}
					
					
					
				}
				
					 window4.getFrame().dispose();
					 
				//game=new Game();
					 SingleList();
					 
			}
			
		});
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(322, 334, 155, 54);
		window4.getFrame().getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Go Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window4.getFrame().dispose();
				SinglePlayer();
			}
		});
		btnNewButton_1.setBackground(new Color(0, 102, 153));
		btnNewButton_1.setForeground(new Color(255, 255, 204));
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBounds(55, 334, 155, 54);
		window4.getFrame().getContentPane().add(btnNewButton_1);
		window4.getFrame().setVisible(true);
		
		
		
		
	}
	public static  ArrayList getGames(boolean plakoto,boolean portes,boolean fevga,boolean asodyo){
		
		ArrayList list= new ArrayList();
		if(plakoto){
			list.add("Plakwto");
			
		}
		if(portes){
			list.add("Portes");
		}
		if(fevga){
			list.add("Feuga");
		}
		if(asodyo){
			list.add("Assoduo");
		}
return list;
	}
	
	
	public static  boolean isInteger( String input )
	{
	   try
	   {
	      Integer.parseInt( input );
	      return true;
	   }
	  catch(Exception e){
		  return false;
	  }
	}
	public static  boolean isValid( String input )
	{
	  
		if(isInteger(input)){
			 int validtext=Integer.parseInt(input);
			 if(validtext<=1 || validtext > 4){
				 return false;
			 }
				 else{
					 return true;
				 }
		   }
		else{
			return false;
		}
	   }
	
public void Order(){
		Window orderwindow = new Window("Order of games",500,400);
		//JFrame frame = new JFrame();
		orderwindow.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			/*frame.getContentPane().setBackground(new Color(255, 153, 0));
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setTitle("Order of Games");
			frame.getContentPane().setLayout(null);
			frame.setSize(500, 400);
			frame.setLocationRelativeTo(null);
			JLabel lblNewLabel = new JLabel("Order of Games");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
			lblNewLabel.setBounds(127, 0, 234, 70);
			frame.getContentPane().add(lblNewLabel);*/
			
			
			
			//System.out.println(Mode.getGamelist().toString());
			//ArrayList<String> gamelist=Mode.getGamelist();
			
			//System.out.println(gamelist.toString());
			
			String[] stockArr = new String[gamelist.size()];
			stockArr = (String[]) gamelist.toArray(stockArr);
			
			
			DefaultListModel model=new DefaultListModel();
			
			for(int i=0;i<stockArr.length;i++){
				model.addElement(stockArr[i]);
			}
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(54, 96, 127, 193);
			orderwindow.getFrame().getContentPane().add(scrollPane);
			JList orderlist = new JList(model);
			orderlist.setBackground(new Color(204, 204, 255));
			scrollPane.setViewportView(orderlist);
			
			JButton btnUp = new JButton("Move Up");
			btnUp.setFont(new Font("Verdana", Font.PLAIN, 14));
			btnUp.setForeground(new Color(255, 255, 204));
			btnUp.setBackground(new Color(0, 102, 153));
			btnUp.setBounds(302, 93, 137, 50);
			orderwindow.getFrame().getContentPane().add(btnUp);
			
			JButton btnDown = new JButton("Move Down");
			btnDown.setForeground(new Color(255, 255, 204));
			btnDown.setFont(new Font("Verdana", Font.PLAIN, 14));
			btnDown.setBackground(new Color(0, 102, 153));
			btnDown.setBounds(302, 178, 137, 50);
			orderwindow.getFrame().getContentPane().add(btnDown);
			
			JButton btnPlay = new JButton("Play");
			btnPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<gamelist.size();i++){
					String str=	(String) model.getElementAt(i);
					gamelist.set(i, str);
					}	
				
					orderwindow.getFrame().dispose();
					MultiList();
				}
			});
			btnPlay.setForeground(new Color(255, 255, 204));
			btnPlay.setFont(new Font("Verdana", Font.PLAIN, 14));
			btnPlay.setBackground(new Color(0, 102, 153));
			btnPlay.setBounds(120, 310, 137, 50);
			orderwindow.getFrame().getContentPane().add(btnPlay);
			
			JButton back = new JButton("Go Back");
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					orderwindow.getFrame().dispose();
					new Mode();
				}
			});
			back.setForeground(new Color(255, 255, 204));
			back.setFont(new Font("Verdana", Font.PLAIN, 14));
			back.setBackground(new Color(0, 102, 153));
			back.setBounds(280, 310, 137, 50);
			orderwindow.getFrame().getContentPane().add(back);
			
			btnUp.addActionListener( new ActionListener()
			    {
			      public void actionPerformed( ActionEvent e )
			      {
			        int index = orderlist.getSelectedIndex();
			        if( index == -1 )
			          JOptionPane.showMessageDialog( null, "Select something to move." );
			        else if( index > 0 )
			        {
			          String temp = (String)model.remove( index );
			          model.add( index - 1, temp );
			          orderlist.setSelectedIndex( index - 1 );
			        }
			      }
			    }   );

			    btnDown.addActionListener( new ActionListener()
			    {
			      public void actionPerformed( ActionEvent e )
			      {
			        int index = orderlist.getSelectedIndex();
			        if( index == -1 )
			          JOptionPane.showMessageDialog( null, "Select something to move." );
			        else if( index < model.size() - 1 )
			        {
			          String temp = (String)model.remove( index );
			          model.add( index + 1, temp );
			          orderlist.setSelectedIndex( index + 1 );
			        }
			      }
			    }   );
			
	orderwindow.getFrame().setVisible(true);
}

	public void SingleList(){
Window window5=new Window("List of Players",500,400);
		
		
		/*JFrame frame3 =new JFrame("Multiplayer");
		frame3.setVisible(true);
		frame3.setResizable(false);
		frame3.getContentPane().setBackground(new Color(255, 153, 0));
		frame3.setMaximumSize(new Dimension(600, 440));
		frame3.setMinimumSize(new Dimension(600, 440));*/
		window5.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame3.setLocationRelativeTo(null);
		frame3.getContentPane().setLayout(null);*/
		
		JList list=new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setForeground(new Color(0, 51, 51));
		list.setFont(new Font("Verdana", Font.PLAIN, 14));
		list.setBackground(new Color(204, 204, 204));
		list.setBounds(35, 81, 171, 163);
	
		window5.getFrame().getContentPane().add(list);
		window5.getFrame().setVisible(true);
		
		
		 try {
			    FileInputStream fstream = new FileInputStream("Players.txt");
			    DataInputStream in = new DataInputStream(fstream);
			    BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    DefaultListModel listModel = new DefaultListModel();
			    String strLine;
			    while ((strLine = br.readLine()) != null)   
			    {
			            listModel.addElement(strLine); 
			           
			    }

			    list.setModel(listModel);
			    
			    JButton btnNewButton = new JButton("Select Player");
			    btnNewButton.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    	
			    		
			    		if(list.isSelectionEmpty()){
			    			JOptionPane.showMessageDialog(null, "You must select a player from the list ");
			    		}
			    		else{
			    			names.add((String) list.getSelectedValue());
			    			names.add("Computer");
			    			
                            JOptionPane.showMessageDialog(null, "Player selected");
			    		
			    			
			    			window5.getFrame().dispose();
			    			game=new Game();
			    			
			    		}
			    		
			    	}
			    });
			    btnNewButton.setForeground(new Color(255, 255, 204));
			    btnNewButton.setBackground(new Color(0, 102, 153));
			    btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
			    btnNewButton.setBounds(272, 138, 142, 54);
			    window5.getFrame().getContentPane().add(btnNewButton);
			    
			    JButton btnGoBack = new JButton("Go Back");
			    btnGoBack.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		list2.clear();
			    		window5.getFrame().dispose();
			    		SinglePlayer();
			    	}
			    });
			    btnGoBack.setForeground(new Color(255, 255, 204));
			    btnGoBack.setFont(new Font("Verdana", Font.PLAIN, 14));
			    btnGoBack.setBackground(new Color(0, 102, 153));
			    btnGoBack.setBounds(197, 289, 142, 54);
			    window5.getFrame().getContentPane().add(btnGoBack);
			    in.close();
		    } catch (Exception e) {
		    }
		
		
		
		
		
	}
	public void MultiList(){
		
Window window6=new Window("List of Players",500,400);
		
		
		/*JFrame frame3 =new JFrame("Multiplayer");
		frame3.setVisible(true);
		frame3.setResizable(false);
		frame3.getContentPane().setBackground(new Color(255, 153, 0));
		frame3.setMaximumSize(new Dimension(600, 440));
		frame3.setMinimumSize(new Dimension(600, 440));*/
		window6.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame3.setLocationRelativeTo(null);
		frame3.getContentPane().setLayout(null);*/
		
		JList list=new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setForeground(new Color(0, 51, 51));
		list.setFont(new Font("Verdana", Font.PLAIN, 14));
		list.setBackground(new Color(204, 204, 204));
		list.setBounds(35, 81, 171, 163);
	
		window6.getFrame().getContentPane().add(list);
		window6.getFrame().setVisible(true);
		
		
		 try {
			    FileInputStream fstream = new FileInputStream("Players.txt");
			    DataInputStream in = new DataInputStream(fstream);
			    BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    DefaultListModel listModel = new DefaultListModel();
			    String strLine;
			    while ((strLine = br.readLine()) != null)   
			    {
			            listModel.addElement(strLine); 
			           
			    }

			    list.setModel(listModel);
			    
			    JButton btnNewButton = new JButton("Select Player");
			    btnNewButton.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    	
			    		
			    		if(list.isSelectionEmpty()){
			    			JOptionPane.showMessageDialog(null, "You must select a player from the list! ");
			    		}
			    		{
			    			if(names.contains((String) list.getSelectedValue())){
			    				JOptionPane.showMessageDialog(null, "Player names must not be the same!");
			    			}else{
			    				names.add((String) list.getSelectedValue());
			    				JOptionPane.showMessageDialog(null, "Player"+(names.size())+" selected");
			    			}
			    		}
				    	
			    		
			    		
			    		if (numofplayers==names.size()){
			    			window6.getFrame().dispose();
				    		game=new Game();
			    		}
			    	}
			    });
			    btnNewButton.setForeground(new Color(255, 255, 204));
			    btnNewButton.setBackground(new Color(0, 102, 153));
			    btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
			    btnNewButton.setBounds(272, 138, 142, 54);
			    window6.getFrame().getContentPane().add(btnNewButton);
			    
			    JButton btnGoBack = new JButton("Go Back");
			    btnGoBack.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		list2.clear();
			    		window6.getFrame().dispose();
			    		MultiPlayer();
			    	}
			    });
			    btnGoBack.setForeground(new Color(255, 255, 204));
			    btnGoBack.setFont(new Font("Verdana", Font.PLAIN, 14));
			    btnGoBack.setBackground(new Color(0, 102, 153));
			    btnGoBack.setBounds(197, 289, 142, 54);
			    window6.getFrame().getContentPane().add(btnGoBack);
			    in.close();
		    } catch (Exception e) {
		    }
		
		
		
		
		
		
		
	}
}
