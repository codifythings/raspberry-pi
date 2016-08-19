package com.codifythings.personalassistant;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.codifythings.personalassistant.Constants;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;

public class SpeechToTextConverter {

	public String convert() {

		System.out.println("[DEBUG] Converting Speech to Text");

		String text = "";

		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword(Constants.SPEECH_TO_TEXT_USERNAME, Constants.SPEECH_TO_TEXT_PASSWORD);

		File recordedFile = new File(Constants.AUDIO_FOLDER + Constants.AUDIO_TYPE_COMMAND + Constants.AUDIO_FORMAT);

		SpeechResults transcript = service.recognize(recordedFile).execute();

		List<Transcript> transcripts = transcript.getResults();
		for (Iterator<Transcript> iter = transcripts.iterator(); iter.hasNext();) {
			Transcript txn = iter.next();
			
			if (txn.isFinal()) {

				SpeechAlternative alternate = txn.getAlternatives().get(0);

				text = alternate.getTranscript();
			}

		}

		System.out.println("[DEBUG] " + text);

		return text;
	}
}
