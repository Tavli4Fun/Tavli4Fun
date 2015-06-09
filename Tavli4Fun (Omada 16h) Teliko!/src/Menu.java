import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Menu extends JFrame {
	
	private Window window = new Window("TAVLI 4 FUN",600,440);;
	
public Menu(){
	
	
	/*JFrame frame =new JFrame("Tavli4Fun");
	frame.setResizable(false);
	frame.getContentPane().setBackground(new Color(255, 153, 0));
	frame.setMaximumSize(new Dimension(600, 440));
	frame.setMinimumSize(new Dimension(600, 440));*/
	window.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	/*frame.setLocationRelativeTo(null);
	frame.getContentPane().setLayout(null);*/
	JButton btnNewPlayer =new JButton("New Player");
	btnNewPlayer.setForeground(new Color(255, 255, 204));
	btnNewPlayer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			CreatePlayer createframe=new CreatePlayer();
			window.getFrame().dispose();
			
		}
	});
	
	btnNewPlayer.setFont(new Font("Verdana", Font.PLAIN, 18));
	btnNewPlayer.setBackground(new Color(0, 102, 153));
	btnNewPlayer.setBounds(93, 117, 140, 60);
	window.getFrame().getContentPane().add(btnNewPlayer);
	
	JButton btnNewButton = new JButton("Play");
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader("Players.txt"));
				if (br.readLine() == null) {
				    JOptionPane.showMessageDialog(null, "You have to create at least one player in order to play!");
				}
				else{
					window.getFrame().dispose();
					new Mode();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}     
			
		}
	});
	btnNewButton.setForeground(new Color(255, 255, 204));
	btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 18));
	btnNewButton.setBackground(new Color(0, 102, 153));
	btnNewButton.setBounds(93, 214, 140, 60);
	window.getFrame().getContentPane().add(btnNewButton);
	
	JButton btnNewButton_1 = new JButton("How to Play");
	btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Howto howto=new Howto();
			window.getFrame().dispose();
		}
	});
	btnNewButton_1.setForeground(new Color(255, 255, 204));
	btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 16));
	btnNewButton_1.setBackground(new Color(0, 102, 153));
	btnNewButton_1.setBounds(345, 158, 140, 60);
	window.getFrame().getContentPane().add(btnNewButton_1);
	
	JButton btnNewButton_2 = new JButton("Scores");
	btnNewButton_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			window.getFrame().dispose();
			new Scores();
		}
	});
	btnNewButton_2.setForeground(new Color(255, 255, 204));
	btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 18));
	btnNewButton_2.setBackground(new Color(0, 102, 153));
	btnNewButton_2.setBounds(93, 303, 140, 60);
	window.getFrame().getContentPane().add(btnNewButton_2);
	
	JButton btnNewButton_3 = new JButton("Settings");
	btnNewButton_3.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			window.getFrame().dispose();
			new Settings();
		}
	});
	btnNewButton_3.setForeground(new Color(255, 255, 204));
	btnNewButton_3.setFont(new Font("Verdana", Font.PLAIN, 18));
	btnNewButton_3.setBackground(new Color(0, 102, 153));
	btnNewButton_3.setBounds(345, 268, 140, 60);
	window.getFrame().getContentPane().add(btnNewButton_3);
	
	JLabel lblNewLabel = new JLabel("TAVLI 4 FUN");
	lblNewLabel.setBackground(Color.WHITE);
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setForeground(new Color(0, 0, 102));
	lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 27));
	lblNewLabel.setBounds(134, 30, 327, 76);
	window.getFrame().getContentPane().add(lblNewLabel);
	
	window.getFrame().pack();
	window.getFrame().setVisible(true);
	
}
}
