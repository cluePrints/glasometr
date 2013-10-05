package org.chesno.glasometr.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.apache.commons.io.IOUtils;
import org.chesno.glasometr.domain.Bill;
import org.chesno.glasometr.domain.BillVote;
import org.chesno.glasometr.domain.Person;
import org.chesno.glasometr.domain.Protocol;
import org.chesno.glasometr.domain.Vote;
import org.chesno.glasometr.service.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaLoader
{
	private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

	EntityManagerFactory ef;

	public JpaLoader(EntityManagerFactory ef)
	{
		super();
		this.ef = ef;
	}

	public void load(File... files) throws Exception
	{
		EntityManager em = ef.createEntityManager();
		em.setFlushMode(FlushModeType.AUTO);
		ProtocolParser parser = new ProtocolParser();
		for (File f : files)
		{
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			try
			{
				Protocol protocol = parseFile(parser, f);
				String name = f.getName();
				String id = name.substring(name
						.indexOf(ProtocolGrabberTest.FILE_PREFFIX)
						+ ProtocolGrabberTest.FILE_PREFFIX.length());

				Bill bill = findOrNull(em, "from Bill where siteId = :param",
						id);
				if (bill == null)
				{
					bill = new Bill();
					bill.setSiteId(id);
					bill.setTitle(protocol.getTitle());
					em.persist(bill);
				} else
				{
					continue;
				}

				for (Map.Entry<String, Vote> entry : protocol.getVotes()
						.entrySet())
				{
					Person person = findOrNull(em,
							"from Person where Name = :param", entry.getKey());
					if (person == null)
					{
						person = new Person().setName(entry.getKey());
						em.persist(person);
					}

					BillVote vote = new BillVote();
					vote.setPerson(person);
					vote.setVote(entry.getValue());
					vote.setBill(bill);
					em.persist(vote);
				}
			}
			finally
			{
				if (txn.getRollbackOnly())
				{
					txn.rollback();
				}
				else
				{
					txn.commit();					
				}
			}
		}

		em.close();
	}

	@SuppressWarnings("unchecked")
	private <T> T findOrNull(EntityManager em, String query, String param)
	{
		try
		{
			return (T) em.createQuery(query).setParameter("param", param)
					.getSingleResult();
		} catch (NoResultException ex)
		{
			return null;
		}
	}

	private Protocol parseFile(ProtocolParser parser, File f)
			throws FileNotFoundException, Exception
	{
		log.debug("Starting on {}({} bytes)", f, f.length());

		FileInputStream stream = new FileInputStream(f);
		Protocol protocol;
		try
		{
			protocol = parser.parse(stream);
		} finally
		{
			IOUtils.closeQuietly(stream);
		}
		log.debug("There were {} votes.", protocol.getVotes().size());
		return protocol;
	}
}
