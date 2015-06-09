import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	private JFrame frame;
	public static final int  WIDTH = 600, HEIGHT =  WIDTH / 9*8; //600x534 // 800x712
	
	public Window(String title){
		frame = new JFrame(title);
		
		
		frame.setMaximumSize(new Dimension(300, 300*7/8));
		frame.setMinimumSize(new Dimension(WIDTH+24, HEIGHT+72));
		//frame.setPreferredSize(new Dimension(width, height));
		
		//frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.pack();
		frame.setVisible(true);
	}

	
	public Window(String title,int width,int height){
		 frame =new JFrame(title);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width,height));
		
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	
}
