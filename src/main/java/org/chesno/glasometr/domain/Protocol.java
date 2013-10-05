package org.chesno.glasometr.domain;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class Protocol
{
	private DateTime date;
	private String title;
	private Map<String, Vote> votes = new HashMap<String, Vote>();
	public DateTime getDate()
	{
		return date;
	}
	public void setDate(DateTime date)
	{
		this.date = date;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public Map<String, Vote> getVotes()
	{
		return votes;
	}
	public void setVotes(Map<String, Vote> votes)
	{
		this.votes = votes;
	}
}
