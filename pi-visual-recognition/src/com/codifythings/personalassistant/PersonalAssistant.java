package com.codifythings.personalassistant;

import java.util.concurrent.TimeUnit;

public class PersonalAssistant {

	private ImageCapturer imageCapturer = null;
	private VisualRecognizer visualRecognizer = null;
	private TextToSpeechConverter textToSpeechConverter = null;
	private AudioSpeaker audioSpeaker = null;

	public PersonalAssistant() {
		imageCapturer = new ImageCapturer();
		visualRecognizer = new VisualRecognizer();
		textToSpeechConverter = new TextToSpeechConverter();
		audioSpeaker = new AudioSpeaker();
	}

	public void startRecognition() {

		System.out.println("[DEBUG] Interaction Started");

		try {
			// Greet user
			audioSpeaker.speak(Constants.AUDIO_TYPE_GREET);

			// Wait for 3 seconds
			TimeUnit.SECONDS.sleep(3);

			// Capture image
			imageCapturer.captureImage();

			// Classify image
			String result = visualRecognizer.recognizeImage();

			if (result.equals(Constants.RESPONSE_UNKNOWN)) {
				// Play back response to user
				audioSpeaker.speak(Constants.AUDIO_TYPE_NOT_RECOGNIZED);
			} else {
				// Convert result into audio
				textToSpeechConverter.convert(result);

				// Play back response to user
				audioSpeaker.speak(Constants.AUDIO_TYPE_RESPONSE);
			}

		} catch (Exception ex) {
			System.out.println("[DEBUG] Error: " + ex.getMessage());
			ex.printStackTrace();
		}

		System.out.println("[DEBUG] Interaction Ended");
	}

	public static void main(String[] args) {
		new PersonalAssistant().startRecognition();
	}
}