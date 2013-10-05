package org.chesno.glasometr.parse;

import java.io.InputStream;
import java.util.Map;

import org.chesno.glasometr.domain.Person;
import org.chesno.glasometr.domain.Protocol;
import org.chesno.glasometr.domain.Vote;
import org.junit.Assert;
import org.junit.Test;

public class ProtocolParserTest
{
	@Test
	public void Should() throws Exception
	{
		String fileName = "ns_golos_print_g_id_=2633";
		InputStream str = getClass().getClassLoader().getResourceAsStream(fileName);
		
		ProtocolParser unit = new ProtocolParser();
		Protocol protocol = unit.parse(str);
		
		
		int yesCount = 0;
		int noCount = 0;
		int notVotedCount = 0;
		int refrainedCount = 0;
		int notPresentCount = 0;
		for (Map.Entry<String, Vote> e : protocol.getVotes().entrySet())
		{
			if (e.getValue() == Vote.Yes)
			{
				yesCount++;
			}else
			if (e.getValue() == Vote.No)
			{
				noCount++;
			}else
			if (e.getValue() == Vote.NotVoted)
			{
				notVotedCount++;
			}else
			if (e.getValue() == Vote.Refrained)
			{
				refrainedCount++;
			} else if (e.getValue() == Vote.WasNotPresent) {
				notPresentCount++;
			} else
			{
				System.out.println("Weird: "+e.getKey()+" "+e.getValue());
			}
		}
		
		Assert.assertEquals(186, yesCount);
		Assert.assertEquals(0, noCount);
		Assert.assertEquals(152, notVotedCount);
		Assert.assertEquals(16, refrainedCount);
		Assert.assertEquals(52+9+12+16, notPresentCount);
		Assert.assertEquals(notPresentCount + refrainedCount + notVotedCount + noCount + yesCount, protocol.getVotes().size());
		Assert.assertEquals(Vote.Yes, protocol.getVotes().get(new Person().setName("Бенюк Б.М.")));
		Assert.assertEquals(Vote.NotVoted, protocol.getVotes().get(new Person().setName("Сольвар Р.М.")));
		String expectedTitle = "Поіменне голосування  про проект Постанови про впровадження мораторію на закриття навчальних закладів системи загальної середньої освіти, розташованих у сільській місцевості (№2367) - за основу та в цілому";
		Assert.assertEquals(expectedTitle, protocol.getTitle());
	}
}
