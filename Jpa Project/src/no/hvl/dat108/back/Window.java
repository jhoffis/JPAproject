package no.hvl.dat108.back;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import no.hvl.dat108.main.Game;
import no.hvl.dat108.main.Main;

/*
 * @param creates the JFrame and hold information about itself
 */
public class Window extends Canvas {

	private static final long serialVersionUID = 7337262500611770276L;
	private static JFrame frame;
	private static Game game;
	public static boolean fullscreen;
	private static Dimension screenSize;
	private static Dimension windowSize;

	public Window(int width, int height, String title, Game game) {
		windowSize = new Dimension(width, height);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame(title);
		frame.setUndecorated(false);
		frame.setBounds(500, 500, width, height);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(game);

		game.start();
		this.game = game;
	}

	public static void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public static void fullscreen() {
		frame.dispose();
		if (!fullscreen) {
			Main.WIDTH = screenSize.width;
			Main.HEIGHT = screenSize.height;
			frame.setBounds(0, 0, screenSize.width, screenSize.height);
			frame.setUndecorated(true);
			fullscreen = true;
		} else {
			frame.setBounds(0, 0, windowSize.width, windowSize.height);
			frame.setLocationRelativeTo(null);
			frame.setUndecorated(false);
			fullscreen = false;
		}
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static Point getFrameLocationOnScreen() {
		try {
			return frame.getLocationOnScreen();
		} catch (IllegalComponentStateException e) {
			return null;
		}
	}

	// Might not be used at all
	public static void addToFrame(Component c) {
		frame.add(c);
	}

}
