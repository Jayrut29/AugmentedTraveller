package com.example.adapters;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.agt.R;
import com.example.bean.Answer;

public class AnswerAdapter extends BaseAdapter
{

	private List<Answer> list;
	private LayoutInflater inflater;

	public AnswerAdapter(Activity act, List<Answer> list)
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
			view = inflater.inflate(R.layout.answer_row, null);
		}
		TextView answerdBy = (TextView) view.findViewById(R.id.ar_textview_answer_by);
		TextView answer = (TextView) view.findViewById(R.id.ar_textview_answer);
		answerdBy.setText("Answerd By: " + list.get(position).getAnsweredBy());
		answer.setText(list.get(position).getAnswer());
		return view;
	}

}
