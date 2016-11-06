package com.example.adapters;

import java.util.List;
import com.example.agt.R;
import com.example.bean.Question;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ForumAdapter extends BaseAdapter
{

	private List<Question> list;
	private LayoutInflater inflater;

	public ForumAdapter(Activity act, List<Question> list)
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
			view = inflater.inflate(R.layout.question_row, null);
		}
		TextView askedBy = (TextView) view.findViewById(R.id.qr_textiew_asked_by);
		TextView question = (TextView) view.findViewById(R.id.qr_textiew_question);
		askedBy.setText("Asked By: " + list.get(position).getAskedBy());
		question.setText(list.get(position).getQuestion());
		return view;
	}

}
