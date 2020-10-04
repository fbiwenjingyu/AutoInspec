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
import com.ywj.dao.DataTransferDao;
import com.ywj.model.DataBase;
import com.ywj.model.DataTransferVO;

public class DataTransferAddDialog extends JDialog{
	
	private JFrame owner;
	private static final int DEFAULT_WIDTH = 350;
	private static final int DEFAULT_HEIGHT = 400;
	private String deptName;
	private String mainIpAddress;
	private String mainPort;
	private String mainUserName;
	private String mainPassword;
	private String mainDatabaName;
	
	private String backIpAddress;
	private String backPort;
	private String backUserName;
	private String backPassword;
	private String backDatabaseName;
		
	JTextField deptNameField;
	JTextField mainIpAddressField;
	JTextField mainPortField;
	JTextField mainUserNameField;
	JTextField mainPasswordField ;
	JTextField mainDatabaNameField ;
	
	JTextField backIpAddressField;
	JTextField backPortField;
	JTextField backUserNameField;
	JTextField backPasswordField ;
	JTextField backDatabaNameField ;

	public DataTransferAddDialog(JFrame owner,DataTransferPanel parent) {
		super(owner,"数据传输管理-新建数据传输",true);
		this.owner = owner;
		JPanel inputPanel = new JPanel();
		inputPanel.setSize(new Dimension(300, 240));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		deptNameField = new JTextField("",20);
		mainIpAddressField = new JTextField("",20);
		mainPortField = new JTextField("", 20);
		mainUserNameField = new JTextField("",20);
		mainPasswordField = new JTextField("",20);
		mainDatabaNameField = new JTextField("",20);
		backIpAddressField = new JTextField("",20);
		backPortField = new JTextField("", 20);
		backUserNameField = new JTextField("",20);
		backPasswordField = new JTextField("",20);
		backDatabaNameField = new JTextField("",20);
		
		
		inputPanel.add(new JLabel("部门名称: "));
		inputPanel.add(deptNameField);
		inputPanel.add(new JLabel("源库地址: "));
		inputPanel.add(mainIpAddressField);
		inputPanel.add(new JLabel("源库端口: "));
		inputPanel.add(mainPortField);
		inputPanel.add(new JLabel("源库用户名:"));  
		inputPanel.add(mainUserNameField);
		inputPanel.add(new JLabel("源库密码:  "));
		inputPanel.add(mainPasswordField);
		inputPanel.add(new JLabel("源库库名:  "));  
		inputPanel.add(mainDatabaNameField);
		inputPanel.add(new JLabel("目标库地址: "));
		inputPanel.add(backIpAddressField);
		inputPanel.add(new JLabel("目标库端口: "));
		inputPanel.add(backPortField);
		inputPanel.add(new JLabel("目标库用户名:"));  
		inputPanel.add(backUserNameField);
		inputPanel.add(new JLabel("目标库密码:  "));
		inputPanel.add(backPasswordField);
		inputPanel.add(new JLabel("目标库库名:  "));  
		inputPanel.add(backDatabaNameField);
		
		JButton okButton = new JButton("保存");
		JButton cancelButton = new JButton("取消");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * if(StringUtils.isEmpty(deptNameField.getText())) {
				 * JOptionPane.showMessageDialog(DataBaseAddDialog.this, "请输入系统名称"); return; }
				 */
				if(StringUtils.isEmpty(mainIpAddressField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入源库地址");
					return;
				}
				
				if(StringUtils.isEmpty(mainPortField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入源库端口号");
					return;
				}
				if(StringUtils.isEmpty(mainUserNameField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入源库用户名");
					return;
				}
				
				if(StringUtils.isEmpty(mainPasswordField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入源库密码");
					return;
				}
				
				if(StringUtils.isEmpty(mainDatabaNameField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入源库库名");
					return;
				}
				if(StringUtils.isEmpty(backIpAddressField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入目标库地址");
					return;
				}
				if(StringUtils.isEmpty(backPortField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入目标库端口号");
					return;
				}
				if(StringUtils.isEmpty(backUserNameField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入目标库用户名");
					return;
				}
				
				if(StringUtils.isEmpty(backPasswordField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入目标库密码");
					return;
				}
				
				if(StringUtils.isEmpty(backDatabaNameField.getText())){
					JOptionPane.showMessageDialog(DataTransferAddDialog.this, "请输入目标库库名");
					return;
				}
				
				deptName = deptNameField.getText();
				mainIpAddress = mainIpAddressField.getText();
				mainPort = mainPortField.getText();
				mainUserName = mainUserNameField.getText();
				mainPassword = mainPasswordField.getText();
				mainDatabaName = mainDatabaNameField.getText();
				
				backIpAddress = backIpAddressField.getText();
				backPort = backPortField.getText();
				backUserName = backUserNameField.getText();
				backPassword = backPasswordField.getText();
				backDatabaseName = backDatabaNameField.getText();
								
				DataTransferVO dataTransferVO = new DataTransferVO();
				dataTransferVO.setDeptName(deptName);
				dataTransferVO.setMainIpAddress(mainIpAddress);
				dataTransferVO.setMainPort(Integer.parseInt(mainPort));
				dataTransferVO.setMainUserName(mainUserName);
				dataTransferVO.setMainPassword(mainPassword);
				dataTransferVO.setMainDatabaseName(mainDatabaName);
				dataTransferVO.setBackIpAddress(backIpAddress);
				dataTransferVO.setBackPort(Integer.parseInt(backPort));
				dataTransferVO.setBackUserName(backUserName);
				dataTransferVO.setBackPassword(backPassword);
				dataTransferVO.setBackDatabaseName(backDatabaseName);
				
				DataTransferDao dao = new DataTransferDao();
				dao.insertDataTransferVO(dataTransferVO);
				JOptionPane.showMessageDialog(DataTransferAddDialog.this, "新增数据传输成功","提示",JOptionPane.INFORMATION_MESSAGE);
				DataTransferAddDialog.this.dispose();
				parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataTransferAddDialog.this.dispose();
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
