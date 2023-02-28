package nerdle;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Win extends JFrame {

	private JPanel contentPane;
	private JTextField txtYouWn;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JButton btnRetMenu;
	private JTextField txtFooter;

	public Win(int t) {
		setResizable(false);
		setBounds(100, 100, 443, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		panel.setBounds(0, 0, 434, 94);
		contentPane.add(panel);
		panel.setLayout(null);

		txtYouWn = new JTextField();
		txtYouWn.setText("You WIN!");
		txtYouWn.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		txtYouWn.setEditable(false);
		txtYouWn.setBackground(Color.GREEN);
		txtYouWn.setHorizontalAlignment(SwingConstants.CENTER);
		txtYouWn.setBounds(0, 0, 434, 93);
		panel.add(txtYouWn);
		txtYouWn.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBounds(0, 94, 434, 72);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lblNewLabel = new JLabel("Time : ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 66, 32);
		panel_1.add(lblNewLabel);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		String str = String.valueOf(t);//Süreyi ekrana yazdýrýyor
		textField.setText(str);
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(78, 16, 86, 26);
		panel_1.add(textField);
		textField.setColumns(10);

		btnRetMenu = new JButton("Main Menu");
		btnRetMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.gc();
				for (Window window : getWindows()) {//Bütün ekranlarý kapatýyor
					window.dispose();
				}
				MainMenu m = new MainMenu();//Main Menüye dönüyor
				m.setVisible(true);
			}
		});
		btnRetMenu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnRetMenu.setBounds(246, 11, 178, 32);
		panel_1.add(btnRetMenu);

		txtFooter = new JTextField();
		txtFooter.setText("13-19011085 Osman Yi\u011Fit S\u00F6kel-19011075 Berkay Demirhan");
		txtFooter.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtFooter.setEditable(false);
		txtFooter.setHorizontalAlignment(SwingConstants.CENTER);
		txtFooter.setBounds(10, 169, 416, 19);
		contentPane.add(txtFooter);
		txtFooter.setColumns(10);
	}
}
