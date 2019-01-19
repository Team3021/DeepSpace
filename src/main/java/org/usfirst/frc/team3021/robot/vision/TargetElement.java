package org.usfirst.frc.team3021.robot.vision;

import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;

public abstract class TargetElement {
	
	protected final int LINE_THICKNESS = 2;
	
	protected final int TARGET_RADIUS = 7;
	
	protected final double STRIPE_ASPECT_RATIO = 2.0 / 5.0; // Width / Height of the target stripes
	protected final double STRIPE_ASPECT_RATIO_TOLERANCE = 0.25;

	protected final double STRIPE_TOP = 50;
	
	protected final double STRIPE_WIDTH_MIN = 15;
	protected final double STRIPE_HEIGHT_MIN = 50;
	
	protected final double STRIPE_WIDTH = 17;
	protected final double STRIPE_HEIGHT = 150;
	
	protected final double STRIPE_WIDTH_MAX = 23;
	protected final double STRIPE_HEIGHT_MAX = 150;
	
	protected final double STRIPE_OFFSET = 80;
	
	protected final Size STRIPE_SIZE = new Size(STRIPE_WIDTH, STRIPE_HEIGHT);
	
	// The video resolution
	public static final int FRAME_WIDTH = 320;
	public static final int FRAME_HEIGHT = 240;
	
	protected final Size FRAME_SIZE = new Size(FRAME_WIDTH, FRAME_HEIGHT);
	
	protected double TARGET_CENTER_X = FRAME_WIDTH / 2;

	protected Point TARGET_CENTER = new Point(TARGET_CENTER_X, STRIPE_TOP + (STRIPE_HEIGHT / 2));

	public Point getCenterPoint() {
		return TARGET_CENTER;
	}
	
	public Point getCenterPoint(Rect leftRect, Rect rightRect) {

		Point leftBoxCenterPoint = new Point(leftRect.x + (leftRect.width / 2), leftRect.y + (leftRect.height / 2));

		Point rightBoxCenterPoint = new Point(rightRect.x + (rightRect.width / 2), rightRect.y + (rightRect.height / 2));

		double centerX = leftBoxCenterPoint.x + ((rightBoxCenterPoint.x - leftBoxCenterPoint.x) / 2);
		double centerY = (leftBoxCenterPoint.y + rightBoxCenterPoint.y) / 2;

		return new Point(centerX, centerY);
	}
	
	public boolean isTargetStripe(Rect rect) {
		double width = rect.width;
		double height = rect.height;
		
		double rectangleAspectRatio = (width / height);
		
		double tolerance = STRIPE_ASPECT_RATIO * STRIPE_ASPECT_RATIO_TOLERANCE;
		
		double lowerRange = STRIPE_ASPECT_RATIO - tolerance;
		double upperRange = STRIPE_ASPECT_RATIO + tolerance;
		
//		System.out.println(lowerRange + " " + rectangleAspectRatio + " " + upperRange) ;
		
		if (lowerRange < rectangleAspectRatio && rectangleAspectRatio < upperRange) {
			return true;
		}
		
		return false;
	}
}
