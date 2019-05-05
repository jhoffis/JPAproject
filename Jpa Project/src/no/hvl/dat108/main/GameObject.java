package no.hvl.dat108.main;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/*
 * @Param Every class extending 'GameObject' must also get their public DB information 
 * from their respective type. What is or cannot be pulled from the DB is to be
 * made here; in this class.
 * 
 * A @Entity can actually extend this abstract class
 */
public abstract class GameObject {
	//TODO add something here
	
	public boolean inFocus;
	public String nameID = "";
	protected int x;
	protected int y;
	public abstract void render(Graphics g);
	public abstract void tick();
	public abstract boolean checkMouse(MouseEvent e);
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
