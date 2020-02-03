package com.example.autism;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class A4 extends A3 implements OnInitListener {
	private int MY_DATA_CHECK_CODE;

	private TextToSpeech tts;
	
	ImageView i1;
	//public static Bitmap bs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a4);
	
		i1 = (ImageView) findViewById(R.id.prss);
		i1.setVisibility(View.VISIBLE); 
		i1.setImageBitmap(b1); 
		try {
			System.out.println("log Speech is%%%%%%%%%%%%%"+Ans);
			
					if (Ans != null && Ans.length() > 0) {
						Toast.makeText(A4.this, "Saying: " + Ans, Toast.LENGTH_LONG).show();

						onInit(MY_DATA_CHECK_CODE);
						// tts.speak(text, TextToSpeech.QUEUE_ADD, null);
					}

					Intent checkIntent = new Intent();
					checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
					startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		try {
			if (requestCode == MY_DATA_CHECK_CODE) {
				if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
					// success, create the TTS instance
					tts = new TextToSpeech(this, this);
				} else {
					// missing data, install it
					Intent installIntent = new Intent();
					installIntent
							.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
					startActivity(installIntent);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	@Override
	public void onInit(int status) {

		try {
			status = TextToSpeech.SUCCESS;
			for (status = 0; status < 4; status++) {
				status--;
				tts.speak(Ans, TextToSpeech.QUEUE_ADD, null);		
				status++;
				Intent i1=new Intent(A4.this,A5.class);
				startActivity(i1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
