
package com.example.agt;

import com.example.bean.Review;
import com.example.dao.ReviewDao;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class AddReviewActivity extends Activity
{

	private RatingBar mRatingBar;
	private EditText mEditText;
	private Button mButton;
	protected String title;
	private EditText etName;

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
		setContentView(R.layout.activity_add_review);
		mRatingBar = (RatingBar) findViewById(R.id.aar_rating_bar);
		mEditText = (EditText) findViewById(R.id.aar_edittext_additional_comments);
		etName = (EditText) findViewById(R.id.aar_edittext_name);
		mButton = (Button) findViewById(R.id.aar_button_add);
		title = getIntent().getStringExtra("title");
		mButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (checkAllFields())
				{
					Review r = new Review();
					r.setRating("" + mRatingBar.getRating());
					r.setReview(mEditText.getText().toString().trim());
					r.setTitle(title);
					r.setName(etName.getText().toString().trim());
					ReviewDao dao = new ReviewDao(AddReviewActivity.this);
					dao.insertReview(r);
					finish();
				}
			}
		});
	}

	protected boolean checkAllFields()
	{
		if (etName.getText().toString().trim().equals(""))
		{
			etName.setError("enter your name");
			return false;
		}
		else if (mRatingBar.getRating() == 0)
		{
			Toast.makeText(AddReviewActivity.this, "please rate", Toast.LENGTH_SHORT).show();
			return false;
		}
		else if (mEditText.getText().toString().trim().equals(""))
		{
			mEditText.setError("Please write a review");
			mEditText.requestFocus();
			return false;
		}
		else
			return true;
	}

}
