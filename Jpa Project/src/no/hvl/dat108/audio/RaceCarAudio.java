package no.hvl.dat108.audio;

import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import no.hvl.dat108.handlers_and_managers.Handler;

public class RaceCarAudio {
	private Random random;
	private MediaAudio turboSurge1;
	private MediaAudio turboSurge2;
	private MediaAudio engine;

	public RaceCarAudio() {
		random = new Random();
		turboSurge1 = new MediaAudio("sfx/turbosurge1");
		turboSurge2 = new MediaAudio("sfx/turbosurge2");

		engine = new MediaAudio("sfx/motorkraftiglyden");

	}

	public void release() {
		if (Handler.instance.getVolumeStatus()) {

			engine.stop();
			if (random.nextInt(2) == 0) {
				turboSurge1.play();
			} else {
				turboSurge2.play();
			}

		}
	}

	public void throttle() {
		if (Handler.instance.getVolumeStatus()) {

//				if (turboSurge1.isPlaying())
			turboSurge1.stop();
//				if (turboSurge2.isPlaying())
			turboSurge2.stop();
			engine.play();

		}
	}
}
