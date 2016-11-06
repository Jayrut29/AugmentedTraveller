package com.example.bean;

public class Review
{

	private String title;
	private String review;
	private String rating;
	private String name;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the review
	 */
	public String getReview()
	{
		return review;
	}

	/**
	 * @param review
	 *            the review to set
	 */
	public void setReview(String review)
	{
		this.review = review;
	}

	/**
	 * @return the rating
	 */
	public String getRating()
	{
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating)
	{
		this.rating = rating;
	}

}
