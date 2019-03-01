package org.usfirst.frc.team3021.robot.controller.onboard;

import org.usfirst.frc.team3021.robot.configuration.Dashboard;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;
import org.usfirst.frc.team3021.robot.inputs.LeftRightDriveInput;
import org.usfirst.frc.team3021.robot.inputs.TankDriveInput;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
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
	
	// TALON PORTS
	private static final int RIGHT_TOP_PORT = 11;
	private static final int RIGHT_FRONT_PORT = 12;
	private static final int RIGHT_REAR_PORT = 13;
	private static final int LEFT_TOP_PORT = 22;
	private static final int LEFT_REAR_PORT = 21;
	private static final int LEFT_FRONT_PORT = 23;

	// SPARK MAX CONTROLLERS
	private CANSparkMax rightTopSpark;
	private CANSparkMax rightRearSpark;
	private CANSparkMax rightFrontSpark;
	private CANSparkMax leftTopSpark;
	private CANSparkMax leftRearSpark;
	private CANSparkMax leftFrontSpark;
	
	// SPEED CONTROLLERS
	private SpeedControllerGroup leftSpeedController;
	private SpeedControllerGroup rightSpeedController;

	// GEAR SHIFTING
	private boolean isHighGear = false;
	private Solenoid gearShiftHigh;
	
	// ENCODERS
	private CANEncoder leftEncoder;
	private CANEncoder rightEncoder;
	
	// DISTANCE
	private static final int PULSE_PER_ROTATION = 256;
	private static final double INCHES_PER_FOOT = 12;
	
	// CURRENT VALUES
	private double currentMoveValue = 0.0f;
	private double currentTurnValue = 0.0f;
	
	public DriveController() {
		// SPARKS
		leftTopSpark = new CANSparkMax(LEFT_TOP_PORT, MotorType.kBrushless);
		leftFrontSpark = new CANSparkMax(LEFT_FRONT_PORT, MotorType.kBrushless);
		leftRearSpark = new CANSparkMax(LEFT_REAR_PORT, MotorType.kBrushless);
		rightTopSpark = new CANSparkMax(RIGHT_TOP_PORT, MotorType.kBrushless);
		rightFrontSpark = new CANSparkMax(RIGHT_FRONT_PORT, MotorType.kBrushless);
		rightRearSpark = new CANSparkMax(RIGHT_REAR_PORT, MotorType.kBrushless);

		// Invert top motors in the gear boxes
		leftTopSpark.setInverted(true);
		rightTopSpark.setInverted(true);
		
		// DRIVE DECLARATION
		leftSpeedController = new SpeedControllerGroup(leftFrontSpark, leftRearSpark, leftTopSpark);
		rightSpeedController = new SpeedControllerGroup(rightFrontSpark, rightRearSpark, rightTopSpark);
		
		robotDrive = new DifferentialDrive(leftRearSpark, rightRearSpark);
		robotDrive.setExpiration(0.25);
		robotDrive.setSafetyEnabled(false);
		
		// GEAR SHIFTER
		gearShiftHigh = new Solenoid(0);
		
		// Calculate encoder distance
		double wheelDiameter = Preferences.getInstance().getDouble(PREF_DRIVE_WHEEL_SIZE, DRIVE_WHEEL_SIZE_DEFAULT);
		
		final double wheelCircumerence = wheelDiameter * Math.PI;
		final double distancePerPulse = (wheelCircumerence / PULSE_PER_ROTATION) / INCHES_PER_FOOT;
		
		// ENCODERS
		leftEncoder = new CANEncoder(leftTopSpark);
		leftEncoder.setPositionConversionFactor(distancePerPulse);
		
		rightEncoder = new CANEncoder(rightTopSpark);
		rightEncoder.setPositionConversionFactor(distancePerPulse);
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
		
		Dashboard.putNumber("Drive : Motor Voltage : Left Top", leftTopSpark.getAppliedOutput());
		Dashboard.putNumber("Drive : Motor Voltage : Left Front", leftFrontSpark.getAppliedOutput());
		Dashboard.putNumber("Drive : Motor Voltage : Left Rear", leftRearSpark.getAppliedOutput());
		Dashboard.putNumber("Drive : Motor Voltage : Right Top", rightTopSpark.getAppliedOutput());
		Dashboard.putNumber("Drive : Motor Voltage : Right Front", rightFrontSpark.getAppliedOutput());
		Dashboard.putNumber("Drive : Motor Voltage : Right Rear", rightRearSpark.getAppliedOutput());
		
		return (leftRearSpark.getAppliedOutput() + rightRearSpark.getAppliedOutput()) / 2;
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
		Dashboard.putNumber("Drive : Encoder Pulses Left: ", leftEncoder.getPosition());
		Dashboard.putNumber("Drive : Encoder Pulses Right: ", rightEncoder.getPosition());
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
		return leftEncoder.getPosition();
	}
	
	public double getRightEncoderDistance() {
		return rightEncoder.getPosition();
	}
	public void zeroDistance() {
		System.out.println("Zero encoders");
		
		leftEncoder.setPosition(0);
		rightEncoder.setPosition(0);
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
			
			gearShiftHigh.set(true);
		} else {
			isHighGear = false;
			
			gearShiftHigh.set(false);
		}
	}
	
	public void stop() {
		currentMoveValue = 0.0;
		currentTurnValue = 0.0;
		
		robotDrive.arcadeDrive(currentMoveValue, currentTurnValue, false);
	}
}
