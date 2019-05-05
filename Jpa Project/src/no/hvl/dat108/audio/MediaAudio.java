package no.hvl.dat108.audio;

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaAudio {
	private Media hit;
	private MediaPlayer mediaPlayer;
	
	public MediaAudio(String file) {
		String bip = "./bin/" + file + ".mp3";
		 hit = new Media(new File(bip).toURI().toString());
		 mediaPlayer = new MediaPlayer(hit);
	}

	public void play() {
		mediaPlayer.play();
	}
	
	public void stop() {
		mediaPlayer.stop();
	}
	
	public boolean isPlaying() {
		return mediaPlayer.getOnPlaying() != null;
	}
}
