package no.hvl.dat108.audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * @param Audio plays a .wav file, convert to a smaller format later. Something like .mp3 or .ogg
 */
public class Audio {

	// to store current position
	private Long currentFrame;
	private Clip clip;

	// current status of clip
	private String status;

	private AudioInputStream audioInputStream;
	private String filePath;
	private LineListener listener;
	private FloatControl control;
	private float lastVolume;

	// constructor to initialize streams and clip
	public Audio(String filePath, LineListener listener, boolean onOffStatus)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		this.filePath ="/" + filePath;
		// create AudioInputStream object
		System.out.println(this.filePath);
		URL defaultSound = Audio.class.getResource(this.filePath);
		audioInputStream = AudioSystem.getAudioInputStream(defaultSound);

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);
		
		//turn off the volume initially if status is false
		
		control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		
		if(!onOffStatus) {
			onOffVolume(onOffStatus);
		}
		
		try {
			this.listener = listener;
			clip.addLineListener(listener);
		} catch (NullPointerException e) {
			// listener was not added
		}
	}

	// Work as the user enters his choice

	public void gotoChoice(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		switch (c) {
		case 1:
			pause();
			break;
		case 2:
			resumeAudio();
			break;
		case 3:
			restart();
			break;
		case 4:
			stop();
			break;
		case 5:
			System.out.println("Enter time (" + 0 + ", " + clip.getMicrosecondLength() + ")");
			Scanner sc = new Scanner(System.in);
			long c1 = sc.nextLong();
			jump(c1);
			break;

		}

	}

	// Method to play the audio
	public void play() {
		// start the clip
		clip.start();

		status = "play";
	}
	
	// Method to play the audio
		public void play(boolean volume) {
			// start the clip
			onOffVolume(volume);
			clip.start();
			
			status = "play";
		}

	// Method to pause the audio
	public void pause() {
		if (status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (status.equals("play")) {
			System.out.println("Audio is already " + "being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (c > 0 && c < clip.getMicrosecondLength()) {
			clip.stop();
			clip.close();
			clip.open(audioInputStream);
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}

	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		URL defaultSound = Audio.class.getResource(this.filePath);
		audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
		clip.open(audioInputStream);
	}
	
	//Spesifically change volume of this audio
	public void setVolume(int value) {
		control.setValue(value);
	}
	
	//Turn off/on volume
	public void onOffVolume(boolean value) {
		//Turn on if true and set it to the last value
		if(value) 
			control.setValue(lastVolume);
		//Save the last value and turn off
		lastVolume = control.getValue();
		control.setValue(control.getMinimum());
	}

	public Long getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(Long currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AudioInputStream getAudioInputStream() {
		return audioInputStream;
	}

	public void setAudioInputStream(AudioInputStream audioInputStream) {
		this.audioInputStream = audioInputStream;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
