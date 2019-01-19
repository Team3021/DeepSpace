package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;

public class TurnRightEscalateInputTest extends DriveCommand {
	
	private double currentTurnValue;
	
	public TurnRightEscalateInputTest() {
		super();
	}
	
	protected void initialize() {
		System.out.println("Start of Test: Turning right at increasing rate");
		
		super.initialize();
		
		currentTurnValue = 0;
		
		System.out.println("Turn Rate, Left Encoder Distance, Right Encoder Distance, Moving, Rotation");
	}
	
	protected void execute() {
		ArcadeDriveInput driveInput = new ArcadeDriveInput(0, currentTurnValue);
		
		driveSystem.drive(driveInput);
		
		String logMessage = 
				currentTurnValue + ", " 
				+ driveSystem.getLeftEncoderDistance() + ", " 
				+ driveSystem.getRightEncoderDistance() + ", " 
				+ driveSystem.isGyroMoving() + ", " 
				+ driveSystem.getGyroRotation();
		
		System.out.println(logMessage);
		
		currentTurnValue = timeSinceInitialized() / 200;
	}
	
	protected boolean isFinished() {
		boolean result = false;
		
		// Wait for the max turn value to be reached
		if (currentTurnValue >= 1) {
			result = true;
		}
		
		return result;
	}
	
	protected void end() {
		System.out.println("End of Test: Turning right at increasing rate");
	}
}
