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

import com.ywj.dao.MachineDao;
import com.ywj.model.Machine;

public class MachineAddDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JFrame owner;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 220;
	private String machineName;
	private String englishName;
	private String osTypeName;
	private String ipAddress;
	JTextField machineNameField;
	JTextField englishNameField;
	JTextField osTypeNameField;
	JTextField ipAddressField;

	public MachineAddDialog(JFrame owner,MachinePanel parent) {
		super(owner,"设备管理-新增设备",true);
		this.owner = owner;
		JPanel inputPanel = new JPanel();
		inputPanel.setMinimumSize(new Dimension(300, 200));
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		
		machineNameField = new JTextField("",20);
		englishNameField = new JTextField("",20);
		osTypeNameField = new JTextField("", 20);
		ipAddressField = new JTextField("",20);
		
		inputPanel.add(new JLabel("中文名称:"));
		inputPanel.add(machineNameField);
		inputPanel.add(new JLabel("英文名称:"));
		inputPanel.add(englishNameField);
		inputPanel.add(new JLabel("系统类型:"));
		inputPanel.add(osTypeNameField);
		inputPanel.add(new JLabel("IP地址    :"));  
		inputPanel.add(ipAddressField);
		
		JButton okButton = new JButton("保存");
		JButton cancelButton = new JButton("取消");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isEmpty(machineNameField.getText())) {
					JOptionPane.showMessageDialog(MachineAddDialog.this, "请输入中文名称");
					return;
				}
				if(StringUtils.isEmpty(englishNameField.getText())){
					JOptionPane.showMessageDialog(MachineAddDialog.this, "请输入英文名称");
					return;
				}
				
				if(StringUtils.isEmpty(osTypeNameField.getText())){
					JOptionPane.showMessageDialog(MachineAddDialog.this, "请输入系统类型");
					return;
				}
				if(StringUtils.isEmpty(ipAddressField.getText())){
					JOptionPane.showMessageDialog(MachineAddDialog.this, "请输入IP地址");
					return;
				}
				
				machineName = machineNameField.getText();
				englishName = englishNameField.getText();
				osTypeName = osTypeNameField.getText();
				ipAddress = ipAddressField.getText();
				Machine machine = new Machine();
				machine.setMachineName(machineName);
				machine.setEnglishName(englishName);
				machine.setOsTypeName(osTypeName);
				machine.setIpAddress(ipAddress);

				MachineDao dao = new MachineDao();
				dao.insertMachine(machine);
				JOptionPane.showMessageDialog(MachineAddDialog.this, "新增设备成功","提示",JOptionPane.INFORMATION_MESSAGE);
				MachineAddDialog.this.dispose();
				parent.showTable();
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MachineAddDialog.this.dispose();
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