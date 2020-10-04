package com.ywj.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.ConsoleDao;
import com.ywj.model.Console;


public class CheckBoxEditor extends DefaultCellEditor {
		private JPanel panel = new JPanel();
		private int consoleId;
		JCheckBox box1 = new JCheckBox();
		JCheckBox box2 = new JCheckBox();
		

		public CheckBoxEditor(JTextField checkBox) {
			super(checkBox);
		}

	    public Component getTableCellEditorComponent(final JTable table, Object value,
	            boolean isSelected,int row, int column) {
	    	if (isSelected) {
	    		panel.setForeground(table.getSelectionForeground());
	    		panel.setBackground(table.getSelectionBackground());
	        } else {
	        	panel.setForeground(table.getForeground());
	        	panel.setBackground(table.getBackground());
	        }
	    	int index = table.getSelectedRow();
			String id = (String) table.getValueAt(index, 0);
			this.setConsoleId(Integer.parseInt(id));
			renderCell();
			table.setValueAt(panel, index, 5);
	        return panel;
	    }
	        
	    private void renderCell() {
	    	ConsoleDao dao = new ConsoleDao();
	    	Console console = dao.getConsoleById(this.getConsoleId());
	    	panel.setLayout(new GridLayout(2, 2));
	    	panel.removeAll();
			if(StringUtils.isNoneEmpty(console.getLoginName1())) {
				panel.add(box1);
				panel.add(new JLabel(console.getLoginName1()));
			}
			if(StringUtils.isNoneEmpty(console.getLoginName2())) {
				panel.add(box2);
				panel.add(new JLabel(console.getLoginName2()));
			}
			panel.setVisible(true);
			this.setClickCountToStart(1);
			panel.setOpaque(true);
			panel.repaint();
		}

		@Override
	    public boolean stopCellEditing() {
	        return super.stopCellEditing();
	    }

	    @Override
	    public boolean shouldSelectCell(EventObject anEvent) {
	        return super.shouldSelectCell(anEvent);
	    }

		public int getConsoleId() {
			return consoleId;
		}

		public void setConsoleId(int consoleId) {
			this.consoleId = consoleId;
		}
	    
	    
}