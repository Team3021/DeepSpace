package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;

public class MoveForwardEscalateInputTest extends DriveCommand {
	
	private double currentMoveValue = 0;
	private double timeOfTopSpeedReached = -1;
	
	public MoveForwardEscalateInputTest() {
		super();
	}
	
	protected void initialize() {
		System.out.println("Start of test: Driving forward at increasing rate");
		
		super.initialize();
		
		currentMoveValue = 0;
		
		System.out.println("Move Vaue, Left Encoder Distance, Right Encoder Distance, Moving");
	}
	
	protected void execute() {
		ArcadeDriveInput driveInput = new ArcadeDriveInput(currentMoveValue, 0);
		
		driveSystem.drive(driveInput);
		
		String logMessage = 
				currentMoveValue + ", " 
				+ driveSystem.getLeftEncoderDistance() + ", " 
				+ driveSystem.getRightEncoderDistance() + ", " 
				+ driveSystem.isGyroMoving();
		
		System.out.println(logMessage);
		
		currentMoveValue = timeSinceInitialized() / 200;
		
		if (currentMoveValue >= 1) {
			timeOfTopSpeedReached = timeSinceInitialized();
		}
	}
	
	protected boolean isFinished() {
		boolean result = false;
		
		// Wait for top speed to be achieved
		if (timeOfTopSpeedReached < 0) {
			return result;
		}
		
		// wait for top speed to be maintained for 5 seconds
		if (timeOfTopSpeedReached + 5.0 > timeSinceInitialized()) {
			result = true;
		}
		
		return result;
	}
	
	protected void end() {
		System.out.println("End of Experiment 1");
	}
}
