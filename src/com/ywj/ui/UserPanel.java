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

import com.ywj.dao.UserDao;
import com.ywj.model.User;

public class UserPanel extends JPanel{
	JButton addbutton;
	JButton editbutton;
	JButton delbutton;
	JButton passbutton;
	JButton refreshbutton;
	JLabel userLoginNameLabel;
	JTextField userLoginNameText;
	JLabel userNameLabel;
	JTextField userNameText;
	JButton searchbutton;
	JPanel top = new JPanel();
	JScrollPane tablePane = new JScrollPane();
	JTable table = new JTable();
	UserDao dao = new UserDao();
	public UserPanel() {
		setProperties();
		initTopPanel();
		initCenterPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(tablePane, BorderLayout.CENTER);
	}

	private void initTopPanel() {
		addbutton = new JButton("新增用户");
		editbutton = new JButton("编辑用户");
		delbutton = new JButton("删除用户");
		passbutton = new JButton("修改密码");
		userLoginNameLabel = new JLabel("用户名");
		userLoginNameLabel.setLabelFor(userLoginNameText);
		userLoginNameText = new JTextField(15);
		userNameLabel = new JLabel("姓名");
		userNameLabel.setLabelFor(userNameText);
		userNameText = new JTextField(15);
		searchbutton = new JButton("搜索");
		refreshbutton= new JButton("刷新");
		top.add(addbutton);
		top.add(editbutton);
		top.add(delbutton);
		top.add(passbutton);
		top.add(userLoginNameLabel);
		top.add(userLoginNameText);
		top.add(userNameLabel);
		top.add(userNameText);
		top.add(searchbutton);	
		top.add(refreshbutton);
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserAddDialog(null,UserPanel.this);
			}
		});
		
		editbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				new UserEditDialog(null,UserPanel.this,Integer.valueOf(id));
			}
		});
		
		delbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				int num = JOptionPane.showConfirmDialog(UserPanel.this, "确认删除？","提示",JOptionPane.YES_NO_OPTION);
				switch (num) {
					case JOptionPane.YES_OPTION:
						dao.deleteUserById(Integer.valueOf(id));
						JOptionPane.showMessageDialog(UserPanel.this, "删除用户成功","提示",JOptionPane.INFORMATION_MESSAGE);
						break;
					case JOptionPane.NO_OPTION:
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
				}
				showTable();
			}
		});
		
		passbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				User user = dao.getUserById(Integer.parseInt(id));
				new UserPassUpdateDialog(null, UserPanel.this, user);
			}
		});
		
		searchbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTableSearch(userLoginNameText.getText(),userNameText.getText());		
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
		String data[][] = dao.getData();
		TableModel dataModel = new DefaultTableModel(data, colum){
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		  };
		table.setModel(dataModel);
		table.validate();
	}
	
	public void showTableSearch(String userLoginName,String userName) {
		if(StringUtils.isEmpty(userLoginName) && StringUtils.isEmpty(userName)) {
			showTable();
			return;
		}
		String colum[] = dao.getColumnNames();
		String data[][] = dao.getDataBySearch(userLoginName,userName);
		TableModel dataModel = new DefaultTableModel(data, colum);
		table.setModel(dataModel);
		table.validate();
	}
	
	private void setProperties() {
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}
	
	

}
