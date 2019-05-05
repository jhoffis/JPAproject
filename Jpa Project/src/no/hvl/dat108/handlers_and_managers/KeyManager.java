package no.hvl.dat108.handlers_and_managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import no.hvl.dat108.game_elements.RaceCar;

import java.util.Stack;

public class KeyManager implements KeyListener {

	private boolean pressed;
	private Stack<KeyEvent> keyStack;
	private HashMap<String, CustomKeyListener> keylisteners;

	public KeyManager() {
		keyStack = new Stack<KeyEvent>();
		keylisteners = new HashMap<String, CustomKeyListener>();
	}

	public void addToKeyListeners(String key, CustomKeyListener value) {
		keylisteners.put(key, value);
	}

	public void removeFromKeyListeners(String key) {
		keylisteners.remove(key);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (keylisteners != null) {
			for (CustomKeyListener elem : keylisteners.values()) {
				elem.keyPressed(arg0);
			}
		}
//		System.out.println("Pressed key: " + arg0);
		if (arg0.isShiftDown()) {
			arg0.setKeyChar(Character.toUpperCase(arg0.getKeyChar()));
		}
		try {
			if (!Handler.instance.getFm().getCurrentFocus().getClass().equals(RaceCar.class))
				keyStack.push(arg0);
		} catch (NullPointerException e) {
			System.out.println("RACECAR FOCUS NOT FOUND \n" + e.getMessage());
		}
		pressed = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (keylisteners != null) {
			for (CustomKeyListener elem : keylisteners.values()) {
				elem.keyReleased(arg0);
			}
		}

		pressed = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	public boolean getPressed() {
		return pressed;
	}

	public Stack<KeyEvent> getStack() {
		return keyStack;
	}

	public KeyEvent pop() {
		return keyStack.pop();
	}

}
