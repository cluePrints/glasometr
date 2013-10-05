package org.chesno.glasometr.service;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
public class SearchResponse
{
	private List<String> optionNames = Lists.newArrayList();
	private List<Integer> optionIds = Lists.newArrayList();
	public List<String> getOptionNames()
	{
		return optionNames;
	}
	public void setOptionNames(List<String> optionNames)
	{
		this.optionNames = optionNames;
	}
	public List<Integer> getOptionIds()
	{
		return optionIds;
	}
	public void setOptionIds(List<Integer> optionIds)
	{
		this.optionIds = optionIds;
	} 
	
}
