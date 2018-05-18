package defaultPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Frame;

public class Login extends JFrame {

	private Frame tF = this;

	private JPanel contentPane;
	private JPasswordField inputPassword;
	private JTextField inputID;
	private JTextField inputHost;

	public static Connection conn = null;
	public static String host;
	public static String id;
	public static String password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("데이터베이스 관리 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 25, 0, 26, 0, 0, 0, 23, 25, 25, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		ImageIcon dbIcon = new ImageIcon(".\\db.png");
		Image dbImage = dbIcon.getImage();
		dbImage = dbImage.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		dbIcon = new ImageIcon(dbImage);

		JLabel dbImageLabel = new JLabel("");

		dbImageLabel.setIcon(dbIcon);
		GridBagConstraints gbc_dbImageLabel = new GridBagConstraints();
		gbc_dbImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dbImageLabel.gridx = 1;
		gbc_dbImageLabel.gridy = 1;
		contentPane.add(dbImageLabel, gbc_dbImageLabel);

		JLabel titleText = new JLabel("데이터베이스 관리 시스템");
		titleText.setFont(new Font("굴림", Font.BOLD, 17));
		GridBagConstraints gbc_titleText = new GridBagConstraints();
		gbc_titleText.gridwidth = 2;
		gbc_titleText.insets = new Insets(0, 0, 5, 5);
		gbc_titleText.gridx = 3;
		gbc_titleText.gridy = 1;
		contentPane.add(titleText, gbc_titleText);

		JLabel hostText = new JLabel("host");
		hostText.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_hostText = new GridBagConstraints();
		gbc_hostText.insets = new Insets(0, 0, 5, 5);
		gbc_hostText.gridx = 1;
		gbc_hostText.gridy = 3;
		contentPane.add(hostText, gbc_hostText);

		inputHost = new JTextField();
		inputHost.setText("localhost");
		inputHost.setColumns(10);
		GridBagConstraints gbc_inputHost = new GridBagConstraints();
		gbc_inputHost.gridwidth = 2;
		gbc_inputHost.insets = new Insets(0, 0, 5, 5);
		gbc_inputHost.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputHost.gridx = 3;
		gbc_inputHost.gridy = 3;
		contentPane.add(inputHost, gbc_inputHost);

		JLabel idText = new JLabel("id");
		idText.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_idText = new GridBagConstraints();
		gbc_idText.insets = new Insets(0, 0, 5, 5);
		gbc_idText.gridx = 1;
		gbc_idText.gridy = 4;
		contentPane.add(idText, gbc_idText);

		inputID = new JTextField();
		inputID.setText("root");
		inputID.setColumns(10);
		GridBagConstraints gbc_inputID = new GridBagConstraints();
		gbc_inputID.gridwidth = 2;
		gbc_inputID.insets = new Insets(0, 0, 5, 5);
		gbc_inputID.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputID.gridx = 3;
		gbc_inputID.gridy = 4;
		contentPane.add(inputID, gbc_inputID);

		JLabel passwordText = new JLabel("password");
		passwordText.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_passwordText = new GridBagConstraints();
		gbc_passwordText.insets = new Insets(0, 0, 5, 5);
		gbc_passwordText.gridx = 1;
		gbc_passwordText.gridy = 5;
		contentPane.add(passwordText, gbc_passwordText);

		inputPassword = new JPasswordField();
		inputPassword.setText("1234");
		GridBagConstraints gbc_inputPassword = new GridBagConstraints();
		gbc_inputPassword.gridwidth = 2;
		gbc_inputPassword.insets = new Insets(0, 0, 5, 5);
		gbc_inputPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputPassword.gridx = 3;
		gbc_inputPassword.gridy = 5;
		contentPane.add(inputPassword, gbc_inputPassword);
		inputPassword.setColumns(10);

		JButton confirmButton = new JButton("DB connect");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmButton.insets = new Insets(0, 0, 5, 5);
		gbc_confirmButton.gridx = 4;
		gbc_confirmButton.gridy = 7;
		contentPane.add(confirmButton, gbc_confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				host = inputHost.getText();
				id = inputID.getText();
				password = inputPassword.getText();

				System.out.println("호스트 : " + host);
				System.out.println("아이디 : " + id);
				System.out.println("패스워드 : " + password);
				System.out.println(" ");

				try {
					Class.forName("com.mysql.jdbc.Driver");

					conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/2_1", id, password);
					if (host.equals("") || id.equals("")) {
						JOptionPane.showMessageDialog(tF, "db연결 실패", "Title", JOptionPane.INFORMATION_MESSAGE, null);
					} else {
						JOptionPane.showMessageDialog(tF, "db연결 성공", "Title", JOptionPane.INFORMATION_MESSAGE, null);

						Main main = new Main();
						main.showMain();
						dispose();
					}

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(tF, "커넥션 실패", "Title", JOptionPane.INFORMATION_MESSAGE, null);
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(tF, "커넥션 실패", "Title", JOptionPane.INFORMATION_MESSAGE, null);
					e1.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

}
