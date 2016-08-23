package com.codifythings.personalassistant;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioSpeaker {

	/*
	 * Plays the audio based on audioType
	 */
	public void speak(String audioType) throws Exception {

		System.out.println("[DEBUG] Playing " + audioType);

		// Reference to audio file that needs to be played
		File audioFile = new File(Constants.AUDIO_FOLDER + audioType);

		final Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
		final CountDownLatch clipDone = new CountDownLatch(1);

		clip.addLineListener(new LineListener() {
			@Override
			public void update(LineEvent event) {
				if (event.getType() == LineEvent.Type.STOP) {
					clip.close();
					clipDone.countDown();
				}
			}
		});

		clip.open(AudioSystem.getAudioInputStream(audioFile));
		clip.start();
		clipDone.await();
	}
}