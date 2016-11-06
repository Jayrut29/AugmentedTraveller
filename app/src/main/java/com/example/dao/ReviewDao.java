package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.bean.Review;
import com.example.provider.Columns;
import com.example.provider.Database;
import com.example.provider.Tables;

public class ReviewDao
{

	private Database database;

	public ReviewDao(Context ctx)
	{
		database = new Database(ctx);
	}

	public void insertReview(Review review)
	{
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Columns.COLUMN_TITLE, review.getTitle());
		values.put(Columns.COLUMN_REVIEW, review.getReview());
		values.put(Columns.COLUMN_RATING, review.getRating());
		values.put(Columns.COLUMN_NAME, review.getName());
		db.insert(Tables.TABLE_REVIEWS, null, values);
		db.close();
	}

	public List<Review> getAllReviews(String title)
	{
		SQLiteDatabase db = database.getReadableDatabase();
		List<Review> list = new ArrayList<Review>();
		String sql = "SELECT * FROM " + Tables.TABLE_REVIEWS + " WHERE " + Columns.COLUMN_TITLE + " = '" + title + "'";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst())
		{
			do
			{
				Review review = new Review();
				review.setRating(c.getString(c.getColumnIndex(Columns.COLUMN_RATING)));
				review.setReview(c.getString(c.getColumnIndex(Columns.COLUMN_REVIEW)));
				review.setName(c.getString(c.getColumnIndex(Columns.COLUMN_NAME)));
				list.add(review);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();
		return list;
	}
}
