package com.ywj.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.ywj.dao.DataTransferDao;
import com.ywj.model.DataTransferVO;

class DataTransferButtonEditor extends DefaultCellEditor {
	    protected JButton button;
	    private String label;
	    private boolean isPushed;
	    private int dataTransferId;

	    public int getDataTransferId() {
			return dataTransferId;
		}

		public void setDataTransferId(int dataTransferId) {
			this.dataTransferId = dataTransferId;
		}

		public DataTransferButtonEditor(JTextField checkBox) {
			super(checkBox);
			this.setClickCountToStart(1);
			button = new JButton("数据传输");
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int id = DataTransferButtonEditor.this.getDataTransferId();
					DataTransferDao dao = new DataTransferDao();
					DataTransferVO dataTransferVO = dao.getDataTransferVOById(id);
					System.out.println(dataTransferVO);	
					
					try {
						new DataTransferDialog(dataTransferVO);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

		}
 
	    public Component getTableCellEditorComponent(final JTable table, Object value,
	            boolean isSelected,int row, int column) {
	        if (isSelected) {
	            button.setForeground(table.getSelectionForeground());
	            button.setBackground(table.getSelectionBackground());
	        } else {
	            button.setForeground(table.getForeground());
	            button.setBackground(table.getBackground());
	        }
	        label = (value == null) ? "" : value.toString();
	        button.setText(label);
//	        System.out.println(table.getSelectedRow()) ;
//            System.out.println(table.getSelectedColumn()) ;
            int index = table.getSelectedRow();
			String id = (String) table.getValueAt(index, 0);
			this.setDataTransferId(Integer.parseInt(id));
	        isPushed = true;
	        return button;
	    }
	    
	    @Override
	    public Object getCellEditorValue() {
	        if (isPushed) {
	            // 
	            // 
	           // JOptionPane.showMessageDialog(button, label + ": Ouch!");
	            // System.out.println(label + ": Ouch!");
	        }
	        isPushed = false;
	        return new String("数据传输");
	    }
	    
	    @Override
	    public boolean stopCellEditing() {
	        isPushed = false;
	        return super.stopCellEditing();
	    }

	    @Override
	    public boolean shouldSelectCell(EventObject anEvent) {
//	        System.out.println(1);
	        return super.shouldSelectCell(anEvent);
	    }
}