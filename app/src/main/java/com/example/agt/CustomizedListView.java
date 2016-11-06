package com.example.agt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapters.GridViewImageAdapter;
import com.example.adapters.ViewPagerAdapter;
import com.example.utils.CommonUtils;

public class CustomizedListView extends Activity
{

	// All static variables
	// static final String URL = "http://192.168.2.3/test/json/json.xml";
	static final String URL = "http://agtandroid.tk/home.xml";
	// XML node keys
	static final String KEY_PLACE = "places"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_SUBTITLE = "subtitle";
	static final String KEY_RATING = "rating";
	static final String KEY_RANK = "rank";
	static final String KEY_IMAGE1 = "image_1";
	static final String KEY_IMAGE2 = "image_2";
	static final String KEY_IMAGE3 = "image_3";
	static final String KEY_THUMB_URL = "thumb_url";
	ArrayList<HashMap<String, String>> placeList;
	ListView list;
	LazyAdapter adapter;
	private ViewPager pager;
	private ViewPagerAdapter pageAdapter;
	private List<Integer> pagerList;
	protected int REQUEST_CODE_LOGIN = 100;
	protected int RESULT_CODE_LOGGED_IN = 200;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Intent z = new Intent(CustomizedListView.this, Myservice.class);
		// startService(z);

