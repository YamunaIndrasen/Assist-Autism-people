package com.example.autism;

import android.graphics.Bitmap;

public class A4gs 
{
	private Bitmap usrphoto,urephoto;

	public A4gs(Bitmap usrph,Bitmap ureph)
	{
		usrphoto=usrph;
		urephoto=ureph;
	}

	public void setUsrphoto(Bitmap usrphoto) {
		this.usrphoto = usrphoto;
	}

	public Bitmap getUsrphoto() {
		return usrphoto;
	}

	public Bitmap getUrephoto() {
		return urephoto;
	}

	public void setUrephoto(Bitmap urephoto) {
		this.urephoto = urephoto;
	}
	
}
