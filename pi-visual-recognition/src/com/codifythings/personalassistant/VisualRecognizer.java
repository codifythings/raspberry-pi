package com.codifythings.personalassistant;

import java.io.File;
import java.util.Iterator;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ImageClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier.VisualClass;

public class VisualRecognizer {

	public String recognizeImage() {
		System.out.println("[DEBUG] Classifying Image");

		// Initialize Visual Recognition
		VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
		service.setApiKey(Constants.VISUAL_RECOGNITION_API_KEY);

		// Read & Classify Image
		ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
				.images(new File(Constants.IMAGE_FOLDER + Constants.IMAGE_NAME)).build();
		VisualClassification result = service.classify(options).execute();

		// Prepare Response String
		String response = Constants.RESPONSE_UNKNOWN;

		// Iterate All Image Classifications
		for (Iterator<ImageClassification> iterator = result.getImages().iterator(); iterator.hasNext();) {

			ImageClassification imageClassification = iterator.next();

			// Iterate All Visual Classifiers
			for (Iterator<VisualClassifier> visualClassifiersIterator = imageClassification.getClassifiers()
					.iterator(); visualClassifiersIterator.hasNext();) {

				VisualClassifier visualClassifier = visualClassifiersIterator.next();

				// Iterate First Visual Class Only
				for (Iterator<VisualClass> visualClassIterator = visualClassifier.getClasses()
						.iterator(); visualClassIterator.hasNext();) {

					VisualClass visualClass = visualClassIterator.next();

					String className = visualClass.getName();
					Double classScore = Math.ceil(visualClass.getScore() * 100);

					response = Constants.RESPONSE_SCORE + classScore + Constants.RESPONSE_CLASS + className;

					break;
				}
			}
		}

		return response;
	}
}
