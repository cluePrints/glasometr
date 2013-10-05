package org.chesno.glasometr.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chesno.glasometr.domain.BillVotes;
import org.chesno.glasometr.domain.MatchResponse;
import org.chesno.glasometr.domain.Vote;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class VoteMatcher
{
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/match")
	public MatchResponse match()
	{
		MatchResponse response = new MatchResponse();
		response.getHeaders().add("#");
		response.getHeaders().add("Закон");
		response.getHeaders().add("Мій голос");
		response.getHeaders().add("Хтось");
		
		BillVotes bill1 = new BillVotes();
		bill1.setMyVote(Vote.Yes);
		bill1.setP1(Vote.Refrained);
		bill1.setTitle("Закон №1");
		response.getBillVotes().add(bill1);
		return response;
	}
}
