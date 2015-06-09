import java.awt.Color;







import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;


public class CreatePlayer extends JFrame {
	private JList list=new JList();
	private String Player;
    private Window window=new Window("Create Player",500,400);
	private int numlines=0;
	public CreatePlayer(){
		
		//JFrame frame = new JFrame();
	window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Create Player");
		frame.getContentPane().setLayout(null);
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("Create Player");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel.setBounds(127, 0, 234, 70);
		frame.getContentPane().add(lblNewLabel);*/
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setFont(new Font("Verdana", Font.PLAIN, 17));
		formattedTextField.setBackground(new Color(255, 255, 204));
		formattedTextField.setBounds(127, 81, 184, 46);
window.getFrame().getContentPane().add(formattedTextField);
		
		JLabel lblNewLabel_1 = new JLabel("Insert Name");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 81, 107, 46);
		window.getFrame().getContentPane().add(lblNewLabel_1);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Player = formattedTextField.getText();
				if (Player.contains(" ")){
					JOptionPane.showMessageDialog(null, "Player name must not contain spaces!");
				}
				else if(Player.equals("")){
					JOptionPane.showMessageDialog(null, "The player's name is empty.Please \n type a player's name!");
				}
				else if(!Player.matches("[a-zA-Z]+")){
					JOptionPane.showMessageDialog(null,"The player's name must contain only English characters!");
					
				}
				
				
				else{
				File file = new File("Players.txt");
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int flag=0;
				
				
				while (scanner.hasNextLine()) {
				   final String lineFromFile = scanner.nextLine();
				   if(lineFromFile.equals(Player)) { 
				       flag=1;
					   JOptionPane.showMessageDialog(null, "Player already exists!!");
				       break;
				   }
				}
				
				
				
			  
				if(flag==0 && numlines<10){
					 BufferedWriter bw = null;
					 try {
				       
				         bw = new BufferedWriter(new FileWriter("Players.txt", true));
				     bw.write(Player);
				     bw.newLine();
				     bw.flush();
				      } catch (IOException ioe) {
				     ioe.printStackTrace();
				      } finally {                       
				     if (bw != null) try {
				        bw.close();
				     } catch (IOException ioe2) {
				        
				     }
				      } 
				 
				   
					JOptionPane.showMessageDialog(null, "Player created successfully!");
				}
				else if(flag==0 && numlines>=10){
					JOptionPane.showMessageDialog(null,"You cannot add more than 10 players.Please\n delete at least one player in order to create a new one!");
				}
				
				 try {
					 numlines=CountLines("Players.txt");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				} }
			
				
			
		});
		btnOk.setBackground(new Color(0, 102, 153));
		btnOk.setForeground(new Color(255, 255, 204));
		btnOk.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnOk.setBounds(321, 84, 118, 43);
		window.getFrame().getContentPane().add(btnOk);
		
		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
             new Menu();
			}
		});
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnNewButton.setBounds(168, 307, 143, 53);
		window.getFrame().getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete Players");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				ListofPlayers();
				
			}
		});
		btnNewButton_1.setForeground(new Color(255, 255, 204));
		btnNewButton_1.setBackground(new Color(0, 102, 153));
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBounds(168, 213, 143, 53);
		window.getFrame().getContentPane().add(btnNewButton_1);
		window.getFrame().setVisible(true);
		
		
	}
	public int CountLines(String filename) throws IOException{
		LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(filename)));
		lnr.skip(Long.MAX_VALUE);
		return lnr.getLineNumber() ; 
		
		
	}
	public void ListofPlayers(){
		
		Window window=new Window("Delete Players",500,400);
		
		//JFrame frame = new JFrame();
		window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setResizable(false);*/
		window.getFrame().setVisible(true);
		/*frame.setTitle("Create Player");
		frame.getContentPane().setLayout(null);
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("Create Player");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel.setBounds(127, 0, 234, 70);
		frame.getContentPane().add(lblNewLabel);*/
		
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setForeground(new Color(0, 51, 51));
		list.setFont(new Font("Verdana", Font.PLAIN, 14));
		list.setBackground(new Color(204, 204, 204));
		list.setBounds(35, 81, 171, 163);
	
		window.getFrame().getContentPane().add(list);
		
		
		
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
			    
			    JButton btnNewButton = new JButton("Delete Player");
			    btnNewButton.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    	
			    		
			    		if(list.isSelectionEmpty()){
			    			JOptionPane.showMessageDialog(null, "You must select a player from the \nlist to be deleted!");
			    		}
			    		else{
			    			String delplayer=(String) list.getSelectedValue();
			    		
			    			deleteLine(delplayer,"Players.txt");
                            JOptionPane.showMessageDialog(null, "Player deleted successfully");
			    		
			    			
			    			window.getFrame().dispose();
			    		 ListofPlayers();
						
			    		
			    		
			    		
			    		}
			    	
			    		
			    		
			    		
			    		
			    		
			    		
			    		
			    		
			    		
			    		
			    		
			    	}
			    });
			    btnNewButton.setForeground(new Color(255, 255, 204));
			    btnNewButton.setBackground(new Color(0, 102, 153));
			    btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
			    btnNewButton.setBounds(272, 138, 142, 54);
			    window.getFrame().getContentPane().add(btnNewButton);
			    
			    JButton btnGoBack = new JButton("Go Back");
			    btnGoBack.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		window.getFrame().dispose();
			    		new CreatePlayer();
			    	}
			    });
			    btnGoBack.setForeground(new Color(255, 255, 204));
			    btnGoBack.setFont(new Font("Verdana", Font.PLAIN, 14));
			    btnGoBack.setBackground(new Color(0, 102, 153));
			    btnGoBack.setBounds(197, 289, 142, 54);
			    window.getFrame().getContentPane().add(btnGoBack);
			    in.close();
		    } catch (Exception e) {
		    }
		
		
		
}
		
		
	public static void deleteLine(String line, String filePath) {

	    File file = new File(filePath);

	    File file2 = new File("temp.txt");
	    PrintWriter pw = null;
	    Scanner read = null;

	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    FileChannel src = null;
	    FileChannel dest = null;

	    try {


	        pw = new PrintWriter(file2);
	        read = new Scanner(file);

	        while (read.hasNextLine()) {

	            String currline = read.nextLine();

	            if (line.equalsIgnoreCase(currline)) {
	                continue;
	            } else {
	                pw.println(currline);
	            }
	        }

	        pw.flush();

	        fis = new FileInputStream(file2);
	        src = fis.getChannel();
	        fos = new FileOutputStream(file);
	        dest = fos.getChannel();

	        dest.transferFrom(src, 0, src.size());


	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {     
	        pw.close();
	        read.close();

	        try {
	            fis.close();
	            fos.close();
	            src.close();
	            dest.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        file2.delete();
	    }

	}
		
		
		
		
		
		
		
	}

