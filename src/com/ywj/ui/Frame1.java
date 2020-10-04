package com.ywj.ui;

import java.awt.*;
import javax.swing.*;
//import com.borland.jbcl.layout.XYLayout;
//import com.borland.jbcl.layout.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame1 extends JFrame {
//XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JRadioButton jRadioButton1 = new JRadioButton();
	JRadioButton jRadioButton2 = new JRadioButton();
	JButton jButton1 = new JButton();
	JLabel jLabel2 = new JLabel();
	JCheckBox jCheckBox1 = new JCheckBox();
	JCheckBox jCheckBox2 = new JCheckBox();
	JCheckBox jCheckBox3 = new JCheckBox();

	public static void main(String[] args) {
		Frame1 frame = new Frame1();
		frame.setBounds(100, 100, 200, 300);
		frame.setVisible(true);
	}

	public Frame1() {
		try {
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
//getContentPane().setLayout(xYLayout1);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		jLabel1.setText("�� ��");
//xYLayout1.setWidth(326);
//xYLayout1.setHeight(214);
		jButton1.setText("��bai ��");
		jButton1.addActionListener(new Frame1_jButton1_actionAdapter(this));
		jRadioButton2.setText("Ů");
		jLabel2.setText("�� �ã�");
		jCheckBox1.setText("����");
		jCheckBox2.setText("����");
		jCheckBox3.setText("�Ķ�");
		this.getContentPane().add(jLabel1);// , new XYConstraints(31, 60, 46, 21));
		this.getContentPane().add(jRadioButton1);// ,new XYConstraints(127, 60, -1, -1));
		this.getContentPane().add(jRadioButton2);// ,new XYConstraints(216, 60, -1, -1));
		this.getContentPane().add(jLabel2);// , new XYConstraints(31, 111, -1, -1));
		this.getContentPane().add(jCheckBox1);// , new XYConstraints(97, 106, -1, -1));
		this.getContentPane().add(jCheckBox2);// ,new XYConstraints(164, 106, -1, -1));
		this.getContentPane().add(jCheckBox3);// ,new XYConstraints(233, 106, -1, -1));
		this.getContentPane().add(jButton1);// , new XYConstraints(124, 154, -1, -1));
		jRadioButton1.setText("��");
		jRadioButton1.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(jRadioButton1);
		bg.add(jRadioButton2);
	}

	public void jButton1_actionPerformed(ActionEvent e) {
		System.out.println("�Ա�" + (jRadioButton1.isSelected() ? jRadioButton1.getText() : jRadioButton2.getText()));
		if (jCheckBox1.isSelected()) {
			System.out.println(jCheckBox1.getText());
		}
		if (jCheckBox2.isSelected()) {
			System.out.println(jCheckBox2.getText());
		}
		if (jCheckBox3.isSelected()) {
			System.out.println(jCheckBox3.getText());
		}
	}
}

class Frame1_jButton1_actionAdapter implements ActionListener {
	private Frame1 adaptee;

	Frame1_jButton1_actionAdapter(Frame1 adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}