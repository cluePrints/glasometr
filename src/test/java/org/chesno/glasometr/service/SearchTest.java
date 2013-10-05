package org.chesno.glasometr.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;

public class SearchTest
{
	@Test
	public void ShouldNotCrash() throws Exception
	{
		EntityManagerFactory ef = Persistence.createEntityManagerFactory("derby");
		Search search = new Search();
		search.ef = ef;
		SearchResponse results = search.search("");
		Assert.assertTrue(results.getOptionNames().size() > 0);
		Assert.assertTrue(results.getOptionNames().size() < 50);
	}
}
