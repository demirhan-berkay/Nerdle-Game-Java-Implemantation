package nerdle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Lose extends JFrame {

	private JPanel contentPane;
	private JTextField Lose;
	private JTextField trueEq;
	private JTextField txtFooter;

	public Lose(String eq) {

		setResizable(false);
		setBounds(100, 100, 375, 220);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Lose = new JTextField();
		Lose.setFont(new Font("Times New Roman", Font.BOLD, 40));
		Lose.setText("You Lose!");
		Lose.setHorizontalAlignment(SwingConstants.CENTER);
		Lose.setEditable(false);
		Lose.setBackground(Color.RED);
		Lose.setBounds(0, 0, 359, 74);
		contentPane.add(Lose);
		Lose.setColumns(10);

		JLabel lblNewLabel = new JLabel("True Equation :");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBackground(Color.YELLOW);
		lblNewLabel.setBounds(0, 85, 107, 30);
		contentPane.add(lblNewLabel);

		trueEq = new JTextField();
		trueEq.setHorizontalAlignment(SwingConstants.CENTER);
		trueEq.setFont(new Font("Tahoma", Font.PLAIN, 13));
		trueEq.setText(eq);
		trueEq.setEditable(false);
		trueEq.setBackground(Color.GREEN);
		trueEq.setBounds(105, 84, 254, 31);
		contentPane.add(trueEq);
		trueEq.setColumns(10);

		JButton returnMain = new JButton("Return Main Menu");
		returnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.gc();
				for (Window window : getWindows()) {//Main Menü'ye basýldýðýnda bulunan bütün pencereleri kapatýyor
					window.dispose();
				}
				MainMenu m = new MainMenu();//Main Menuyu açýyor
				m.setVisible(true);
			}
		});
		returnMain.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		returnMain.setBounds(69, 125, 220, 28);
		contentPane.add(returnMain);

		txtFooter = new JTextField();
		txtFooter.setText("13-19011085 Osman Yi\u011Fit S\u00F6kel-19011075 Berkay Demirhan");
		txtFooter.setHorizontalAlignment(SwingConstants.CENTER);
		txtFooter.setEditable(false);
		txtFooter.setBounds(0, 154, 359, 19);
		contentPane.add(txtFooter);
		txtFooter.setColumns(10);
	}
}
