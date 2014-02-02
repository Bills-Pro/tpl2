package com.example.tpl2;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class resumeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resume);
		
		Intent myIntent= getIntent(); // gets the previously created intent
		final String QuestionCompleted = myIntent.getStringExtra("qc"); // will return "FirstKeyValue"
		
	    Toast.makeText(resumeActivity.this, QuestionCompleted,Toast.LENGTH_SHORT).show();
	    
	    ProgressBar pbQuestion = (ProgressBar) findViewById(R.id.pbcompeleteQuestion);
	    pbQuestion.setProgress(Integer.parseInt(QuestionCompleted));
	    pbQuestion.setMax(10);
	    
	    TextView txtQuestionCompeted = (TextView) findViewById(R.id.txtQuestionCompeleted);
	    txtQuestionCompeted.setText("You have compeleted "+QuestionCompleted+" question out of 10.");
	    
	    Button btnContinue = (Button) findViewById(R.id.btnContinue);
	    
	    btnContinue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startQuestionActivity(QuestionCompleted);
			}
		});
	    
	    Button btnStartAgain = (Button) findViewById(R.id.btnStartAgain);
	    
	    btnStartAgain.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startQuestionActivity("0");
			}
		});
	}
	
	public void startQuestionActivity(String complete)
	{
		Log.i("QuestionActivity", complete);
        Intent myIntent = new Intent(resumeActivity.this, questionActivity.class);
        myIntent.putExtra("qc",complete);
        startActivity(myIntent);

	}
}
