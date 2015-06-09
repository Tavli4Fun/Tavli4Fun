import javax.swing.*;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class Howto extends JFrame {
	
	private Window window=new Window("How to Play",500,400);
	
	public Howto(){
		//JFrame frame = new JFrame();
		window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frame.getContentPane().setBackground(new Color(255, 153, 0));
		//frame.setResizable(false);
		//frame.setVisible(true);
		//frame.setTitle("How to Play");
		//frame.getContentPane().setLayout(null);
		//frame.setSize(500, 400);
		//frame.setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("How to Play");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNewLabel.setBounds(127, 0, 234, 70);
		window.getFrame().getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("\u03A0\u03BB\u03B1\u03BA\u03C9\u03C4\u03CC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {window.getFrame().dispose();
			try {
				Howtodoc howtodoc=new Howtodoc("Plakoto");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setName("");
		btnNewButton.setBounds(35, 94, 200, 50);
		window.getFrame().getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u03A6\u03B5\u03CD\u03B3\u03B1");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				try {
					Howtodoc howtodoc=new Howtodoc("Fevga");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBackground(new Color(0, 102, 153));
		btnNewButton_1.setForeground(new Color(255, 255, 204));
		btnNewButton_1.setBounds(273, 94, 200, 50);
		window.getFrame().getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\u03A0\u03CC\u03C1\u03C4\u03B5\u03C2");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				window.getFrame().dispose();
				try {
					Howtodoc howtodoc=new Howtodoc("Portes");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_2.setBackground(new Color(0, 102, 153));
		btnNewButton_2.setForeground(new Color(255, 255, 204));
		btnNewButton_2.setBounds(35, 171, 200, 50);
		window.getFrame().getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("\u0391\u03C3\u03C3\u03CC\u03B4\u03C5\u03BF");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				try {
					Howtodoc howtodoc=new Howtodoc("Asodyo");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
		});
		btnNewButton_3.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_3.setBackground(new Color(0, 102, 153));
		btnNewButton_3.setForeground(new Color(255, 255, 204));
		btnNewButton_3.setBounds(273, 171, 200, 50);
		window.getFrame().getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Go Back");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
	             new Menu();
			}
		});
		btnNewButton_4.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_4.setBackground(new Color(0, 102, 153));
		btnNewButton_4.setForeground(new Color(255, 255, 204));
		btnNewButton_4.setBounds(161, 278, 200, 50);
		window.getFrame().getContentPane().add(btnNewButton_4);
		window.getFrame().setVisible(true);
		
	}
}
