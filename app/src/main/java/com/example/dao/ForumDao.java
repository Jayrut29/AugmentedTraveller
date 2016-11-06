package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.bean.Answer;
import com.example.bean.Question;
import com.example.provider.Columns;
import com.example.provider.Database;
import com.example.provider.Tables;

public class ForumDao
{

	private Database database;

	public ForumDao(Context ctx)
	{
		database = new Database(ctx);
	}

	public void askQuestion(String question, String name)
	{
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Columns.COLUMN_QUESTION, question);
		values.put(Columns.COLUMN_NAME, name);
		db.insert(Tables.TABLE_QUESTIONS, null, values);
		db.close();
	}

	public List<Question> getAllQuestions()
	{
		List<Question> list = new ArrayList<Question>();
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "SELECT * FROM " + Tables.TABLE_QUESTIONS;
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst())
		{
			do
			{
				Question q = new Question();
				q.setQuestion(c.getString(c.getColumnIndex(Columns.COLUMN_QUESTION)));
				q.setAskedBy(c.getString(c.getColumnIndex(Columns.COLUMN_NAME)));
				list.add(q);
			}
			while (c.moveToNext());
		}
		return list;
	}

	public List<Answer> getAnswersOfThisQuestion(String question)
	{
		List<Answer> list = new ArrayList<Answer>();
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "SELECT * FROM " + Tables.TABLE_ANSWERS + " WHERE " + Columns.COLUMN_QUESTION + " = '" + question + "'";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst())
		{
			do
			{
				Answer a = new Answer();
				a.setAnswer(c.getString(c.getColumnIndex(Columns.COLUMN_ANSWER)));
				a.setAnsweredBy(c.getString(c.getColumnIndex(Columns.COLUMN_NAME)));
				list.add(a);
			}
			while (c.moveToNext());
		}
		return list;
	}

	public void answerToThisQuestion(Answer a)
	{
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Columns.COLUMN_QUESTION, a.getQuestion());
		values.put(Columns.COLUMN_ANSWER, a.getAnswer());
		values.put(Columns.COLUMN_NAME, a.getAnsweredBy());
		db.insert(Tables.TABLE_ANSWERS, null, values);
		db.close();
	}
}
