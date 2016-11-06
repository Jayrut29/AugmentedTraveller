/**
 * 
 */
package com.example.agt;

import java.util.List;
import com.example.adapters.ReviewsAdapter;
import com.example.bean.Review;
import com.example.dao.ReviewDao;
import com.example.provider.Database;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class ReviewsActivity extends Activity
{

	private ListView listview;
	private ReviewsAdapter adapter;
	private List<Review> list;
	private Context context;
	private Button mButton_addReview;
	// private EditText mEditText_addReview;
	private ReviewDao database;
	private String title;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reviews);
		context = this;
		listview = (ListView) findViewById(R.id.ar_listview_reviews);
		mButton_addReview = (Button) findViewById(R.id.ar_button_add_review);
		// mEditText_addReview = (EditText)
		// findViewById(R.id.ar_edittext_review);
		database = new ReviewDao(context);
		title = getIntent().getStringExtra("title");
		mButton_addReview.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(context, AddReviewActivity.class);
				i.putExtra("title", title);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		list = database.getAllReviews(title);
		adapter = new ReviewsAdapter(this, list);
		if (list != null && list.size() > 0)
		{
			listview.setAdapter(adapter);
		}
		else
		{
			Toast.makeText(context, "no reviews", Toast.LENGTH_SHORT).show();
		}
	}
}
