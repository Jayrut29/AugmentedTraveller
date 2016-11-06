package com.example.adapters;

import java.util.List;
import com.example.agt.R;
import com.example.bean.Review;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReviewsAdapter extends BaseAdapter
{

	private List<Review> list;
	private LayoutInflater inflater;

	public ReviewsAdapter(Activity act, List<Review> list)
	{

		this.list = list;
		inflater = act.getLayoutInflater();
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.review_row, null);
		}
		TextView rating = (TextView) view.findViewById(R.id.rr_textiew_rating);
		TextView review = (TextView) view.findViewById(R.id.rr_textview_review);
		TextView name = (TextView) view.findViewById(R.id.rr_textview_name);
		rating.setText(list.get(position).getRating() + " Stars");
		review.setText(list.get(position).getReview());
		name.setText(list.get(position).getName());
		return view;
	}

}
