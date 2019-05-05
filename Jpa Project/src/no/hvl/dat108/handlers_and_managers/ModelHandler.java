package no.hvl.dat108.handlers_and_managers;

import java.util.ArrayList;
import java.util.List;

import no.hvl.dat108.main.InitConnection;
import no.hvl.dat108.model.Alias;
import no.hvl.dat108.model.Competitor;

/**
 * @param ModelHandler Handles classes that directly corelates to the database.
 * @author Jhoffis
 *
 */

public class ModelHandler {

	private Competitor currentCompetitor;
	private Alias currentAlias;
	private List<Alias> currentAliasList;

	public ModelHandler() {
		nullifyAlias();
	}
	
	/**
	 * 
	 * @param i finds a alias with a fitting id.
	 * @return returns wether or not it found a alias with the given id.
	 */
	
	public boolean loadAlias(Integer i) {
		updateLocalAliasList();
		for (Alias element : currentAliasList) {
			if (i == element.getAliasid()) {
				currentAlias = element;
				updateGlobalCurrentAliasText();
				return true;
			}
		}
		updateGlobalCurrentAliasText();
		return false;
	}

	public void updateLocalAliasList() {
		try {
			currentAliasList = currentCompetitor.getAliasList();
		} catch (NullPointerException e) {
			currentAliasList = new ArrayList<Alias>();
		}
	}
	
	public void updateGlobalCurrentAliasText() {
		try {
			Handler.instance.getSh().getCurrentAliasText().changeText(getCurrentAlias().getName());
		}catch (NullPointerException e) {
			Handler.instance.getSh().getCurrentAliasText().changeText("");
		}
	}
	/**
	 * @param nullifyAlias Resets current Alias to free up space to load or create a different alias
	 */
	public void nullifyAlias() {
		if (currentAlias != null) 
			currentAlias = null;
		updateLocalAliasList();
		updateGlobalCurrentAliasText();
	}

	public void setCompetitor(Competitor competitor) {
		currentCompetitor = competitor;
	}

	public Competitor getCompetitor() {
		return currentCompetitor;
	}
	
	public Alias getCurrentAlias() {
		return currentAlias;
	}

	public void updateCompetitorFully(Competitor competitor) {
		setCompetitor(competitor);
		InitConnection.instance.persistCompetitor(competitor);
	}
}
