package no.hvl.dat108.actions;

import no.hvl.dat108.handlers_and_managers.Handler;
import no.hvl.dat108.handlers_and_managers.ModelHandler;
import no.hvl.dat108.model.Alias;
import no.hvl.dat108.model.Competitor;
import no.hvl.dat108.simple_interactions.TextFieldInput;

public class CreateAliasAction implements Action {

	@Override
	public void run() {
		// Legg til attributt i en tabell som hektes til Competitor tabellen.
		// Hver competitor har en en til mange kobling til aliaser. Altså en competitor
		// kan ha mange aliaser, men ikke omvendt
		ModelHandler tempMH = Handler.instance.getMh();
		TextFieldInput input = (TextFieldInput) Handler.instance.getSh().getGOViaNameCurrentScene("NewAliasUsername");
		Alias alias = new Alias(tempMH.getCompetitor(), input.getActualString(), 0);
		Competitor competitor = tempMH.getCompetitor();
		competitor.addAlias(alias);
		tempMH.updateCompetitorFully(competitor);
		tempMH.updateLocalAliasList();
		tempMH.loadAlias(alias.getAliasid());
		System.out.println(tempMH.getCompetitor().getAliasList().size());
		System.out.println(Handler.instance.getMh().getCompetitor().getAliasList().size());
		Handler.instance.getSh().setScene(8);
	}

}
