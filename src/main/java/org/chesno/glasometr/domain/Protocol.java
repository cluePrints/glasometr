package org.chesno.glasometr.domain;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class Protocol
{
	private String siteId;	
	private DateTime date;
	private String title;
	private Map<Person, Vote> votes = new HashMap<Person, Vote>();
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
	public Map<Person, Vote> getVotes()
	{
		return votes;
	}
	public void setVotes(Map<Person, Vote> votes)
	{
		this.votes = votes;
	}
	public String getSiteId()
	{
		return siteId;
	}
	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}
}
