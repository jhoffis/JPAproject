package no.hvl.dat108.actions;

import no.hvl.dat108.audio.MediaAudio;
import no.hvl.dat108.handlers_and_managers.Handler;
import no.hvl.dat108.main.InitConnection;
import no.hvl.dat108.model.Competitor;
import no.hvl.dat108.simple_interactions.TextFieldInput;

public class CheckLoginAction implements Action{

	private Competitor competitor;
	
	@Override
	public void run() {
		System.out.println("Checking login");
		String username = ((TextFieldInput) Handler.instance.getSh().getGOCurrentScene(0)).getActualString();
		String password = ((TextFieldInput) Handler.instance.getSh().getGOCurrentScene(1)).getActualString();
		
		try {
			competitor = InitConnection.instance.getCompetitorOffDB(username);
			if(password.equals(competitor.getPassword())) {
				System.out.println(competitor.toString());
				Handler.instance.getSh().setScene(1);
				Handler.instance.getMh().setCompetitor(competitor);
			}
			else
				System.out.println("Feil passord");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
