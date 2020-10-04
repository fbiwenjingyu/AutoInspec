package com.ywj.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.ConsoleDao;
import com.ywj.model.Console;

class ButtonEditor extends DefaultCellEditor {
	    protected JButton button;
	    private String label;
	    private boolean isPushed;
	    private int consoleId;

	    public int getConsoleId() {
			return consoleId;
		}

		public void setConsoleId(int consoleId) {
			this.consoleId = consoleId;
		}

		public ButtonEditor(JTextField checkBox) {
			super(checkBox);
			this.setClickCountToStart(1);
			button = new JButton("登录");
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int id = ButtonEditor.this.getConsoleId();
					ConsoleDao dao = new ConsoleDao();
					Console console = dao.getConsoleById(id);
					String webBrowser = "iexplore";
					if (console != null) {
						if (console.getWebBrowser().toLowerCase().indexOf("chrome") != -1) {
							webBrowser = "chrome";
						}
					}

					System.out.println("id = " + id);

					// 构造命令
					String cmd = "cmd /c start ";
					cmd = cmd + webBrowser + " ";
					// 构造本地文件路径或者网页URL
					//String file = " https://www.baidu.com/s?wd=java";
					String innerurl = console.getInnerurl();
					String outurl = console.getOuturl();
					String loginname1 = console.getLoginName1();
					String loginpasword1 = console.getLoginPassword1();
					String loginname2 = console.getLoginName2();
					String loginpasword2 = console.getLoginPassword2();
					String inneruserTag = console.getInnerUserTagName();
					String innerPasswordTag = console.getInnerPasswordTagName();
					String outuserTag = console.getOutUserTagName();
					String outpasswordTag = console.getOutPasswordTagName();
					
					try {
						// 执行操作
						/*
						 * if(StringUtils.isNoneEmpty(innerurl)) {
						 * if(StringUtils.isNotEmpty(loginname1)) { String paramStr = "";
						 * if(innerurl.indexOf("?") != -1) { paramStr = paramStr + "&" + inneruserTag +
						 * "=" + loginname1 + "&" + innerPasswordTag + "=" + loginpasword1;
						 * Runtime.getRuntime().exec(cmd +"\""+ innerurl + paramStr + "\""); }else {
						 * paramStr = paramStr + "?" + inneruserTag + "=" + loginname1 + "&" +
						 * innerPasswordTag + "=" + loginpasword1; Runtime.getRuntime().exec(cmd +"\"" +
						 * innerurl + paramStr + "\""); } } if(StringUtils.isNotEmpty(loginname2)) {
						 * String paramStr = ""; if(innerurl.indexOf("?") != -1) { paramStr = paramStr +
						 * "&" + inneruserTag + "=" + loginname2 + "&" + innerPasswordTag + "=" +
						 * loginpasword2; Runtime.getRuntime().exec(cmd + "\""+ innerurl + paramStr +
						 * "\""); }else { paramStr = paramStr + "?" + inneruserTag + "=" + loginname2 +
						 * "&" + innerPasswordTag + "=" + loginpasword2; Runtime.getRuntime().exec(cmd +
						 * "\"" + innerurl + paramStr + "\""); } }
						 * 
						 * } if(StringUtils.isNoneEmpty(outurl)) {
						 * if(StringUtils.isNotEmpty(loginname1)) { String paramStr = "";
						 * if(outurl.indexOf("?") != -1) { paramStr = paramStr + "&" + outuserTag + "="
						 * + loginname1 + "&" + outpasswordTag + "=" + loginpasword1;
						 * Runtime.getRuntime().exec(cmd + "\""+ outurl + paramStr + "\""); }else {
						 * paramStr = paramStr + "?" + outuserTag + "=" + loginname1 + "&" +
						 * outpasswordTag + "=" + loginpasword1; System.out.println(cmd + "\"" + outurl
						 * + paramStr + "\""); Runtime.getRuntime().exec(cmd + "\"" + outurl + paramStr
						 * + "\""); } } if(StringUtils.isNotEmpty(loginname2)) { String paramStr = "";
						 * if(outurl.indexOf("?") != -1) { paramStr = paramStr + "&" + outuserTag + "="
						 * + loginname2 + "&" + outpasswordTag + "=" + loginpasword2;
						 * Runtime.getRuntime().exec(cmd + "\""+ outurl + paramStr + "\""); }else {
						 * paramStr = paramStr + "?" + outuserTag + "=" + loginname2 + "&" +
						 * outpasswordTag + "=" + loginpasword2; Runtime.getRuntime().exec(cmd + "\""+
						 * outurl + paramStr + "\""); } } }
						 */
						if(StringUtils.isNotEmpty(innerurl)) {
							Runtime.getRuntime().exec(cmd +"\""+ innerurl + "\"");
						}
						if(StringUtils.isNotEmpty(outurl)) {
							Runtime.getRuntime().exec(cmd +"\""+ outurl + "\"");
						}
					} catch (IOException e1) {
						// 打印异常
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
			this.setConsoleId(Integer.parseInt(id));
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
	        return new String("登录");
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