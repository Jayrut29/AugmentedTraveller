package com.example.bean;

import java.io.Serializable;

public class Answer implements Serializable
{


	private static final long serialVersionUID = 7931347276503775669L;
	private String answeredBy;
	private String answer;
	private String question;

	/**
	 * @return the question
	 */
	public String getQuestion()
	{
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question)
	{
		this.question = question;
	}

	/**
	 * @return the answeredBy
	 */
	public String getAnsweredBy()
	{
		return answeredBy;
	}

	/**
	 * @param answeredBy
	 *            the answeredBy to set
	 */
	public void setAnsweredBy(String answeredBy)
	{
		this.answeredBy = answeredBy;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}
