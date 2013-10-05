package org.chesno.glasometr.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person
{
	private String name;

	public String getName()
	{
		return name;
	}

	public Person setName(String name)
	{
		this.name = name;
		return this;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
	
}
