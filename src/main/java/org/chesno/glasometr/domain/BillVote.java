package org.chesno.glasometr.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BillVote
{
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne(optional = false)
	private Bill bill;
	
	@ManyToOne(optional = false)
	private Person person;
	
	private Vote vote;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public Vote getVote()
	{
		return vote;
	}

	public void setVote(Vote vote)
	{
		this.vote = vote;
	}
	
	public Bill getBill()
	{
		return bill;
	}
	
	public void setBill(Bill bill)
	{
		this.bill = bill;
	}
}
