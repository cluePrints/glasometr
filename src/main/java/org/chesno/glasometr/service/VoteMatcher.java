package org.chesno.glasometr.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chesno.glasometr.domain.Bill;
import org.chesno.glasometr.domain.BillVotes;
import org.chesno.glasometr.domain.ClientState;
import org.chesno.glasometr.domain.MatchResponse;
import org.chesno.glasometr.domain.Vote;

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
		MatchResponse response = new MatchResponse();
		response.getHeaders().add("#");
		response.getHeaders().add("Закон");
		response.getHeaders().add("Мій голос");
		response.getHeaders().add("Хтось");

		if (state == null ||state.getSelectedBillIds() == null) {
			return response;
		}
		
		EntityManager em = ef.createEntityManager();
		
		for (int billId : state.getSelectedBillIds())
		{
			BillVotes b = new BillVotes();
			b.setId(billId + "");
			
			// TODO: this one should be from state
			b.setMyVote(Vote.Yes);
			b.setP1(Vote.Refrained);
			
			Bill bill = em.find(Bill.class, billId);
			b.setTitle(bill.getTitle());
			response.getBillVotes().add(b);

		}
		
		em.close();
		return response;
	}
}
