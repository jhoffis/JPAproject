package no.hvl.dat108.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import no.hvl.dat108.handlers_and_managers.Handler;
import no.hvl.dat108.model.Competitor;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 5113207912011895537L;

	private Thread thread;
	private boolean running;
	private Font font;
	private int fontSize;

	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		try {
			running = false;
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		fontSize = 16;
		this.addKeyListener(Handler.instance.getKm());
		this.addMouseListener(Handler.instance.getMm());
		this.setFocusTraversalKeysEnabled(false);
		font = new Font("Lucida Console", Font.PLAIN, fontSize);
	}
	
	@Override
	public void run() {
		init();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
				frames++;

				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
		}
		stop();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		Handler.instance.getGh().render(g);

		g.dispose();
		bs.show();
	}

	public void tick() {
		Handler.instance.getGh().tick();
	}

}
