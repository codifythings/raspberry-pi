package com.codifythings.personalassistant;

public interface Constants {

	// Image Location & Types
	public static String IMAGE_FOLDER = "./image/";
	public static String IMAGE_NAME = "query.png";

	// Audio Location & Types
	public static String AUDIO_FOLDER = "./audio/";
	public static String AUDIO_TYPE_GREET = "greet.wav";
	public static String AUDIO_TYPE_NOT_RECOGNIZED = "not-recognized.wav";
	public static String AUDIO_TYPE_RESPONSE = "response.wav";

	// Watson API Credentials
	public static String VISUAL_RECOGNITION_API_KEY = “YOUR_USERNAME”;
	public static String TEXT_TO_SPEECH_USERNAME = “YOUR_PASSWORD”;
	public static String TEXT_TO_SPEECH_PASSWORD = “YOUR_API_KEY”;

	// Response
	public static String RESPONSE_SCORE = "There is a ";
	public static String RESPONSE_CLASS = " percent chance that this is ";
	public static String RESPONSE_UNKNOWN = "Unknown";
}
