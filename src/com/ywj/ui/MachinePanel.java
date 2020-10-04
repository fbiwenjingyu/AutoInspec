package com.ywj.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;

import com.ywj.dao.MachineDao;
import com.ywj.model.Machine;
import com.ywj.utils.PingTask;

public class MachinePanel extends JPanel {
	JButton addbutton;
	JButton editbutton;
	JButton delbutton;
	JLabel machineNameLabel;
	JTextField machineNameText;
	JLabel ipAddressLabel;
	JTextField ipAddressText;
	JButton searchbutton;
	JButton refreshbutton;
	JPanel top = new JPanel();
	JScrollPane tablePane = new JScrollPane();
	JTable table = new JTable();
	MachineDao dao = new MachineDao();
	
	public MachinePanel() {
		setProperties();
		initTopPanel();
		initCenterPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(tablePane, BorderLayout.CENTER);
		
	}
	
	private void addPingThread() {
		new Thread() {
			@Override
			public void run() {
				 List<FutureTask<Boolean>> taskList = new ArrayList<FutureTask<Boolean>>();
			        // 创建线程池
			        ExecutorService exec = Executors.newCachedThreadPool();
			        List<Machine> list = dao.getAllMachines();
			        for (int i = 0; i < list.size(); i++) {
			            // 传入Callable对象创建FutureTask对象
			            FutureTask<Boolean> ft = new FutureTask<Boolean>(new PingTask(list.get(i)));
			            taskList.add(ft);
			            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
			            exec.submit(ft);
			        }
			        for(int i=0;i<taskList.size();i++) {
			        	FutureTask<Boolean> ft = taskList.get(i);
			        	try {
							Boolean result = ft.get();
							String pingResult = "";
							if(result) {
								pingResult = "√";
							}else {
								pingResult = "×";
							}
							table.setValueAt(pingResult, i, 5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			}
		}.start();
		
	}

	private void initTopPanel() {
		addbutton = new JButton("新增设备");
		editbutton = new JButton("编辑设备");
		delbutton = new JButton("删除设备");
		machineNameLabel = new JLabel("中文名");
		machineNameLabel.setLabelFor(machineNameText);
		machineNameText = new JTextField(15);
		ipAddressLabel = new JLabel("IP地址");
		ipAddressLabel.setLabelFor(ipAddressText);
		ipAddressText = new JTextField(15);
		searchbutton = new JButton("搜索");
		refreshbutton = new JButton("刷新");
		top.add(addbutton);
		top.add(editbutton);
		top.add(delbutton);
		top.add(machineNameLabel);
		top.add(machineNameText);
		top.add(ipAddressLabel);
		top.add(ipAddressText);
		top.add(searchbutton);	
		top.add(refreshbutton);	
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MachineAddDialog(null,MachinePanel.this);
			}
		});
		
		editbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				new MachineEditDialog(null,MachinePanel.this,Integer.valueOf(id));
			}
		});
		
		delbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				int num = JOptionPane.showConfirmDialog(MachinePanel.this, "确认删除？","提示",JOptionPane.YES_NO_OPTION);
				switch (num) {
					case JOptionPane.YES_OPTION:
						dao.deleteMachineById(Integer.valueOf(id));
						JOptionPane.showMessageDialog(MachinePanel.this, "删除设备成功","提示",JOptionPane.INFORMATION_MESSAGE);
						break;
					case JOptionPane.NO_OPTION:
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
				}
				showTable();
			}
		});
		
		searchbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTableSearch(machineNameText.getText(),ipAddressText.getText());		
			}
		});
		
		refreshbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTable();	
			}
		});
	}
	
	private void initCenterPanel() {
		TableModel dataModel = new DefaultTableModel(new String[][] {},new String[] {});
		table.setModel(dataModel);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		table.setDefaultRenderer(Object.class, tcr);
		showTable();
		tablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tablePane.getViewport().add(table);
	}
	
	public void showTableSearch(String name,String ipAddress) {
		if(StringUtils.isEmpty(name) && StringUtils.isEmpty(ipAddress)) {
			showTable();
			return;
		}
		String colum[] = dao.getColumnNames();
		String data[][] = dao.getDataBySearch(name,ipAddress);
		TableModel dataModel = new DefaultTableModel(data, colum);
		table.setModel(dataModel);
		addPingThread();
		table.validate();
	}
	
	public void showTable() {
		String colum[] = dao.getColumnNames();
		String data[][] = dao.getData();
		TableModel dataModel = new DefaultTableModel(data, colum){
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		  };
		table.setModel(dataModel);
		addPingThread();
		table.validate();
	}
	
	private void setProperties() {
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}

}
