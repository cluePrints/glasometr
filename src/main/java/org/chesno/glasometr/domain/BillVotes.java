package org.chesno.glasometr.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BillVotes
{
	private String id;
	private String title;
	private Vote myVote;	
	private Vote p1;
	private Vote p2;
	private Vote p3;
	private Vote p4;
	private Vote p5;
	
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
	public Vote getP2()
	{
		return p2;
	}
	public void setP2(Vote p2)
	{
		this.p2 = p2;
	}
	public Vote getP3()
	{
		return p3;
	}
	public void setP3(Vote p3)
	{
		this.p3 = p3;
	}
	public Vote getP4()
	{
		return p4;
	}
	public void setP4(Vote p4)
	{
		this.p4 = p4;
	}
	public Vote getP5()
	{
		return p5;
	}
	public void setP5(Vote p5)
	{
		this.p5 = p5;
	}	
}
