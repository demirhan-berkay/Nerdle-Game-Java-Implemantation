package nerdle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private FileOperations fileOps;
	private JPanel contentPane;
	private JTextField winsText;
	private JTextField losesText;
	private JTextField interruptedText;
	private JTextField linesText;
	private JTextField timeText;
	private JTextField textField;

	public MainMenu() {
		fileOps = new FileOperations();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel MainPanel = new JPanel();
		MainPanel.setBackground(SystemColor.info);
		MainPanel.setBounds(0, 0, 434, 492);
		contentPane.add(MainPanel);
		MainPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Main Menu");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(123, 11, 164, 34);
		MainPanel.add(lblNewLabel);

		JLabel statistic1 = new JLabel("Wins:");
		statistic1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		statistic1.setBounds(10, 62, 93, 25);
		MainPanel.add(statistic1);

		JLabel statistic2 = new JLabel("Loses:");
		statistic2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		statistic2.setBounds(10, 98, 93, 25);
		MainPanel.add(statistic2);

		JLabel statistic3 = new JLabel("Interrupted Games:");
		statistic3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		statistic3.setBounds(10, 134, 125, 25);
		MainPanel.add(statistic3);

		JLabel statistic4 = new JLabel("Average Lines:");
		statistic4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		statistic4.setBounds(10, 170, 93, 25);
		MainPanel.add(statistic4);

		JLabel statistic5 = new JLabel("Average Time:");
		statistic5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		statistic5.setBounds(10, 206, 104, 25);
		MainPanel.add(statistic5);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File("interruptedGame.txt");//Yeni oyun açýyor
				if (f.exists() && !f.isDirectory()) {
					f.delete();
				}
				GameScreen g = new GameScreen(fileOps, false);
				setVisible(false);
				g.setVisible(true);

			}
		});
		btnNewGame.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnNewGame.setBounds(10, 302, 164, 42);
		MainPanel.add(btnNewGame);

		JButton btnResume = new JButton("Resume");
		btnResume.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnResume.setBounds(260, 302, 164, 42);
		btnResume.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				File f = new File("interruptedGame.txt");
				if (f.exists() && !f.isDirectory()) {//Eðer yarýda býrakýlmýþ oyun varsa

					boolean isHalfGame = true;//Oyunu açýyor
					GameScreen g = new GameScreen(fileOps, isHalfGame);
					g.setVisible(true);
					setVisible(false);
				}

			}

		});
		MainPanel.add(btnResume);

		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Test t = new Test();//Sürekli denklem oluþturan ekraný açýyor
				t.setVisible(true);

			}
		});
		btnTest.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnTest.setBounds(123, 375, 164, 42);
		MainPanel.add(btnTest);

		winsText = new JTextField();
		winsText.setBackground(SystemColor.info);
		winsText.setEditable(false);
		winsText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		winsText.setHorizontalAlignment(SwingConstants.CENTER);
		winsText.setBounds(202, 64, 152, 22);
		winsText.setText(Integer.toString(fileOps.getSuccessfulGames()));//Dosyadan istatistik çekiyor
		MainPanel.add(winsText);
		winsText.setColumns(10);

		losesText = new JTextField();
		losesText.setHorizontalAlignment(SwingConstants.CENTER);
		losesText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		losesText.setEditable(false);
		losesText.setColumns(10);
		losesText.setBackground(SystemColor.info);
		losesText.setBounds(202, 101, 152, 22);
		losesText.setText(Integer.toString(fileOps.getFailedGames()));//Dosyadan istatistik çekiyor
		MainPanel.add(losesText);

		interruptedText = new JTextField();
		interruptedText.setHorizontalAlignment(SwingConstants.CENTER);
		interruptedText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		interruptedText.setEditable(false);
		interruptedText.setColumns(10);
		interruptedText.setBackground(SystemColor.info);
		interruptedText.setBounds(202, 137, 152, 22);
		interruptedText.setText(Integer.toString(fileOps.getHalfGame()));//Dosyadan istatistik çekiyor
		MainPanel.add(interruptedText);

		linesText = new JTextField();
		linesText.setHorizontalAlignment(SwingConstants.CENTER);
		linesText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		linesText.setEditable(false);
		linesText.setColumns(10);
		linesText.setBackground(SystemColor.info);
		linesText.setBounds(202, 173, 152, 22);
		BigDecimal bd = new BigDecimal(fileOps.getAverageLines()).setScale(2, RoundingMode.HALF_DOWN);

		linesText.setText(bd.toString());//Dosyadan istatistik çekiyor
		MainPanel.add(linesText);

		timeText = new JTextField();
		timeText.setHorizontalAlignment(SwingConstants.CENTER);
		timeText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		timeText.setEditable(false);
		timeText.setColumns(10);
		timeText.setBackground(SystemColor.info);
		timeText.setBounds(202, 209, 152, 22);
		timeText.setText(Integer.toString(fileOps.getAverageSeconds()) + " seconds");//Dosyadan istatistik çekiyor
		MainPanel.add(timeText);

		textField = new JTextField();
		textField.setText("13-19011085 Osman Yi\u011Fit S\u00F6kel-19011075 Berkay Demirhan");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(0, 451, 424, 19);
		MainPanel.add(textField);
	}

}
