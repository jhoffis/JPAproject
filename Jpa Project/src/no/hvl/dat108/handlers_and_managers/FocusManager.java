package no.hvl.dat108.handlers_and_managers;

import no.hvl.dat108.main.GameObject;

public class FocusManager {

	GameObject currentFocus;
	
	public FocusManager() {
		
	}
	
	public FocusManager(GameObject focus) {
		currentFocus = focus;
	}

	public GameObject getCurrentFocus() {
		return currentFocus;
	}

	public void setCurrentFocus(GameObject newFocus) {
		if(currentFocus != null) {
			currentFocus.inFocus = false;
		}
		currentFocus = newFocus;
		if(currentFocus != null) {
			currentFocus.inFocus = true;
		}
	}
}
