package com.example.tpl2;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 1000; // time to display the splash screen in ms

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*DatabaseHandler db = new DatabaseHandler(MainActivity.this);
		// Reading all question
        Log.d("Reading: ", "Reading all question..");
        List<Question> questions = db.getAllQuestions();       
 
        for (Question qs : questions) {
            String log = "Id: "+qs.getID()+" ,Question: " + qs.getQuestion() + " ,Qid: " + qs.getqID();
                // Writing Contacts to log
            Log.d("DBRow: ", log);
        
        }
        
        Log.i("TotalQuestion",Integer.toString(db.getQuestionsCount()));*/
		
			Thread splashTread = new Thread() {
		        @Override
		        public void run() {
		            try {
		                int waited = 0;
		                while (_active && (waited < _splashTime)) {
		                    sleep(100);
		                    if (_active) {
		                        waited += 100;
		                    }
		                }
		            } catch (Exception e) {
	
		            } finally {
	
		                startActivity(new Intent(MainActivity.this,
		                        TokenActivity.class));
		                finish();
		            }
		        };
		             };
		    splashTread.start();
		}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
