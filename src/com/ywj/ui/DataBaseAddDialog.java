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

public class DataBaseAddDialog extends JDialog{
	
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

	public DataBaseAddDialog(JFrame owner,DataBasePanel parent) {
		super(owner,"���ݿ����-�½����ݿ�",true);
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
		
		inputPanel.add(new JLabel("��������: "));
		inputPanel.add(deptNameField);
		inputPanel.add(new JLabel("���ݿ�����:"));
		inputPanel.add(databaseNameField);
		inputPanel.add(new JLabel("ip��ַ:      "));
		inputPanel.add(ipaddressField);
		inputPanel.add(new JLabel("�˿ں�:      "));  
		inputPanel.add(portField);
		inputPanel.add(new JLabel("�û���:      "));
		inputPanel.add(usernameField);
		inputPanel.add(new JLabel("����:        "));  
		inputPanel.add(passwordField);
		
		JButton okButton = new JButton("����");
		JButton cancelButton = new JButton("ȡ��");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * if(StringUtils.isEmpty(deptNameField.getText())) {
				 * JOptionPane.showMessageDialog(DataBaseAddDialog.this, "������ϵͳ����"); return; }
				 */
				if(StringUtils.isEmpty(databaseNameField.getText())){
					JOptionPane.showMessageDialog(DataBaseAddDialog.this, "���������ݿ�����");
					return;
				}
				
				if(StringUtils.isEmpty(portField.getText())){
					JOptionPane.showMessageDialog(DataBaseAddDialog.this, "������˿ں�");
					return;
				}
				if(StringUtils.isEmpty(usernameField.getText())){
					JOptionPane.showMessageDialog(DataBaseAddDialog.this, "�������û���");
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
				dao.insertDataBase(database);
				JOptionPane.showMessageDialog(DataBaseAddDialog.this, "�������ݿ�ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				DataBaseAddDialog.this.dispose();
				parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBaseAddDialog.this.dispose();
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
