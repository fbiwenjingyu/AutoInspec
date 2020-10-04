package com.ywj.ui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.ConsoleDao;
import com.ywj.model.Console;


public class CheckBoxRenderer extends JPanel implements TableCellRenderer {
	JCheckBox box1 = new JCheckBox();
	JCheckBox box2 = new JCheckBox();
	private int consoleId;
    public CheckBoxRenderer() {
//    	this.setLayout(new GridLayout(2, 2));
//		this.add(box1);
//		this.add(new JLabel("admin"));
//		this.add(box2);
//		this.add(new JLabel("tmfrk"));
//		this.setVisible(true);
//		this.setOpaque(true);
//        this.setVisible(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
    	this.setVisible(true);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("UIManager"));
        }
        
        int index = table.getSelectedRow();
        if(index == -1) return this;
		String id = (String) table.getValueAt(index, 0);
		this.setConsoleId(Integer.parseInt(id));
		renderCell();
		table.setValueAt(this, index, 5);
        return this;
    }
    
    private void renderCell() {
    	ConsoleDao dao = new ConsoleDao();
    	Console console = dao.getConsoleById(this.getConsoleId());
    	this.setLayout(new GridLayout(2, 2));
    	this.removeAll();
		if(StringUtils.isNoneEmpty(console.getLoginName1())) {
			this.add(box1);
			this.add(new JLabel(console.getLoginName1()));
		}
		if(StringUtils.isNoneEmpty(console.getLoginName2())) {
			this.add(box2);
			this.add(new JLabel(console.getLoginName2()));
		}
		this.setVisible(true);
		this.setOpaque(true);
		this.repaint();
	}

	public int getConsoleId() {
		return consoleId;
	}

	public void setConsoleId(int consoleId) {
		this.consoleId = consoleId;
	}
    
    
}