		placeList = new ArrayList<HashMap<String, String>>();
		initializeViewPager();
		list = (ListView) findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent i = null;
				switch (Integer.parseInt(placeList.get(position).get(KEY_ID)))
				{
				case 1:
					i = new Intent(CustomizedListView.this,maptest.class);
					startActivity(i);
					break;
				case 2:
					i = new Intent(CustomizedListView.this, detailListView.class);
					i.putExtra("URL", "http://agtandroid.tk/attractions.xml");
					startActivity(i);
					break;
				case 3:
					i = new Intent(CustomizedListView.this, detailListView.class);
					i.putExtra("URL", "http://agtandroid.tk/food.xml");
					startActivity(i);
					break;
				case 4:
					i = new Intent(CustomizedListView.this, detailListView.class);
					i.putExtra("URL", "http://agtandroid.tk/rej.xml");
					startActivity(i);
					break;
				case 5:
					i = new Intent(CustomizedListView.this, detailListView.class);
					i.putExtra("URL", "http://agtandroid.tk/eatstreet.xml");
					startActivity(i);
					break;
				case 6:
					i = new Intent(CustomizedListView.this, detailListView.class);
					i.putExtra("URL", "http://agtandroid.tk/entertainment.xml");
					startActivity(i);
					break;
				case 7:
					i = new Intent(CustomizedListView.this, detailListView.class);
					i.putExtra("URL", "http://agtandroid.tk/doorstep.xml");
					startActivity(i);
					break;
				case 8:
					i = new Intent(CustomizedListView.this, GridViewActivity.class);
					startActivity(i);
					break;
				case 9:
					i = new Intent(CustomizedListView.this, ShowAllBookmarksActivity.class);
					startActivity(i);
					break;
				case 11:
					if (CommonUtils.isUserLoggedInToApplication(CustomizedListView.this))
					{
						i = new Intent(CustomizedListView.this, ForumActivity.class);
						startActivity(i);
					}
					else
					{
						Toast.makeText(CustomizedListView.this, "please login first", Toast.LENGTH_SHORT).show();
						i = new Intent(CustomizedListView.this, LoginActivity.class);
						startActivityForResult(i, REQUEST_CODE_LOGIN);
					}

					break;
				case 12:
					if (!isLoggedIn())
					{
						i = new Intent(CustomizedListView.this, LoginActivity.class);
						startActivityForResult(i, REQUEST_CODE_LOGIN);
					}
					else
					{
						SharedPreferences loginPref = CustomizedListView.this.getSharedPreferences(getString(R.string.login_prefs), MODE_PRIVATE);
						Editor editor = loginPref.edit();
						editor.putString(getString(R.string.key_is_signed_in), "false");
						editor.clear();
						editor.commit();
						Toast.makeText(CustomizedListView.this, "Logged out", Toast.LENGTH_SHORT).show();
						finish();
					}
					break;
				}
			}
		});

		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(80);
		Executor poolExecutor = new ThreadPoolExecutor(60, 80, 10, TimeUnit.SECONDS, workQueue);
		if (Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD)
		{
			new BackgroundTask().executeOnExecutor(poolExecutor);
		}
		else
		{
			new BackgroundTask().execute(null, null);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_CODE_LOGGED_IN)
		{
			placeList.get(11).put(KEY_TITLE, "Logout");
			adapter = new LazyAdapter(CustomizedListView.this, placeList);
			list.setAdapter(adapter);
		}
	}

	protected boolean isLoggedIn()
	{
		SharedPreferences loginPref = getSharedPreferences(getString(R.string.login_prefs), MODE_PRIVATE);
		if (loginPref.getString(getString(R.string.key_is_signed_in), "").equals("true"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void initializeViewPager()
	{
		pagerList = new ArrayList<Integer>();
		pagerList.add(R.drawable.home);
		pagerList.add(R.drawable.go);
		pagerList.add(R.drawable.sm);
		pagerList.add(R.drawable.ush);
		pagerList.add(R.drawable.hollywood);
		pagerList.add(R.drawable.sm);
		pager = (ViewPager) findViewById(R.id.pager_1);
		pageAdapter = new ViewPagerAdapter(this, pagerList);
		pager.setAdapter(pageAdapter);

	}

	private class BackgroundTask extends AsyncTask<Void, Void, String>
	{

		private XMLParser parser;

		@Override
		protected String doInBackground(Void... params)
		{
			parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL);
			return xml;
		}

		@Override
		protected void onPostExecute(String xml)
		{
			//String tempXml = "<home><places><id>1</id><title>Near Me</title><thumb_url>http:agtandroid.tk/imgs/nearmenow.png</thumb_url></places><places><id>2</id><title>Attractions</title><thumb_url>http:agtandroid.tk/imgs/attraction.png</thumb_url></places><places><id>3</id><title>Foods Drinks</title><thumb_url>http:agtandroid.tk/imgs/icon_restaurant.png</thumb_url></places><places><id>4</id><title>Rejuvenate</title><thumb_url>http:agtandroid.tk/imgs/icon_home_screen_shopping.png</thumb_url></places><places><id>5</id><title>Eat Street</title><thumb_url>http:agtandroid.tk/imgs/eatstreet.png</thumb_url></places><places><id>6</id><title>Entertainment</title><thumb_url>http:agtandroid.tk/imgs/entertainment.png</thumb_url></places><places><id>7</id><title>Doorstep Delivery</title><thumb_url>http:agtandroid.tk/imgs/doorstep.png</thumb_url></places><places><id>8</id><title>Photos</title><thumb_url>http:agtandroid.tk/imgs/photos.png</thumb_url></places><places><id>9</id><title>Bookmarks</title><thumb_url>http:agtandroid.tk/imgs/saves.png</thumb_url></places><places><id>10</id><title>Write a Review</title><thumb_url>http:agtandroid.tk/imgs/icon_home_write_reviews.png</thumb_url></places><places><id>11</id><title>Forum</title><thumb_url>http:agtandroid.tk/imgs/forum.png</thumb_url></places><places><id>12</id><title>Log In</title><thumb_url>http:agtandroid.tk/imgs/login.png</thumb_url></places></home>";
			Document doc = null;
			try
			{
				doc = parser.getDomElement(xml);
				NodeList nl = doc.getElementsByTagName(KEY_PLACE);
				// looping through all place nodes <place>
				for (int i = 0; i < nl.getLength(); i++)
				{
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_SUBTITLE, parser.getValue(e, KEY_SUBTITLE));
					map.put(KEY_RATING, parser.getValue(e, KEY_RATING));
					map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
					map.put(KEY_RANK, parser.getValue(e, KEY_RANK));

					// adding HashList to ArrayList
					placeList.add(map);

				}
				if (isLoggedIn())
				{
					placeList.get(11).put(KEY_TITLE, "Logout");
				}
				adapter = new LazyAdapter(CustomizedListView.this, placeList);
				list.setAdapter(adapter);
			}
			catch (ParserConfigurationException e1)
			{
				e1.printStackTrace();
			}
			catch (SAXException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			} // getting DOM element

		}
	}
}