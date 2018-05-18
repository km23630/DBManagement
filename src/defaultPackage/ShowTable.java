package defaultPackage;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;

public class ShowTable extends JPanel {
	PreparedStatement pstmt;
	ResultSet rs = null;
	
	JScrollPane scroll;
	JTable table;
	
	public ShowTable() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
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

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		add(scroll, gbc_table);
	}

	public JPanel refreshTable(String selectedTable, String db) {
		JPanel jp = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		jp.setLayout(gridBagLayout);
		
		try {
			System.out.println("테이블 리프레쉬 함?");
			
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
		* 두번씩 돌아가는거고쳐야댐
		* 테이블 리프레시 되게 해야함
*/