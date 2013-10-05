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
		EmbeddedObjectContainer db = Db4oEmbedded.openFile("db.db4o");
		File root = new File(".");
		File[] files = root.listFiles(new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return pathname.getName().startsWith("ns_golos_print") && pathname.length() > 10;
			}
		});

		
		ProtocolParser parser = new ProtocolParser();
		int peopleAdded = 0;
		for (File f : files)
		{
			String name = f.getName();
			
			log.debug("Starting on {}", f);
			
			Protocol protocol = parser.parse(new FileInputStream(f));
			String id = name.substring(name.indexOf(ProtocolGrabberTest.FILE_PREFFIX) + ProtocolGrabberTest.FILE_PREFFIX.length());
			protocol.setSiteId(id);
			Protocol protocolExample = new Protocol();
			protocolExample.setSiteId(id);
			if (db.queryByExample(protocolExample).isEmpty())
			{
				db.store(protocol);
			}
			
			//Map<Person, Vote> votes = protocol.getVotes();
			//peopleAdded = storePeople(db, peopleAdded, votes);
			db.commit();
		}
		
		log.info("Added {} people. Now {}.", peopleAdded, db.query(Person.class).size());
		db.commit();
		db.close();
	}
	private int storePeople(EmbeddedObjectContainer cfg, int peopleAdded,
			Map<Person, Vote> votes)
	{
		for (Map.Entry<Person, Vote> vote : votes.entrySet())
		{
			ObjectSet<Person> q = cfg.queryByExample(vote.getKey());
			if (q.isEmpty())
			{
				peopleAdded++;
				cfg.store(vote.getKey());
			}
		}
		return peopleAdded;
	}
}
