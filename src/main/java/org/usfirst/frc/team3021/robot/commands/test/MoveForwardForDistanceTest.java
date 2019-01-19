package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;

public class MoveForwardForDistanceTest extends MoveForwardForDistance {
	
	public MoveForwardForDistanceTest() {
		super(0,0);
	}

	@Override
	protected void initialize() {
		this.desiredSpeed = DriveCommand.getAutonomousMoveSpeed();
		this.desiredDistance = DriveCommand.getAutonomousMoveDistance();
		
		super.initialize();
	}
}
