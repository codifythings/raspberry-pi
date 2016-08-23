package com.codifythings.personalassistant;

import java.io.File;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.AWB;
import com.hopding.jrpicam.enums.DRC;
import com.hopding.jrpicam.enums.Encoding;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

public class ImageCapturer {

	public void captureImage() throws Exception {

		System.out.println("[DEBUG] Capturing Image");

		// Attempt to create an instance of RPiCamera, will fail
		// if raspistill is not properly installed
		RPiCamera piCamera = new RPiCamera(Constants.IMAGE_FOLDER);
		
		//Take a still image and save it
		if (piCamera != null) {
			piCamera.setAWB(AWB.AUTO); //Change Automatic White Balance setting to automatic 
			piCamera.setDRC(DRC.OFF); //Turn off Dynamic Range Compression
			piCamera.setContrast(100); //Set maximum contrast
			piCamera.setSharpness(100); //Set maximum sharpness
			piCamera.setQuality(100); //Set maximum quality
			piCamera.setTimeout(1000); //Wait 1 second to take the image
			piCamera.turnOnPreview(); //Turn on image preview
			piCamera.setEncoding(Encoding.PNG); //Change encoding of images to PNG
			
			//Take a 650x650 still image and save it as "/image/query.png"
			File image = piCamera.takeStill(Constants.IMAGE_NAME, 650, 650);
		}		
	}
}