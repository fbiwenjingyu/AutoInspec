package com.ywj.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import com.ywj.dao.DataTransferDao;

public class DataTransferPanel extends JPanel {
	JButton addbutton;
	JButton editbutton;
	JButton delbutton;
	JButton refreshbutton;
	JLabel deptNameLabel;
	JTextField deptNameText;
	JButton searchbutton;
	JPanel top = new JPanel();
	JScrollPane tablePane = new JScrollPane();
	JTable table = new JTable();
	DataTransferDao dao = new DataTransferDao();
	
	public DataTransferPanel() {
		setProperties();
		initTopPanel();
		initCenterPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(tablePane, BorderLayout.CENTER);
	}
	
	private void initTopPanel() {
		addbutton = new JButton("新增数据传输");
		editbutton = new JButton("编辑数据传输");
		delbutton = new JButton("删除数据传输");
		deptNameLabel = new JLabel("部门名称");
		deptNameLabel.setLabelFor(deptNameText);
		deptNameText = new JTextField(15);
		searchbutton = new JButton("搜索");
		refreshbutton= new JButton("刷新");
		top.add(addbutton);
		top.add(editbutton);
		top.add(delbutton);
		top.add(deptNameLabel);
		top.add(deptNameText);
		top.add(searchbutton);	
		top.add(refreshbutton);
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataTransferAddDialog(null,DataTransferPanel.this);
			}
		});
		
		editbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				new DataTransferEditDialog(null,DataTransferPanel.this,Integer.valueOf(id));
			}
		});
		
		delbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				int num = JOptionPane.showConfirmDialog(DataTransferPanel.this, "确认删除？","提示",JOptionPane.YES_NO_OPTION);
				switch (num) {
					case JOptionPane.YES_OPTION:
						dao.deleteDataTransferVOById(Integer.valueOf(id));
						JOptionPane.showMessageDialog(DataTransferPanel.this, "删除数据传输成功","提示",JOptionPane.INFORMATION_MESSAGE);
						break;
					case JOptionPane.NO_OPTION:
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
				}
				showTable();
			}
		});
		
		searchbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTableSearchByDeptName(deptNameText.getText());	
			}
		});
		
		refreshbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTable();	
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
		String colum[] = dao.getColumnNames();
		Object data[][] = dao.getData();
		TableModel dataModel = new DefaultTableModel(data, colum){
		    public boolean isCellEditable(int row, int column)
		    {
		      if(column <= 3) {
		    	  return false;  
		      }
		      return true;
		    }
		  };
		table.setModel(dataModel);
		table.setRowHeight(30);	
		table.getColumnModel().getColumn(4).setCellRenderer(new DataTransferButtonRenderer());
		table.getColumnModel().getColumn(4).setCellEditor(new DataTransferButtonEditor(new JTextField()));
		
		
		table.validate();
    }
	
	public void showTableSearchByDeptName(String deptName) {
		if(StringUtils.isEmpty(deptName)) {
			showTable();
			return;
		}
		String colum[] = dao.getColumnNames();
		String data[][] = dao.getDataByDeptName(deptName);
		TableModel dataModel = new DefaultTableModel(data, colum);
		table.setModel(dataModel);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
		table.validate();
    }
	
	

	private void setProperties() {
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}


}
