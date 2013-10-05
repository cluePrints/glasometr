package org.chesno.glasometr.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BillVotes
{
	private String id;
	private String title;
	private Vote myVote;	
	private Vote p1;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public Vote getMyVote()
	{
		return myVote;
	}
	public void setMyVote(Vote myVote)
	{
		this.myVote = myVote;
	}
	public Vote getP1()
	{
		return p1;
	}
	public void setP1(Vote p1)
	{
		this.p1 = p1;
	}
}
