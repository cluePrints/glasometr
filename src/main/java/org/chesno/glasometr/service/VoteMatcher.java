package org.chesno.glasometr.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chesno.glasometr.domain.ClientState;
import org.chesno.glasometr.domain.MatchResponse;
import org.chesno.glasometr.parse.Matcher;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class VoteMatcher
{
	@PersistenceUnit(unitName = "derby")
	EntityManagerFactory ef = Persistence.createEntityManagerFactory("derby");
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/match")
	public MatchResponse match(ClientState state)
	{
		return new Matcher(ef).lookFor(state.getSelectedValues(), state.getSelectedBillIds());
	}
}
