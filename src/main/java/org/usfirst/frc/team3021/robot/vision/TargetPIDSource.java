package org.usfirst.frc.team3021.robot.vision;

import edu.wpi.first.wpilibj.PIDSourceType;

public class TargetPIDSource implements edu.wpi.first.wpilibj.PIDSource {

	private PIDSourceType pidSourceType;
	
	private TargetElement targetScope;
	private TargetElement targetLocator;

	private double offset;

	private double originalTargetPositionX;
	private double currentTargetPositionX;

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSourceType;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSourceType = pidSource;
	}

	public void setTargetScope(TargetElement targetScope) {
		this.targetScope = targetScope;
	}

	public void setTargetLocator(TargetElement targetLocator) {
		this.targetLocator = targetLocator;
	}

	public double getSetPoint() {
		// save the current target location x value so that 
		// relative comparison can be calculated as the robot turns to target
		// This will be a value from 0 to the max frame size
		originalTargetPositionX = Math.abs(targetLocator.getCenterPoint().x);
		
		currentTargetPositionX = originalTargetPositionX;
		
		offset = targetLocator.getCenterPoint().x - targetScope.getCenterPoint().x;
		
		return offset;
	}
	
	@Override
	public double pidGet() {
		if (targetLocator.getCenterPoint() != null) {
			currentTargetPositionX = targetLocator.getCenterPoint().x;
		}
				
		// compare the original target x position against the current target locator x position
		// as this determines how much the robot has turned
		return  (originalTargetPositionX - currentTargetPositionX);
	}
}
