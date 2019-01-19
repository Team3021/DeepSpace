package org.usfirst.frc.team3021.robot.vision;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class TargetLocator extends TargetElement {
	
	private Mat blurredImage = new Mat();
	private Mat hsvImage = new Mat();
	private Mat maskImage = new Mat();
	private Mat contourFilter = new Mat();
	
	private Mat contouredImage = new Mat();

	private double hueStart = 30; // 80
	private double hueStop = 60; // 100

	private double saturationStart = 0; // 0
	private double saturationStop = 90; // 60

	private double valueStart = 245; // 255
	private double valueStop = 255; // 255
	
	private String valuesToPrint;
	
	private Scalar color = new Scalar(0,0,255);
	
	private Point centerPoint = null;

	public void draw(Mat frame) {
		// init
		buildContourMask(frame);
		
		List<MatOfPoint> contours = new ArrayList<>();
		List<Rect> rectangles = new ArrayList<>();

		// find contours
		Imgproc.findContours(contourFilter, contours, contouredImage, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

		// filter contours by area and then by width and height
		for (int i = 0; i < contours.size(); i++) {
			if (Imgproc.contourArea(contours.get(i)) > 200 ) {
				
				Rect rect = Imgproc.boundingRect(contours.get(i));
				
				if (isTargetStripe(rect)) {
					rectangles.add(rect);
				}
			}
		}
		
	    for (int i = 0; i < rectangles.size(); i++) {
	    	Rect rect = rectangles.get(i);
	    	
	        Imgproc.rectangle(frame, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), color, LINE_THICKNESS);
	    }
	    
	    if (rectangles.size() == 2) {
	    	Rect leftRect = rectangles.get(0);
	    	Rect rightRect = rectangles.get(1);
	        
	    	if (leftRect.x > rightRect.x) {
	        	Rect temp = rightRect;
	        	
	        	rightRect = leftRect;
	        	leftRect = temp;
	        }
	    	centerPoint = getCenterPoint(leftRect, rightRect);
	    	
	    	Imgproc.circle(frame, centerPoint, TARGET_RADIUS, color, LINE_THICKNESS);
	    } else {
	    	centerPoint = null;
	    }
	}

	private Mat buildContourMask(Mat frame) {
		// if the frame is not empty, process it
		if (frame.empty()) {
			return frame;
		}

		try {
			// remove some noise
			Imgproc.blur(frame, blurredImage, new Size(9, 9));

			// convert the frame to HSV
			Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

			// get thresholding values from the UI
			// remember: H ranges 0-180, S and V range 0-255
			Scalar minValues = new Scalar(hueStart, saturationStart, valueStart);
			Scalar maxValues = new Scalar(hueStop, saturationStop, valueStop);

			// show the current selected HSV range
			valuesToPrint = "Hue range: " + minValues.val[0] + "-" + maxValues.val[0]
					+ "\tSaturation range: " + minValues.val[1] + "-" + maxValues.val[1] + "\tValue range: "
					+ minValues.val[2] + "-" + maxValues.val[2];
			
			// threshold HSV image to select target
			Core.inRange(hsvImage, minValues, maxValues, maskImage);
			
			// blur the filtered image
			Imgproc.GaussianBlur(maskImage, contourFilter, new Size(9, 9), 0);
		}
		catch (Exception e) {
			// log the (full) error
			System.err.print("ERROR building contour mask");
			e.printStackTrace();
		}

		return contourFilter;
	}

	public void setHueStart(double hueStart) {
		this.hueStart = hueStart;
	}

	public void setHueStop(double hueStop) {
		this.hueStop = hueStop;
	}

	public void setSaturationStart(double saturationStart) {
		this.saturationStart = saturationStart;
	}

	public void setSaturationStop(double saturationStop) {
		this.saturationStop = saturationStop;
	}

	public void setValueStart(double valueStart) {
		this.valueStart = valueStart;
	}

	public void setValueStop(double valueStop) {
		this.valueStop = valueStop;
	}

	public Mat getHSVImage() {
		return hsvImage;
	}
	
	public Mat getMaskImage() {
		return maskImage;
	}

	public String toString() {
		return valuesToPrint;
	}
}