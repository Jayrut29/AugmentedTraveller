package com.example.bean;

import java.io.Serializable;

public class Question implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4412797011877279041L;
	private String question;
	private String askedBy;

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
	 * @return the askedBy
	 */
	public String getAskedBy()
	{
		return askedBy;
	}

	/**
	 * @param askedBy
	 *            the askedBy to set
	 */
	public void setAskedBy(String askedBy)
	{
		this.askedBy = askedBy;
	}

}
