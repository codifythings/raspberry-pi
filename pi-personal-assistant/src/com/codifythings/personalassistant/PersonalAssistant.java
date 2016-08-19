package com.codifythings.personalassistant;

import com.codifythings.personalassistant.AudioRecorder;
import com.codifythings.personalassistant.AudioSpeaker;
import com.codifythings.personalassistant.WeatherSearcher;
import com.codifythings.personalassistant.CommandClassifier;
import com.codifythings.personalassistant.SpeechToTextConverter;
import com.codifythings.personalassistant.TextToSpeechConverter;

public class PersonalAssistant {

	// Variables
	private AudioRecorder audioRecorder = null;
	private SpeechToTextConverter speechToTextConverter = null;
	private CommandClassifier commandClassifier = null;
	private TextToSpeechConverter textToSpeechConverter = null;
	private AudioSpeaker audioSpeaker = null;

	public PersonalAssistant() {
		// Initialize required objects
		audioRecorder = new AudioRecorder();
		speechToTextConverter = new SpeechToTextConverter();
		commandClassifier = new CommandClassifier();
		textToSpeechConverter = new TextToSpeechConverter();
		audioSpeaker = new AudioSpeaker();
	}

	public void startInteraction() {
		System.out.println("[DEBUG] Interaction Started");

		try {
			// Start conversation with a greeting
			audioSpeaker.speak(Constants.AUDIO_TYPE_GREETING);

			// Listen and record query
			audioRecorder.record();

			// Convert the recorded query speech into text
			String query = speechToTextConverter.convert();

			// Classify the command
			String commandClassification = commandClassifier.classify(query);

			// If unknown classification then say not understood, else say let
			// me check and continue processing
			if (commandClassification.equals(Constants.CLASSIFICATION_UNKNOWN)) {
				audioSpeaker.speak(Constants.AUDIO_TYPE_NOT_UNDERSTOOD);
			} else {
				audioSpeaker.speak(Constants.AUDIO_TYPE_CHECKING);

				// Based on command classification, perform the search
				String answer = null;

				if (commandClassification.equals(Constants.CLASSIFICATION_WEATHER)) {
					answer = new WeatherSearcher().search(null);
				} else if (commandClassification.equals(Constants.CLASSIFICATION_TRAFFIC)) {
					// Do Nothing
				}

				// If answer is invalid then say not found, else continue
				// processing
				if (answer == null || answer.equals("")) {
					// Answer is invalid, say not found
					audioSpeaker.speak(Constants.AUDIO_TYPE_NOT_FOUND);
				} else {
					// Answer is valid, convert into speech
					textToSpeechConverter.convert(answer);

					// Say the answer
					audioSpeaker.speak(Constants.AUDIO_TYPE_ANSWER);
				}
			}

		} catch (Exception ex) {
			System.out.println("[DEBUG] " + ex.getMessage());
			ex.printStackTrace();
		}

		System.out.println("[DEBUG] Interaction Ended");
	}

	public static void main(String args[]) {
		new PersonalAssistant().startInteraction();
	}
}