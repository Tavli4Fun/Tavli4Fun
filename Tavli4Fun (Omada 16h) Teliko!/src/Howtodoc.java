import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Howtodoc extends JFrame  {
	  
	
	public Howtodoc(String type) throws IOException{
		Window window=new Window(type,592,463);
		
		//JFrame howtodoc =new JFrame(type);
		window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*howtodoc.setResizable(false);
		howtodoc.setSize(592, 463);
		howtodoc.setMinimumSize(new Dimension(500, 400));
		howtodoc.setLocationRelativeTo(null);
		howtodoc.getContentPane().setBackground(new Color(255, 153, 0));
		howtodoc.getContentPane().setLayout(null);*/
		
		JLabel label = new JLabel("How to play "+type);
		label.setFont(new Font("Verdana", Font.PLAIN, 18));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(162, 11, 257, 50);
		window.getFrame().getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 74, 539, 258);
		window.getFrame().getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 538, 261);
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(new Color(0, 51, 153));
		textArea.setFont(new Font("Verdana", Font.PLAIN, 13));
		textArea.setBackground(new Color(204, 204, 204));
		textArea.setEditable(false);
		 FileReader reader = new FileReader(type+".txt");
         textArea.read(reader,type+".txt");
		
		
	scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.getFrame().dispose();
				new Howto();
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(222, 349, 158, 55);
		window.getFrame().getContentPane().add(btnNewButton);
		window.getFrame().setVisible(true);
	}
	}

