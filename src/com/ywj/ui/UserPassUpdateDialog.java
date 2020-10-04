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

public class UserPassUpdateDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JFrame owner;
	private User user;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 240;
	JPasswordField oldpasswordField;
	JPasswordField newpasswordField;
	JPasswordField checknewpasswordField;
	UserDao dao = new UserDao();

	public UserPassUpdateDialog(JFrame owner,UserPanel parent,User user) {
		super(owner,"用户管理-修改密码",true);
		this.owner = owner;
		this.user = user;
		JPanel inputPanel = new JPanel();
		inputPanel.setMinimumSize(new Dimension(180, 220));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		oldpasswordField = new JPasswordField("",20);
		newpasswordField = new JPasswordField("",20);
		checknewpasswordField = new JPasswordField("", 20);
		
		inputPanel.add(new JLabel("原密码    :"));
		inputPanel.add(oldpasswordField);
		inputPanel.add(new JLabel("新密码    :"));
		inputPanel.add(newpasswordField);
		inputPanel.add(new JLabel("确认密码:"));
		inputPanel.add(checknewpasswordField);
		
		JButton okButton = new JButton("保存");
		JButton cancelButton = new JButton("取消");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isEmpty(oldpasswordField.getText())) {
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "请输入原密码");
					return;
				}
				if(StringUtils.isEmpty(newpasswordField.getText())){
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "请输入新密码");
					return;
				}
				
				if(StringUtils.isEmpty(checknewpasswordField.getText())){
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "请输入确认密码");
					return;
				}
				if(StringUtils.isNotEmpty(oldpasswordField.getText())){
					if(!oldpasswordField.getText().equals(user.getPassword())) {
						JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "原密码错误，请重新输入");
						return;
					}	
				}
				if(!newpasswordField.getText().equals(checknewpasswordField.getText())){
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "新密码和确认密码不一致，请重新输入");
					return;
				}
				
				user.setPassword(newpasswordField.getText());
				user.setFirstLogin("0");
				UserDao dao = new UserDao();
				dao.update(user.getId(), user);
				JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "修改密码成功","提示",JOptionPane.INFORMATION_MESSAGE);
				UserPassUpdateDialog.this.dispose();
				//parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserPassUpdateDialog.this.dispose();
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
