package no.hvl.dat108.game_elements;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import no.hvl.dat108.audio.RaceCarAudio;
import no.hvl.dat108.main.GameObject;
import no.hvl.dat108.main.Main;

public class RaceCar extends GameObject {

	private int maxGear;
	private int gear;
	private float speed;
	private float rpm;
	private boolean clutch;
	private boolean throttle;
	private boolean status;
	private float speedIncrease;
	private int hp;
	private int weight;
	private int tires;
	private int y;
	private BufferedImage image;
	private RaceCarAudio rca;

	public RaceCar() {
		
		clutch = false;
		throttle = false;
		status = false;
		
		try {
			image = ImageIO.read(RaceCar.class.getResourceAsStream("/car.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		speed = 0;
		maxGear = 5;
		hp = 250;
		weight = 0;
		tires = 0;
		speedIncrease = (hp + (1410 - weight) + tires) / 1000;
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(image, 0, y, Main.WIDTH, Main.HEIGHT, null);
		
		
		if (clutch)
			g.drawString("clutching", 500, 100);
		else
			g.drawString("not clutching", 500, 100);
		g.drawString(String.valueOf(gear), 500, 150);
	}

	@Override
	public void tick() {
		if (!clutch) {
			if (throttle && gear > 0) {
				speed += speedIncrease;
				y = -5;
			} else
				speedDecrease(true);
		} else
			speedDecrease(false);

	}
	/**
	 * @param speedDecrease decreases the speed of the vehicle by type. True meaning with enginebraking.
	 */
	private void speedDecrease(boolean type) {
		if (speed > 0) {
			if(type)
				speed -= 0.4f;
			else
				speed -= 0.25f;
			y = 0;
		}
	}

	public void throttleDown() {
		if (!throttle) {
			throttle = true;
			rca.throttle();
			System.out.println("Kjørw");
		}
	}

	public void throttleUp() {
		if (throttle) {
			throttle = false;
			rca.release();
			System.out.println("ikke Kjørw");
		}
	}

	public void brakeDown() {

	}

	public void brakeUp() {

	}

	public void clutchDown() {
		if (!clutch) {
			clutch = true;
			rca.release();
		}
	}

	public void clutchUp() {
		if (clutch) {
			clutch = false;
			if(throttle) {
				rca.throttle();
			}
		}
	}

	public void gearDown() {
		if (clutch && gear > 0) {
			gear--;
		}
	}

	public void gearUp() {
		if (clutch && gear < maxGear) {
			gear++;
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public boolean checkMouse(MouseEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isClutch() {
		return clutch;
	}

	public void setClutch(boolean clutch) {
		this.clutch = clutch;
	}

	public boolean isThrottle() {
		return throttle;
	}

	public void setThrottle(boolean throttle) {
		this.throttle = throttle;
	}

	public RaceCarAudio getRca() {
		return rca;
	}

	public void setRca(RaceCarAudio rca) {
		this.rca = rca;
	}

}
