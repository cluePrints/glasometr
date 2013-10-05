package org.chesno.glasometr.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chesno.glasometr.domain.Person;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class VoteMatcher
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/match")
	public Person match()
	{
		return new Person();
	}
}
