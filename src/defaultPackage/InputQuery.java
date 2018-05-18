package defaultPackage;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Frame;

public class InputQuery extends JPanel {
	
	private Frame tF = null;
	PreparedStatement pstmt;
	int rs;
	
	public InputQuery() {
		
		setLayout(new BorderLayout(0, 0));
		
		BevelBorder border = new BevelBorder(BevelBorder.RAISED);
		JLabel queryText = new JLabel("������");
		queryText.setBorder(border);
		queryText.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(queryText, BorderLayout.NORTH);
		
		
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane();
		
		textArea.setFont(new Font("D2Coding", Font.PLAIN, 20));
		scrollPane.setViewportView(textArea);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JButton inputButton = new JButton("������ �Է��ϱ�");
		
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("\n ���� - ���� ���õ� ����? "+ Main.selectedDB + "\n");
				System.out.println("�Է¹��� �ؽ�Ʈ : "+textArea.getText()+"\n");
				
				try {
					Login.conn = DriverManager.getConnection("jdbc:mysql://" + Login.host + ":3306/" + Main.selectedDB, Login.id, Login.password);
					pstmt = Login.conn.prepareStatement(textArea.getText());
					rs = pstmt.executeUpdate();
					
					JOptionPane.showMessageDialog(tF, "���� ���� ����", "Title", JOptionPane.INFORMATION_MESSAGE, null);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(tF, "���� ���� ����", "Title", JOptionPane.INFORMATION_MESSAGE, null);
					
					e.printStackTrace();
				}
				
			}
		});
		
		add(inputButton, BorderLayout.SOUTH);
	}

}

