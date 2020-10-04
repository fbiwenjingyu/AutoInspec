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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.UserDao;
import com.ywj.model.User;

public class UserAddDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JFrame owner;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 240;
	private String userLoginName;
	private String username;
	private String password;
	private String phoneNum;
	private String bz;
	JTextField userLoginNameField;
	JTextField usernameField;
	JPasswordField passwordField;
	JTextField phoneNumField;
	JTextField bzField ;

	public UserAddDialog(JFrame owner,UserPanel parent) {
		super(owner,"用户管理-新建用户",true);
		this.owner = owner;
		JPanel inputPanel = new JPanel();
		inputPanel.setMinimumSize(new Dimension(180, 220));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		userLoginNameField = new JTextField("",20);
		usernameField = new JTextField("",20);
		passwordField = new JPasswordField("", 20);
		phoneNumField = new JTextField("",20);
		bzField = new JTextField("",20);
		
		inputPanel.add(new JLabel("用户名  :"));
		inputPanel.add(userLoginNameField);
		inputPanel.add(new JLabel("姓名      :"));
		inputPanel.add(usernameField);
		inputPanel.add(new JLabel("密码      :"));
		inputPanel.add(passwordField);
		inputPanel.add(new JLabel("手机号   :"));  
		inputPanel.add(phoneNumField);
		inputPanel.add(new JLabel("备注       :"));
		inputPanel.add(bzField);
		
		JButton okButton = new JButton("保存");
		JButton cancelButton = new JButton("取消");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isEmpty(userLoginNameField.getText())) {
					JOptionPane.showMessageDialog(UserAddDialog.this, "请输入用户名");
					return;
				}
				if(StringUtils.isEmpty(usernameField.getText())){
					JOptionPane.showMessageDialog(UserAddDialog.this, "请输入姓名");
					return;
				}
				
				if(StringUtils.isEmpty(passwordField.getText())){
					JOptionPane.showMessageDialog(UserAddDialog.this, "请输入密码");
					return;
				}
				if(StringUtils.isEmpty(phoneNumField.getText())){
					JOptionPane.showMessageDialog(UserAddDialog.this, "请输入手机号码");
					return;
				}
				
				userLoginName = userLoginNameField.getText();
				username = usernameField.getText();
				password = passwordField.getText();
				phoneNum = phoneNumField.getText();
				bz = bzField.getText();
				User user = new User();
				user.setUserLoginName(userLoginName);
				user.setUsername(username);
				user.setPassword(password);
				user.setPhoneNum(phoneNum);
				user.setBz(bz);
				
				UserDao dao = new UserDao();
				dao.insertUser(user);
				JOptionPane.showMessageDialog(UserAddDialog.this, "新增用户成功","提示",JOptionPane.INFORMATION_MESSAGE);
				UserAddDialog.this.dispose();
				parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserAddDialog.this.dispose();
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