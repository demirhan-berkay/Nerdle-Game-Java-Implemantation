package nerdle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GameScreen extends JFrame {

	private GameOperations gameOps;
	private Equation equation;
	@SuppressWarnings("unused")
	private FileOperations fileOps;
	private JPanel TitlePanel;
	ArrayList<JPanel> lines = new ArrayList<>();
	private JTextField textField;
	private JPanel tempLine;
	private JTextField tempInput;
	JTextField[][] inputs;
	ArrayList<JTextField> tempVariables = new ArrayList<>();
	int countLine = 0;
	int i, j;
	private JTextField timeField;

	public GameScreen(FileOperations fileOps, boolean isHalfGame) {

		class TimerTask extends java.util.TimerTask {
			private int seconds;

			public void run() {// Oyun ekran�ndaki s�renin saniye saniye de�i�mesini sa�l�yor
				int minute;
				minute = seconds / 60;
				timeField.setText(String.valueOf(minute) + "." + String.valueOf(seconds % 60));
				seconds++;
			}

			public int getSeconds() {
				return this.seconds;
			}

			public void setSeconds(int seconds) {
				this.seconds = seconds;
			}

		}

		timeField = new JTextField();
		timeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeField.setEditable(false);
		timeField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		Timer timer = new Timer();

		TimerTask task = new TimerTask();

		timer.schedule(task, 0, 1000);

		String[] splitted = null;
		if (isHalfGame) {// E�er oyun yar�da b�rak�lm�� ise devam etmeyi sa�l�yor

			String data = null;
			try {
				File myObj = new File("interruptedGame.txt");// Yar�da b�rak�lan oyuna
				Scanner myReader = new Scanner(myObj); // devam etmek i�in gerekli elemanlar� ve
				while (myReader.hasNextLine()) { // objeleri �ekiyor
					data = myReader.nextLine();
				}
				myReader.close();
			} catch (FileNotFoundException e) {
			}

			splitted = data.split("\\s+");
			countLine = Integer.parseInt(splitted[0]);
			this.equation = new Equation(Integer.parseInt(splitted[2]), splitted[1]);
			task.setSeconds(Integer.parseInt(splitted[3]));

			fileOps.decreaseHalfGame(); // Oyuna devam edildi�i i�in bu oyunun yar�da
										// b�rak�lmas�n� inaktif ediyor
		} else {
			equation = new Equation();
		}
		this.fileOps = fileOps;
		inputs = new JTextField[6][equation.getEquationLength()];
		setResizable(false);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 525, 600);
		TitlePanel = new JPanel();
		TitlePanel.setBackground(SystemColor.info);
		TitlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(TitlePanel);
		TitlePanel.setLayout(null);

		JLabel Title = new JLabel("Game");
		Title.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setBounds(110, 11, 252, 43);
		TitlePanel.add(Title);

		for (i = 0; i < 6; i++) {
			JPanel panel = new JPanel(); // Paneller ile sat�rlar olu�turuluyor
			panel.setBackground(SystemColor.info);
			panel.setBounds(10, 65 + (54 * i), 489, 40);
			TitlePanel.add(panel);
			panel.setLayout(new GridLayout(0, equation.getEquationLength(), 5, 5));
			panel.setEnabled(false);
			lines.add(i, panel); // Her panel arraylist'e ekleniyor
			for (j = 0; j < equation.getEquationLength(); j++) {// Her panele denklem uzunlu�unda TextField ekleniyor
				textField = new JTextField();
				textField.setText(null);
				inputs[i][j] = textField;
				textField.setHorizontalAlignment(SwingConstants.CENTER);
				textField.setFont(new Font("Times New Roman", Font.PLAIN, 25));
				textField.setEditable(false);
				textField.setBackground(Color.GRAY);
				panel.add(textField);
				textField.setColumns(10);
			}
		}
		int index = 4;
		String str, str2;

		for (i = 0; i < countLine; i++) { // Yar�da b�rak�lan devam edildi�i durumda ya da
			for (j = 0; j < equation.getEquationLength(); j++) {// Yeni oyuna ba�land��� durumda
				str = splitted[index]; // Gerekli boyamalar� yap�yor ve
				str2 = splitted[index + 1]; // daha �nce girilen de�erleri yerle�tiriyor
				if (str.contains("r=255,g=0,b=0")) {
					inputs[i][j].setBackground(Color.RED);
					inputs[i][j].setText(str2);
				} else if (str.contains("r=255,g=255,b=0")) {
					inputs[i][j].setBackground(Color.YELLOW);
					inputs[i][j].setText(str2);

				} else {
					inputs[i][j].setBackground(Color.GREEN);
					inputs[i][j].setText(str2);
				}
				index = index + 2;
			}

		}

		inputs[countLine][0].setBackground(Color.WHITE);
		lines.get(countLine).setEnabled(true);
		tempInput = inputs[countLine][0]; // �lk giri� yap�lacak eleman se�iliyor
		tempLine = lines.get(countLine); // �lk giri� yap�lacaksat�r se�iliyor
		for (i = 0; i < equation.getEquationLength(); i++) {
			tempVariables.add(inputs[countLine][i]);// O sat�rdaki textFieldlar arraylistte tutuluyor
		}
		for (JTextField obj : tempVariables) {
			obj.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (countLine < 6) {
						if (lines.get(countLine).isEnabled()) {// O sat�rdaki de�erlerin se�ilebilmesi i�in
							selectInput(lines.get(countLine), obj);// Mouse ile t�klayarak o inputun se�ilmesi
																	// sa�lan�yor
						}
					}
				}
			});
		}

		JPanel numbers = new JPanel();
		numbers.setBackground(SystemColor.info);
		numbers.setBounds(10, 430, 489, 43);
		TitlePanel.add(numbers);
		numbers.setLayout(new GridLayout(0, 10, 3, 3));

		for (i = 0; i < 10; i++) {
			JButton number = new JButton("" + i + "");
			number.addActionListener(new ActionListener() {// Buttonlar�n de�erleri atan�yor
				public void actionPerformed(ActionEvent e) {
					if (countLine < 6) {
						tempInput.setText(number.getText());// Butonlara bas�lmas� durumunda TextField'�n
						traverseInput(tempLine, tempVariables);// de�i�tirelece�i ayarlan�yor
					}
				}
			});
			number.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			numbers.add(number);
		}

		JPanel Operators = new JPanel();
		Operators.setBounds(10, 480, 246, 45);
		TitlePanel.add(Operators);
		Operators.setLayout(new GridLayout(1, 5, 0, 5));

		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {// Butona bas�lmas� durumunda + i�aretini at�yor
					tempInput.setText(btnAdd.getText());
					traverseInput(tempLine, tempVariables);
				}
			}
		});
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		Operators.add(btnAdd);

		JButton btnSub = new JButton("-");
		btnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {// Butona bas�lmas� durumunda - i�aretini at�yor
					tempInput.setText(btnSub.getText());
					traverseInput(tempLine, tempVariables);
				}
			}
		});
		btnSub.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		Operators.add(btnSub);

		JButton btnDiv = new JButton("/");
		btnDiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {// Butona bas�lmas� durumunda / i�aretini at�yor
					tempInput.setText(btnDiv.getText());
					traverseInput(tempLine, tempVariables);
				}
			}
		});
		btnDiv.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		Operators.add(btnDiv);

		JButton btnMul = new JButton("*");
		btnMul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {// Butona bas�lmas� durumunda * i�aretini at�yor
					tempInput.setText(btnMul.getText());
					traverseInput(tempLine, tempVariables);
				}
			}
		});
		btnMul.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		Operators.add(btnMul);

		JButton btnEq = new JButton("=");
		btnEq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {// Butona bas�lmas� durumunda = i�aretini at�yor
					tempInput.setText(btnEq.getText());
					traverseInput(tempLine, tempVariables);
				}

			}
		});
		btnEq.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		Operators.add(btnEq);

		JPanel Actions = new JPanel();
		Actions.setBounds(266, 480, 232, 45);
		TitlePanel.add(Actions);
		Actions.setLayout(new GridLayout(0, 2, 5, 5));

		JButton returnMenu = new JButton("Return Main Menu");
		returnMenu.setVisible(false);
		returnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false); // Main Menu'ye d�nen buton atan�yor
				MainMenu m = new MainMenu();
				m.setVisible(true);
			}
		});
		returnMenu.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		returnMenu.setBounds(360, 396, 139, 23);
		TitlePanel.add(returnMenu);

		JButton btnDel = new JButton("Delete");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {// Butona bas�ld��� durumda mevcut TextField'� temizliyor
					tempInput.setText(null);
					deleteInput(tempLine, tempVariables);// Mevcut inputun sola kaymas�n� sa�l�yor
				}
			}
		});
		btnDel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Actions.add(btnDel);

		timeField.setBackground(SystemColor.info);
		timeField.setBounds(422, 11, 77, 43);
		TitlePanel.add(timeField);
		timeField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Time :");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(336, 11, 77, 43);
		TitlePanel.add(lblNewLabel);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countLine < 6) {
					if (enterButton(lines)) {// Enter butonundan d�nen de�er true ise
						File f = new File("interruptedGame.txt");// Sonradan bitirilmesi planlanan oyun siliniyor
						f.delete();
						lines.get(countLine).setEnabled(false);// Yeni giri� yapmak engelleniyor
						fileOps.updateWinGames(countLine + 1, task.getSeconds());// Kazan�lan oyun,s�re,sat�r
																					// g�ncelleniyor
						fileOps.writeToFile();
						timer.cancel();// Zaman durduruluyor
						returnMenu.setVisible(true);
						Win w = new Win(task.getSeconds());// Kazanma ekran� ��k�yor
						w.setVisible(true);
						returnMenu.setVisible(true);
					} else {
						if (countLine >= 6) {
							File f = new File("interruptedGame.txt");// Enter butonundan d�nen false ise ve
							f.delete();// ba�ka hakk�m�zkalmam�� ise
							fileOps.updateFailedGames();// Sonradan bitirilmesi planlanan oyun siliniyor
							fileOps.writeToFile();// Kaybedilen oyun istatistiklere yans�yor
							timer.cancel();// zaman duruyor
							returnMenu.setVisible(true);
							Lose l = new Lose(equation.getEquation());// Kaybetme ekran� ��k�yor
							l.setVisible(true);
						}

					}

				}

			}
		});
		btnEnter.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		Actions.add(btnEnter);

		JTextField txtFooter = new JTextField();
		txtFooter.setBackground(SystemColor.info);
		txtFooter.setText("13-19011085 Osman Yi\u011Fit S\u00F6kel-19011075 Berkay Demirhan");
		txtFooter.setHorizontalAlignment(SwingConstants.CENTER);
		txtFooter.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtFooter.setEditable(false);
		txtFooter.setBounds(92, 534, 409, 29);
		TitlePanel.add(txtFooter);
		txtFooter.setColumns(10);

		JButton btnDelay = new JButton("Finish Later");
		btnDelay.setBounds(0, 0, 145, 45);
		TitlePanel.add(btnDelay);
		btnDelay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fileOps.increaseHalfGame();//Yar�da kalan oyun say�s� artt�r�l�yor
				fileOps.writeToFile();//Yar�da kalan oyun dosyaya yazd�r�l�yor
				setVisible(false);
				MainMenu m = new MainMenu();//Men�ye d�n�yor
				m.setVisible(true);
				try {
					File myObj = new File("interruptedGame.txt");
					myObj.createNewFile();
				} catch (IOException a) {
					a.printStackTrace();
				}

				try {
					FileWriter myWriter = new FileWriter("interruptedGame.txt");//Yar�da kalan oyun i�in dosyalar kaydediliyor
					myWriter.write(countLine + " " + equation.getEquation() + " " + equation.getEquationLength() + " "
							+ task.getSeconds() + " ");

					for (int i = 0; i < countLine; i++) {
						for (int j = 0; j < equation.getEquationLength(); j++) {
							myWriter.write(inputs[i][j].getBackground() + " ");
							myWriter.write(inputs[i][j].getText() + " ");
						}
					}
					myWriter.close();
				} catch (IOException b) {
					b.printStackTrace();
				}

			}
		});
		btnDelay.setFont(new Font("Times New Roman", Font.PLAIN, 20));

	}

	public void selectInput(JPanel panel, JTextField input) {

		tempInput.setBackground(Color.GRAY);//Daha �nceki input griye boyan�r
		if (panel.isEnabled()) {//Sat�r aktif ise
			tempInput = input;//Yeni se�ilen input beyaza boyunuyor
			tempInput.setBackground(Color.WHITE);
		}
	}

	public void enterInput(JPanel panel, JTextField input) {

		if (panel.isEnabled()) {
			tempInput = input;//Yeni sat�ra ge�ildi�inde alt sat�r�n ilk inputu se�iliyor
			tempInput.setBackground(Color.WHITE);
		}
	}

	public void traverseInput(JPanel panel, ArrayList<JTextField> list) {

		i = list.indexOf(tempInput);
		i++;
		if (i == equation.getEquationLength()) {//Girilen her de�er sonras� inputun sa� tarafa kaymas�n� sa�l�yor
			i = 0;//Denklem uzunlu�una gelince ba�a d�n�yor
		}
		tempInput.setBackground(Color.GRAY);
		tempInput = (JTextField) list.get(i);
		selectInput(panel, tempInput);//tekrar input se�iliyor

	}

	public void deleteInput(JPanel panel, ArrayList<JTextField> list) {

		i = list.indexOf(tempInput);
		if (i > 0) {//Inputtaki de�eri temizliyor
			tempInput.setBackground(Color.GRAY);
			i--;//Silinen de�erin solundaki inputa ge�i� yap�yor
			tempInput = (JTextField) list.get(i);
			selectInput(panel, tempInput);//Tekrar input se�iliyor
		}

	}

	public boolean enterButton(ArrayList<JPanel> list) {

		StringBuilder guess = new StringBuilder();

		for (JTextField obj : tempVariables) {
			guess.append(obj.getText());
		}//Tahmin edilen denklemi stringe �eviriyor

		Equation tmpequation = new Equation(equation.getEquationLength(), guess.toString());
		gameOps = new GameOperations(equation);

		if (guess.toString().length() != equation.getEquationLength()) {
			JOptionPane.showMessageDialog(null, "Fill in the Blanks");//Tahmin edilen denklemin uzunlu�u kar��la�t�r�l�yor
			return false;
		} else if (!gameOps.isEquation(guess.toString())) {//Denklemin do�rulu�una bak�l�yor
			JOptionPane.showMessageDialog(null, "That Guess Does not compute!");
			return false;
		} else {
			
			gameOps.setInputEquation(tmpequation);		//E�er tahmin etti�imiz denklem do�ru ise
			ArrayList<String> colors = new ArrayList<String>();//tahmin edilen denklem ile
			gameOps.compareEquations();						//do�ru denklemi kar��la�t�r�yor
			colors = gameOps.getColors();
			gameOps.control(gameOps.getColors());

			for (int i = 0; i < equation.getEquationLength(); i++) {//Kar��la�t�r�lan denklemlere g�re
																	//Boyama i�lemleri yap�l�yor
				if (colors.get(i).equals("green")) {
					inputs[countLine][i].setBackground(Color.GREEN);
				} else if (colors.get(i).equals("yellow")) {
					inputs[countLine][i].setBackground(Color.YELLOW);
				} else if (colors.get(i).equals("red")) {
					inputs[countLine][i].setBackground(Color.RED);
				}

			}

			if (gameOps.isMatch()) {//E�er tahmin edilen denklem ile girilen denklem ayn� ise
				return true;		//True d�n�yor
			} else {
									//De�ilse
				tempVariables.clear();//Sat�r�n aktifli�i kapan�yor alt sat�r�n TextFieldlar� al�n�yor
				tempLine.setEnabled(false);
				countLine++;

				try {
					tempLine = (JPanel) list.get(countLine);
					tempLine.setEnabled(true);
					for (i = 0; i < equation.getEquationLength(); i++) {
						tempVariables.add(inputs[countLine][i]);  //Arrayliste textfieldlar al�n�yor
					}
					for (JTextField obj : tempVariables) {
						obj.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {

								if (countLine < 6) {
									if (lines.get(countLine).isEnabled() && tempVariables.contains(obj)) {
										selectInput(lines.get(countLine), obj);//Aktif sat�rdaki textFieldlara mouse ile
									}											//se�ilme �zelli�i atan�yor
								}
							}
						});
					}

					enterInput(tempLine, inputs[countLine][0]);//Alt sat�r�n ilk inputunu birinci s�tun yap�yor

				} catch (IndexOutOfBoundsException e) {
					tempInput = null;//6'dan fazla tahmin oldu�u i�in yeni tahmin yapt�rm�yor
				}

				return false;

			}

		}

	}

}
