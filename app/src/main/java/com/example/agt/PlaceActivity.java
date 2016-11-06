/**
 * 
 */
package com.example.agt;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bean.Place;
import com.example.dao.BookmarkDao;


public class PlaceActivity extends Activity
{

	static final String URL = "http://agtandroid.tk/home.xml";
	// XML node keys
	static final String KEY_PLACE = "place"; // parent node
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
	static final String KEY_IMAGE1 = "image_1";
	static final String KEY_IMAGE2 = "image_2";
	static final String KEY_IMAGE3 = "image_3";

	private ImageView mImageView3;
	private ImageView mImageView1;
	private ImageView mImageView2;
	private TextView mTextView_name;
	private ImageView mImageView_bookmark;
	private LinearLayout mLayout_save;
	private LinearLayout mLayout_map;
	private LinearLayout mLayout_Address;
	private LinearLayout mLayout_Call;
	private LinearLayout mLayout_Email;
	private LinearLayout mLayout_WebSite;
	private LinearLayout mLayout_Reviews;
	// private String title;
	private TextView mRatingBar;
	// private String telephone, address, email, website;
	protected Context context;
	protected boolean isBookmarked = false;
	private Place place;

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
		setContentView(R.layout.activity_place);
		instantiateWidgets();
		setListeners();

	}

	private void setListeners()
	{
		mLayout_save.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (isBookmarked)
				{
					/*
					 * mImageView_bookmark.setImageResource(R.drawable.unbookmark
					 * ); isBookmarked = false;
					 */
				}
				else
				{
					isBookmarked = true;
					mImageView_bookmark.setImageResource(R.drawable.bookmark);
					BookmarkDao dao = new BookmarkDao(context);
					dao.insertBookmark(place);
					Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mLayout_map.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showTheMap(place.getTitle());

			}
		});

		mLayout_Address.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (place.getAddress() != null && !place.getAddress().equals(""))
				{
					showTheMap(place.getAddress());
				}
				else
				{
					Toast.makeText(PlaceActivity.this, "adddress not available", Toast.LENGTH_SHORT).show();
				}

			}
		});

		mLayout_Call.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(Intent.ACTION_DIAL);
				i.setData(Uri.parse("tel:" + place.getPhone()));
				Log.v("TAG", "telephone: " + place.getPhone());
				startActivity(i);
			}
		});

		mLayout_Email.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				String email = place.getEmail();
				Intent intent = new Intent(Intent.ACTION_SEND);
				Uri uri = Uri.parse("mailto:");
				String[] to = new String[] { email };
				intent.setData(uri);
				intent.setType("message/rfc822");
				intent.putExtra(Intent.EXTRA_EMAIL, to);
				try
				{
					startActivity(Intent.createChooser(intent, "Send mail"));
				}
				catch (ActivityNotFoundException ex)
				{
					Toast.makeText(PlaceActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mLayout_WebSite.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (place.getWebsite() != null && !place.getWebsite().equals(""))
				{
					if (!place.getWebsite().startsWith("http://") && !place.getWebsite().startsWith("https://"))
					{
						String website = "http://" + place.getWebsite();
						Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
						startActivity(i);
					}
					else
					{
						Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(place.getWebsite()));
						startActivity(i);
					}
				}
				else
				{
					Toast.makeText(context, "Website doesn't exist", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mLayout_Reviews.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(context, ReviewsActivity.class);
				i.putExtra("title", place.getTitle());
				startActivity(i);
			}
		});
	}

	protected void showTheMap(String address)
	{
		String address1 = address.replace(" ", "+");
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address1));
		startActivity(i);
	}

	private void instantiateWidgets()
	{
		context = this;
		mImageView3 = (ImageView) findViewById(R.id.ap_imageview_main);
		mImageView1 = (ImageView) findViewById(R.id.ap_imageview_image_1);
		mImageView2 = (ImageView) findViewById(R.id.ap_imageview_image_2);
		mImageView_bookmark = (ImageView) findViewById(R.id.ap_imageview_bookmark);
		mLayout_save = (LinearLayout) findViewById(R.id.ap_button_call);
		mLayout_map = (LinearLayout) findViewById(R.id.ap_button_map);
		mLayout_Address = (LinearLayout) findViewById(R.id.ap_layout_address);
		mLayout_Call = (LinearLayout) findViewById(R.id.ap_layout_call);
		mLayout_Email = (LinearLayout) findViewById(R.id.ap_layout_email);
		mLayout_WebSite = (LinearLayout) findViewById(R.id.ap_layout_visit_web_site);
		mLayout_Reviews = (LinearLayout) findViewById(R.id.ap_layout_reviews);
		mTextView_name = (TextView) findViewById(R.id.ap_textview_name);
		mRatingBar = (TextView) findViewById(R.id.sub_title);
		ImageLoader loader = new ImageLoader(PlaceActivity.this);
		place = new Place();
		place.setAddress(getIntent().getStringExtra(KEY_ADDRESS));
		place.setEmail(getIntent().getStringExtra(KEY_EMAIL));
		place.setId(getIntent().getStringExtra(KEY_ID));
		place.setIntroduction(getIntent().getStringExtra(KEY_INTRODUCTION));
		place.setPhone(getIntent().getStringExtra(KEY_PHONE));
		place.setRank(getIntent().getStringExtra(KEY_RANK));
		place.setRating(getIntent().getStringExtra(KEY_RATING));
		place.setSubTitle(getIntent().getStringExtra(KEY_SUBTITLE));
		place.setThumbUrl(getIntent().getStringExtra(KEY_THUMB_URL));
		place.setTitle(getIntent().getStringExtra(KEY_TITLE));
		place.setWebsite(getIntent().getStringExtra(KEY_WEB_SITE));
		place.setImage1(getIntent().getStringExtra(KEY_IMAGE1));
		place.setImage2(getIntent().getStringExtra(KEY_IMAGE2));
		place.setImage3(getIntent().getStringExtra(KEY_IMAGE3));
		loader.DisplayImage(place.getThumbUrl(), mImageView3);
		loader.DisplayImage(place.getImage1(), mImageView1);
		loader.DisplayImage(place.getImage2(), mImageView2);
		mTextView_name.setText("" + place.getTitle());
		mRatingBar.setText(place.getSubTitle());
		TextView txtAddress = (TextView) findViewById(R.id.ap_textview_address);
		TextView txtCall = (TextView) findViewById(R.id.ap_textview_call);
		TextView txtIntroduction = (TextView) findViewById(R.id.ap_textview_introduction);
		txtAddress.setText(place.getAddress());
		if (place.getPhone() != null && !place.getPhone().equals(""))
		{
			txtCall.setText("" + place.getPhone());
		}
		else
		{
			txtCall.setText("NOT AVAILABLE");
		}

		txtIntroduction.setText("" + getIntent().getStringExtra(KEY_INTRODUCTION));
	}

}
