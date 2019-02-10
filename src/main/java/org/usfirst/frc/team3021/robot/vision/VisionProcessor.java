package org.usfirst.frc.team3021.robot.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.usfirst.frc.team3021.robot.device.RunnableDevice;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

public class VisionProcessor extends RunnableDevice {
	
	private final String PREF_TARGET_SCOPE_ENABLED = "VisionProcessor.target.scope.enabled";
	private final String PREF_TARGET_LOCATOR_ENABLED = "VisionProcessor.target.locator.enabled";
	
	private TargetScope targetScope;
	private TargetLocator targetLocator;

	private CvSink input; // Video sink that will receive images from the camera source

	private CvSource output;
	
	private Mat mat;
	private boolean targetScopeEnabled = false;
	private boolean targetLocatorEnabled = false;

	public VisionProcessor(VideoSource initialCam) {
		// Setup a CvSink. This will capture Mats from an external source
		input = new CvSink("Camera Sink");
		input.setSource(initialCam);
		
		// Setup a CvSource. This will send images back to an external sink
		output = CameraServer.getInstance().putVideo("3021 : Vision", TargetElement.FRAME_WIDTH, TargetElement.FRAME_HEIGHT);

		// Mats are very memory expensive.
		mat = new Mat();
		
		targetLocator = new TargetLocator();
		targetScope = new TargetScope();
	}

	public void setInput(VideoSource source) {
		input.setSource(source);	
	}
	
	public VideoSource getOutput() {
		return output;
	}

	public TargetElement getTargetScope() {
		return targetScope;
	}

	public TargetElement getTargetLocator() {
		return targetLocator;
	}
	
	public void setTargetScopeEnabled(boolean newValue) {
		this.targetScopeEnabled = newValue;
	}

	public void setTargetLocatorEnabled(boolean newValue) {
		this.targetLocatorEnabled = newValue;
	}
	
	private boolean isTargetScopeEnabled() {
		return Preferences.getInstance().getBoolean(PREF_TARGET_SCOPE_ENABLED, false) || targetScopeEnabled;
	}
	
	private boolean isTargetLocatorEnabled() {
		return Preferences.getInstance().getBoolean(PREF_TARGET_LOCATOR_ENABLED, false) || targetLocatorEnabled;
	}
	
	@Override
	protected void runPeriodic() {

		// Grab a frame from the source camera
		// If there is an error notify the output
		if (input.grabFrame(mat) == 0) {
			// Send the output the error.
			output.notifyError(input.getError());

			// skip the rest of the current iteration
			System.out.println("Unable to grab frame.");
			delay(50);
			return;
		}

//		System.out.println("mat height: " + mat.height());
//		System.out.println("mat width: " + mat.width());
		
		// Draw a target location on the image
		if (isTargetLocatorEnabled()) {
			targetLocator.draw(mat);
		}

		// Draw a target scope on the image
		if (isTargetScopeEnabled()) {
			targetScope.draw(mat);
		}

		// Give the frame to the output
		Core.rotate(mat, mat, Core.ROTATE_90_COUNTERCLOCKWISE);
		output.putFrame(mat);

		delay(100);
	}
}
