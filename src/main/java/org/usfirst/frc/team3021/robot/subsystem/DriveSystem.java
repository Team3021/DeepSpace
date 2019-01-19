package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.DriveWithJoystick;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle45;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle90;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle45;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle90;
import org.usfirst.frc.team3021.robot.commands.driving.TurnToAngle180;
import org.usfirst.frc.team3021.robot.controller.onboard.DriveController;
import org.usfirst.frc.team3021.robot.controller.onboard.GyroController;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;

import edu.wpi.first.wpilibj.command.Scheduler;

public class DriveSystem extends Subsystem {

	private DriveController driveController;

	private GyroController gyroController;

	private DriveCommand defaultCommand;

	private DriveCommand autonomousCommand;
	
	private boolean isPrintingData = false;
	private boolean printButtonPressed = false;

	public DriveSystem() {
		driveController = new DriveController();

		gyroController = new GyroController(this);
	}

	@Override
	protected void initDefaultCommand() {
		defaultCommand = new DriveWithJoystick(mainController);

		setDefaultCommand(defaultCommand);
	}

	// ****************************************************************************
	// **********************              DATA              **********************
	// ****************************************************************************

	public double getDistance() {
		return driveController.getEncoderDistance();
	}

	public double getLeftEncoderDistance() {
		return driveController.getLeftEncoderDistance();
	}

	public double getRightEncoderDistance() {
		return driveController.getRightEncoderDistance();
	}

	public double getMotorOutput() {
		return Math.abs(driveController.getMotorOutput());
	}

	public boolean isPrintingData() {
		return isPrintingData;
	}

	public void toggleDataPrinting() {
		// reverse the printing of data
		isPrintingData = !isPrintingData;
	}

	public void printHeaderData() {
		if (!isPrintingData()) {
			return;
		}
		
		System.out.println("MoveValue, TurnValue, Left Motor Voltage, Right Motor Voltage, Gyro Angle");
	}
	
	public void printData() {
		if (!isPrintingData()) {
			return;
		}
		
		String logMessage = driveController.getMoveValue() + ", " 
				+ driveController.getTurnValue() + ", " 
				+ driveController.getLeftMotorInput() + ", " 
				+ driveController.getRightMotorInput() + ", " 
				+ gyroController.getGyroRotation();

		System.out.println(logMessage);
	}

	// ****************************************************************************
	// **********************        DRIVE CONTROLLER        **********************
	// ****************************************************************************

	public void zeroEncoders() {
		driveController.zeroDistance();
	}

	public void drive(DriveInput input) {
		driveController.drive(input);
	}

	public void stop() {
		driveController.stop();
	}

	// ****************************************************************************
	// **********************         GYRO CONTROLLER        **********************
	// ****************************************************************************

	public void zeroGyro() {
		gyroController.zeroGyro();
	}

	public void enableGyro() {
		gyroController.enable();
	}

	public void resetGyro() {
		gyroController.reset();
	}

	public void setGyroDesiredAngle(double angle) {
		gyroController.setDesiredAngle(angle);
	}

	public double getGyroRotation() {
		return gyroController.getGyroRotation();
	}

	public boolean isGyroMoving() {
		return gyroController.isMoving();
	}

	public boolean isGyroOnTarget() {
		return gyroController.isOnTarget();
	}

	// ****************************************************************************
	// **********************             TELEOP             **********************
	// ****************************************************************************

	@Override
	public void teleopPeriodic() {

		gyroController.getGyroRotation();

		driveController.getEncoderDistance();
		driveController.getEncoderRate();

		driveController.getMotorOutput();

		if (mainController.isStoppingCommands() || auxController.isStoppingCommands()) {
			Scheduler.getInstance().removeAll();
		}

		if (mainController.isZeroGyro() || auxController.isZeroGyro()) {
			zeroGyro();
		}

		if (mainController.isZeroEncoders() || auxController.isZeroEncoders()) {
			zeroEncoders();
		}
		
		//Checks to see if print button is pressed, but doesn't start printing yet
		if (mainController.isCollectingData() && !printButtonPressed) {
			printButtonPressed = true;
		}
		
		// Starts printing data as soon as the activation button is released
		if (!mainController.isCollectingData() && printButtonPressed && !isPrintingData) {
			isPrintingData = true;
			printButtonPressed = false;
		}
	
		//Works the same as the if statement above this one, but turns printing off instead
		if (!mainController.isCollectingData() && printButtonPressed && isPrintingData) {
			isPrintingData = false;
			printButtonPressed = false;
		}

		// Lets the current autonomous command continue to run.
		if (autonomousCommand != null && autonomousCommand.isRunning()) {
			return;
		}

		// Clears the current autonomous command if finished.
		else if (autonomousCommand != null && !autonomousCommand.isRunning()) {
			autonomousCommand = null;
		}

		if (mainController.isRotatingToNinety()) {
			autonomousCommand = new TurnRightToAngle90();
		}
		else if (mainController.isRotatingToNegativeNinety()) {
			autonomousCommand = new TurnLeftToAngle90();
		}
		else if (mainController.isRotatingToOneHundredEighty()) {
			autonomousCommand = new TurnToAngle180();
		}
		else if (mainController.isRotatingRight45()) {
			autonomousCommand = new TurnRightToAngle45();
		}
		else if (mainController.isRotatingLeft45()) {
			autonomousCommand = new TurnLeftToAngle45();
		}

		// Updates the scheduler to the selected autonomous command. 
		// If none is chosen, the scheduler runs the default command, driving with the joystick.
		if (autonomousCommand != null) {
			Scheduler.getInstance().removeAll();
			Scheduler.getInstance().add(autonomousCommand);
			return;
		}
		
		// Check to determine what command is executing
		if (getCurrentCommandName().isEmpty()) {
			// No commands running so send signal to drive controller to stop the motors
			stop();
		}
	}

}
