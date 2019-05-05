package no.hvl.dat108.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import no.hvl.dat108.model.Alias;
import no.hvl.dat108.model.Competitor;

/*
 * Hver eneste "side" eller kanskje klasse må lage sin egen versjon av denne og bruke enklere metoder enn dette
 */

public class InitConnection implements Runnable {

	public static InitConnection instance;

	private EntityManagerFactory emf;
	private EntityManager em;
	private Competitor competitor;

	public InitConnection() {
		if (instance != null)
			// Destroy myself
			return;
		else
			instance = this;
	}

	public Competitor getCompetitorOffDB(String name) {
		emf = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();
		return em.find(Competitor.class, name);
	}

	public List<Alias> getAliasList(String name) throws NullPointerException {
		Competitor temp = getCompetitorOffDB(name);
		if (temp != null) {
			return temp.getAliasList();
		}
		throw new NullPointerException();
	}

	public void persistAlias(Alias alias) {
		// Create EntityManagerFactory¨
		emf = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_NAME);
		// Create EntityManager
		em = emf.createEntityManager();

		//Persist entity
		em.getTransaction().begin();
		try {
		Alias temp = em.find(Alias.class, alias.getAliasid());
		if (temp != null)
			em.remove(temp);
		} catch (Exception e) {
			//Failed
			System.out.println(e.getMessage());
		}
		em.persist(alias);
		em.flush();
		em.refresh(alias);
		em.getTransaction().commit();
	}

	public void persistCompetitor(Competitor competitor) {
		// Create EntityManagerFactory¨
		emf = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_NAME);
		// Create EntityManager
		em = emf.createEntityManager();

		// Delete
		Competitor temp = getCompetitorOffDB(competitor.getUsername());

		if (temp != null) {
			em.getTransaction().begin();
			for (Alias element : temp.getAliasList()) {
				em.remove(element.getCar());
			}
			em.remove(temp);
			em.getTransaction().commit();
		}
		// Persist entity

		em.getTransaction().begin();
		em.persist(competitor);
		em.flush();
		em.refresh(competitor);
		em.getTransaction().commit();
	}

	@Override
	public void run() {
		// Create EntityManagerFactory

		emf = Persistence.createEntityManagerFactory(Main.PERSISTENCE_UNIT_NAME);

		// Create EntityManager
		em = emf.createEntityManager();

		// Create and populate Entity

		competitor = em.find(Competitor.class, "Biggus_Dickus");
		if (competitor != null) {
			em.getTransaction().begin();
			em.remove(competitor);
			em.getTransaction().commit();
		}
		competitor = new Competitor();
		competitor.setUsername("Biggus_Dickus");
		competitor.setEmail("bigd@gmail.com");
		competitor.setPassword("1");
		competitor.addAlias(new Alias(competitor, "gunnar", 0));

		// Persist entity
		em.getTransaction().begin();
		em.persist(competitor);
		em.getTransaction().commit();

		// Retrieve entity
		competitor = em.find(Competitor.class, "Biggus_Dickus");
		System.out.println("FANT NY COMETITOR:" + competitor);

		// Update entity
//		em.getTransaction().begin();
//		competitor.setUsername("Dickus_Biggus");
//		System.out.println("Competitor after updation: " + competitor);
//		em.getTransaction().commit();

		// Remove entity
		em.getTransaction().begin();
		em.remove(competitor);
		em.getTransaction().commit();

//		competitor = em.find(Competitor.class, "Dickus_Biggus");
//		System.out.println("Competitor after removal and namechange: " +  competitor);
//		
		competitor = em.find(Competitor.class, "Biggus_Dickus");
		System.out.println("Just to be sure; Competitor after removal and namechange: " + competitor);

//		try {
//			em.getTransaction().begin();
//			em.persist(competitor);
//			em.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		em.close();
	}
}
