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

import com.ywj.dao.DataBaseDao;
import com.ywj.model.DataBase;
import com.ywj.utils.CountTask;


public class DataBasePanel extends JPanel{
	JButton addbutton;
	JButton editbutton;
	JButton delbutton;
	JButton refreshbutton;
	JLabel deptNameLabel;
	JTextField deptNameText;
	JButton searchbutton;
	JLabel totalLabel;
	JPanel top = new JPanel();
	JScrollPane tablePane = new JScrollPane();
	JTable table = new JTable();
	DataBaseDao dao = new DataBaseDao();
	
	public DataBasePanel() {
		setProperties();
		initTopPanel();
		initCenterPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(tablePane, BorderLayout.CENTER);
	}
	
	private void initTopPanel() {
		addbutton = new JButton("新增数据库");
		editbutton = new JButton("编辑数据库");
		delbutton = new JButton("删除数据库");
		deptNameLabel = new JLabel("部门名称");
		deptNameLabel.setLabelFor(deptNameText);
		deptNameText = new JTextField(15);
		searchbutton = new JButton("搜索");
		refreshbutton= new JButton("刷新");
		totalLabel = new JLabel("总条数 : 0条" ,JLabel.RIGHT);
		totalLabel.setAlignmentX(SwingConstants.RIGHT);
		top.add(addbutton);
		top.add(editbutton);
		top.add(delbutton);
		top.add(deptNameLabel);
		top.add(deptNameText);
		top.add(searchbutton);	
		top.add(refreshbutton);
		top.add(totalLabel);
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataBaseAddDialog(null,DataBasePanel.this);
			}
		});
		
		editbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				new DataBaseEditDialog(null,DataBasePanel.this,Integer.valueOf(id));
			}
		});
		
		delbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index == -1) return;
				String id = (String) table.getValueAt(index, 0);
				int num = JOptionPane.showConfirmDialog(DataBasePanel.this, "确认删除？","提示",JOptionPane.YES_NO_OPTION);
				switch (num) {
					case JOptionPane.YES_OPTION:
						dao.deleteDataBaseById(Integer.valueOf(id));
						JOptionPane.showMessageDialog(DataBasePanel.this, "删除数据库成功","提示",JOptionPane.INFORMATION_MESSAGE);
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
				showTableSearchByDeptName(deptNameText.getText());	
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
	
	public void showTable() {
		new Thread(()->{
			String colum[] = dao.getColumnNames();
			Object data[][] = dao.getData();
			TableModel dataModel = new DefaultTableModel(data, colum){
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			};
			table.setModel(dataModel);
			table.setRowHeight(30);
			addCountThread();
			table.validate();
		}).start();
		
    }
	
	public void showTableSearchByDeptName(String deptName) {
		if(StringUtils.isEmpty(deptName)) {
			showTable();
			return;
		}
		String colum[] = dao.getColumnNames();
		String data[][] = dao.getDataBySearch((deptName));
		TableModel dataModel = new DefaultTableModel(data, colum);
		table.setModel(dataModel);
		table.setRowHeight(30);
		addCountThread();
		table.validate();
    }
	
	private void addCountThread() {
		new Thread() {
			@Override
			public void run() {
				 List<FutureTask<Long>> taskList = new ArrayList<FutureTask<Long>>();
			        // 创建线程池
			        ExecutorService exec = Executors.newCachedThreadPool();
			        List<DataBase> list = dao.getAllDataBases();
			        for (int i = 0; i < list.size(); i++) {
			            // 传入Callable对象创建FutureTask对象
			            FutureTask<Long> ft = new FutureTask<Long>(new CountTask(list.get(i)));
			            taskList.add(ft);
			            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
			            exec.submit(ft);
			        }
			        long total = 0;
			        for(int i=0;i<taskList.size();i++) {
			        	FutureTask<Long> ft = taskList.get(i);
			        	try {
							Long result = ft.get();
							total += result;
							table.setValueAt(result, i, 3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			        totalLabel.setText("总条数 : " + total);
			}
		}.start();
		
	}
	
	

	private void setProperties() {
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}



}
