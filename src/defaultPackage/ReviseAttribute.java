package defaultPackage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Component;

public class ReviseAttribute extends JPanel {
	PreparedStatement pstmt;
	ResultSet rs = null;
	
	JScrollPane scroll;
	JTable table;

	public ReviseAttribute() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		try {
			Login.conn = DriverManager.getConnection("jdbc:mysql://" + Login.host + ":3306/information_schema",
					Login.id, Login.password);

			pstmt = Login.conn.prepareStatement("select * from CHARACTER_SETS;");
			rs = pstmt.executeQuery();
			
			table = new JTable();
			table.setModel(DbUtils.resultSetToTableModel(rs));

			scroll = new JScrollPane(table);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Login.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		ImageIcon addAttributeIcon = new ImageIcon(".\\famfamfam_silk_icons_v013\\icons\\add.png");
		Image addAttributeImage= addAttributeIcon.getImage();
		addAttributeImage = addAttributeImage.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		addAttributeIcon = new ImageIcon(addAttributeImage);
		
		ImageIcon reviseAttributeIcon = new ImageIcon(".\\famfamfam_silk_icons_v013\\icons\\cancel.png");
		Image reviseAttributeImage= reviseAttributeIcon.getImage();
		reviseAttributeImage = reviseAttributeImage.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		reviseAttributeIcon = new ImageIcon(reviseAttributeImage);
		
		ImageIcon deleteAttributeIcon = new ImageIcon(".\\famfamfam_silk_icons_v013\\icons\\wrench.png");
		Image deleteAttributeImage= deleteAttributeIcon.getImage();
		deleteAttributeImage = deleteAttributeImage.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		deleteAttributeIcon = new ImageIcon(deleteAttributeImage);
		
		
		JButton button_addAttribute = new JButton("\uC18D\uC131\uCD94\uAC00");
		
		button_addAttribute.setIcon(addAttributeIcon);
		button_addAttribute.setPreferredSize(new Dimension(20, 20));
		
		button_addAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		GridBagConstraints gbc_button_AddAttribute = new GridBagConstraints();
		gbc_button_AddAttribute.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_AddAttribute.insets = new Insets(0, 0, 5, 5);
		gbc_button_AddAttribute.gridx = 0;
		gbc_button_AddAttribute.gridy = 0;
		add(button_addAttribute, gbc_button_AddAttribute);
		
		
		JButton button_reviseAttribute = new JButton("\uC18D\uC131\uD3B8\uC9D1");
		
		button_reviseAttribute.setIcon(deleteAttributeIcon);
		button_reviseAttribute.setPreferredSize(new Dimension(20, 20));
		
		button_reviseAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		GridBagConstraints gbc_button_reviseAttribute = new GridBagConstraints();
		gbc_button_reviseAttribute.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_reviseAttribute.insets = new Insets(0, 0, 5, 5);
		gbc_button_reviseAttribute.gridx = 1;
		gbc_button_reviseAttribute.gridy = 0;
		add(button_reviseAttribute, gbc_button_reviseAttribute);
		
		
		JButton 버튼_속성제거 = new JButton("\uC18D\uC131\uC81C\uAC70");
		버튼_속성제거.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		버튼_속성제거.setIcon(reviseAttributeIcon);
		버튼_속성제거.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_버튼_속성제거 = new GridBagConstraints();
		gbc_버튼_속성제거.fill = GridBagConstraints.HORIZONTAL;
		gbc_버튼_속성제거.insets = new Insets(0, 0, 5, 0);
		gbc_버튼_속성제거.gridx = 2;
		gbc_버튼_속성제거.gridy = 0;
		add(버튼_속성제거, gbc_버튼_속성제거);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scroll, gbc_scrollPane);;
		
	}
	
	public JPanel refreshAttribute(String selectedTable, String db) {
		JPanel jp = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		jp.setLayout(gridBagLayout);
		
		try {
			System.out.println("속성 리프레쉬 함?");
			
			Login.conn = DriverManager.getConnection("jdbc:mysql://" + Login.host + ":3306/"+db,Login.id, Login.password);
			
			pstmt = Login.conn.prepareStatement("select * from " + selectedTable);
			rs = pstmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			scroll = new JScrollPane(table);
			
			scroll.invalidate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Login.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		jp.add(scroll, gbc_table);
		
		return jp;

	}
	

}

/*
 * 속성 추가에 들어가야 할 내용
 * 데이터 타입
 * 속성 이름
 * Primary Key, Not Null, Unique, binary, Unsigned, Zero Fill, Auto Increment
 * Default 값
 */ 
