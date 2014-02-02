package com.example.tpl2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHAnswers extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "AnswersManager";

	// Answers table name
	private static final String TABLE_Answers = "Answer";

	// Answers Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_AID = "aid";
	private static final String KEY_Answer = "answer";

	public DBHAnswers(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_AnswerS_TABLE = "CREATE TABLE " + TABLE_Answers + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_AID + " TEXT,"
				+ KEY_Answer + " TEXT" + ")";
		db.execSQL(CREATE_AnswerS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Answer
	void addAnswer(Answer Answer) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_AID, Answer.getaID()); // Answer QID
		values.put(KEY_Answer, Answer.getAnswer()); // Answer
		

		// Inserting Row
		db.insert(TABLE_Answers, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Answer
	Answer getAnswer(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_Answers, new String[] { KEY_ID,
				KEY_AID, KEY_Answer }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Answer Answer = new Answer(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return Answer
		return Answer;
	}
	
	// Getting All Answers
	public List<Answer> getAllAnswers() {
		List<Answer> AnswerList = new ArrayList<Answer>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_Answers;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Answer answer = new Answer();
				answer.setID(Integer.parseInt(cursor.getString(0)));
				answer.setaID(cursor.getString(1));
				answer.setAnswer(cursor.getString(2));
				// Adding Answer to list
				AnswerList.add(answer);
			} while (cursor.moveToNext());
		}
		db.close();
		// return Answer list
		return AnswerList;
	}

	// Updating single Answer
	public int updateAnswer(Answer Answer) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_AID, Answer.getaID());
		values.put(KEY_Answer, Answer.getAnswer());
		

		// updating row
		return db.update(TABLE_Answers, values, KEY_ID + " = ?",
				new String[] { String.valueOf(Answer.getID()) });
	}

	// Deleting single Answer
	public void deleteAnswer(Answer Answer) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_Answers, KEY_ID + " = ?",
				new String[] { String.valueOf(Answer.getID()) });
		db.close();
	}
	
	public void deleteAllAnswer() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_Answers,"",new String[] {  } );
		db.close();
	}


	// Getting Answers Count
	public int getAnswersCount() {
		String countQuery = "SELECT  * FROM " + TABLE_Answers;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count= cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

}
