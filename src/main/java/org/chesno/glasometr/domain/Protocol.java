package org.chesno.glasometr.domain;

import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.Maps;

public class Protocol
{	
	private String siteId;	
	private DateTime date;
	private String title;
	
	private Map<String, Vote> votes = Maps.newHashMap();

	public String getSiteId()
	{
		return siteId;
	}

	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}

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
