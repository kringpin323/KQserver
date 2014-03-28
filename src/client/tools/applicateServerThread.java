package client.tools;

import java.lang.Thread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class applicateServerThread extends Thread{
	
	private static final int POOL_SIZE = 500;   // ĳ��������size
	final int port = 10001;    // ����QQ����̵߳����Ӷ˿�
	public ServerSocket server;  // serverSocket
	Socket  client;   // ��ͻ�������
	ArrayList<String>  ipCountList = new ArrayList<String>();  // qq�����б�
	
	class clearIpCountThread extends Thread  // ��ʱ���ip�����б�
	{
		public void run(){
			try
			{
				while(true)
				{
					sleep(86400000);  // ���ÿ��24��Сʱ ���һ��ip�����б� �� һ�պ����ip����������qq����
					ipCountList.clear();
				}
			}
			catch(Exception e)
			{
				run();   // �׳��˴���������һ�Ρ�������������
				e.printStackTrace();
			}
		}
	}
	
	public applicateServerThread() throws SQLException, InterruptedException
	{
		try{
			server = new ServerSocket(port);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		start();
	}
	
	public void run()
	{
		clearIpCountThread clear = new clearIpCountThread();
		clear.start();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		while(true)
		{
			try
			{
				client = server.accept(); //��������ڿ�ʼ���ܴӿͻ��˴�������Ϣ��
				applicate_dealingThread adt = new applicate_dealingThread(client, ipCountList);
				executorService.execute(adt);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	
}