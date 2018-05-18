package defaultPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.border.BevelBorder;

public class Main extends JFrame implements ActionListener {
	public Main() {
		
	}
	
	public static JTabbedPane tap = null;

	private JPanel contentPane;
	private GridBagConstraints gbc_title;
	public static String selectedTable;

	JList tableList;
	String[] tableArray = { "" };

	PreparedStatement pstmt;
	ResultSet rs = null;
	JScrollPane scrollList;

	JComboBox<String> selectList = new JComboBox<String>();

	public static String selectedDB = "2_1";
	
	ShowTable showTables = new ShowTable();
	ReviseAttribute reviseAttribute = new ReviseAttribute();
	InputQuery inputQuery = new InputQuery();
	
	JLabel state;
	
	public void showMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.init();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void init() {
		setTitle("db\uAD00\uB9AC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 136, 30, 0, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 40, 0, 45, 0, 25, 0, 0, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

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
		getContentPane().add(dbImageLabel, gbc_dbImageLabel);

		JLabel dbManagementText = new JLabel("데이터베이스 관리 프로그램");
		dbManagementText.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		GridBagConstraints gbc_dbManagementText = new GridBagConstraints();
		gbc_dbManagementText.insets = new Insets(0, 0, 5, 5);
		gbc_dbManagementText.gridx = 3;
		gbc_dbManagementText.gridy = 1;
		getContentPane().add(dbManagementText, gbc_dbManagementText);

		JLabel dbSelectText = new JLabel("데이터베이스 선택");
		dbSelectText.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_dbSelectText = new GridBagConstraints();
		gbc_dbSelectText.insets = new Insets(0, 0, 5, 5);
		gbc_dbSelectText.gridx = 1;
		gbc_dbSelectText.gridy = 3;
		getContentPane().add(dbSelectText, gbc_dbSelectText);

		GridBagConstraints gbc_dbSelect = new GridBagConstraints();
		gbc_dbSelect.insets = new Insets(0, 0, 5, 5);
		gbc_dbSelect.fill = GridBagConstraints.HORIZONTAL;

		try {
			Login.conn = DriverManager.getConnection("jdbc:mysql://" + Login.host + ":3306/information_schema",
					Login.id, Login.password);
			pstmt = Login.conn.prepareStatement("SHOW DATABASES;");
			rs = pstmt.executeQuery();
			System.out.println("현재 가지고 있는 데이터베이스");

			while (rs.next()) {
				selectList.addItem(rs.getString(1));
				System.out.println(rs.getString(1));
			}
			System.out.println(" ");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if (Login.conn != null)
					Login.conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

		selectList.addActionListener(this);

		gbc_dbSelect.gridx = 3;
		gbc_dbSelect.gridy = 3;
		getContentPane().add(selectList, gbc_dbSelect);

		JLabel tableListText = new JLabel("테이블 목록");
		tableListText.setFont(new Font("굴림", Font.BOLD, 15));
		GridBagConstraints gbc_tableListText = new GridBagConstraints();
		gbc_tableListText.insets = new Insets(0, 0, 5, 5);
		gbc_tableListText.gridx = 1;
		gbc_tableListText.gridy = 5;
		getContentPane().add(tableListText, gbc_tableListText);

		tap = new JTabbedPane(JTabbedPane.TOP);
		tap.addTab("테이블 보기", showTables);
		tap.addTab("속성 수정", reviseAttribute);
		tap.addTab("쿼리문 입력", inputQuery);

		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 3;
		gbc_tabbedPane.gridy = 5;
		getContentPane().add(tap, gbc_tabbedPane);
		
		scrollList = new JScrollPane();
		GridBagConstraints gbc_scrollList = new GridBagConstraints();
		gbc_scrollList.insets = new Insets(0, 0, 5, 5);
		gbc_scrollList.fill = GridBagConstraints.BOTH;
		gbc_scrollList.gridx = 1;
		gbc_scrollList.gridy = 6;
		getContentPane().add(scrollList, gbc_scrollList);
		
		state = new JLabel();
		state.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		GridBagConstraints gbc_state = new GridBagConstraints();
		gbc_state.insets = new Insets(0, 0, 5, 5);
		gbc_state.gridx = 3;
		gbc_state.gridy = 7;
		getContentPane().add(state, gbc_state);
		
		tableList = new JList(tableArray);

		scrollList.setViewportView(tableList);

		tableList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

	}
	
	public static String before_selected = "";

	public void actionPerformed(ActionEvent e) {
		System.out.println("현재 선택된 데이터베이스 : " + (String) selectList.getSelectedItem());
		selectedDB=(String) selectList.getSelectedItem();
		
		System.out.println(" ");

		try {
			Login.conn = DriverManager.getConnection("jdbc:mysql://" + Login.host + ":3306/2_1", Login.id,
					Login.password);
			pstmt = Login.conn.prepareStatement("USE " + (String) selectList.getSelectedItem() + ";");
			pstmt.executeUpdate();
			state.setText("db : "+selectedDB+"          table : "+selectedTable); 

			pstmt = Login.conn.prepareStatement("SHOW TABLES;");
			rs = pstmt.executeQuery();

			System.out.println("현재 가지고 있는 테이블");
			ArrayList<String> tableArray = new ArrayList<>();
			while (rs.next()) {
				System.out.println(rs.getString(1));

				tableArray.add(rs.getString(1));
			}
			System.out.println(" ");

			JList<Object> jl = new JList<>(tableArray.toArray());
			jl.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					selectedTable = (String) jl.getSelectedValue();
					if(!before_selected.equals(selectedTable)){
						before_selected =selectedTable;

						System.out.println("현재 선택된 테이블 : " + selectedTable);
						// table 새로고침
						JPanel jtable = showTables.refreshTable(selectedTable, (String) selectList.getSelectedItem());
						state.setText("db : "+selectedDB+"          table : "+selectedTable); 
						tap.setComponentAt(0, jtable);
					}
				}
			});

			scrollList.setViewportView(jl);

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if (Login.conn != null)
					Login.conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
}
