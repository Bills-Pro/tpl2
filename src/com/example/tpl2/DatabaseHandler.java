package com.example.tpl2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "QuestionsManager";

	// Questions table name
	private static final String TABLE_Questions = "Questions";

	// Questions Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_QID = "qid";
	private static final String KEY_Question = "question";
	private static final String KEY_LANGUAGE = "language";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_QuestionS_TABLE = "CREATE TABLE " + TABLE_Questions + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_QID + " TEXT,"
				+ KEY_LANGUAGE + " TEXT,"
				+ KEY_Question + " TEXT" + ")";
		db.execSQL(CREATE_QuestionS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Questions);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Question
	void addQuestion(Question question) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_QID, question.getqID()); // Question QID
		values.put(KEY_Question, question.getQuestion()); // Question
		values.put(KEY_LANGUAGE, question.getLanguage()); // Question Language

		// Inserting Row
		db.insert(TABLE_Questions, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Question
	Question getQuestion(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_Questions, new String[] { KEY_ID,
				KEY_QID, KEY_Question,KEY_LANGUAGE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Question Question = new Question(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2),cursor.getString(3));
		// return Question
		return Question;
	}
	
	// Getting All Questions
	public List<Question> getAllQuestions() {
		List<Question> QuestionList = new ArrayList<Question>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_Questions;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Question Question = new Question();
				Question.setID(Integer.parseInt(cursor.getString(0)));
				Question.setqID(cursor.getString(1));
				Question.setLanguage(cursor.getString(2));
				Question.setQuestion(cursor.getString(3));
				// Adding Question to list
				QuestionList.add(Question);
			} while (cursor.moveToNext());
		}
		db.close();
		// return Question list
		return QuestionList;
	}

	// Updating single Question
	public int updateQuestion(Question Question) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_QID, Question.getqID());
		values.put(KEY_Question, Question.getQuestion());
		values.put(KEY_LANGUAGE, Question.getLanguage());

		// updating row
		return db.update(TABLE_Questions, values, KEY_ID + " = ?",
				new String[] { String.valueOf(Question.getID()) });
	}

	// Deleting single Question
	public void deleteQuestion(Question Question) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_Questions, KEY_ID + " = ?",
				new String[] { String.valueOf(Question.getID()) });
		db.close();
	}
	
	public void deleteAllQuestion() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_Questions,"",new String[] {  } );
		db.close();
	}


	// Getting Questions Count
	public int getQuestionsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_Questions;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count= cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

}
