package org.chesno.glasometr.parse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import org.chesno.glasometr.domain.Bill;
import org.chesno.glasometr.domain.BillVote;
import org.chesno.glasometr.domain.BillVotes;
import org.chesno.glasometr.domain.MatchResponse;
import org.chesno.glasometr.domain.Person;
import org.chesno.glasometr.domain.Vote;

import com.google.common.collect.Lists;

public class Matcher
{
	EntityManagerFactory ef;

	public Matcher(EntityManagerFactory ef)
	{
		super();
		this.ef = ef;
	}

	public MatchResponse lookFor(List<Boolean> votes,
			List<Integer> selectedBillIds)
	{
		EntityManager em = ef.createEntityManager();

		List<BillVotes> billVotes = Lists.newArrayList();
		List<Integer> yesBillIds = Lists.newArrayList();
		List<Integer> noBillIds = Lists.newArrayList();
		if (votes == null || selectedBillIds == null) {
			return new MatchResponse();
		}
		for (int i = 0; i < votes.size(); i++)
		{
			if (votes.get(i) == null) 
			{
				continue;
			}
			if (votes.get(i))
			{
				yesBillIds.add(selectedBillIds.get(i));
			} else
			{
				noBillIds.add(selectedBillIds.get(i));
			}
		}

		@SuppressWarnings("unchecked")
		List<Object[]> results = em
				.createQuery(
						"select person.id,count(*) from BillVote v where "
								+ "(v.bill.id in (:yesBillIds) and v.vote = :voteYes) or "
								+ "(v.bill.id in (:noBillIds)  and v.vote = :voteNo) "
								+ "group by person.id " + "having count(*)>0 "
								+ "order by count(*) desc, person.id")
				.setParameter("yesBillIds", yesBillIds)
				.setParameter("voteYes", Vote.Yes)
				.setParameter("noBillIds", noBillIds)
				.setParameter("voteNo", Vote.No).setMaxResults(5)
				.getResultList();

		
		List<String> people = Lists.newArrayList();
		if (!results.isEmpty())
		{
			Person person = em.find(Person.class, (Integer)(results.get(0))[0]);
			people.add(person.getName());
			if (results.size() > 1)
			{
				person = em.find(Person.class, (Integer)(results.get(1))[0]);
				people.add(person.getName());
			}
			if (results.size() > 2)
			{
				person = em.find(Person.class, (Integer)(results.get(2))[0]);
				people.add(person.getName());
			}
			if (results.size() > 3)
			{
				person = em.find(Person.class, (Integer)(results.get(3))[0]);
				people.add(person.getName());
			}
			if (results.size() > 4)
			{
				person = em.find(Person.class, (Integer)(results.get(4))[0]);
				people.add(person.getName());
			}
		}
		for (int i=0; i<selectedBillIds.size(); i++)
		{
			int billId = selectedBillIds.get(i);
			BillVotes b = new BillVotes();
			b.setId(billId + "");
			if (votes.get(i) == null) {
				b.setMyVote(null);
			} else if (votes.get(i) == Boolean.TRUE) {
				b.setMyVote(Vote.Yes);
			} else if (votes.get(i) == Boolean.FALSE) { 
				b.setMyVote(Vote.No);
			}
			
						
			if (!results.isEmpty())
			{
				b.setP1(voteFor(em, results, billId, 0));
				b.setP2(voteFor(em, results, billId, 1));
				b.setP3(voteFor(em, results, billId, 2));
				b.setP4(voteFor(em, results, billId, 3));
				b.setP5(voteFor(em, results, billId, 4));
			}
			
			Bill bill = em.find(Bill.class, billId);
			b.setTitle(bill.getTitle());

			billVotes.add(b);

		}
		em.close();

		
		MatchResponse response = new MatchResponse();
		response.setBillVotes(billVotes);
		
		response.getHeaders().add("#");
		response.getHeaders().add("Закон");
		response.getHeaders().add("Мій голос");
		response.getHeaders().addAll(people);

		return response;
	}

	private Vote voteFor(EntityManager em, List<Object[]> results, int billId,
			int idx)
	{
		try
		{
			int pId = (Integer)(results.get(idx))[0];
			BillVote vote = (BillVote) em.createQuery("from BillVote v where v.person.id = :id and v.bill.id=:bid")
				.setParameter("id", pId)
				.setParameter("bid", billId)
				.getSingleResult();
			Vote vv = vote.getVote();
			return vv;
		} catch (NoResultException ex) {
			return null;
		}	
	}
}
