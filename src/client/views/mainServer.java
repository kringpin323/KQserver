package client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Menu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jws.Oneway;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import common.message.*;

import client.tools.applicate_dealingThread;
import client.tools.applicateServerThread;

//import server.thread.applicateThread.applicateServerThread;
//import server.thread.getInfoThread.getInfoThread;
//import server.thread.loginThread.loginServerThread;

public class mainServer extends JFrame{
	JPanel j1 = null;
	JPanel j2 = null;
	JPanel j3 = null;
	JPanel j4 = null;
	JScrollPane jsroll0 = null;
	JScrollPane jsroll1 = null;
	JScrollPane jsroll2 = null;
	JScrollPane jsroll3 = null;
	JTextPane textPane0 = null;
	JTextPane textPane1 = null;
	public static JTextPane textPane2 = null;
	JTextField textf_setLimit =null;
	JButton jb_setLimit = null;
	JLabel jlable_setLimit = null;
	JComboBox jcom_setDatabase = null;
	JButton jb_setDatabase = null;
	JLabel jlable_setDatabase = null;
	JTable table = null;
	JLabel jlable1 = null;
	JLabel jlable2 = null;
	JLabel jlable_warning = null;
	
	JButton jb1 = null;
	JButton jb2 = null;
	JButton jb3 = null;
	JButton jb4 = null;
	
	MyTableModel myModel = null;
	
	applicateServerThread applicateServer;
//	getInfoThread getInfoServer;
	
	public static Connection con1 = null;
	public static Connection con2 = null;
	public static int limit = 3; //申请限制次数 ， 同一个ip不能 超过3次申请qq号
	public static ArrayList<String> array_qqCanUse = new ArrayList<String>();  // 可用的qq数组
	public static HashMap<String, String> map_array_passwordMap = new HashMap<String, String>();
	public static HashMap<String, Integer> map_IsSysMessageExist = new HashMap<String , Integer>();
	
	
	public mainServer()
	{
		createUserInterface();
	}
	
	class refresh extends Thread{}
	
	private void createUserInterface(){
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.lightGray);
		contentPane.setLayout(null);
		
		//系统消息
		j1 = new JPanel();
		j1.setLayout(null);
		
		jlable1 = new JLabel("已发送的系统消息");
		jlable1.setFont(new Font("宋体",Font.PLAIN,12));
		jlable1.setBounds(0,0,120,15);
		j1.add(jlable1);
		
//		
//		
		textPane0 = new JTextPane();
		textPane0.setEditable(false);
		textPane0.setFont(new Font("宋体",Font.PLAIN,12));
		textPane0.setDisabledTextColor(Color.red);
		textPane0.setBounds(0,20,600,180);
		j1.add(textPane0);
		jsroll0 = new JScrollPane(textPane0);
		jsroll0.setBounds(0,20,590,180);
		jsroll0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		j1.add(jsroll0);
		//
		//
		jlable2 = new JLabel("请在下面输入系统信息，按ctrl+enter发送：");
		jlable2.setBounds(0,200,250,15);
		jlable2.setFont(new Font("宋体", Font.PLAIN,12));
		j1.add(jlable2);
		//
		jb1 = new JButton("清空已发送");
		jb1.setBounds(470,202,100,15);
		jb1.setFont(new Font("宋体", Font.PLAIN,12));
		j1.add(jb1);
		jb1.addActionListener(new ActionListener(){
//			@Override
			public void actionPerformed(ActionEvent e){
				
			} 
		});
		
