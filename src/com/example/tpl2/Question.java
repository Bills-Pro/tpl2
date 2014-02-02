package com.example.tpl2;

public class Question {
	
	//private variables
		int _id;
		String _qid;
		String _question;
		String _language;
		
		
		// Empty constructor
		public Question(){
			
		}
		// constructor
		public Question(int id, String _qid, String _question, String _language){
			this._id = id;
			this._qid = _qid;
			this._question = _question;
			this._language= _language;
		} 
		
		public int getID(){
			return this._id;
		}
		
		// setting id
		public void setID(int id){
			this._id = id;
		}
		
		public String getqID(){
			return this._qid;
		}
		
		// setting id
		public void setqID(String qid){
			this._qid = qid;
		}
		
		public String getQuestion(){
			return this._question;
		}
		
		// setting id
		public void setQuestion(String question){
			this._question = question;
		}
		
		public String getLanguage(){
			return this._language;
		}
		
		// setting id
		public void setLanguage(String language){
			this._language = language;
		}

}
