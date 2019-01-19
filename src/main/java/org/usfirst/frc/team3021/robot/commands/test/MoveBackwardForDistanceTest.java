package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveBackwardForDistance;

public class MoveBackwardForDistanceTest extends MoveBackwardForDistance {
	
	public MoveBackwardForDistanceTest() {
		super(0,0);
	}

	@Override
	protected void initialize() {
		this.desiredSpeed = DriveCommand.getAutonomousMoveSpeed();
		this.desiredDistance = DriveCommand.getAutonomousMoveDistance();
		
		super.initialize();
	}
}
