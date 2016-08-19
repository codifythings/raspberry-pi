package com.codifythings.personalassistant;

import java.util.Iterator;
import java.util.List;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

public class CommandClassifier {
	public String classify(String command) {
		NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		service.setUsernameAndPassword(Constants.NATURAL_LANG_CLASSIFIER_USERNAME,
				Constants.NATURAL_LANG_CLASSIFIER_PASSWORD);

		String classificationStr = Constants.CLASSIFICATION_UNKNOWN;

		if (!(command == null || command.equals(""))) {
			try {
				// Classify command
				Classification classification = service.classify(Constants.NATURAL_LANG_CLASSIFIER, command).execute();
				List<ClassifiedClass> classifiedClasses = classification.getClasses();

				for (Iterator<ClassifiedClass> iterator = classifiedClasses.iterator(); iterator.hasNext();) {
					ClassifiedClass classifiedClass = iterator.next();
					Double classificationConfidence = classifiedClass.getConfidence();

					System.out.println("[DEBUG] Classification Confidence: " + classificationConfidence);

					if (classificationConfidence <= 0.5) {
						return classificationStr;
					}
				}

				classificationStr = classification.getTopClass();

				System.out.println("[DEBUG] Command Classification: " + classificationStr);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return classificationStr;
	}
}
