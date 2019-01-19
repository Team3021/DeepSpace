package org.usfirst.frc.team3021.robot.commands.driving;

public class MoveBackwardForDistance extends MoveForwardForDistance {
	
	public MoveBackwardForDistance(double speed, double distance) {
		super(speed, distance);
		
		this.direction = BACKWARD;
	}	
}
