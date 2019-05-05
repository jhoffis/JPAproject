package no.hvl.dat108.bundles;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import no.hvl.dat108.main.GameObject;

public class Menu extends GameObject{

	ArrayList<GameObject> elements;
	
	public Menu(int x, int y) {
		elements = new ArrayList<GameObject>();
		this.x = x;
		this.y = y;
	}
	
	public void addElement(GameObject go) {
		go.setY(y + 100 * elements.size());
		go.setX(x);
		elements.add(go);
	}

	@Override
	public void render(Graphics g) {
		for(GameObject go : elements) {
			go.render(g);
		}
	}

	@Override
	public void tick() {
		for(GameObject go : elements) {
			go.tick();
		}
	}
	
	public ArrayList<GameObject> getElements(){
		return elements;
	}

	@Override
	public boolean checkMouse(MouseEvent e) {
		for(GameObject go : elements) {
			go.checkMouse(e);
		}
		return false;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}



}
