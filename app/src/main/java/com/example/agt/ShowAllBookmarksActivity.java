/**
 * 
 */
package com.example.agt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.example.bean.Place;
import com.example.dao.BookmarkDao;


public class ShowAllBookmarksActivity extends Activity
{

	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_SUBTITLE = "subtitle";
	static final String KEY_INTRODUCTION = "introduction";
	static final String KEY_PHONE = "phone";
	static final String KEY_EMAIL = "email";
	static final String KEY_ADDRESS = "address";
	static final String KEY_WEB_SITE = "website";
	static final String KEY_RATING = "rating";
	static final String KEY_RANK = "rank";
	static final String KEY_THUMB_URL = "thumb_url";

	private ListView listview;
	private List<Place> list;
	private Context mContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_all_bookmarks);
		instantiateWidgets();
		setListeners();
	}

	private void setListeners()
	{
		listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
			{

				Intent i = new Intent(ShowAllBookmarksActivity.this, PlaceActivity.class);
				i.putExtra(KEY_ID, list.get(position).getId());
				i.putExtra(KEY_TITLE, list.get(position).getTitle());
				i.putExtra(KEY_SUBTITLE, list.get(position).getSubTitle());
				i.putExtra(KEY_INTRODUCTION, list.get(position).getIntroduction());
				i.putExtra(KEY_PHONE, list.get(position).getPhone());
				i.putExtra(KEY_EMAIL, list.get(position).getEmail());
				i.putExtra(KEY_ADDRESS, list.get(position).getAddress());
				i.putExtra(KEY_WEB_SITE, list.get(position).getWebsite());
				i.putExtra(KEY_RATING, list.get(position).getRating());
				i.putExtra(KEY_RANK, list.get(position).getRank());
				i.putExtra(KEY_THUMB_URL, list.get(position).getThumbUrl());
				startActivity(i);

			}

		});
	}

	private void instantiateWidgets()
	{
		mContext = this;
		listview = (ListView) findViewById(R.id.asab_listview);
		BookmarkDao dao = new BookmarkDao(mContext);
		list = dao.getAllBookmarks();
		if (list != null && list.size() > 0)
		{
			ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < list.size(); i++)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_ADDRESS, list.get(i).getAddress());
				map.put(KEY_EMAIL, list.get(i).getEmail());
				map.put(KEY_ID, list.get(i).getId());
				map.put(KEY_INTRODUCTION, list.get(i).getIntroduction());
				map.put(KEY_PHONE, list.get(i).getPhone());
				map.put(KEY_RANK, list.get(i).getRank());
				map.put(KEY_RATING, list.get(i).getRating());
				map.put(KEY_SUBTITLE, list.get(i).getSubTitle());
				map.put(KEY_THUMB_URL, list.get(i).getThumbUrl());
				map.put(KEY_TITLE, list.get(i).getTitle());
				map.put(KEY_WEB_SITE, list.get(i).getWebsite());
				newList.add(map);
			}
			LazyAdapter adapter = new LazyAdapter(ShowAllBookmarksActivity.this, newList);
			listview.setAdapter(adapter);
		}
		else
		{
			Toast.makeText(mContext, "No Bookmarks yet", Toast.LENGTH_SHORT).show();
		}
	}
}
