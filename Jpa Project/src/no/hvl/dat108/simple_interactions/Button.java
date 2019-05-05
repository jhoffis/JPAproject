package no.hvl.dat108.simple_interactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import no.hvl.dat108.actions.Action;
import no.hvl.dat108.audio.Audio;
import no.hvl.dat108.handlers_and_managers.Handler;
import no.hvl.dat108.main.GameObject;

public class Button extends GameObject {

	private boolean isPressed;
	private int width;
	private int height;
	private float fontSize;
	private float fontLengthPx;
	private int sideWidth;
	private Color none;
	private Color over;
	private Color down;
	private Color currentColor;
	private Color textColor;
	private String title;
	private Action action;
	private Point p;
	private Font font;
	private int type;
	private int newX;


	public Button(int x, int y, int width, String title, Action action) {
		// Standard values
		init(x, y, width, (width / 16 * 9), Color.CYAN, Color.BLUE, Color.DARK_GRAY, title, action, (byte) 0);
	}

	public Button(int x, int y, int width, int height, Color none, Color over, Color down, String title,
			Action action, byte type) {
		// Custom values
		init(x, y, width, height, none, over, down, title, action, type);
	}

	private void init(int x, int y, int width, int height, Color none, Color over, Color down, String title,
			Action action, byte type) {
		// Init values
		this.x = x;
		this.y = y;
		fontSize = 14;
		font = new Font("Lucida Console", Font.PLAIN, (int) fontSize);
		fontLengthPx = (float) fontSize * (7f / 12f);

		this.width = width;
		sideWidth = (width - (int) (fontLengthPx * title.length())) / 2;
		this.height = height;
		this.none = none;
		this.over = over;
		this.down = down;
		currentColor = none;
		textColor = Color.black;
		this.title = title;
		this.action = action;
		this.type = type;
		changeNewX(type);
	}
	
	

	@Override
	public void tick() {
		checkMouseStatus();
		textColor = getContrastColor(currentColor);
	}

	@Override
	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(currentColor);
		g.fillRect(x, y, width, height);
		g.setColor(textColor);
		g.drawString(title, x + sideWidth, y + height / 2);
	}

	public Color getContrastColor(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;
	}

	public boolean getIsPressed() {
		return isPressed;
	}
	
	public void changeNewX(int type) {
		if (type == 1)
			newX = x - (int) (width / 2);
		else if (type == 2)
			newX = x - (int) (width);
		else
			newX = x;
	}

	private void checkMouseStatus() {
		// Check first where the mouse is. If the mouse is over the button, check if it
		// is pressed. Give appropiate color.

		try {
			p = Handler.instance.getMm().getPoint();
			if ((p.x >= x && p.x <= width + x) && (p.y >= y && p.y <= height + y)) {
				if (Handler.instance.getMm().peekPressed())
					currentColor = down;
				else
					currentColor = over;
			} else
				currentColor = none;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doDown() {
		action.run();
		try {
			new Audio("sfx/btn1.wav", null, Handler.instance.getVolumeStatus()).play();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doOver() {
		currentColor = over;
	}

	private void doNone() {
		currentColor = none;
	}

	@Override
	public boolean checkMouse(MouseEvent e) {

		if ((e.getX() >= x && e.getX() <= width + x) && (e.getY() >= y && e.getY() <= height + y)) {
			doDown();
			return true;
		}
		doNone();
		return false;
	}

}
