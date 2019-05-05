package no.hvl.dat108.simple_interactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import no.hvl.dat108.main.GameObject;

/**
 * @param PlainText visualize text in canvas. Types (controls x-value): 
 * @param	$0 render from given x-value,  
 * 	$1 render from middle of text, 
 * 	$2 render from end of text
 *
 * @param   (int x, int y, String title, byte type, int fontSize)
 * @param   (int x, int y, String title, byte type, int fontSize, String nameID, Color textColor)
 */

public class TextPlain extends GameObject {

	private Color textColor;
	private String title;
	private int type;
	private Font font;
	private float fontSize;
	private float fontLengthPx;
	private int newX;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param title
	 * @param i
	 * @param fontSize
	 */
	
	public TextPlain(int x, int y, String title, int type, int fontSize) {
		init(x, y, title, type, fontSize, "", Color.WHITE);
	}

	public TextPlain(int x, int y, String title, int type, int fontSize, String nameID, Color textColor) {
		init(x, y, title, type, fontSize, nameID, textColor);
	}

	private void init(int x, int y, String title, int type, int fontSize, String nameID, Color textColor) {
		super.x = x;
		super.y = y;
		this.title = title;
		this.type = type;
		this.fontSize = fontSize;
		font = new Font("Lucida Console", Font.PLAIN, (int) fontSize);
		fontLengthPx = (float) fontSize * (7f / 12f);
		changeNewX(type);
		super.nameID = nameID;
		this.textColor = textColor;
	}

	public void changeNewX(int type) {
		if (type == 1)
			newX = x - (int) (fontLengthPx * title.length() / 2);
		else if (type == 2)
			newX = x - (int) (fontLengthPx * title.length());
		else
			newX = x;
	}
	
	public void changeText(String newTitle) {
		title = newTitle;
		changeNewX(type);
	}

	@Override
	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(textColor);
		g.drawString(title, newX, y);
	}

	@Override
	public void tick() {
	}

	@Override
	public boolean checkMouse(MouseEvent e) {
		return false;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	public int getNewX() {
		return newX;
	}

}
