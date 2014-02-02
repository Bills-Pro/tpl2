package com.example.tpl2;

public class Answer {
	
	//private variables
		int _id;
		String _aid;
		String _answer;
		
		
		// Empty constructor
		public Answer(){
			
		}
		// constructor
		public Answer(int id, String _aid, String _answer){
			this._id = id;
			this._aid = _aid;
			this._answer = _answer;
		} 
		
		public int getID(){
			return this._id;
		}
		
		// setting id
		public void setID(int id){
			this._id = id;
		}
		
		public String getaID(){
			return this._aid;
		}
		
		// setting id
		public void setaID(String aid){
			this._aid = aid;
		}
		
		public String getAnswer(){
			return this._answer;
		}
		
		// setting id
		public void setAnswer(String answwer){
			this._answer = answwer;
		}
		
}
