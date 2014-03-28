package common.message;

import java.io.Serializable;
/**
 * public String qq ="";
	public int sign = 0; // 妈的，不知道什么意思
	public int port ;
 * 
 * */



public class testMessage implements Serializable{

	private static final long serialVersionUID= 1L;
	public String qq ="";
	public int sign = 0; // 1 代表成功了
	public int port ;
	
	public testMessage(String qq, int sign)
	{
		super();
		this.qq = qq;
		this.sign = sign;
	}
	
	public testMessage(int sign)
	{
		super();
		this.sign = sign;
	}
	
	public testMessage()
	{
		super();
	}
	
	public testMessage(testMessage t)
	{
		this.port = t.port;
		this.qq= t.qq;
		this.sign = t.sign;
	}
	
	
}
