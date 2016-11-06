package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.bean.User;
import com.example.provider.Columns;
import com.example.provider.Database;
import com.example.provider.Tables;

public class UserDao
{

	private Database database;

	public UserDao(Context ctx)
	{
		database = new Database(ctx);
	}

	public void insertUser(User user)
	{
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Columns.COLUMN_NAME, user.getName());
		values.put(Columns.COLUMN_EMAIL, user.getEmail());
		values.put(Columns.COLUMN_PASSWORD, user.getPassword());
		values.put(Columns.COLUMN_CITY, user.getCity());
		db.insert(Tables.TABLE_USER, null, values);
		db.close();
	}

	public Boolean isValidUser(User user)
	{
		Boolean value = false;
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "SELECT * FROM " + Tables.TABLE_USER;
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst())
		{
			do
			{
				String email = c.getString(c.getColumnIndex(Columns.COLUMN_EMAIL));
				String password = c.getString(c.getColumnIndex(Columns.COLUMN_PASSWORD));
				if (email.equals(user.getEmail()) && password.equals(user.getPassword()))
				{
					value = true;
					break;
				}
			}
			while (c.moveToNext());
		}
		return value;
	}

	public String getNameFromEmail(String email)
	{
		String name = "";
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "SELECT * FROM " + Tables.TABLE_USER;
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst())
		{
			do
			{
				String emailInDb = c.getString(c.getColumnIndex(Columns.COLUMN_EMAIL));
				if (emailInDb.equals(email))
				{
					name = c.getString(c.getColumnIndex(Columns.COLUMN_NAME));
					break;
				}
			}
			while (c.moveToNext());
		}
		return name;
	}
}
