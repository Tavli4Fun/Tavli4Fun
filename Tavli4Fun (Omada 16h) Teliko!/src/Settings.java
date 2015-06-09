import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Settings extends JFrame {
   public static boolean doubledice =true;
	private static int boardcolor=0 ;   //0:brown 1:grey
	private static int player1 = 3;    // 1:purple, 2 :white , 3:yellow, 4:blue 
	private static int player2= 4;
	private static ArrayList list=new ArrayList();
	public static int numOfGames;
    public static int getPlayer1() {
		return player1;
	}

	public static boolean isDoubledice() {
		return doubledice;
	}

	public static void setDoubledice(boolean doubledice) {
		Settings.doubledice = doubledice;
	}

	public static void setPlayer1(int player1) {
		Settings.player1 = player1;
	}

	public static int getPlayer2() {
		return player2;
	}

	public static void setPlayer2(int player2) {
		Settings.player2 = player2;
	}
	private static boolean flag=false;
	public static int getBoardcolor() {
		return boardcolor;
	}

	public static void setBoardcolor(int boardcolor) {
		Settings.boardcolor = boardcolor;
	}

	public static ArrayList getList() {
		if(flag){
		return list;
		}
		else{
			list.add(2);
			list.add(10);
			return list;
		}
	}

	public static void setList(ArrayList list) {
		Settings.list = list;
	}
	private Window window=new Window("Settings",600,440);
	public Settings(){
	   
		
	/*	JFrame frame =new JFrame("Settings");
		window.getFrame().setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setMaximumSize(new Dimension(600, 440));
		frame.setMinimumSize(new Dimension(600, 440));*/
		window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);*/
		
		JLabel lblNewLabel = new JLabel("Settings");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window.getFrame().getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Panel Settings");
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(231, 67, 155, 50);
		window.getFrame().getContentPane().add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				Panelsettings();
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("Game Settings");
		btnNewButton_1.setBackground(new Color(0, 102, 153));
		btnNewButton_1.setForeground(new Color(255, 255, 204));
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBounds(231, 138, 155, 50);
		window.getFrame().getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				Gamesettings();
			}
		});
		
		JButton btnNewButton_2 = new JButton("Go Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				new Menu();
			}
		});
		btnNewButton_2.setBackground(new Color(0, 102, 153));
		btnNewButton_2.setForeground(new Color(255, 255, 204));
		btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_2.setBounds(231, 325, 155, 50);
		window.getFrame().getContentPane().add(btnNewButton_2);
		window.getFrame().setVisible(true);
	}
	
	public void Panelsettings(){
		Window window2=new Window("Panel Settings",600,440);
		/*JFrame frame =new JFrame("Panel Settings");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setMaximumSize(new Dimension(600, 440));
		frame.setMinimumSize(new Dimension(600, 440));*/
		window2.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);*/
		
		JLabel lblNewLabel = new JLabel("Panel Settings");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window2.getFrame().getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Checkers color");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(101, 66, 102, 31);
		window2.getFrame().getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Board Color");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(367, 67, 105, 28);
		window2.getFrame().getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Player 1");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(34, 112, 88, 16);
		window2.getFrame().getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Player 2");
		lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(167, 108, 88, 20);
		window2.getFrame().getContentPane().add(lblNewLabel_4);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Purple");
		rdbtnNewRadioButton.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		rdbtnNewRadioButton.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton.setBounds(18, 147, 124, 28);
		window2.getFrame().getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("White");
		rdbtnNewRadioButton_1.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_1.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		rdbtnNewRadioButton_1.setBounds(18, 194, 124, 31);
		window2.getFrame().getContentPane().add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Yellow");
		rdbtnNewRadioButton_2.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_2.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		rdbtnNewRadioButton_2.setBounds(18, 244, 124, 31);
		rdbtnNewRadioButton_2.setSelected(true);
		window2.getFrame().getContentPane().add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Blue");
		rdbtnNewRadioButton_3.setBackground(new Color(0, 102, 153));
		rdbtnNewRadioButton_3.setForeground(new Color(255, 255, 204));
		rdbtnNewRadioButton_3.setFont(new Font("Verdana", Font.PLAIN, 12));
		rdbtnNewRadioButton_3.setBounds(18, 292, 124, 28);
		window2.getFrame().getContentPane().add(rdbtnNewRadioButton_3);
		
		ButtonGroup group2= new ButtonGroup();
		group2.add(rdbtnNewRadioButton);
		group2.add(rdbtnNewRadioButton_1);
		group2.add(rdbtnNewRadioButton_2);
		group2.add(rdbtnNewRadioButton_3);
		
		JRadioButton radioButton = new JRadioButton("Purple");
		radioButton.setForeground(new Color(255, 255, 204));
		radioButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		radioButton.setBackground(new Color(0, 102, 153));
		radioButton.setBounds(167, 147, 124, 28);
		window2.getFrame().getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("White");
		radioButton_1.setForeground(new Color(255, 255, 204));
		radioButton_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		radioButton_1.setBackground(new Color(0, 102, 153));
		radioButton_1.setBounds(167, 194, 124, 31);
		window2.getFrame().getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("Yellow");
		radioButton_2.setForeground(new Color(255, 255, 204));
		radioButton_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		radioButton_2.setBackground(new Color(0, 102, 153));
		radioButton_2.setBounds(167, 244, 124, 31);
		window2.getFrame().getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("Blue");
		radioButton_3.setForeground(new Color(255, 255, 204));
		radioButton_3.setFont(new Font("Verdana", Font.PLAIN, 12));
		radioButton_3.setBackground(new Color(0, 102, 153));
		radioButton_3.setBounds(167, 292, 124, 28);
		radioButton_3.setSelected(true);
		window2.getFrame().getContentPane().add(radioButton_3);
		
		ButtonGroup group3=new ButtonGroup();
		group3.add(radioButton);
		group3.add(radioButton_1);
		group3.add(radioButton_2);
		group3.add(radioButton_3);
		
		
		JRadioButton radioButton_4 = new JRadioButton("Grey");
		radioButton_4.setForeground(new Color(255, 255, 204));
		radioButton_4.setFont(new Font("Verdana", Font.PLAIN, 12));
		radioButton_4.setBackground(new Color(0, 102, 153));
		radioButton_4.setBounds(367, 127, 124, 28);
		window2.getFrame().getContentPane().add(radioButton_4);
		
		JRadioButton rdbtnBrown = new JRadioButton("Brown");
		rdbtnBrown.setForeground(new Color(255, 255, 204));
		rdbtnBrown.setFont(new Font("Verdana", Font.PLAIN, 12));
		rdbtnBrown.setBackground(new Color(0, 102, 153));
		rdbtnBrown.setBounds(367, 180, 124, 28);
		rdbtnBrown.setSelected(true);
		window2.getFrame().getContentPane().add(rdbtnBrown);
		
		
		
		ButtonGroup group4=new ButtonGroup();
		group4.add(radioButton_4);
		
		group4.add(rdbtnBrown);
		
		
		
		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window2.getFrame().dispose();
				new Settings();
			}
		});
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(120, 362, 185, 39);
		window2.getFrame().getContentPane().add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("Apply");
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		if( (rdbtnNewRadioButton.isSelected() &&  radioButton.isSelected()) ||  (rdbtnNewRadioButton_1.isSelected() && radioButton_1.isSelected()) ||(  rdbtnNewRadioButton_2.isSelected() && radioButton_2.isSelected()) || (rdbtnNewRadioButton_3.isSelected()&& radioButton_3.isSelected())  ){	
				
				
				JOptionPane.showMessageDialog(null, "Checkers color must not be the same!");
				
				
		}
		else{
				if(rdbtnNewRadioButton.isSelected()){
					player1=1;
				}
				else if(rdbtnNewRadioButton_1.isSelected()){
					player1=2;
				}
				else if(rdbtnNewRadioButton_2.isSelected()){
					player1=3;
				}
				else if(rdbtnNewRadioButton_3.isSelected()){
					player1=4;
				}
			
			if( radioButton.isSelected()){
				player2=1;
			}
			else if(radioButton_1.isSelected()){
				player2=2;
			}
			else if(radioButton_2.isSelected()){
				player2=3;
			}
			else if(radioButton_3.isSelected()){
				player2=4;
			}
			
			
			
				if(rdbtnBrown.isSelected()){
					boardcolor=0;
				}
				else if(radioButton_4.isSelected()){
					boardcolor=1;
				}
				 JOptionPane.showMessageDialog(null, "Your changes have been applied!");
				
			}
			}
		});
		btnNewButton2.setBackground(new Color(0, 102, 153));
		btnNewButton2.setForeground(new Color(255, 255, 204));
		btnNewButton2.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton2.setBounds(320, 362, 185, 39);
		window2.getFrame().getContentPane().add(btnNewButton2);
		
		
		
		window2.getFrame().setVisible(true);
		}
	
	public void Gamesettings(){
		
		Window window3=new Window("Game Settings",600,440);
		
		/*JFrame frame =new JFrame("Game Settings");
		frame.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 12));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setMaximumSize(new Dimension(600, 440));
		frame.setMinimumSize(new Dimension(600, 440));*/
		window3.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);*/
		
		JLabel lblNewLabel = new JLabel("Game Settings");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window3.getFrame().getContentPane().add(lblNewLabel);
		
		JCheckBox rdbtnDoubleDiceEnabled = new JCheckBox("Double dice enabled");
		rdbtnDoubleDiceEnabled.setSelected(true);
		rdbtnDoubleDiceEnabled.setBackground(new Color(0, 102, 153));
		rdbtnDoubleDiceEnabled.setForeground(new Color(255, 255, 204));
		rdbtnDoubleDiceEnabled.setFont(new Font("Verdana", Font.PLAIN, 12));
		rdbtnDoubleDiceEnabled.setBounds(332, 74, 161, 50);
		window3.getFrame().getContentPane().add(rdbtnDoubleDiceEnabled);
		
		JLabel lblNewLabel_1 = new JLabel("Number of games for tournament:");
		
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 79, 231, 40);
		window3.getFrame().getContentPane().add(lblNewLabel_1);
		
		JTextField textField = new JTextField();
		textField.setBounds(236, 90, 37, 20);
		window3.getFrame().getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("5");
		textField.setEditable(true);
		
		JLabel lblNewLabel_2 = new JLabel("Order of Games");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(189, 155, 192, 26);
		window3.getFrame().getContentPane().add(lblNewLabel_2);
		
		JRadioButton chckbxNewCheckBox = new JRadioButton("Manual");
		chckbxNewCheckBox.setBackground(new Color(0, 102, 153));
		chckbxNewCheckBox.setForeground(new Color(255, 255, 204));
		chckbxNewCheckBox.setFont(new Font("Verdana", Font.PLAIN, 12));
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxNewCheckBox.setBounds(57, 200, 131, 40);
		window3.getFrame().getContentPane().add(chckbxNewCheckBox);
		
		JRadioButton chckbxInOrder = new JRadioButton("In Order");
		chckbxInOrder.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxInOrder.setForeground(new Color(255, 255, 204));
		chckbxInOrder.setFont(new Font("Verdana", Font.PLAIN, 12));
		chckbxInOrder.setBackground(new Color(0, 102, 153));
		chckbxInOrder.setBounds(218, 200, 131, 40);
		chckbxInOrder.setSelected(true);
		window3.getFrame().getContentPane().add(chckbxInOrder);
		
		JRadioButton chckbxRandom = new JRadioButton("Random");
		chckbxRandom.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxRandom.setForeground(new Color(255, 255, 204));
		chckbxRandom.setFont(new Font("Verdana", Font.PLAIN, 12));
		chckbxRandom.setBackground(new Color(0, 102, 153));
		chckbxRandom.setBounds(397, 200, 131, 40);
		window3.getFrame().getContentPane().add(chckbxRandom);
		
		ButtonGroup group=new  ButtonGroup();
		group.add(chckbxRandom);
		group.add(chckbxInOrder);
		group.add(chckbxNewCheckBox);
		
		
		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window3.getFrame().dispose();
				new Settings();
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setBounds(220, 343, 161, 40);
		window3.getFrame().getContentPane().add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("Apply");
		
		btnNewButton2.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnNewButton2.setBackground(new Color(0, 102, 153));
		btnNewButton2.setForeground(new Color(255, 255, 204));
		btnNewButton2.setBounds(220, 280, 161, 40);
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnDoubleDiceEnabled.isSelected()){
					doubledice=true;
				}
				else if(!rdbtnDoubleDiceEnabled.isSelected()){
					doubledice=false;
				}
				
				
				
				if(isValid(textField.getText()) ){
				
				int order =0,numberofgames;
				if(chckbxNewCheckBox.isSelected()){
					order=1;
					
					
				}
				else if(chckbxInOrder.isSelected()){
					order=2;
				}
				else if(chckbxRandom.isSelected()){
					order=3;
				}
			
				String number=textField.getText();
			    numOfGames= Integer.parseInt(number) ;
			    
			    list=Settings.getSettings(order, numOfGames);
			   
			    JOptionPane.showMessageDialog(null, "Your changes have been applied!");
			    
			   
			    	
			    
			}
			
			else{
				JOptionPane.showMessageDialog(null, "You must input a valid number of games to be played! \n Number of games must be between 1-8!");
			}
			}
			
		});
		
		window3.getFrame().getContentPane().add(btnNewButton2);
		
		
		
		
		
		window3.getFrame().setVisible(true);
		
		
		
		
		
	}
	public static ArrayList getSettings(int order , int numberofgames){
		flag=true;
		ArrayList list =new ArrayList();
		
		list.add( order);
		list.add( numberofgames);
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
			 if(validtext<=0 || validtext > 9){
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

	
	
	}


