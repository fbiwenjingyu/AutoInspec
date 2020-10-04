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

import com.ywj.dao.ConsoleDao;
import com.ywj.model.Console;

public class ConsoleAddDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JFrame owner;
	private static final int DEFAULT_WIDTH = 350;
	private static final int DEFAULT_HEIGHT = 410;
	private String name;
	private String innerurl;
	private String outurl;
	private String loginName1;
	private String loginPassword1;
	private String loginName2;
	private String loginPassword2;
	private String webBrowser;
	private String innerUserTagName;
	private String innerPasswordTagName;
	private String outUserTagName;
	private String outPasswordTagName;
	
	JTextField nameField;
	JTextField innerurlField;
	JTextField outurlField;
	JTextField loginName1Field;
	JTextField loginPassword1Field ;
	JTextField loginName2Field ;
	JTextField loginPassword2Field;
	JTextField webBrowserField;
	JTextField innerUserTagNameField;
	JTextField innerPasswordTagNameField;
	JTextField outUserTagNameField;
	JTextField outPasswordTagNameField;

	public ConsoleAddDialog(JFrame owner,ConsolePanel parent) {
		super(owner,"����̨����-�½�����̨",true);
		this.owner = owner;
		JPanel inputPanel = new JPanel();
		inputPanel.setSize(new Dimension(300, 400));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		nameField = new JTextField("",20);
		innerurlField = new JTextField("",20);
		outurlField = new JTextField("", 20);
		loginName1Field = new JTextField("",20);
		loginPassword1Field = new JTextField("",20);
		loginName2Field = new JTextField("",20);
		loginPassword2Field = new JTextField("",20);
		webBrowserField = new JTextField("",20);
		innerUserTagNameField = new JTextField("",20);
		innerPasswordTagNameField = new JTextField("",20);
		outUserTagNameField = new JTextField("",20);
		outPasswordTagNameField = new JTextField("",20);
		
		inputPanel.add(new JLabel("ϵͳ����: "));
		inputPanel.add(nameField);
		inputPanel.add(new JLabel("������ַ: "));
		inputPanel.add(innerurlField);
		inputPanel.add(new JLabel("��������ַ:  "));
		inputPanel.add(outurlField);
		inputPanel.add(new JLabel("�˻�1:      "));  
		inputPanel.add(loginName1Field);
		inputPanel.add(new JLabel("����1:      "));
		inputPanel.add(loginPassword1Field);
		inputPanel.add(new JLabel("�˻�2:      "));  
		inputPanel.add(loginName2Field);
		inputPanel.add(new JLabel("����2:      "));
		inputPanel.add(loginPassword2Field);
		inputPanel.add(new JLabel("����� :    "));
		inputPanel.add(webBrowserField);
		inputPanel.add(new JLabel("�����˻���ǩ:"));
		inputPanel.add(innerUserTagNameField);
		inputPanel.add(new JLabel("���������ǩ:"));
		inputPanel.add(innerPasswordTagNameField);
		inputPanel.add(new JLabel("�������˻���ǩ:"));
		inputPanel.add(outUserTagNameField);
		inputPanel.add(new JLabel("�����������ǩ:"));
		inputPanel.add(outPasswordTagNameField);
		
		JButton okButton = new JButton("����");
		JButton cancelButton = new JButton("ȡ��");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isEmpty(nameField.getText())) {
					JOptionPane.showMessageDialog(ConsoleAddDialog.this, "������ϵͳ����");
					return;
				}
				if(StringUtils.isEmpty(innerurlField.getText())){
					//JOptionPane.showMessageDialog(ConsoleAddDialog.this, "������������ַ");
					//return;
				}
				
				if(StringUtils.isEmpty(loginName1Field.getText())){
					//JOptionPane.showMessageDialog(ConsoleAddDialog.this, "�������¼�˻�1");
					//return;
				}
				if(StringUtils.isEmpty(loginPassword1Field.getText())){
					//JOptionPane.showMessageDialog(ConsoleAddDialog.this, "�������¼����1");
					//return;
				}
				
				name = nameField.getText();
				innerurl = innerurlField.getText();
				outurl = outurlField.getText();
				loginName1 = loginName1Field.getText();
				loginPassword1 = loginPassword1Field.getText();
				loginName2 = loginName2Field.getText();
				loginPassword2 = loginPassword2Field.getText();
				webBrowser = webBrowserField.getText();
				innerUserTagName = innerUserTagNameField.getText();
				innerPasswordTagName = innerPasswordTagNameField.getText();
				outUserTagName = outUserTagNameField.getText();
				outPasswordTagName = outPasswordTagNameField.getText();
				
				Console console = new Console();
				console.setName(name);
				console.setInnerurl(innerurl);
				console.setOuturl(outurl);
				console.setLoginName1(loginName1);
				console.setLoginPassword1(loginPassword1);
				console.setLoginName2(loginName2);
				console.setLoginPassword2(loginPassword2);
				console.setWebBrowser(webBrowser);
				console.setInnerUserTagName(innerUserTagName);
				console.setInnerPasswordTagName(innerPasswordTagName);
				console.setOutUserTagName(outUserTagName);
				console.setOutPasswordTagName(outPasswordTagName);
				
				ConsoleDao dao = new ConsoleDao();
				dao.insertConsole(console);
				JOptionPane.showMessageDialog(ConsoleAddDialog.this, "��������̨�ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				ConsoleAddDialog.this.dispose();
				parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsoleAddDialog.this.dispose();
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