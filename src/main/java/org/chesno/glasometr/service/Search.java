package org.chesno.glasometr.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class Search
{
	private static final Logger log = LoggerFactory.getLogger(Search.class);
	
	@PersistenceUnit(unitName = "derby")
	EntityManagerFactory ef = Persistence.createEntityManagerFactory("derby");

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public SearchResponse search(@QueryParam("q") String fragment) throws Exception
	{
		log.debug("Seraching for {}", fragment);
		SearchResponse r = new SearchResponse();
		EntityManager em = ef.createEntityManager();
		try
		{
			Query query = em.createQuery("select b.id,b.title from Bill b where b.title like :fragment");
			query.setParameter("fragment", "%"+fragment+"%");
			query.setMaxResults(10);
			
			List<Object[]> results = query.getResultList();
			for (Object[] item : results)
			{
				r.getOptionIds().add((Integer) item[0]);
				r.getOptionNames().add((String) item[1]);
			}
			return r;
		}
		finally
		{
			em.close();
		}
	}
}
