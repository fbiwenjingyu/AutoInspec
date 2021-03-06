package com.ywj.ui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.DataBaseDao;
import com.ywj.utils.ExcelExporter;

public class UpdateTimePanel extends JPanel {
	private JFrame parent;
	JButton exportExcelButton;
	JPanel top = new JPanel();
	JScrollPane tablePane = new JScrollPane();
	JTable table = new JTable();
	DataBaseDao dao = new DataBaseDao();
	
	JLabel databaseNameLabel;
	JTextField databaseNameText;
	JLabel tableEnNameLabel;
	JTextField tableEnNameText;
	JButton searchbutton;
	JButton refreshbutton;
	
	public UpdateTimePanel(JFrame parent) {
		this.parent = parent;
		setProperties();
		initTopPanel();
		initCenterPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(tablePane, BorderLayout.CENTER);
		
	}
	private void initTopPanel() {
		databaseNameLabel = new JLabel("库中文名");
		databaseNameLabel.setLabelFor(databaseNameText);
		databaseNameText = new JTextField(15);
		tableEnNameLabel = new JLabel("表英文名");
		tableEnNameLabel.setLabelFor(tableEnNameText);
		tableEnNameText = new JTextField(15);
		searchbutton = new JButton("搜索");
		refreshbutton = new JButton("刷新");
		top.add(databaseNameLabel);
		top.add(databaseNameText);
		top.add(tableEnNameLabel);
		top.add(tableEnNameText);
		top.add(searchbutton);	
		top.add(refreshbutton);	
		
		exportExcelButton = new JButton("导出到excel");
		top.add(exportExcelButton);
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		exportExcelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 FileDialog fd = new FileDialog(parent, "导出数据到excel", FileDialog.SAVE);
				 fd.setLocation(500, 350);
			     fd.setVisible(true);  
			     String stringfile = fd.getDirectory()+fd.getFile()+".xls";  
		         try {
		        	 ExcelExporter export = new ExcelExporter();
		        	 export.exportTable(table, new File(stringfile));
		         } catch (IOException ex) {
		             ex.printStackTrace();
		         }
			}
		});
		
		refreshbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTable();	
			}
		});
		
		searchbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTableSearch(databaseNameText.getText(),tableEnNameText.getText());		
			}
		});
	}
	
	private void initCenterPanel() {
		TableModel dataModel = new DefaultTableModel(new String[][] {},new String[] {});
		table.setModel(dataModel);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		table.setDefaultRenderer(Object.class, tcr);
		showTable();
		tablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tablePane.getViewport().add(table);
	}
	
	public void showTable() {
		new Thread(()->{
			Vector colum = dao.getUpdateTimeColumnNames();
			Vector data = dao.getUpdateTimeData();
			TableModel dataModel = new DefaultTableModel(data, colum){
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			};
			table.setModel(dataModel);
			table.setRowHeight(30);
			table.validate();
		}).start();
		
    }
	
	public void showTableSearch(String databaseName,String tableEnName) {
		new Thread(()->{
			if(StringUtils.isEmpty(databaseName) && StringUtils.isEmpty(tableEnName)) {
				showTable();
				return;
			}
			Vector colum = dao.getUpdateTimeColumnNames();
			Vector data = dao.getDataBySearch(databaseName,tableEnName);
			TableModel dataModel = new DefaultTableModel(data, colum){
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			};
			table.setModel(dataModel);
			table.setRowHeight(30);
			table.validate();
		}).start();
	}
	
	private void setProperties() {
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}

		
}