		//
		textPane1 = new JTextPane();
		textPane1.setBounds(0,220,600,80);
		textPane1.setFont(new Font("宋体",Font.PLAIN,12));
		j1.add(textPane1);
		jsroll3 = new JScrollPane(textPane1);
		jsroll3.setBounds(0,220,590,80);
		jsroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		j1.add(jsroll3);
		//
		jb2 = new JButton("发送");
		jb2.setBounds(470,302,100,15);
		jb2.setFont(new Font("宋体", Font.PLAIN,12));
		j1.add(jb2);
		jb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
			
			}
		});
		
		//系统日志
		j2 = new JPanel();
		j2.setLayout(null);
		textPane2 = new JTextPane();
		textPane2.setBounds(0,0,600,300);
		textPane2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		textPane2.setText("系统日志:\n");
		textPane2.setFont(new Font("宋体",Font.PLAIN,12));
		textPane2.setEditable(false);
		j2.add(textPane2);
		jsroll1 = new JScrollPane(textPane2);
		jsroll1.setBounds(0,0 ,590, 300);
		jsroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		j2.add(jsroll1);
		jb3 = new JButton("清空");
		jb3.setBounds(470, 302, 100, 15);
		jb3.setFont(new Font("宋体",Font.PLAIN,12));
		j2.add(jb3);
		jb3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		
		//在线情况
		j3 = new JPanel();
		j3.setLayout(null);
		//表格
		myModel = new MyTableModel();
		table = new JTable(myModel);
		table.setEnabled(true);
		table.setRowSelectionAllowed(true);//设置可否被选择，默认为false
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setGridColor(Color.blue);
		table.setBounds(0, 0, 600, 200);
		jsroll2 = new JScrollPane(table);
		jsroll2.setBounds(0,0,590,300);
		jsroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		j3.add(jsroll2);
		//
		jb4 = new JButton("刷新");
		jb4.setBounds(470, 302, 100, 15);
		jb4.setFont(new Font("宋体",Font.PLAIN,12));
		j3.add(jb4);
		jb4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{}
		});
		//系统设置
		j4 = new JPanel();
		j4.setLayout(null);
		jlable_setLimit = new JLabel("同一个Ip一天可申请的次数：");
		jlable_setLimit.setBounds(10,10,200,20);
		textf_setLimit = new JTextField();
		textf_setLimit.setBounds(200,10,90,20);
		jb_setLimit = new JButton("确定");
		jb_setLimit.setBounds(300,10,60,20);
		jb_setLimit.setFont(new Font("宋体",Font.PLAIN,12));
		j4.add(jlable_setLimit);
		j4.add(jb_setLimit);
		j4.add(textf_setLimit);
		jb_setLimit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		jlable_setDatabase = new JLabel("请选择数据库，对不起，其实你没得选择，只有MYsql。");
		jlable_setDatabase.setBounds(10,40,200,20);
		String item[] = {"MySql","Oracle"};
		jcom_setDatabase = new JComboBox(item);
		jcom_setDatabase.setBounds(200,40,90,20);
		jlable_warning = new JLabel("(提示：现在只有mysql，你选什么都是连接mysql！)");
		jlable_warning.setBounds(300,40,300,20);
		jlable_warning.setFont(new Font("宋体",Font.PLAIN,12));
		j4.add(jlable_setDatabase);
		j4.add(jcom_setDatabase);
		j4.add(jlable_warning);
		jcom_setDatabase.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e)
			{
				
			}
		});
		
		//
		JTabbedPane tab = new JTabbedPane();
		tab.setBounds(0,0,600,400);
		
		tab.addTab("系统日志", j2);
		tab.addTab("发送系统消息",j1);
		
		tab.addTab("在线情况", j3);
		tab.addTab("系统设置", j4);
		tab.setFont(new Font("宋体",Font.PLAIN,12));
		contentPane.add(tab);
		Menu m = new Menu();
		m.setFont(new Font("宋体",Font.PLAIN,12));
		this.setJMenuBar(m);
		
		Dimension  screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = this.getSize();
		int x = (screensize.width - framesize.width)/4;
		int y = (screensize.height - framesize.height)/4;
		this.setLocation(x,y);
		this.show();
		setTitle("KQserver MatherFucker");
		setResizable(false);
		setSize(600,400);
		setVisible(true);
		
		
		}
	
	class MyTableModel extends AbstractTableModel
	{
		int count = 0;
		private String[] columnNames = {"QQ","ip","main_port","sys_port","heartbeat_port","chat_port"};
		private String[][] data = new String[count][0];
		public MyTableModel()
		{
//			Map m =
			
		}
		
		public int getColumnCount()
		{
			return columnNames.length;
		}
		public int getRowCount()
		{
			return data.length;
		}
		
		public String getColumnName(int col)
		{
			return columnNames[col];
		}
		
		public String getValueAt(int row,int col)
		{
			return data[row][col];
		}
		
		public Class geetColumnClass(int c)
		{
			return getValueAt(0,c).getClass();
		}
		
		public boolean isCellEditable(int row, int col)
		{
			return false;
		}
		
		
	}
	class Menu extends JMenuBar
	{
		private JDialog aboutDialog;
		
		/*
		 * 菜单初始化操作
		 * */
		public Menu()
		{
			JMenu fileMenu1 = new JMenu("文件");
			JMenu fileMenu2 = new JMenu("操作");
			JMenu fileMenu3 = new JMenu("帮助");
			JMenuItem aboutMenuItem = new JMenuItem("关于。。。");
			JMenuItem exitMenuItem = new JMenuItem("退出");
			JMenuItem startMenuItem = new JMenuItem("重启服务器");
			JMenuItem stopMenuItem = new JMenuItem("停止服务器");
			fileMenu1.setFont(new Font("宋体",Font.PLAIN,12));
			fileMenu2.setFont(new Font("宋体",Font.PLAIN,12));
			fileMenu3.setFont(new Font("宋体",Font.PLAIN,12));
			aboutMenuItem.setFont(new Font("宋体", Font.PLAIN,12));
			exitMenuItem.setFont(new Font("宋体",Font.PLAIN,12));
			startMenuItem.setFont(new Font("宋体",Font.PLAIN,12));
			stopMenuItem.setFont(new Font("宋体",Font.PLAIN,12));
			fileMenu1.add(exitMenuItem);
			fileMenu2.add(startMenuItem);
			fileMenu2.add(stopMenuItem);
			fileMenu3.add(aboutMenuItem);
			this.add(fileMenu1);
			this.add(fileMenu2);
			this.add(fileMenu3);
			aboutDialog = new JDialog();
			initAboutDialog();
			
			exitMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
//					String sql_update = "update mainInfo set status "
					dispose();
					System.exit(0);
				}
			});
			
			aboutMenuItem.addActionListener(new ActionListener(){
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e)
				{
					aboutDialog.show();
				}
			});
		}
		
		public JDialog getAboutDialog()
		{
			return aboutDialog;
		}
		
		/*
		 * 设置"关于"对话框的一些操作
		 * */
		public void initAboutDialog()
		{
			aboutDialog.setTitle("motherfucker,扑街，最近好中意呢句粗话");
			Container con = aboutDialog.getContentPane();
			//Swing 中使用html语句
			JLabel aboutLabel = new JLabel(
					"<html><b>"
						+"<center><br>QQserver</br><br>verson:1.0</br><br>Copyright 2014 gdufs, All matherfucker reserverd 版权所有 广东外语外贸大学 kingpin lin</br></html></b>",
						JLabel.CENTER);
			aboutLabel.setFont(new Font("黑体",Font.HANGING_BASELINE,15));
			con.add(aboutLabel,BorderLayout.CENTER);
			aboutDialog.setResizable(false);
			aboutDialog.setSize(600,200);
			aboutDialog.setLocation(205,300);
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException,
		SQLException, IOException, InterruptedException
	{
		mainServer server = new mainServer();
	}
	
}
