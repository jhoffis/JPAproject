package no.hvl.dat108.game_elements;

import java.awt.event.KeyEvent;

import no.hvl.dat108.audio.RaceCarAudio;
import no.hvl.dat108.handlers_and_managers.CustomKeyListener;

public class RaceCarInteraction implements CustomKeyListener {

	private RaceCar myCar;
	private boolean racing;
	private RaceCarAudio rca;

	public RaceCarInteraction(RaceCar myCar) {
		this.myCar = myCar;
		racing = false;
		rca = new RaceCarAudio();
		if (myCar != null)
			myCar.setRca(rca);
	}

	/**
	 * controls keyinput and redirects to the correct methods. Allows input
	 */

	@Override
	public void keyPressed(KeyEvent c) {
		if (myCar != null && myCar.inFocus) {
			switch (c.getKeyCode()) {
			case 87:
				// W
				myCar.throttleDown();
				break;
			case 83:
				// S
				myCar.brakeDown();
				break;
			case 16:
				// L-shift
				myCar.gearUp();
				break;
			case 17:
				// L-ctrl
				myCar.gearDown();
				break;
			case 32:
				// space
				myCar.clutchDown();
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent c) {
		if (myCar != null && myCar.inFocus) {
			switch (c.getKeyCode()) {
			case 87:
				// W
				myCar.throttleUp();
				break;
			case 83:
				// S
				myCar.brakeUp();
				break;
			case 32:
				// space
				myCar.clutchUp();
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
//NOT USED		
	}

	public void setRacing(boolean b) {
		racing = b;
	}

	public void setCar(RaceCar myCar2) {
		myCar = myCar2;
		if (myCar != null)
			myCar.setRca(rca);
	}
}
