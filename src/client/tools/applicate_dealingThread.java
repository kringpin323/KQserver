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
	PreparedStatement state2;   // ������˵�����ã� ���������õģ���������
	Socket client;		// ������QQ�Ŀͻ���socket
	String qq = "";   // qq��
	ArrayList<String> iplist;  // ��ȡ����紫��������ip�б�
	int count = 0;
	
	public applicate_dealingThread(Socket s_client, ArrayList<String> l1) throws IOException, SQLException
	{
		this.iplist = l1;
//     �������岻�Ϸ�
//		this.state1 = mainServer.con1.prepareStatement();
//		this.state2 = mainServer.con2.prepareStatement();
		this.client = s_client;
	}
	
	public void run()
	{  //  �ӿ�����qq���������ȡ��һ������
		try
		{
			ObjectInputStream oin = new ObjectInputStream(client.getInputStream());
			personalInfo applicatePersonInfo = (personalInfo)oin.readObject();
			ObjectOutputStream oout = new ObjectOutputStream(client.getOutputStream());
			String ip = client.getInetAddress().toString().substring(1); // �ӵ�һ����ʼ���ǵ�ַ����0���� 0��
			// ���ip��client ip�ĵ�ַ
			for(int i=0;i<iplist.size();i++)
			{
				if((iplist.get(i)).equals(ip))
					count++;  // ��������� iplist�������������ip��ͬ����ôcount������1
			}
			iplist.add(ip);
			if(count < mainServer.limit)  // �жϴ�ip�Ƿ��������ι��࣡����
			{
				mainServer.textPane2.setText(mainServer.textPane2.getText()+(new Date(0))+": "+ip+"�Ѿ��ɹ�����"+count+"�Σ�\n");
				if(mainServer.array_qqCanUse.isEmpty())  // û�п��õ�qq
				{
					oout.writeObject(null);
					return;
				}
				qq = mainServer.array_qqCanUse.remove(0);// ����п��õ�QQ����ͷһ��QQ�ŷ��������ͻ���Ȼ���arraylist���Ƴ����qq��
				oout.writeObject(new testMessage(qq,1)); // ����qq��1�������ɹ����룬qq����qq
				
				PreparedStatement state_del = mainServer.con1.prepareStatement("delete from valuableqqnum where qq = ?");
				state_del.setString(1, qq);
				state_del.execute();
				// �������ʵ���������ݿ���ɾ����Ҫ�����QQ
				
				mainServer.map_array_passwordMap.put(qq, applicatePersonInfo.password); // �ӵ���������
				
				PreparedStatement state_ins_pwd = mainServer.con1.prepareStatement("insert into qqsersys(qq,password) values(?,?)");
				state_ins_pwd.setString(1,qq);
				state_ins_pwd.setString(2,applicatePersonInfo.password);
				state_ins_pwd.execute();
				// ʵ���˽�QQ�ӵ����ݿ���
				
				PreparedStatement state_ins_qqip = mainServer.con1.prepareStatement("insert into qqip(qq,ip,date) values(?,?,?)");
				state_ins_qqip.setString(1, qq);
				state_ins_qqip.setString(2, "");
				state_ins_qqip.setString(3, "");
				state_ins_qqip.executeUpdate();
				state_ins_qqip.close();
				
				// whether can add �ù���ʡ��
				
				// insert email ����ʡ��
				
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
						
		//++++++++++++++++++++++++++���¿�ʼ��con2���ӣ�Ϊʲô��		
				// createFriendsList
				String sql_create_FriendList = "create table friendsList_"+qq+" (qq varchar(7) primary key,remark varchar(20), groupName varchar(20)) charset utf8 collate utf8_general_ci;";
				PreparedStatement state_create_FriendList = mainServer.con2.prepareStatement(sql_create_FriendList);
				state_create_FriendList.executeUpdate();
				// createGroupsList
				String sql_create_GroupList = "create table  groupsList_" + qq + " (groupName varchar(20) primary key) charset utf8 collate utf8_general_ci;";
				PreparedStatement state_create_GroupList = mainServer.con2.prepareStatement(sql_create_GroupList);
				state_create_GroupList.executeUpdate();
				// insertGroupsList
				String sql_ins_group1 = "insert into groupsList_" + qq + " values ('�ҵĺ���'); ";
				String sql_ins_group2 = "insert into groupsList_" + qq + " values ('ͬѧ'); ";
				String sql_ins_group3 = "insert into groupsList_" + qq + " values ('����'); ";
				String sql_ins_group4 = "insert into groupsList_" + qq + " values ('ͬ��'); ";
				
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
				
				// ��ϲ�㣬���ڣ�д���ˡ�
				mainServer.textPane2.setText(mainServer.textPane2.getText()+(new Date())+ ": "+"qq���룺"+qq+" ������ϣ�\n");
				oin.close();
				mainServer.map_IsSysMessageExist.put(qq,0);// �Ƿ��˳�ϵͳ��Ϣ���Ǹ�qq���ڵĿͻ����Ѿ���������ϵͳ��ͨѶ�������Ѿ���0
				
				
				state_del.close();
				state_ins_pwd.close();
				
				
				
				
			}
			else  // ������������࣬�޷����뵽qq
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
			mainServer.textPane2.setText(mainServer.textPane2.getText()+"����qq�����뵼�£�ĳ��qq���ظ��������ݿ⣬���ݿ��Ѿܾ���\n");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}