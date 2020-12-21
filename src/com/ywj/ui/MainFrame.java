package com.ywj.ui;

import javax.swing.*;

import com.ywj.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	static final int WIDTH=1200;
    static final int HEIGHT=800;
    private User user;
    public MainFrame(User user) {
    	this.user = user;
    	setProperties();
    	addTabs();
    	if("1".equals(user.getFirstLogin())) {
    		new UserPassUpdateDialog(this, null, user);
    	}
    	
    }

	private void addTabs() {
		JTabbedPane contentTabPanel = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		Container c = this.getContentPane();
		contentTabPanel.addTab("�û�����",new UserPanel());
		contentTabPanel.addTab("�豸IPѲ��",new MachinePanel());
		contentTabPanel.addTab("����̨����",new ConsolePanel());
		contentTabPanel.addTab("����ͳ��",new DataBasePanel());
		contentTabPanel.addTab("���ݴ���",new DataTransferPanel());
		contentTabPanel.addTab("����ʱ��ͳ��",new UpdateTimePanel(MainFrame.this));
		contentTabPanel.setSelectedIndex(0);
		c.add(contentTabPanel);
	}

	private void setProperties() {
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("�Զ�����ά����ϵͳ");
		this.setLocationRelativeTo(null);
    	this.setVisible(true);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
}
