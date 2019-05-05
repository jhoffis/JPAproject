package no.hvl.dat108.game_elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import no.hvl.dat108.handlers_and_managers.Handler;
import no.hvl.dat108.main.GameObject;

public class RaceTrack extends GameObject{

	private int length;
	private RaceCar myCar;
	private RaceCarInteraction interaction;
	
	public RaceTrack(int length, RaceCar myCar) {
		this.length = length;
		this.myCar = myCar;
		interaction = Handler.instance.getRci();
		interaction.setCar(myCar);
		Handler.instance.getFm().setCurrentFocus(myCar);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString(String.valueOf(myCar.getSpeed()), 300, 300);
		myCar.render(g);
	}

	@Override
	public void tick() {
		myCar.tick();
	}

	@Override
	public boolean checkMouse(MouseEvent e) {
		return false;
	}
	
}
