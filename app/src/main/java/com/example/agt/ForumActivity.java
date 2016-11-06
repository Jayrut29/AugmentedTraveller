
package com.example.agt;

import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.adapters.ForumAdapter;
import com.example.bean.Question;
import com.example.dao.ForumDao;


public class ForumActivity extends Activity
{

	private Button mButton_adTopic;
	private ListView listview;
	private TextView mTextView_NoOfTopics;
	private SharedPreferences loginPrefs;
	private Context mContext;
	List<Question> list;
	ForumDao dao;
	private ForumAdapter adapter;

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
		setContentView(R.layout.activity_forum);
		instantiateWidgets();
		setListeners();
	}

	private void instantiateWidgets()
	{
		mContext = this;
		dao = new ForumDao(mContext);
		loginPrefs = getSharedPreferences(getString(R.string.login_prefs), MODE_PRIVATE);
		mButton_adTopic = (Button) findViewById(R.id.af_button_add_topic);
		listview = (ListView) findViewById(R.id.af_listview);
		mTextView_NoOfTopics = (TextView) findViewById(R.id.af_textview_no_of_topics);
		list = dao.getAllQuestions();
		adapter = new ForumAdapter(ForumActivity.this, list);
		listview.setAdapter(adapter);
		mTextView_NoOfTopics.setText(list.size() + " Topics");
	}

	private void setListeners()
	{
		mButton_adTopic.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				addTopic();
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
			{
				Question q = list.get(position);
				Intent i = new Intent(ForumActivity.this, AnswersActivity.class);
				i.putExtra("question", q.getQuestion());
				i.putExtra("asked_by", q.getAskedBy());
				startActivity(i);
			}
		});
	}

	protected void addTopic()
	{
		final Dialog dialog = new Dialog(mContext);
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.layout_dialog, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(v);
		Button add = (Button) v.findViewById(R.id.ld_button_add);
		final EditText et = (EditText) v.findViewById(R.id.ld_edittext);
		add.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (et.getText().toString().trim().equals(""))
				{
					et.setError("Please enter the topic");
					et.requestFocus();
				}
				else
				{
					String name = getMyNameFromDatabase();
					String question = et.getText().toString().trim();
					Question q = new Question();
					q.setAskedBy(name);
					q.setQuestion(question);
					dao.askQuestion(question, name);
					dialog.cancel();
					list.add(q);
					mTextView_NoOfTopics.setText(list.size() + " Topics");
					adapter = new ForumAdapter(ForumActivity.this, list);
					listview.setAdapter(adapter);
				}
			}
		});
		dialog.show();
	}

	protected String getMyNameFromDatabase()
	{
		SharedPreferences loginPref = getSharedPreferences(getString(R.string.login_prefs), MODE_PRIVATE);
		return loginPref.getString(getString(R.string.key_my_name), "");
	}

	@Override
	protected void onResume()
	{
		super.onResume();

	}
}
