package com.example.autism;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

public class A3 extends Activity
{
	public  static final int RESULT_SPEECH = 1;
	TextView t1;
	
	public static String Ques="",Namej="", Ans="";
public byte[] img;
	
	 static Bitmap b1;
	 DatabaseHandler dbs=new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a3);
		
		t1=(TextView)findViewById(R.id.word);
		speaking();
	}
	public void speaking()
	{
		try {
			Intent intent = new Intent(
    				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

    		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

			startActivityForResult(intent, RESULT_SPEECH);
			/*t1.setText("");*/
			System.out.println("true here  speech.....&&&&&&&");
				
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(getApplicationContext(),
					"Ops! Your device doesn't support Speech to Text",
					Toast.LENGTH_SHORT);
			t.show(); 
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				t1.setText(text.get(0).toString());
				t1.getText();
				Namej=t1.getText().toString();
				
				t1.setText(Namej);
				System.out.println("Names AREEEEEEE"+Namej);
				SQLiteDatabase db=dbs.getReadableDatabase();
				//Cursor c1=db.rawQuery("select UQues from UserDetails", null);
				Cursor c=db.rawQuery("SELECT * from UserdetailsQA where UQues='"+Namej+"'", null);
				 
			 if(c !=null)
			 {
				
				 c.moveToFirst();
				//Ques=c.getString(c.getColumnIndex("UQues"));	
				//Ans=c.getString(c.getColumnIndex("Uans"));
				//img=c.getBlob(c.getColumnIndex("Uphoto"));
				Ques=c.getString(0);	
				Ans=c.getString(1);
				img=c.getBlob(2);
				b1=BitmapFactory.decodeByteArray(img, 0, img.length);
				System.out.println("Namej is "+Namej);
				System.out.println("Namej is "+Ques);
				System.out.println("Namej is "+Ans);
				System.out.println("Namej is "+img);
				 Intent i1=new Intent(A3.this,A4.class);
					startActivity(i1);
					Toast.makeText(getBaseContext(), "Sucess"+Ans, Toast.LENGTH_LONG).show();
				/*if(Namej.contains(Ques))
				{
					
						System.out.println("true here*********"+Ans);
						System.out.println("true here*********"+Ques);
						 
				}
				else 
				{
					Toast.makeText(getBaseContext(), "Not Success", Toast.LENGTH_LONG).show();
				}*/
			 }
			}
			break;
		}
		}
	}
}
