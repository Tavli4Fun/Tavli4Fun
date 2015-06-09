import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;


public class Scores extends JFrame{
	private Window window=new Window("Scores",600,440);
	public Scores(){
		/*JFrame frame =new JFrame("Scores");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 153, 0));
		frame.setMaximumSize(new Dimension(600, 440));
		frame.setMinimumSize(new Dimension(600, 440));*/
		window.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		//frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Scores");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel.setBounds(205, 11, 212, 45);
		window.getFrame().getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 204, 255));
		panel.setForeground(new Color(102, 153, 204));
		panel.setBounds(10, 66, 574, 186);
		window.getFrame().getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Player           Plakoto         Portes          Asodyo         Fevga          Total Points");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 11, 570, 22);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFrame().dispose();
				new Menu();
			}
		});
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setForeground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(230, 331, 144, 45);
		window.getFrame().getContentPane().add(btnNewButton);
		window.getFrame().setVisible(true);
		
	}
}
