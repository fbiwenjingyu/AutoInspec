package com.ywj.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.DataBaseDao;
import com.ywj.model.DataBase;

public class DataBaseEditDialog extends JDialog{
	private JFrame owner;
	private static final int DEFAULT_WIDTH = 350;
	private static final int DEFAULT_HEIGHT = 250;
	private String deptName;
	private String databaseName;
	private String ipaddress;
	private String port;
	private String username;
	private String password;
		
	JTextField deptNameField;
	JTextField databaseNameField;
	JTextField ipaddressField;
	JTextField portField;
	JTextField usernameField ;
	JTextField passwordField ;

	public DataBaseEditDialog(JFrame owner, DataBasePanel parent, Integer dataBaseId) {
		// TODO Auto-generated constructor stub
		super(owner,"数据库管理-编辑数据库",true);
		this.owner = owner;
		JPanel inputPanel = new JPanel();
		inputPanel.setSize(new Dimension(300, 240));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		deptNameField = new JTextField("",20);
		databaseNameField = new JTextField("",20);
		ipaddressField = new JTextField("", 20);
		portField = new JTextField("",20);
		usernameField = new JTextField("",20);
		passwordField = new JTextField("",20);
		
		DataBaseDao dao = new DataBaseDao();
		DataBase dataBase = dao.getDataBaseById(dataBaseId);
		if(dataBase != null) {
			deptNameField.setText(dataBase.getDeptName());
			databaseNameField.setText(dataBase.getDatabaseName());
			ipaddressField.setText(dataBase.getIpaddress());
			portField.setText(dataBase.getPort());
			usernameField.setText(dataBase.getUsername());
			passwordField.setText(dataBase.getPassword());
		}
		
		inputPanel.add(new JLabel("部门名称: "));
		inputPanel.add(deptNameField);
		inputPanel.add(new JLabel("数据库名称:"));
		inputPanel.add(databaseNameField);
		inputPanel.add(new JLabel("ip地址:      "));
		inputPanel.add(ipaddressField);
		inputPanel.add(new JLabel("端口号:      "));  
		inputPanel.add(portField);
		inputPanel.add(new JLabel("用户名:      "));
		inputPanel.add(usernameField);
		inputPanel.add(new JLabel("密码:        "));  
		inputPanel.add(passwordField);
		
		JButton okButton = new JButton("保存");
		JButton cancelButton = new JButton("取消");
		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * if(StringUtils.isEmpty(deptNameField.getText())) {
				 * JOptionPane.showMessageDialog(DataBaseAddDialog.this, "请输入系统名称"); return; }
				 */
				if(StringUtils.isEmpty(databaseNameField.getText())){
					JOptionPane.showMessageDialog(DataBaseEditDialog.this, "请输入数据库名称");
					return;
				}
				
				if(StringUtils.isEmpty(portField.getText())){
					JOptionPane.showMessageDialog(DataBaseEditDialog.this, "请输入端口号");
					return;
				}
				if(StringUtils.isEmpty(usernameField.getText())){
					JOptionPane.showMessageDialog(DataBaseEditDialog.this, "请输入用户名");
					return;
				}
				
				deptName = deptNameField.getText();
				databaseName = databaseNameField.getText();
				ipaddress = ipaddressField.getText();
				port = portField.getText();
				username = usernameField.getText();
				password = passwordField.getText();
								
				DataBase database = new DataBase();
				database.setDeptName(deptName);
				database.setDatabaseName(databaseName);
				database.setIpaddress(ipaddress);
				database.setPort(port);
				database.setUsername(username);
				database.setPassword(password);
				
				DataBaseDao dao = new DataBaseDao();
				dao.update(dataBaseId, database);
				JOptionPane.showMessageDialog(DataBaseEditDialog.this, "修改数据库成功","提示",JOptionPane.INFORMATION_MESSAGE);
				DataBaseEditDialog.this.dispose();
				parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBaseEditDialog.this.dispose();
			}
		});
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}	

}
