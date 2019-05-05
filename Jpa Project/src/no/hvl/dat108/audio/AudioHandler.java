package no.hvl.dat108.audio;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioHandler {

	private String[] backgroundMusic;
	private Audio bgRecord;
	private boolean running;
	private Random r = new Random();
	private BgMusicListener bmListener;
	

	public AudioHandler() {

		String[] backgroundMusic = { "RED-PasswordKeys1.40crk.wav", "AGAiN - MOBILedit!crk.wav",
				"AGES - Half Life 2 +19trn.wav", "PiZZA - 7 Sins +5trn.wav", "RED - Advanced X Video Converter 5.0.9 crk.wav" };
		this.backgroundMusic = backgroundMusic;
		bmListener = new BgMusicListener(backgroundMusic);
	}

	public void playRandomBackgroundMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		bmListener.play();
	}
	
	public void turnOffBackgroundMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		bmListener.turnOff();
	}
}
