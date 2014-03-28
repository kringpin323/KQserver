package client.tools;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.net.*;

import common.message.personalInfo;
import common.message.testMessage;

import client.views.mainServer;

public class applicate_dealingThread extends Thread{ 
	PreparedStatement state1;
	PreparedStatement state2;   // 这两个说明何用？ 本来是有用的，现在算了
	Socket client;		// 与申请QQ的客户端socket
	String qq = "";   // qq号
	ArrayList<String> iplist;  // 获取从外界传来的申请ip列表
	int count = 0;
	
	public applicate_dealingThread(Socket s_client, ArrayList<String> l1) throws IOException, SQLException
	{
		this.iplist = l1;
//     这样定义不合法
//		this.state1 = mainServer.con1.prepareStatement();
//		this.state2 = mainServer.con2.prepareStatement();
		this.client = s_client;
	}
	
	public void run()
	{  //  从可申请qq号码表里面取出一个号码
		try
		{
			ObjectInputStream oin = new ObjectInputStream(client.getInputStream());
			personalInfo applicatePersonInfo = (personalInfo)oin.readObject();
			ObjectOutputStream oout = new ObjectOutputStream(client.getOutputStream());
			String ip = client.getInetAddress().toString().substring(1); // 从第一个开始才是地址，第0个是 0？
			// 获得ip的client ip的地址
			for(int i=0;i<iplist.size();i++)
			{
				if((iplist.get(i)).equals(ip))
					count++;  // 如果申请列 iplist中与现在申请的ip相同，那么count计数加1
			}
			iplist.add(ip);
			if(count < mainServer.limit)  // 判断此ip是否申请数次过多！！！
			{
				mainServer.textPane2.setText(mainServer.textPane2.getText()+(new Date(0))+": "+ip+"已经成功申请"+count+"次！\n");
				if(mainServer.array_qqCanUse.isEmpty())  // 没有可用的qq
				{
					oout.writeObject(null);
					return;
				}
				qq = mainServer.array_qqCanUse.remove(0);// 如果有可用的QQ，将头一个QQ号分配给这个客户，然后从arraylist中移除这个qq号
				oout.writeObject(new testMessage(qq,1)); // 返回qq和1，代表成功申请，qq号是qq
				
				PreparedStatement state_del = mainServer.con1.prepareStatement("delete from valuableqqnum where qq = ?");
				state_del.setString(1, qq);
				state_del.execute();
				// 以上语句实现了在数据库中删除将要给予的QQ
				
				mainServer.map_array_passwordMap.put(qq, applicatePersonInfo.password); // 加到密码序列
				
				PreparedStatement state_ins_pwd = mainServer.con1.prepareStatement("insert into qqsersys(qq,password) values(?,?)");
				state_ins_pwd.setString(1,qq);
				state_ins_pwd.setString(2,applicatePersonInfo.password);
				state_ins_pwd.execute();
				// 实现了将QQ加到数据库中
				
				PreparedStatement state_ins_qqip = mainServer.con1.prepareStatement("insert into qqip(qq,ip,date) values(?,?,?)");
				state_ins_qqip.setString(1, qq);
				state_ins_qqip.setString(2, "");
				state_ins_qqip.setString(3, "");
				state_ins_qqip.executeUpdate();
				state_ins_qqip.close();
				
				// whether can add 该功能省略
				
				// insert email 功能省略
				
				// insert sysmes
				PreparedStatement state_ins_sysmes = mainServer.con1.prepareStatement("insert into sysmes(qq,warning) values(?,?)");
				state_ins_sysmes.setString(1, qq);
				state_ins_sysmes.setString(2, "");
				state_ins_sysmes.executeUpdate();
				state_ins_sysmes.close();
				// insertMainInfo
				PreparedStatement state_ins_mainInfo = mainServer.con1.prepareStatement("insert into qqusersys(qq,nickname,sex,bri_year,bri_month,bri_day,country,province,city) values(?,?,?,?,?,?,?,?,?)");
				state_ins_mainInfo.setString(1, qq);
				state_ins_mainInfo.setString(2, applicatePersonInfo.nickname);
				state_ins_mainInfo.setString(3,applicatePersonInfo.sex);
				state_ins_mainInfo.setString(4,applicatePersonInfo.bri_year);
				state_ins_mainInfo.setString(5,applicatePersonInfo.bri_month);
				state_ins_mainInfo.setString(6,applicatePersonInfo.bri_day);
				state_ins_mainInfo.setString(7,applicatePersonInfo.country);
				state_ins_mainInfo.setString(8,applicatePersonInfo.province);
				state_ins_mainInfo.setString(9,applicatePersonInfo.city);
				state_ins_mainInfo.executeUpdate();
				state_ins_mainInfo.close();
						
		//++++++++++++++++++++++++++以下开始用con2连接，为什么？		
				// createFriendsList
				String sql_create_FriendList = "create table friendsList_"+qq+" (qq varchar(7) primary key,remark varchar(20), groupName varchar(20)) charset utf8 collate utf8_general_ci;";
				PreparedStatement state_create_FriendList = mainServer.con2.prepareStatement(sql_create_FriendList);
				state_create_FriendList.executeUpdate();
				// createGroupsList
				String sql_create_GroupList = "create table  groupsList_" + qq + " (groupName varchar(20) primary key) charset utf8 collate utf8_general_ci;";
				PreparedStatement state_create_GroupList = mainServer.con2.prepareStatement(sql_create_GroupList);
				state_create_GroupList.executeUpdate();
				// insertGroupsList
				String sql_ins_group1 = "insert into groupsList_" + qq + " values ('我的好友'); ";
				String sql_ins_group2 = "insert into groupsList_" + qq + " values ('同学'); ";
				String sql_ins_group3 = "insert into groupsList_" + qq + " values ('家人'); ";
				String sql_ins_group4 = "insert into groupsList_" + qq + " values ('同事'); ";
				
				PreparedStatement state_ins_Group1 = mainServer.con2.prepareStatement(sql_ins_group1);
				PreparedStatement state_ins_Group2 = mainServer.con2.prepareStatement(sql_ins_group2);
				PreparedStatement state_ins_Group3 = mainServer.con2.prepareStatement(sql_ins_group3);
				PreparedStatement state_ins_Group4 = mainServer.con2.prepareStatement(sql_ins_group4);
				
				state_ins_Group1.executeUpdate();
				state_ins_Group2.executeUpdate();
				state_ins_Group3.executeUpdate();
				state_ins_Group4.executeUpdate();
				
				state_ins_Group1.close();
				state_ins_Group2.close();
				state_ins_Group3.close();
				state_ins_Group4.close();
				
				// 恭喜你，终于，写完了。
				mainServer.textPane2.setText(mainServer.textPane2.getText()+(new Date())+ ": "+"qq号码："+qq+" 申请完毕！\n");
				oin.close();
				mainServer.map_IsSysMessageExist.put(qq,0);// 是否退出系统信息，那个qq所在的客户端已经处理完与系统的通讯，所以已经是0
				
				
				state_del.close();
				state_ins_pwd.close();
				
				
				
				
			}
			else  // 若申请次所过多，无法申请到qq
			{
				oout.writeObject(null);
			}
			oout.close();
			oin.close();
			
			client.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			mainServer.textPane2.setText(mainServer.textPane2.getText()+"过多qq号申请导致，某个qq号重复插入数据库，数据库已拒绝！\n");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
