package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import com.example.bean.Place;
import com.example.provider.Columns;
import com.example.provider.Database;
import com.example.provider.Tables;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BookmarkDao
{

	private Database database;

	public List<Place> getAllBookmarks()
	{
		List<Place> list = new ArrayList<Place>();
		SQLiteDatabase db = database.getReadableDatabase();
		String sql = "SELECT * FROM " + Tables.TABLE_BOOKMARKS;
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst())
		{
			do
			{
				Place place = new Place();
				String address = c.getString(c.getColumnIndex(Columns.COLUMN_ADDRESS));
				String email = c.getString(c.getColumnIndex(Columns.COLUMN_EMAIL));
				String id = c.getString(c.getColumnIndex(Columns.COLUMN_ID));
				String introduction = c.getString(c.getColumnIndex(Columns.COLUMN_INTRODUCTION));
				String phone = c.getString(c.getColumnIndex(Columns.COLUMN_PHONE));
				String rank = c.getString(c.getColumnIndex(Columns.COLUMN_RANK));
				String rating = c.getString(c.getColumnIndex(Columns.COLUMN_RATING));
				String subTitle = c.getString(c.getColumnIndex(Columns.COLUMN_SUBTITLE));
				String thumbUrl = c.getString(c.getColumnIndex(Columns.COLUMN_THUMB_URL));
				String title = c.getString(c.getColumnIndex(Columns.COLUMN_TITLE));
				String website = c.getString(c.getColumnIndex(Columns.COLUMN_WEBSITE));
				place.setAddress(address);
				place.setEmail(email);
				place.setId(id);
				place.setIntroduction(introduction);
				place.setPhone(phone);
				place.setRank(rank);
				place.setRating(rating);
				place.setSubTitle(subTitle);
				place.setThumbUrl(thumbUrl);
				place.setTitle(title);
				place.setWebsite(website);
				list.add(place);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();
		return list;
	}

	public void insertBookmark(Place place)
	{
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Columns.COLUMN_ADDRESS, place.getAddress());
		values.put(Columns.COLUMN_EMAIL, place.getEmail());
		values.put(Columns.COLUMN_ID, place.getId());
		values.put(Columns.COLUMN_INTRODUCTION, place.getIntroduction());
		values.put(Columns.COLUMN_PHONE, place.getPhone());
		values.put(Columns.COLUMN_RANK, place.getRank());
		values.put(Columns.COLUMN_RATING, place.getRating());
		values.put(Columns.COLUMN_SUBTITLE, place.getSubTitle());
		values.put(Columns.COLUMN_THUMB_URL, place.getThumbUrl());
		values.put(Columns.COLUMN_TITLE, place.getTitle());
		values.put(Columns.COLUMN_WEBSITE, place.getWebsite());
		db.insert(Tables.TABLE_BOOKMARKS, null, values);
		db.close();
	}

	public BookmarkDao(Context ctx)
	{
		database = new Database(ctx);
	}
}
