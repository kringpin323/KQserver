package common.message;

import java.io.Serializable;

// 申请模块的personalInfo编写完毕，27-mar-14
/**
 * public String qq ="";
	public String nickname ="";
	public String sex="";
	public int age=0;
	public String bri_year="";
	public String bri_month="";
	public String bri_day="";
	public String zodiacsign="";
	public String constellation ="";
	public String bloodtype="";
	public String country="";
	public String province="";
	public String city="";
	public int headimage=0;
	public int status=0;
	public String phoneNumber="";
	public String occupation="";
	public String mailaddress="";
	
	public String password=""; //password系数据库里面不存在一起就不代表无
 * 
 * */
public class personalInfo implements Serializable // 个人资料
{
	/*
	 * 不知道多少个变量
	 * */
	private static final long serialVersionUID = 1L; //与老版本兼容
	public String qq ="";
	public String nickname ="";
	public String sex="";
	public int age=0;
	public String bri_year="";
	public String bri_month="";
	public String bri_day="";
	public String zodiacsign="";
	public String constellation ="";
	public String bloodtype="";
	public String country="";
	public String province="";
	public String city="";
	public int headimage=0;
	public int status=0;
	public String phoneNumber="";
	public String occupation="";
	public String mailaddress="";
	
	public String password=""; //password系数据库里面不存在一起就不代表无
	
	//还有三个发送主框架，聊天时的变量未设置，到时在设置
	//password, nickname, sex, year, month, day, country, province, city
	public personalInfo(String password,String nickname,String sex,
			String bri_year,String bri_month,String bri_day,
			String country,
			String province,String city)
	{
		// 申请时用
		super();
		this.password = password;
		this.nickname=nickname;
		this.sex=sex; 
		this.bri_year=bri_year;
		this.bri_month = bri_month;
		this.bri_day = bri_day;
		this.country = country;
		this.province = province;
		this.city= city;
		
	}
	
	public personalInfo(personalInfo personalInformation)
	{
		this.password = personalInformation.password;
		this.nickname=personalInformation.nickname;
		this.sex=personalInformation.sex;
		this.age=personalInformation.age;
		this.bri_year=personalInformation.bri_year;
		this.bri_month = personalInformation.bri_month;
		this.bri_day = personalInformation.bri_day;
		this.zodiacsign = personalInformation.zodiacsign;
		this.constellation = personalInformation.constellation;
		this.bloodtype = personalInformation.bloodtype;
		this.province = personalInformation.province;
		this.city= personalInformation.city;
		this.headimage=personalInformation.headimage;
		this.status=personalInformation.status;
		this.phoneNumber=personalInformation.phoneNumber;
		this.occupation=personalInformation.occupation;
		this.mailaddress=personalInformation.mailaddress;
	}
	
	public personalInfo()
	{
		super();
	}
	
	
	
	
	
}