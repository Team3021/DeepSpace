package org.usfirst.frc.team3021.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TargetScope extends TargetElement {
	
	private static final Scalar color = new Scalar(255, 255, 255);

	private Rect getLeftTargetStripe() {
		Point leftTop = new Point(TARGET_CENTER_X - STRIPE_OFFSET - STRIPE_WIDTH, STRIPE_TOP);
		Point bottomRight = new Point(TARGET_CENTER_X - STRIPE_OFFSET, STRIPE_TOP + STRIPE_HEIGHT);
		
		Rect rect = new Rect(leftTop, bottomRight);
		
		return rect;
	}

	private Rect getRightTargetStripe() {
		Point leftTop = new Point(TARGET_CENTER_X + STRIPE_OFFSET, STRIPE_TOP);
		Point bottomRight = new Point(TARGET_CENTER_X + STRIPE_OFFSET + STRIPE_WIDTH, STRIPE_TOP + STRIPE_HEIGHT);
		
		Rect rect = new Rect(leftTop, bottomRight);
		
		return rect;
	}
	
	public void draw(Mat mat) {
		
		Rect leftRect = getLeftTargetStripe();
		
		Imgproc.rectangle(mat, leftRect.tl(), leftRect.br(), color, LINE_THICKNESS);
		
		Rect rightRect = getRightTargetStripe();
		
		Imgproc.rectangle(mat, rightRect.tl(), rightRect.br(), color, LINE_THICKNESS);
		
		Imgproc.circle(mat, TARGET_CENTER, TARGET_RADIUS, color, LINE_THICKNESS);
	}
}
