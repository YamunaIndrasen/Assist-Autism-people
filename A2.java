package com.example.autism;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class A2 extends Activity implements OnClickListener {

	EditText uname, uphone, uadd, uques, uans, udocno;
	ImageView uphoto, urephoto;
	String Uname = "", Uphone = "", Uadd = "", Uans = "", UQues = "",
			Docno = "";

	Button qadd, aadd, Register;
	private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_LOAD_IMAGE1 = 2;
	static final String KEY = "com.example.aurtisum";
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs";
	SQLiteDatabase reg;
	DatabaseHandler db = new DatabaseHandler(this);
	private byte[] img = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a2);

		uname = (EditText) findViewById(R.id.usrn);
		uphone = (EditText) findViewById(R.id.usrph);
		uadd = (EditText) findViewById(R.id.usradd);
		uans = (EditText) findViewById(R.id.ans);
		uques = (EditText) findViewById(R.id.ques);
		udocno = (EditText) findViewById(R.id.dcno);

		Register = (Button) findViewById(R.id.Reg);

		aadd = (Button) findViewById(R.id.QAAdd);

		uphoto = (ImageView) findViewById(R.id.prs);
		urephoto = (ImageView) findViewById(R.id.i2);

		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		sharedpreferences = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		boolean previouslyStarted = sharedpreferences.getBoolean(
				getString(R.string.com_example_autisum), false);
		SharedPreferences.Editor edit = sharedpreferences.edit();
		edit.putBoolean(getString(R.string.com_example_autisum), Boolean.TRUE);
		edit.commit();

		System.out.println(previouslyStarted);
		if (previouslyStarted == true) {
			Intent i2 = new Intent(A2.this, A3.class);
			startActivity(i2);
		} else if(previouslyStarted == false)
		{	
			Toast.makeText(getBaseContext(), "Please Register First", Toast.LENGTH_LONG).show();
		}
		try {
			Toast.makeText(getBaseContext(), "Please Register First",
					Toast.LENGTH_LONG).show();
		
			reg = openOrCreateDatabase("User", MODE_PRIVATE, null);
			reg.execSQL("create table UserdetailsQA(UQues VARCHAR PRIMARY KEY,Uans VARCHAR,Uphoto BLOB)");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("xception" + e);
		}
		
		urephoto.setImageResource(0);
		uphoto.setImageResource(0);
		uphoto.setOnClickListener(this);

		urephoto.setOnClickListener(this);
		aadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				UQues = uques.getText().toString();
				Uans = uans.getText().toString();
				Bitmap bmt1 = ((BitmapDrawable) A2.this.urephoto
						.getDrawable()).getBitmap();
				
				if (TextUtils.isEmpty(UQues) || UQues.length() < 0
						&& TextUtils.isEmpty(Uans) || Uans.length() < 0) 
				{
					uques.setError("Required Fields");
					uans.setError("Required Fields");
				}
				else
				{
					ContentValues contentValues = new ContentValues();
					contentValues.put("Uans", Uans);
					contentValues.put("UQues", UQues);
					contentValues.put("Uphoto", Utility.getBytes(bmt1));
					reg.insert("UserdetailsQA", null, contentValues);
					Toast.makeText(getBaseContext(), "Your Queries Added", Toast.LENGTH_LONG).show();
					System.out.println("Question And Answer Added Successfully");
					uques.setText("");
					uans.setText("");
				}
				
			}
		});

		Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uname = uname.getText().toString();
				Uphone = uphone.getText().toString(); 
				Uadd = uadd.getText().toString(); 
				Docno = udocno.getText().toString();
				if (TextUtils.isEmpty(Uname) || Uname.length() < 0
						&& TextUtils.isEmpty(Uphone) || Uphone.length() < 0
						&& TextUtils.isEmpty(Uadd) || Uadd.length() < 0
						&& TextUtils.isEmpty(Docno) || Docno.length()<0) 
				{
					uname.setError("Required Fields");
					uphone.setError("Required Fields");
					uadd.setError("Required Fields");
					udocno.setError("Required Fields");
				}
				else
				{
					Bitmap bmt = ((BitmapDrawable) A2.this.uphoto.getDrawable())
							.getBitmap();
					Bitmap bmt1 = ((BitmapDrawable) A2.this.urephoto.getDrawable())
							.getBitmap();

					A2gs reg1 = new A2gs(Uname = uname.getText().toString(),
							Uphone = uphone.getText().toString(), Uadd = uadd
									.getText().toString(), Docno = udocno.getText()
									.toString(), bmt, bmt1);
					db.addregister(reg1);

					Toast.makeText(getBaseContext(), "Registered Successfully",
							Toast.LENGTH_LONG).show();
					Intent i1 = new Intent(A2.this, A3.class);
					startActivity(i1);
					clear();
					reg.close();
				}
				
				

			}
		});
	}
	void clear() {
		uname.setText("");
		uphone.setText("");
		uadd.setText("");
		uques.setText("");
		uans.setText("");
		udocno.setText("");
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			uphoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			try {
				Bitmap bitmap;
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(selectedImage));
				uphoto.setImageBitmap(bitmap);
				uphoto.setVisibility(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (requestCode == RESULT_LOAD_IMAGE1 && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			urephoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			try {
				Bitmap bitmap;
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(selectedImage));
				urephoto.setImageBitmap(bitmap);

				urephoto.setVisibility(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.prs) {
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_IMAGE);
		} else if (v.getId() == R.id.i2) {
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_IMAGE1);
		}

	}

}
