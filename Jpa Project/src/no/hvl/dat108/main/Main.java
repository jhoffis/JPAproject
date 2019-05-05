package no.hvl.dat108.main;

import javafx.embed.swing.JFXPanel;
import no.hvl.dat108.audio.MediaAudio;
import no.hvl.dat108.back.Window;
import no.hvl.dat108.handlers_and_managers.Handler;

public class Main {
    public static final String PERSISTENCE_UNIT_NAME = "todos";
    public static int HEIGHT = 720, WIDTH = HEIGHT * 16 / 9;
    private Thread connectionThread;
    
    public Main() {
    	new JFXPanel();
    	new Handler();
    	startThread();
    	Game game = new Game();
    	Thread gameThread = new Thread(game);
    	new Window(WIDTH, HEIGHT, "Doomsday", game);
    }
    
    public static void main(String[] args) {
    	new Main();
	}
    
    private synchronized void startThread() {
    	connectionThread = new Thread(new InitConnection());
    	connectionThread.start();
    }
    
    private synchronized void joinThread() {
    	try {
    		connectionThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}