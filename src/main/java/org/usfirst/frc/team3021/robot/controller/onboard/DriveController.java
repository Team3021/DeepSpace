package org.usfirst.frc.team3021.robot.controller.onboard;

import org.usfirst.frc.team3021.robot.configuration.Dashboard;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;
import org.usfirst.frc.team3021.robot.inputs.LeftRightDriveInput;
import org.usfirst.frc.team3021.robot.inputs.TankDriveInput;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveController {
	
	private static final String PREF_DRIVE_WHEEL_SIZE = "DriveController.wheel.diameter";
	private static final double DRIVE_WHEEL_SIZE_DEFAULT = 8.0;

	// DRIVE SYSTEM
	private DifferentialDrive robotDrive;
	
	// SPEED CONTROLLERS
	private SpeedControllerGroup leftSpeedController;
	private SpeedControllerGroup rightSpeedController;

	// GEAR SHIFTING
	private boolean isHighGear = false;
	
	private Solenoid gearShiftLow;
	private Solenoid gearShiftHigh;
	
	// DISTANCE
	private static final int PULSE_PER_ROTATION = 256;
	private static final double INCHES_PER_FOOT = 12;
	
	// CURRENT VALUES
	private double currentMoveValue = 0.0f;
	private double currentTurnValue = 0.0f;
	
	public DriveController() {
		// Right side of robot
		WPI_TalonSRX rightTalon = new WPI_TalonSRX(21);

		// TODO check if this needs to inverted
		rightTalon.setInverted(true);
		
		// Right Spark Max to follow the Right TalonSRX
		CANSparkMax rightSpark = new CANSparkMax(11, MotorType.kBrushless);
		rightSpark.follow(ExternalFollower.kFollowerPhoenix, 21);

		// Left side of robot
		WPI_TalonSRX leftTalon = new WPI_TalonSRX(22);
		
		CANSparkMax leftSpark = new CANSparkMax(23, MotorType.kBrushless);
		leftSpark.follow(ExternalFollower.kFollowerPhoenix, 22);
		
		// DRIVE DECLARATION
		leftSpeedController = new SpeedControllerGroup(leftTalon);
		rightSpeedController = new SpeedControllerGroup(rightTalon);

		robotDrive = new DifferentialDrive(leftSpeedController, rightSpeedController);

		robotDrive.setExpiration(0.25);
		robotDrive.setSafetyEnabled(false);

		// GEAR SHIFTER
		gearShiftLow = new Solenoid(4);
		gearShiftHigh = new Solenoid(5);

		// Calculate encoder distance
		double wheelDiameter = Preferences.getInstance().getDouble(PREF_DRIVE_WHEEL_SIZE, DRIVE_WHEEL_SIZE_DEFAULT);

		final double wheelCircumerence = wheelDiameter * Math.PI;
		final double distancePerPulse = (wheelCircumerence / PULSE_PER_ROTATION) / INCHES_PER_FOOT;

	}

	// ****************************************************************************
	// **********************         CURRENT VALUES         **********************
	// ****************************************************************************

	public double getMoveValue() {
		return currentMoveValue;
	}

	public double getTurnValue() {
		return currentTurnValue;
	}
	
	// ****************************************************************************
	// **********************            MOTORS              **********************
	// ****************************************************************************

	public double getMotorOutput() {
		
		return 0.00;
	}

	public double getLeftMotorInput() {
		return leftSpeedController.get();
	}

	public double getRightMotorInput() {
		return rightSpeedController.get();
	}

	// ****************************************************************************
	// **********************            ENCODERS            **********************
	// ****************************************************************************
	
	public void printEncoderValues() {
	}

	public void printEncoderDistance(double distance) {
		Dashboard.putNumber("Drive : Encoder Distance", distance);
	}
	
	public double getEncoderDistance() {

		double distanceTraveledLeftSide = getLeftEncoderDistance();
		
		Dashboard.putNumber("Drive : Encoder Distance : Left", distanceTraveledLeftSide);

		double distanceTraveledRightSide = getRightEncoderDistance();
		
		Dashboard.putNumber("Drive : Encoder Distance : Right", distanceTraveledRightSide);
		
		double distanceTraveledAverage = (distanceTraveledLeftSide + distanceTraveledRightSide) / 2;
		
		Dashboard.putNumber("Drive : Encoder Distance", distanceTraveledAverage);
		
		return distanceTraveledAverage;
	}

	public double getLeftEncoderDistance() {
		return 0;
	}
	
	public double getRightEncoderDistance() {
		return 0;
	}
	public void zeroDistance() {
		System.out.println("Zero encoders");
	}

	public double getDistanceTraveled(Encoder driveEncoder) {
		return driveEncoder.getDistance();
	}

	// ****************************************************************************
	// **********************            ACTIONS             **********************
	// ****************************************************************************

	public void drive(DriveInput input) {
		if (input instanceof ArcadeDriveInput) {
			ArcadeDriveInput arcadeInput = (ArcadeDriveInput) input;
			
			currentMoveValue = arcadeInput.getMoveValue();
			currentTurnValue = arcadeInput.getTurnValue();
			
			robotDrive.curvatureDrive(currentMoveValue, currentTurnValue, true);
		}
		else if (input instanceof LeftRightDriveInput) {
			LeftRightDriveInput voltageInput = (LeftRightDriveInput) input;
			
			leftSpeedController.set(voltageInput.getLeftInput());
			rightSpeedController.set(voltageInput.getRightInput());
		}
		else if (input instanceof TankDriveInput) {
			TankDriveInput tankInput = (TankDriveInput) input;
			
			robotDrive.tankDrive(tankInput.getLeftInput(), tankInput.getRightInput());
		}
		
	}

	public void changeGear() {
		if (!isHighGear) {
			isHighGear = true;
			
			gearShiftLow.set(false);
			gearShiftHigh.set(true);
		} else {
			isHighGear = false;
			
			gearShiftLow.set(true);
			gearShiftHigh.set(false);
		}
	}
	
	public void stop() {
		currentMoveValue = 0.0;
		currentTurnValue = 0.0;
		
		robotDrive.arcadeDrive(currentMoveValue, currentTurnValue, false);
	}
}
