package org.chesno.glasometr.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
public class MatchResponse
{
	private List<String> headers = Lists.newArrayList();
	private List<BillVotes> billVotes = Lists.newArrayList();
	public List<String> getHeaders()
	{
		return headers;
	}
	public void setHeaders(List<String> headers)
	{
		this.headers = headers;
	}
	public List<BillVotes> getBillVotes()
	{
		return billVotes;
	}
	public void setBillVotes(List<BillVotes> billVotes)
	{
		this.billVotes = billVotes;
	}	
}
