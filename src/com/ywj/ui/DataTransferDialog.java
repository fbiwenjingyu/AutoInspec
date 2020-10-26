package com.ywj.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ywj.model.DataSourceConnectDBVO;
import com.ywj.model.DataTransferVO;
import com.ywj.model.MainAndBackDataSourceConnectDBVO;
import com.ywj.utils.MyDataBaseUtil;
import com.ywj.utils.SynchTask;

public class DataTransferDialog extends JDialog {
	private static final int DEFAULT_WIDTH = 350;
	private static final int DEFAULT_HEIGHT = 600;
	private DataTransferVO dataTransferVO;
	JCheckBox[] checkboxes;
	JCheckBox allSelect = new JCheckBox("全选");

	public DataTransferVO getDataTransferVO() {
		return dataTransferVO;
	}

	public void setDataTransferVO(DataTransferVO dataTransferVO) {
		this.dataTransferVO = dataTransferVO;
	}

	public DataTransferDialog(DataTransferVO dataTransferVO) throws Exception {
		super();
		this.setTitle("选择表");
		this.dataTransferVO = dataTransferVO;
		JButton startButton = new JButton("开始");
		JButton cancelButton = new JButton("取消");
		
		JPanel checkBoxPanel = new JPanel();
		
		JScrollPane jscrollPane=new JScrollPane(checkBoxPanel);
		jscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		checkBoxPanel.setSize(new Dimension(300, 240));
		checkBoxPanel.setBorder(BorderFactory.createEtchedBorder());
		
		MainAndBackDataSourceConnectDBVO mainAndBackDataSourceConnectDBVO = this.dataTransferVO.convertToMainAndBackDataSourceConnectDBVO();
		DataSourceConnectDBVO maindb = mainAndBackDataSourceConnectDBVO.getMaindb();
		List<String> tableNames = MyDataBaseUtil.getMysqlTableNames(maindb);
		checkBoxPanel.setLayout(new GridLayout(tableNames.size()+1, 1));
		checkBoxPanel.add(allSelect);
		checkboxes = new JCheckBox[tableNames.size()];
		for(int i=0;i<tableNames.size();i++) {
			checkboxes[i]= new JCheckBox(tableNames.get(i));
			checkBoxPanel.add(checkboxes[i]);
		}
		JPanel buttonPanel = new JPanel();
		
		allSelect.addChangeListener(new ChangeListener() {

	        public void stateChanged(ChangeEvent e) {
	            if(((JCheckBox)e.getSource()).isSelected()){
	            	for(JCheckBox box : checkboxes)
	            		box.setSelected(true);
	            }
	            if(!((JCheckBox)e.getSource()).isSelected()){
	            	for(JCheckBox box : checkboxes)
	            		box.setSelected(false);
	            }

	        }
	    });
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataTransferDialog.this.dispose();
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> tableNames = new ArrayList<String>();
				DataTransferDialog.this.dispose();
				for(JCheckBox box : checkboxes) {
					if(box.isSelected()) {
						tableNames.add(box.getText());
					}
				}
				
				ExecutorService exector = Executors.newFixedThreadPool(tableNames.size());
				long startTime = System.currentTimeMillis();
//				try {
//					mainAndBackDataSourceConnectDBVO.cancelForeinKey();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				for(String tableName : tableNames) {
					SynchTask synchTask = new SynchTask(mainAndBackDataSourceConnectDBVO,tableName);
					exector.submit(synchTask);
				}
				exector.shutdown();
				while(true) {
					if(exector.isTerminated()) {
						break;
					}
				}
				System.out.println("总共 耗时："
						+ (System.currentTimeMillis() - startTime) + "毫秒");
				
//				try {
//					mainAndBackDataSourceConnectDBVO.setForeinKey();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				JOptionPane.showMessageDialog(DataTransferDialog.this, "数据传输完成，总共耗时 " + (System.currentTimeMillis() - startTime)/1000 + "秒","提示",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		buttonPanel.add(startButton);
		buttonPanel.add(cancelButton);
		
		
		getContentPane().add(jscrollPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public DataTransferDialog() {
		super();
	}
	
	
	

}
