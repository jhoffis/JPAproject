package no.hvl.dat108.handlers_and_managers;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import no.hvl.dat108.audio.AudioHandler;
import no.hvl.dat108.main.GameObject;
import no.hvl.dat108.model.Alias;

public class GameHandler {

	private int sceneUsed;
	private ArrayList<GameObject> gos;
	private AudioHandler audioHandler;
	private boolean useGlobalScene;

	public GameHandler(ArrayList<GameObject> gos, int sceneUsed, boolean useGlobalScene) {
		this.useGlobalScene = useGlobalScene;
		setObjects(gos);
		this.sceneUsed = sceneUsed;
		audioHandler = new AudioHandler();
	}

	public void render(Graphics g) {
		for (int i = 0; i < gos.size(); i++) {
			gos.get(i).render(g);
		}
	}

	public void tick() {
		for (int i = 0; i < gos.size(); i++) {
			gos.get(i).tick();
		}
	}

	public ArrayList<GameObject> getObjects() {
		return gos;
	}

	public void setObjects(ArrayList<GameObject> gos) {
		this.gos = gos;
		for (int i = 0; i < gos.size(); i++) {
			System.out.println(gos.get(i).getClass());
		}
		checkUseGlobalScene();
	}

	public void playBackgroundMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioHandler.playRandomBackgroundMusic();
	}

	public void turnOffBackgroundMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioHandler.turnOffBackgroundMusic();
	}

	public void sceneChange(int sceneUsed, ArrayList<GameObject> objects) {
		this.sceneUsed = sceneUsed;
		setObjects(objects);

		if (sceneUsed == 1) {
			try {
				audioHandler.playRandomBackgroundMusic();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkUseGlobalScene() {
		if (useGlobalScene) {
			for (GameObject go : Handler.instance.getSh().getGlobalScene()) {
				gos.add(go);
			}
		}
	}

	public boolean isUseGlobalScene() {
		return useGlobalScene;
	}

	public void setUseGlobalScene(boolean useGlobalScene) {
		this.useGlobalScene = useGlobalScene;
	}

}
