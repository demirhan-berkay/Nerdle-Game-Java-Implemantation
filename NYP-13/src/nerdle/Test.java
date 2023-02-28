package nerdle;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Equation eq;
	private JTextField textField_1;


	public Test() {
		setResizable(false);
		setType(Type.UTILITY);
		eq = new Equation();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.info);
		panel.setBounds(0, 0, 434, 174);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		textField.setText(eq.generateEquation());
		textField.setEditable(false);
		textField.setBackground(SystemColor.info);
		textField.setBounds(50, 11, 329, 50);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnRecreate = new JButton("Recreate");
		btnRecreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eq=new Equation();//Yeni denklem oluþturuyor
				textField.setText(eq.generateEquation());//Yeni denklemi yazdýrýyor
			}
		});
		btnRecreate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnRecreate.setBounds(120, 72, 173, 23);
		panel.add(btnRecreate);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);//Menüye geri dönüþ yapýyor
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnBack.setBounds(120, 106, 173, 23);
		panel.add(btnBack);
		
		textField_1 = new JTextField();
		textField_1.setText("13-19011085 Osman Yi\u011Fit S\u00F6kel-19011075 Berkay Demirhan");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 8));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(28, 145, 353, 19);
		panel.add(textField_1);
	}

}
