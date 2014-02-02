package com.example.tpl2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicDomainHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TokenActivity extends Activity {

	Button btnContinue;
	EditText txtToken;
	String stext; String valid_token="";
	Spinner spinner ;
	String dbavalid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_token);
		btnContinue = (Button) findViewById(R.id.token_continue);
		txtToken = (EditText) findViewById(R.id.txtToken);

		// Spinner element
		spinner = (Spinner) findViewById(R.id.spinner_token);

		// Spinner click listener
		//spinner.setOnItemSelectedListener(this);

		// Spinner Drop down elements
		List<String> categories = new ArrayList<String>();
		categories.add("Leader");
		categories.add("Raters");

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);

		btnContinue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// do stuff for signInButtonClick


				if (txtToken.getText().toString().length() <= 0) {
					txtToken.setError("Accept Alphabets Only.");
					valid_token = null;
				} else if (!txtToken.getText().toString().matches("[a-zA-Z0-9 ]+")) {
					txtToken.setError("Accept Alphabets Only.");
					valid_token = null;
				} else {
					valid_token = txtToken.getText().toString();
					// WebServer Request URL
					String serverURL = "http://tlp360.com/tlpfeedback/services/tokenauth.php";
					//String serverURL = "http://192.168.1.3/tpl/tokenauth.php";

					// Use AsyncTask execute Method To Prevent ANR Problem
					new AutanticateToken().execute(serverURL);
				}

				//stext = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();

				// Toast.makeText(TokenActivity.this, stext,Toast.LENGTH_SHORT).show();

			}
		});
	}

	// Class with extends AsyncTask class
	private class AutanticateToken  extends AsyncTask<String, Void, Void> {


		// Required initialization

		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(TokenActivity.this);
		String data =""; 
		//TextView uiUpdate = (TextView) findViewById(R.id.output);
		//TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;  
		//EditText serverText = (EditText) findViewById(R.id.serverText);

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			//Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

			try{

				Log.i("JSON parse", valid_token);
				Log.i("JSON parse", Integer.toString(spinner.getSelectedItemPosition()));

				// Set Request parameter
				data +="&" + URLEncoder.encode("token", "UTF-8") + "="+valid_token;
				data +="&" + URLEncoder.encode("type", "UTF-8") + "="+spinner.getSelectedItemPosition();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/
			BufferedReader reader=null;

			// Send data 
			try
			{

				// Defined URL  where to send data
				URL url = new URL(urls[0]);

				// Send POST data request

				URLConnection conn = url.openConnection(); 
				conn.setDoOutput(true); 
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
				wr.write( data ); 
				wr.flush(); 

				// Get the server response 

				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while((line = reader.readLine()) != null)
				{
					// Append server response in string
					sb.append(line + "\n");
				}

				// Append Server Response To Content String 
				Content = sb.toString();
			}
			catch(Exception ex)
			{
				Error = ex.getMessage();
				Log.e("JSON parse Exception", Error);

			}
			finally
			{
				try
				{

					reader.close();
				}

				catch(Exception ex) {}
			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.
			Log.v("Authenticate",Content);
			// Close progress dialog
			Dialog.dismiss();

			if (Error != null) {

				//uiUpdate.setText("Output : "+Error);
				Log.e("JSON parse", "Error null");

			} else {

				// Show Response Json On Screen (activity)
				//uiUpdate.setText( Content );

				/****************** Start Parse Response JSON Data *************/

				String OutputData = "";
				JSONObject jsonResponse;

				try {

					/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
					jsonResponse = new JSONObject(Content);

					/***** Returns the value mapped by name if it exists and is a JSONArray. ***/
					/*******  Returns null otherwise.  *******/
					JSONArray jsonMainNode = jsonResponse.optJSONArray("Authanticate");

					/*********** Process each JSON Node ************/

					int lengthJsonArr = jsonMainNode.length();  
					String valid = "";
					String complete="" ;
					String dbvalid="";
					
					for(int i=0; i < lengthJsonArr; i++) 
					{
						/****** Get Object for each JSON node.***********/
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

						/******* Fetch node values **********/
						valid       = jsonChildNode.optString("valid").toString();
						complete     = jsonChildNode.optString("complete").toString();                          
						dbvalid = jsonChildNode.optString("dbv").toString(); 
						dbavalid = jsonChildNode.optString("dbav").toString(); 
						Log.i("AnsValid",jsonChildNode.optString("dbav").toString());
						OutputData += " valid 		    : "+ valid +" \n "
								+ "complete 		: "+ complete +" \n "
								+ "dbv 		: "+ dbvalid +" \n "
								+ "dbav 		: "+ dbavalid +" \n "
								+"--------------------------------------------------\n";

					}
					Log.i("JSON parse", OutputData);
					/****************** End Parse Response JSON Data *************/     

					//Show Parsed Output on screen (activity)
					//jsonParsed.setText( OutputData );

					Toast.makeText(TokenActivity.this, OutputData,Toast.LENGTH_SHORT).show();

					if(!(valid.equals("-1")))
					{
						Log.i("Token", "valid: "+valid);
						if((dbvalid.equals("-1")))
						{

							Log.i("UpdateSqlite", "No need to update");

							// Reading all contacts
							DatabaseHandler db = new DatabaseHandler(TokenActivity.this);
							Log.d("Reading: ", "Reading all questions..");
							List<Question> questions = db.getAllQuestions();       
							Log.i("TotalQuestion",Integer.toString(db.getQuestionsCount()));
							for (Question qs : questions) {
								String log = "Id: "+qs.getID()+" ,Question: " + qs.getQuestion() + " ,Qid: " + qs.getqID();
								// Writing Contacts to log
								Log.d("Name: ", log);

							}
							NextActivity(complete);
						}
						else if ((dbvalid.equals("0")))
						{
							Log.v("UpdateQuestions", "Need to update");

							// WebServer Request URL
							String serverURL = "http://tlp360.com/tlpfeedback/services/questionUpdate.php";
							// Use AsyncTask execute Method To Prevent ANR Problem
							new updateQuestion().execute(serverURL);
						}
						
						if ((dbavalid.equals("0")))
						{
							Log.v("UpdateAnswer","Need to update");
							new updateAnswer().execute("http://10.0.2.2/tlp/answerUpdate.php");
						}
					}
					else
					{
						//                   	 Log.i("questions", valid+"||"+complete);
						//                   	 Intent myIntent = new Intent(TokenActivity.this, questionActivity.class);
						//                        startActivity(myIntent);
						Log.i("Token", "Not valid: "+valid);
						Toast.makeText(TokenActivity.this, "Invalid Token",Toast.LENGTH_SHORT).show();
					}
					//                    
				} catch (JSONException e) {

					Log.e("JSON parse", "Error execp");
					Toast.makeText(TokenActivity.this, "Exception",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		}
	}

	void NextActivity(String complete)
	{
		if(!complete.equals("0"))
		{
			Log.i("resumeActivity", complete);
			Intent myIntent = new Intent(TokenActivity.this, resumeActivity.class);
			myIntent.putExtra("qc",complete);
			startActivity(myIntent);
		}else
		{
			Log.i("QuestionActivity", complete);
			Intent myIntent = new Intent(TokenActivity.this, questionActivity.class);
			myIntent.putExtra("qc",complete);
			startActivity(myIntent);
		}
	}

	private class updateQuestion  extends AsyncTask<String, Void, Void> {

		// Required initialization

		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(TokenActivity.this);
		String data =""; 
		//TextView uiUpdate = (TextView) findViewById(R.id.output);
		//TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;  
		//EditText serverText = (EditText) findViewById(R.id.serverText);


		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			//Start Progress Dialog (Message)

			Dialog.setMessage("Checking Question Version");
			Dialog.show();

			//Passing parameter
			/*try{

            	Log.i("JSON parse", valid_token);
            	Log.i("JSON parse", Integer.toString(spinner.getSelectedItemPosition()));

            	// Set Request parameter
                //data +="&" + URLEncoder.encode("token", "UTF-8") + "="+valid_token;
                //data +="&" + URLEncoder.encode("type", "UTF-8") + "="+spinner.getSelectedItemPosition();

            } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/
			BufferedReader reader=null;

			// Send data 
			try
			{

				// Defined URL  where to send data
				URL url = new URL(urls[0]);

				// Send POST data request

				URLConnection conn = url.openConnection(); 
				conn.setDoOutput(true); 
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
				wr.write( data ); 
				wr.flush(); 

				// Get the server response 

				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while((line = reader.readLine()) != null)
				{
					// Append server response in string
					sb.append(line + "\n");
				}

				// Append Server Response To Content String 
				Content = sb.toString();
			}
			catch(Exception ex)
			{
				Error = ex.getMessage();
				Log.e("JSON parse Exception", Error);

			}
			finally
			{
				try
				{
					reader.close();
				}

				catch(Exception ex) {}
			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			Dialog.setMessage("Updating Question...");
			Dialog.show();

			if (Error != null) {

				//uiUpdate.setText("Output : "+Error);
				Log.e("JSON parse", "Error null");

			} else {

				// Show Response Json On Screen (activity)
				//uiUpdate.setText( Content );

				/****************** Start Parse Response JSON Data *************/

				String OutputData = "";
				JSONObject jsonResponse;

				try {

					/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
					jsonResponse = new JSONObject(Content);

					/***** Returns the value mapped by name if it exists and is a JSONArray. ***/
					/*******  Returns null otherwise.  *******/
					JSONArray jsonMainNode = jsonResponse.optJSONArray("questiosupdate");

					/*********** Process each JSON Node ************/

					int lengthJsonArr = jsonMainNode.length();  
					String qid = "";
					String question = "";
					String language = "";
					DatabaseHandler db = new DatabaseHandler(TokenActivity.this);
					db.deleteAllQuestion();
					Log.d("Delete: ", "QuestionTableAllRow");
					for(int i=0; i < lengthJsonArr; i++) 
					{
						/****** Get Object for each JSON node.***********/
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

						/******* Fetch node values **********/
						qid       = jsonChildNode.optString("qid").toString();
						question       = jsonChildNode.optString("question").toString();
						language       = jsonChildNode.optString("language").toString();

						OutputData += " qid 		    : "+ qid +" \n "
								+" question 		    : "+ question +" \n "
								+" language 		    : "+ language +" \n "
								+"--------------------------------------------------\n";

						/**
						 * CRUD Operations
						 * */
						// Inserting Contacts
						Log.d("Insert: ", "Inserting question..");
						db.addQuestion(new Question( 0,qid,question,language));

					}

					// Reading all question
					Log.d("Reading: ", "Reading all question..");
					List<Question> questions = db.getAllQuestions();       

					for (Question qs : questions) {
						String log = "Id: "+qs.getID()+" ,Question: " + qs.getQuestion() + " ,Qid: " + qs.getqID();
						// Writing Contacts to log
						Log.d("DBRow: ", log);

					}

					Log.i("TotalQuestion",Integer.toString(db.getQuestionsCount()));
					Log.i("JSON parse", OutputData);

					/****************** End Parse Response JSON Data *************/     

					//Show Parsed Output on screen (activity)
					//jsonParsed.setText( OutputData );

					//Toast.makeText(TokenActivity.this, OutputData,Toast.LENGTH_SHORT).show();
					Log.v("dbavalidinQuestion",dbavalid);
					/*if ((dbavalid.equals("0")))
					{
						Log.v("UpdateAnswer","Need to update");
						new updateAnswer().execute("http://10.0.2.2/tlp/answerUpdate.php");
					}*/
				} catch (JSONException e) {

					Log.e("JSON parse", "Error execp");
					Toast.makeText(TokenActivity.this, "Exception",Toast.LENGTH_SHORT).show();

					e.printStackTrace();
				}
				// Close progress dialog
				Dialog.dismiss();
			}
		}
	}

	private class updateAnswer extends AsyncTask<String, Void, Void> {

		// Required initialization

		private final HttpClient Client = new DefaultHttpClient();
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(TokenActivity.this);
		String data =""; 
		//TextView uiUpdate = (TextView) findViewById(R.id.output);
		//TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
		int sizeData = 0;  
		//EditText serverText = (EditText) findViewById(R.id.serverText);


		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			//Start Progress Dialog (Message)

			Dialog.setMessage("Loading answers");
			Dialog.show();

			//Passing parameter
			/*try{

            	Log.i("JSON parse", valid_token);
            	Log.i("JSON parse", Integer.toString(spinner.getSelectedItemPosition()));

            	// Set Request parameter
                //data +="&" + URLEncoder.encode("token", "UTF-8") + "="+valid_token;
                //data +="&" + URLEncoder.encode("type", "UTF-8") + "="+spinner.getSelectedItemPosition();

            } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... urls) {

			/************ Make Post Call To Web Server ***********/
			BufferedReader reader=null;

			// Send data 
			try
			{

				// Defined URL  where to send data
				URL url = new URL(urls[0]);

				// Send POST data request

				URLConnection conn = url.openConnection(); 
				conn.setDoOutput(true); 
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
				wr.write( data ); 
				wr.flush(); 

				// Get the server response 

				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while((line = reader.readLine()) != null)
				{
					// Append server response in string
					sb.append(line + "\n");
				}

				// Append Server Response To Content String 
				Content = sb.toString();
			}
			catch(Exception ex)
			{
				Error = ex.getMessage();
				Log.e("JSON parse Exception", Error);

			}
			finally
			{
				try
				{
					reader.close();
				}

				catch(Exception ex) {}
			}

			/*****************************************************/
			return null;
		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			Dialog.setMessage("Updating database...");
			Dialog.show();

			if (Error != null) {

				//uiUpdate.setText("Output : "+Error);
				Log.e("JSON parse", "Error null");

			} else {

				// Show Response Json On Screen (activity)
				//uiUpdate.setText( Content );

				/****************** Start Parse Response JSON Data *************/

				String OutputData = "";
				JSONObject jsonResponse;

				try {

					/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
					jsonResponse = new JSONObject(Content);

					/***** Returns the value mapped by name if it exists and is a JSONArray. ***/
					/*******  Returns null otherwise.  *******/
					JSONArray jsonMainNode = jsonResponse.optJSONArray("answersupdate");

					/*********** Process each JSON Node ************/

					int lengthJsonArr = jsonMainNode.length();  
					String aid = "";
					String answer = "";
					DBHAnswers db = new DBHAnswers(TokenActivity.this);
					db.deleteAllAnswer();
					Log.d("Delete: ", "AnswersTableAllRow");
					for(int i=0; i < lengthJsonArr; i++) 
					{
						/****** Get Object for each JSON node.***********/
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

						/******* Fetch node values **********/
						aid       = jsonChildNode.optString("aid").toString();
						answer       = jsonChildNode.optString("answer").toString();

						OutputData += " aid 		    : "+ aid +" \n "
								+" answer 		    : "+ answer +" \n "
								+"--------------------------------------------------\n";

						/**
						 * CRUD Operations
						 * */
						// Inserting Contacts
						Log.d("Insert: ", "Inserting answer..");
						db.addAnswer(new Answer( 0,aid,answer));

					}

					// Reading all answer
					Log.d("Reading: ", "Reading all answer..");
					List<Answer> answers = db.getAllAnswers();       

					for (Answer ans : answers) {
						String log = "Id: "+ans.getID()+" ,Answer: " + ans.getAnswer() + " ,Aid: " + ans.getaID();
						// Writing Contacts to log
						Log.d("DBRow: ", log);

					}

					Log.i("TotalAnswer",Integer.toString(db.getAnswersCount()));
					Log.i("JSON parse", OutputData);

					/****************** End Parse Response JSON Data *************/     

					//Show Parsed Output on screen (activity)
					//jsonParsed.setText( OutputData );

					//Toast.makeText(TokenActivity.this, OutputData,Toast.LENGTH_SHORT).show();

					//                    
				} catch (JSONException e) {

					Log.e("JSON parse", "Error execp");
					Toast.makeText(TokenActivity.this, "Exception",Toast.LENGTH_SHORT).show();

					e.printStackTrace();
				}
				// Close progress dialog
				Dialog.dismiss();
			}
		}
	}
}
