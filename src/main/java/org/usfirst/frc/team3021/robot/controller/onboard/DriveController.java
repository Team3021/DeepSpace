package org.usfirst.frc.team3021.robot.controller.onboard;

import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;
import org.usfirst.frc.team3021.robot.inputs.LeftRightDriveInput;
import org.usfirst.frc.team3021.robot.inputs.TankDriveInput;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3021.robot.configuration.Dashboard;

public class DriveController {
	
	private static final String PREF_DRIVE_WHEEL_SIZE = "DriveController.wheel.diameter";
	private static final double DRIVE_WHEEL_SIZE_DEFAULT = 8.0;

	// DRIVE SYSTEM
	private DifferentialDrive robotDrive;
	
	// TALON PORTS
	private static final int RIGHT_FRONT_PORT = 22;
	private static final int RIGHT_REAR_PORT = 25;
	private static final int LEFT_REAR_PORT = 23;
	private static final int LEFT_FRONT_PORT = 24;

	// TALONS
	private WPI_TalonSRX rightRearTalon;
	private WPI_TalonSRX rightFrontTalon;
	private WPI_TalonSRX leftRearTalon;
	private WPI_TalonSRX leftFrontTalon;
	
	// SPEED CONTROLLERS
	private SpeedControllerGroup leftSpeedController;
	private SpeedControllerGroup rightSpeedController;
	
	// ENCODER CHANNELS
	private static final int RIGHT_ENCODER_CHANNEL_A = 0;
	private static final int RIGHT_ENCODER_CHANNEL_B = 1;

	private static final int LEFT_ENCODER_CHANNEL_A = 2;
	private static final int LEFT_ENCODER_CHANNEL_B = 3;
	
	// ENCODERS
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	// DISTANCE
	private static final int PULSE_PER_ROTATION = 256;
	private static final double INCHES_PER_FOOT = 12;
	
	// RATE
	private double maxRateAchieved = 0.0;

	// CURRENT VALUES
	private double currentMoveValue = 0.0f;
	private double currentTurnValue = 0.0f;
	
	public DriveController() {
		// TALONS
		leftFrontTalon = new WPI_TalonSRX(LEFT_FRONT_PORT);
		leftRearTalon = new WPI_TalonSRX(LEFT_REAR_PORT);
		rightFrontTalon = new WPI_TalonSRX(RIGHT_FRONT_PORT);
		rightRearTalon = new WPI_TalonSRX(RIGHT_REAR_PORT);
		
//		rightRearTalon.configOpenloopRamp(0.35, 0)
		
		// DRIVE DECLARATION
		leftSpeedController = new SpeedControllerGroup(leftFrontTalon, leftRearTalon);
		rightSpeedController = new SpeedControllerGroup(rightFrontTalon, rightRearTalon);
		
		robotDrive = new DifferentialDrive(leftRearTalon, rightRearTalon);
		robotDrive.setExpiration(0.25);
		robotDrive.setSafetyEnabled(false);
		
		rightRearTalon.setSafetyEnabled(false);
		leftRearTalon.setSafetyEnabled(false);

		// Calculate encoder distance
		double wheelDiameter = Preferences.getInstance().getDouble(PREF_DRIVE_WHEEL_SIZE, DRIVE_WHEEL_SIZE_DEFAULT);
		
		final double wheelCircumerence = wheelDiameter * Math.PI;
		final double distancePerPulse = (wheelCircumerence / PULSE_PER_ROTATION) / INCHES_PER_FOOT;
		
		// ENCODERS
		leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B, false, Encoder.EncodingType.k4X);
		leftEncoder.setMinRate(10);
		leftEncoder.setDistancePerPulse(distancePerPulse);
		
		rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B, true, Encoder.EncodingType.k4X);
		rightEncoder.setMinRate(10);
		rightEncoder.setDistancePerPulse(distancePerPulse);
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
		
		Dashboard.putNumber("Drive : Motor Voltage : Left Front", leftFrontTalon.getMotorOutputVoltage());
		Dashboard.putNumber("Drive : Motor Voltage : Left Rear", leftRearTalon.getMotorOutputVoltage());
		Dashboard.putNumber("Drive : Motor Voltage : Right Front", rightFrontTalon.getMotorOutputVoltage());
		Dashboard.putNumber("Drive : Motor Voltage : Right Rear", rightRearTalon.getMotorOutputVoltage());
		
		return (leftRearTalon.getMotorOutputVoltage() + rightRearTalon.getMotorOutputVoltage()) / 2;
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
		Dashboard.putNumber("Drive : Encoder Speed Left", leftEncoder.getRate());
		Dashboard.putNumber("Drive : Encoder Speed Right", rightEncoder.getRate());
		Dashboard.putNumber("Drive : Encoder Pulses Left: ", leftEncoder.get());
		Dashboard.putNumber("Drive : Encoder Pulses Right: ", rightEncoder.get());
	}

	public void printEncoderDistance(double distance) {
		Dashboard.putNumber("Drive : Encoder Distance", distance);
	}

	public double getEncoderRate() {
		double rateLeftSide = leftEncoder.getRate();
		
		Dashboard.putNumber("Drive : Encoder Rate : Left", rateLeftSide);

		double rateRightSide = rightEncoder.getRate();
		
		Dashboard.putNumber("Drive : Encoder Rate : Right", rateRightSide);
		
		double rateAverage = (rateLeftSide + rateRightSide) / 2;
		
		Dashboard.putNumber("Drive : Encoder Rate", rateAverage);
		
		if (rateAverage > maxRateAchieved) {
			maxRateAchieved = rateAverage;
			
			Dashboard.putNumber("Drive : Encoder Rate Max", maxRateAchieved);
		}
		
		return rateAverage;
	}
	
	public double getEncoderDistance() {

		double distanceTraveledLeftSide = getDistanceTraveled(leftEncoder);
		
		Dashboard.putNumber("Drive : Encoder Distance : Left", distanceTraveledLeftSide);

		double distanceTraveledRightSide = getDistanceTraveled(rightEncoder);
		
		Dashboard.putNumber("Drive : Encoder Distance : Right", distanceTraveledRightSide);
		
		double distanceTraveledAverage = (distanceTraveledLeftSide + distanceTraveledRightSide) / 2;
		
		Dashboard.putNumber("Drive : Encoder Distance", distanceTraveledAverage);
		
		return distanceTraveledAverage;
	}

	public double getLeftEncoderDistance() {
		return getDistanceTraveled(leftEncoder);
	}
	
	public double getRightEncoderDistance() {
		return getDistanceTraveled(rightEncoder);
	}
	public void zeroDistance() {
		System.out.println("Zero encoders");
		
		leftEncoder.reset();
		rightEncoder.reset();
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
	
	public void stop() {
		currentMoveValue = 0.0;
		currentTurnValue = 0.0;
		
		robotDrive.arcadeDrive(currentMoveValue, currentTurnValue, false);
	}
}
