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
		super(owner,"�û�����-�޸�����",true);
		this.owner = owner;
		this.user = user;
		JPanel inputPanel = new JPanel();
		inputPanel.setMinimumSize(new Dimension(180, 220));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		oldpasswordField = new JPasswordField("",20);
		newpasswordField = new JPasswordField("",20);
		checknewpasswordField = new JPasswordField("", 20);
		
		inputPanel.add(new JLabel("ԭ����    :"));
		inputPanel.add(oldpasswordField);
		inputPanel.add(new JLabel("������    :"));
		inputPanel.add(newpasswordField);
		inputPanel.add(new JLabel("ȷ������:"));
		inputPanel.add(checknewpasswordField);
		
		JButton okButton = new JButton("����");
		JButton cancelButton = new JButton("ȡ��");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isEmpty(oldpasswordField.getText())) {
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "������ԭ����");
					return;
				}
				if(StringUtils.isEmpty(newpasswordField.getText())){
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "������������");
					return;
				}
				
				if(StringUtils.isEmpty(checknewpasswordField.getText())){
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "������ȷ������");
					return;
				}
				if(StringUtils.isNotEmpty(oldpasswordField.getText())){
					if(!oldpasswordField.getText().equals(user.getPassword())) {
						JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "ԭ�����������������");
						return;
					}	
				}
				if(!newpasswordField.getText().equals(checknewpasswordField.getText())){
					JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "�������ȷ�����벻һ�£�����������");
					return;
				}
				
				user.setPassword(newpasswordField.getText());
				user.setFirstLogin("0");
				UserDao dao = new UserDao();
				dao.update(user.getId(), user);
				JOptionPane.showMessageDialog(UserPassUpdateDialog.this, "�޸�����ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
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
