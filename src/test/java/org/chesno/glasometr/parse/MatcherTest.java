package org.chesno.glasometr.parse;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.chesno.glasometr.domain.BillVotes;
import org.junit.Test;

public class MatcherTest
{
	@Test
	public void test()
	{
		EntityManagerFactory ef = Persistence.createEntityManagerFactory("derby");
		List<BillVotes> res = new Matcher(ef).lookFor(Arrays.asList(true, true, true, true, true, false, false, true, false, false, true), Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16));
	}
}
