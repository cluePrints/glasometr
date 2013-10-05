package org.chesno.glasometr.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientState
{
	private int[] selectedBillIds;

	public int[] getSelectedBillIds()
	{
		return selectedBillIds;
	}

	public void setSelectedBillIds(int[] selectedBillIds)
	{
		this.selectedBillIds = selectedBillIds;
	}	
}
