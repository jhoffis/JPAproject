package no.hvl.dat108.handlers_and_managers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Stack;

import no.hvl.dat108.back.Window;
import no.hvl.dat108.main.GameObject;

/*
 * Perhaps this also needs a stack so you can only get a press after one press.
 * 
 * Does not work
 */
public class MouseManager extends MouseAdapter {

	private boolean pressed;


	public MouseManager() {
	}

	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	public void mouseClicked(MouseEvent e) {
		ArrayList<GameObject> list = Handler.instance.getGh().getObjects();
		
		for(GameObject go : list) {
			go.checkMouse(e);
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println(e.toString());
	}

	public Point getPoint() {
		if (Window.fullscreen)
			return new Point(MouseInfo.getPointerInfo().getLocation().x - Window.getFrameLocationOnScreen().x,
					MouseInfo.getPointerInfo().getLocation().y - Window.getFrameLocationOnScreen().y);
		return new Point(MouseInfo.getPointerInfo().getLocation().x - Window.getFrameLocationOnScreen().x,
				MouseInfo.getPointerInfo().getLocation().y - Window.getFrameLocationOnScreen().y - 25);
	}

	
	//Have a usePressed and peekPressed
	public boolean peekPressed() {
		return pressed;
	}

}
