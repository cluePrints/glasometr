package org.chesno.glasometr.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientState
{
	private List<Integer> selectedBillIds;
	private List<Boolean> selectedValues;
	public List<Integer> getSelectedBillIds()
	{
		return selectedBillIds;
	}
	public void setSelectedBillIds(List<Integer> selectedBillIds)
	{
		this.selectedBillIds = selectedBillIds;
	}
	public List<Boolean> getSelectedValues()
	{
		return selectedValues;
	}
	public void setSelectedValues(List<Boolean> selectedValues)
	{
		this.selectedValues = selectedValues;
	}

}
