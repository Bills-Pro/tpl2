package com.example.tpl2;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class questionActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		//Get the bundle
	    Bundle bundle = getIntent().getExtras();

	    //Extract the data…
	    String qnum = bundle.getString("qc"); 
		
		DatabaseHandler db = new DatabaseHandler(questionActivity.this);
		Question question = new Question();
		question=db.getQuestion(Integer.parseInt(qnum+1));
		TextView txtquestion = (TextView) findViewById(R.id.txtQuestion);
		txtquestion.setText(question.getQuestion());
		
		//Generation answer radio buttons from sqlite database
		/*RadioGroup rg = (RadioGroup) findViewById(R.id.rgAnswer);
		
		// Reading all answer
		Log.d("Reading: ", "Reading all answer..");
		DBHAnswers dbAns = new DBHAnswers(questionActivity.this);
		List<Answer> answers = dbAns.getAllAnswers();       

		for (Answer ans : answers) {
			String log = "Id: "+ans.getID()+" ,Answer: " + ans.getAnswer() + " ,Aid: " + ans.getaID();
			// Writing Contacts to log
			Log.d("DBRow: ", log);
			
			RadioButton rb  = new RadioButton(this);
	        rg.addView(rb); //the RadioButtons are added to the radioGroup instead of the layout
	        rb.setText(ans.getAnswer().toString());
	        Log.d("RadioButton: ", "added to radiogroup");

		}*/
		
	}
}
