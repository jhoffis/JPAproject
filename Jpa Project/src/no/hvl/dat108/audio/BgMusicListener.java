package no.hvl.dat108.audio;

import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import no.hvl.dat108.actions.Action;
import no.hvl.dat108.handlers_and_managers.Handler;

public class BgMusicListener implements LineListener {

	private Action action;
	private String[] backgroundMusic;
	private Audio bgRecord;
	private Random r = new Random();
	private int lastPlayed;
	

	public BgMusicListener(String[] backgroundMusic) {
		// Maybe use action for something later, cause it's awesome
		this.backgroundMusic = backgroundMusic;
		lastPlayed = -1;
	}

	@Override
	public void update(LineEvent arg0) {
		attempt();
	}

	public void play() {
		attempt();
	}
	
	public void turnOff() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		bgRecord.stop();
	}
	
	private void attempt() {
		
		if ((bgRecord == null || !bgRecord.getClip().isRunning()) && Handler.instance.getVolumeStatus()) {
			// play new, son
			try {
				int temp;
				do {
					temp = r.nextInt(backgroundMusic.length);
				} while (temp == lastPlayed);
				lastPlayed = temp;
				bgRecord = new Audio("music/" + backgroundMusic[lastPlayed], this, Handler.instance.getVolumeStatus());
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bgRecord.play();
		}
	}

}
