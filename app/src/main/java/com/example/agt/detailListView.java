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
import com.example.adapters.ViewPagerAdapter;
import com.example.utils.CommonUtils;

public class detailListView extends Activity
{

	// All static variables
	// static final String URL = "http://192.168.2.3/test/json/json2.xml";
	static final String URL = "http://agtandroid.tk/home1.xml";
	// XML node keys
	static final String KEY_PLACE = "place"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_SUBTITLE = "subtitle";
	public static final String KEY_INTRODUCTION = "introduction";
	static final String KEY_PHONE = "phone";
	static final String KEY_EMAIL = "email";
	static final String KEY_ADDRESS = "address";
	static final String KEY_WEB_SITE = "website";
	static final String KEY_RATING = "rating";
	static final String KEY_RANK = "rank";
	static final String KEY_THUMB_URL = "thumb_url";
	static final String KEY_IMAGE1 = "image_1";
	static final String KEY_IMAGE2 = "image_2";
	static final String KEY_IMAGE3 = "image_3";

	String url = null;
	ListView list;
	LazyAdapter adapter;
	private ArrayList<HashMap<String, String>> placeList;
	private List<Integer> pagerList;
	private ViewPager pager;
	private ViewPagerAdapter pageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			url = extras.getString("URL");
		}
		initializeViewPager();
		placeList = new ArrayList<HashMap<String, String>>();

		list = (ListView) findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent i = new Intent(detailListView.this, PlaceActivity.class);
				i.putExtra(KEY_ID, placeList.get(position).get(KEY_ID));
				i.putExtra(KEY_TITLE, placeList.get(position).get(KEY_TITLE));
				i.putExtra(KEY_SUBTITLE, placeList.get(position).get(KEY_SUBTITLE));
				i.putExtra(KEY_INTRODUCTION, placeList.get(position).get(KEY_INTRODUCTION));
				i.putExtra(KEY_PHONE, placeList.get(position).get(KEY_PHONE));
				i.putExtra(KEY_EMAIL, placeList.get(position).get(KEY_EMAIL));
				i.putExtra(KEY_ADDRESS, placeList.get(position).get(KEY_ADDRESS));
				i.putExtra(KEY_WEB_SITE, placeList.get(position).get(KEY_WEB_SITE));
				i.putExtra(KEY_RATING, placeList.get(position).get(KEY_RATING));
				i.putExtra(KEY_RANK, placeList.get(position).get(KEY_RANK));
				i.putExtra(KEY_THUMB_URL, placeList.get(position).get(KEY_THUMB_URL));
				i.putExtra(KEY_IMAGE1, placeList.get(position).get(KEY_IMAGE1));
				i.putExtra(KEY_IMAGE2, placeList.get(position).get(KEY_IMAGE2));
				i.putExtra(KEY_IMAGE3, placeList.get(position).get(KEY_IMAGE3));
				startActivity(i);
			}
		});

		if (CommonUtils.isNetworkAvailable(detailListView.this))
		{
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
		else
		{
			Toast.makeText(detailListView.this, "please connect to internet", Toast.LENGTH_SHORT).show();
		}

	}

	private void initializeViewPager()
	{

		pagerList = new ArrayList<Integer>();
		pagerList.add(R.drawable.champ);
		pagerList.add(R.drawable.hazira);
		pagerList.add(R.drawable.home);
		pagerList.add(R.drawable.enter);
		pagerList.add(R.drawable.food);
		pagerList.add(R.drawable.rej);
		pagerList.add(R.drawable.sur);
		pagerList.add(R.drawable.champa);
		pagerList.add(R.drawable.bus);	
		pager = (ViewPager) findViewById(R.id.pager_2);
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
			String xml = parser.getXmlFromUrl(url); // getting XML from URL
			return xml;
		}

		@Override
		protected void onPostExecute(String xml)
		{

			if (xml != null)
			{
				Document doc = null;
				try
				{
					doc = parser.getDomElement(xml);
					NodeList nl = doc.getElementsByTagName(KEY_PLACE);
					// looping through all song nodes <song>
					for (int i = 0; i < nl.getLength(); i++)
					{
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						Element e = (Element) nl.item(i);
						// adding each child node to HashMap key => value
						map.put(KEY_ID, parser.getValue(e, KEY_ID));
						map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
						map.put(KEY_SUBTITLE, parser.getValue(e, KEY_SUBTITLE));
						map.put(KEY_INTRODUCTION, parser.getValue(e, KEY_INTRODUCTION));
						map.put(KEY_PHONE, parser.getValue(e, KEY_PHONE));
						map.put(KEY_EMAIL, parser.getValue(e, KEY_EMAIL));
						map.put(KEY_ADDRESS, parser.getValue(e, KEY_ADDRESS));
						map.put(KEY_WEB_SITE, parser.getValue(e, KEY_WEB_SITE));
						map.put(KEY_RATING, parser.getValue(e, KEY_RATING));
						map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
						map.put(KEY_RANK, parser.getValue(e, KEY_RANK));
						map.put(KEY_IMAGE1, parser.getValue(e, KEY_IMAGE1));
						map.put(KEY_IMAGE2, parser.getValue(e, KEY_IMAGE2));
						map.put(KEY_IMAGE3, parser.getValue(e, KEY_IMAGE3));
						// adding HashList to ArrayList
						placeList.add(map);
					}
					adapter = new LazyAdapter(detailListView.this, placeList);
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
			else
			{
				showToast("No response from server. please check your internet connection");
			}

		}
	}

	public void showToast(String string)
	{
		Toast.makeText(detailListView.this, "" + string, Toast.LENGTH_SHORT).show();
	}
}