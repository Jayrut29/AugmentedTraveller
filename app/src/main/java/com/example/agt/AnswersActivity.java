
package com.example.agt;

import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.adapters.AnswerAdapter;
import com.example.adapters.ForumAdapter;
import com.example.bean.Answer;
import com.example.bean.Question;
import com.example.dao.ForumDao;


public class AnswersActivity extends Activity
{

	private TextView mTextView_AskedBy;
	private TextView mTextView_NoOfReplies;
	private TextView mTextView_Question;
	private ListView mListView_Replies;
	private Button mButton_Reply;
	private List<Answer> list;
	private Context mContext;
	private ForumDao dao;
	private AnswerAdapter adapter;
	private String question;
	private String askedBy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answers);
		instantiateWidgets();
		setListeners();
	}

	private void setListeners()
	{
		mButton_Reply.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				replyToThisQuestion();
			}
		});
	}

	protected void replyToThisQuestion()
	{
		final Dialog dialog = new Dialog(mContext);
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.layout_dialog, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(v);
		Button add = (Button) v.findViewById(R.id.ld_button_add);
		final EditText et = (EditText) v.findViewById(R.id.ld_edittext);
		et.setHint("Enter your answer");
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
					String answer = et.getText().toString().trim();
					Answer a = new Answer();
					a.setAnswer(answer);
					a.setAnsweredBy(name);
					a.setQuestion(question);
					dao.answerToThisQuestion(a);
					dialog.cancel();
					list.add(a);
					mTextView_NoOfReplies.setText(list.size() + " Replies");
					adapter = new AnswerAdapter(AnswersActivity.this, list);
					mListView_Replies.setAdapter(adapter);
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

	private void instantiateWidgets()
	{
		mContext = this;
		mTextView_AskedBy = (TextView) findViewById(R.id.aa_textview_asked_by);
		mTextView_NoOfReplies = (TextView) findViewById(R.id.aa_textview_no_of_replies);
		mTextView_Question = (TextView) findViewById(R.id.aa_textview_question);
		mListView_Replies = (ListView) findViewById(R.id.aa_listview_answers);
		mButton_Reply = (Button) findViewById(R.id.aa_button_reply);
		dao = new ForumDao(mContext);
		question = getIntent().getStringExtra("question");
		askedBy = getIntent().getStringExtra("asked_by");
		mTextView_AskedBy.setText("" + askedBy);
		mTextView_Question.setText(question);
		list = dao.getAnswersOfThisQuestion(question);
		mTextView_NoOfReplies.setText(list.size() + " replies");
		adapter = new AnswerAdapter(AnswersActivity.this, list);
		mListView_Replies.setAdapter(adapter);
	}

}
