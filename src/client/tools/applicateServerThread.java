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
	
	private static final int POOL_SIZE = 500;   // 某个东西的size
	final int port = 10001;    // 申请QQ这个线程的连接端口
	public ServerSocket server;  // serverSocket
	Socket  client;   // 与客户端连接
	ArrayList<String>  ipCountList = new ArrayList<String>();  // qq申请列表
	
	class clearIpCountThread extends Thread  // 定时清空ip申请列表
	{
		public void run(){
			try
			{
				while(true)
				{
					sleep(86400000);  // 这个每隔24个小时 清空一次ip申请列表 ， 一日后，这个ip可以再申请qq号了
					ipCountList.clear();
				}
			}
			catch(Exception e)
			{
				run();   // 抛出了错误，再运行一次。。。。。。。
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
				client = server.accept(); //到这里，终于开始接受从客户端穿来的信息。
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
