package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.subsystem.DriveSystem;

import org.usfirst.frc.team3021.robot.configuration.Preferences;

public abstract class DriveCommand extends Command {
	
	private static final String PREF_AUTONOMOUS_MOVE_SPEED = "Drive.autonomouns.move.speed";
	private static final String PREF_AUTONOMOUS_MOVE_TIME = "Drive.autonomouns.move.time";
	private static final String PREF_AUTONOMOUS_MOVE_DISTANCE = "Drive.autonomouns.move.distance";
	
	private static final double AUTONOMOUS_MOVE_SPEED_DEFUALT = 0.3;
	private static final double AUTONOMOUS_MOVE_TIME_DEFUALT = 3;
	private static final double AUTONOMOUS_MOVE_DISTANCE_DEFUALT = 3;
	
	protected static final String FORWARD = "forward";
	protected static final String BACKWARD = "backward";
	
	protected DriveSystem driveSystem = null;
	
	protected boolean hasStarted = false;
	
	protected boolean hasMoved = false;
	
	public DriveCommand() {
		super();
		
		this.driveSystem = QBert.getDriveSystem();
		
		requires(this.driveSystem);
		
		
	}

	@Override
	protected void initialize() {
		hasStarted = false;

		hasMoved = false;
	}
	
	public static double getAutonomousMoveSpeed() {
		return Preferences.getInstance().getDouble(PREF_AUTONOMOUS_MOVE_SPEED, AUTONOMOUS_MOVE_SPEED_DEFUALT);
	}

	public static double getAutonomousMoveTime() {
		return Preferences.getInstance().getDouble(PREF_AUTONOMOUS_MOVE_TIME, AUTONOMOUS_MOVE_TIME_DEFUALT);
	}

	public static double getAutonomousMoveDistance() {
		return Preferences.getInstance().getDouble(PREF_AUTONOMOUS_MOVE_DISTANCE, AUTONOMOUS_MOVE_DISTANCE_DEFUALT);
	}

	protected boolean isMoving() {
		// Assume there is motion 
		// (not necessarily the case, but this is required for proper function of turnToAngle).
		boolean isMoving = true;
		
		if (!hasStarted) {
			System.out.println("started moving at time: " + timeSinceInitialized());
			
			hasStarted = true;
		}
		
		if (timeSinceInitialized() < 0.5) {
			return true;
		}
		
		// Checks to see if the robot has started moving.
		if (driveSystem.isGyroMoving() && hasMoved == false) {
			isMoving = true;
			hasMoved = true;
			System.out.println("started checking movement at time: " + timeSinceInitialized());
		}
		// False will not be returned unless the robot has already started moving.
		else if (hasMoved == true) {
			if (!driveSystem.isGyroMoving()) {
				System.out.println("gyro status caused command to stop moving at time: " + timeSinceInitialized());
				
				isMoving = false;
				hasMoved = false;
			}
			
			if (driveSystem.getMotorOutput() < 0.05) {
				System.out.println("motor status of " + driveSystem.getMotorOutput() + " caused command to stop moving at time: " + timeSinceInitialized());
				
				isMoving = false;
				hasMoved = false;
			}
		}
		
		return isMoving;
	}

}