package org.chesno.glasometr.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.common.collect.Lists;

@Entity
public class Bill
{
	@Id
	@GeneratedValue
	private int id;
	private String siteId;
	
	@Column(length=500)
	private String title;
	
	@OneToMany
	private List<BillVote> votes = Lists.newArrayList();
	
	public List<BillVote> getVotes()
	{
		return votes;
	}
	public void setVotes(List<BillVote> votes)
	{
		this.votes = votes;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getSiteId()
	{
		return siteId;
	}
	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
}
