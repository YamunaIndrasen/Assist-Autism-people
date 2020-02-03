package com.example.autism;

import android.graphics.Bitmap;

public class A2gs 
{
	private Bitmap usrphoto,urephoto;
	private String Usrname="";
	private String UsrPhone="";
	private String Usradd="";
	private String Usrans="";
	private String UsrQues="";
	private String UsrDocno="";
	
	public A2gs()
	{
		
	}
	public A2gs(String Question,String Answer,Bitmap Urephoto)
	{
		UsrQues=Question;
		Usrans=Answer;
		urephoto=Urephoto;
	}
	
	public void setUsrphoto(Bitmap usrphoto) {
		this.usrphoto = usrphoto;
	}


	public void setUrephoto(Bitmap urephoto) {
		this.urephoto = urephoto;
	}


	public void setUsrname(String usrname) {
		Usrname = usrname;
	}


	public void setUsrPhone(String usrPhone) {
		UsrPhone = usrPhone;
	}


	public void setUsradd(String usradd) {
		Usradd = usradd;
	}


	public void setUsrans(String usrans) {
		Usrans = usrans;
	}


	public void setUsrQues(String usrQues) {
		UsrQues = usrQues;
	}


	public void setUsrDocno(String usrDocno) {
		UsrDocno = usrDocno;
	}


	public A2gs( String Usrname1, String UsrPhone1,
			String Usradd1,String UsrDocno1,Bitmap usrphoto1,Bitmap urephoto1) {
		// TODO Auto-generated constructor stub
		
		usrphoto=usrphoto1;
		urephoto=urephoto1;
		Usrname=Usrname1;
		UsrPhone=UsrPhone1;
		Usradd=Usradd1;
		
		UsrDocno=UsrDocno1;
	}


	public Bitmap getUsrphoto() {
		return usrphoto;
	}


	public Bitmap getUrephoto() {
		return urephoto;
	}


	public String getUsrname() {
		return Usrname;
	}


	public String getUsrPhone() {
		return UsrPhone;
	}


	public String getUsradd() {
		return Usradd;
	}


	public String getUsrans() {
		return Usrans;
	}


	public String getUsrQues() {
		return UsrQues;
	}


	public String getUsrDocno() {
		return UsrDocno;
	}

}
