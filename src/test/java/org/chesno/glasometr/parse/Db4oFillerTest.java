package org.chesno.glasometr.parse;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.Map;

import org.chesno.glasometr.domain.Person;
import org.chesno.glasometr.domain.Protocol;
import org.chesno.glasometr.domain.Vote;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;

public class Db4oFillerTest
{
	private static final Logger log = LoggerFactory.getLogger(Db4oFillerTest.class);
	@Test
	public void Fill() throws Exception
	{
		EmbeddedObjectContainer cfg = Db4oEmbedded.openFile("db.db4o");
		File root = new File(".");
		File[] files = root.listFiles(new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return pathname.getName().startsWith("ns_golos_print") && pathname.length() > 10;
			}
		});

		Person examplePerson = new Person();
		ProtocolParser parser = new ProtocolParser();
		for (File f : files)
		{
			log.debug("Starting on {}", f);
			Protocol protocol = parser.parse(new FileInputStream(f));
			Map<String, Vote> votes = protocol.getVotes();
			for (Map.Entry<String, Vote> vote : votes.entrySet())
			{
				examplePerson.setName(vote.getKey());
				ObjectSet<Person> q = cfg.queryByExample(examplePerson);
				if (q.isEmpty())
				{
					cfg.store(examplePerson);
				}
			}						
		}
		
		cfg.close();
	}
}
