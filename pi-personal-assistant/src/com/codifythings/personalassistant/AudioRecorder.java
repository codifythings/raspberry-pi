package com.codifythings.personalassistant;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.codifythings.personalassistant.Constants;

public class AudioRecorder {

	// Reference to the audio file where recording will be stored
	File recordingFile = new File(Constants.AUDIO_FOLDER + Constants.AUDIO_TYPE_COMMAND + Constants.AUDIO_FORMAT);

	// Type of audio file
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	// Line from which audio data is captured
	TargetDataLine line;

	public void record() throws Exception {
		// Create a new thread that waits for a specified of time before
		// stopping
		Thread stopper = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(Constants.AUDIO_RECORD_TIME);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				finish();
			}
		});

		stopper.start();

		// Start recording
		start();
	}

	/**
	 * Captures the sound and record into a WAV file
	 */
	private void start() throws Exception {
		// Create audio format
		AudioFormat audioFormat = new AudioFormat(Constants.AUDIO_FORMAT_SAMPLE_RATE,
				Constants.AUDIO_FORMAT_SAMPLE_SIZE_IN_BITS, Constants.AUDIO_FORMAT_CHANNELS,
				Constants.AUDIO_FORMAT_SIGNED, Constants.AUDIO_FORMAT_BIG_ENDIAN);

		// Get target data line
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

		// Check if system supports the data line
		/*if (!AudioSystem.isLineSupported(info)) {
			System.out.println("[DEBUG] Line Not Supported");
			System.exit(0);
		}*/

		line = (TargetDataLine) AudioSystem.getLine(info);

		// Acquire all resources
		line.open(audioFormat);

		// Start recording
		line.start();

		System.out.println("[DEBUG] Recording Started");

		AudioInputStream audioInputStream = new AudioInputStream(line);

		// Store recording
		AudioSystem.write(audioInputStream, fileType, recordingFile);
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	private void finish() {
		line.stop();
		line.close();
		System.out.println("[DEBUG] Recording Finished");
	}
}
