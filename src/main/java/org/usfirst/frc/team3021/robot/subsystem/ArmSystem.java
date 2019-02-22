package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ArmCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ArmSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Arm.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;

	private static final String PREF_ELBOW_VOLTAGE = "Arm.elblow.motor.voltage";
	private static final double ELBOW_VOLTAGE_DEFAULT = 0.7;

	private double elbowVoltage = ELBOW_VOLTAGE_DEFAULT;

	private static final String PREF_WRIST_VOLTAGE = "Arm.wrist.motor.voltage";
	private static final double WRIST_VOLTAGE_DEFAULT = 0.7;

	private double wristVoltage = WRIST_VOLTAGE_DEFAULT;
	
	private static final double REVERSE_MULTIPLIER = -1.0;

	private WPI_TalonSRX elbowMotor;
	private WPI_TalonSRX wristMotor;
	
	public ArmSystem() {	
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		elbowVoltage = Preferences.getInstance().getDouble(PREF_ELBOW_VOLTAGE, ELBOW_VOLTAGE_DEFAULT);
		wristVoltage = Preferences.getInstance().getDouble(PREF_WRIST_VOLTAGE, WRIST_VOLTAGE_DEFAULT);

		if (isEnabled) {
			elbowMotor = new WPI_TalonSRX(41);
			wristMotor = new WPI_TalonSRX(42);
		}
	}

	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		// Control the arm motor
		if (mainController.isArmForward() || auxController.isArmForward()) {
			armForward();
		}
		else if (mainController.isArmBackward() || auxController.isArmBackward()) {
			armBackward();
		}
		else {
			stopArm();
		}

		// Control the wrist motor
		if (mainController.isWristBackward() || auxController.isWristBackward()) {
			wristForward();
		}
		else if (mainController.isWristBackward() || auxController.isWristBackward()) {
			wristBackward();
		}
		else {
			stopWrist();
		}
	}

	public void armForward() {
		if (!isEnabled) {
			return;
		}
		
		elbowMotor.set(elbowVoltage);
	}
	
	public void armBackward() {
		if (!isEnabled) {
			return;
		}
		
		elbowMotor.set(REVERSE_MULTIPLIER * elbowVoltage);
	}
	
	public void stopArm() {
		if (!isEnabled) {
			return;
		}
		
		elbowMotor.set(0);
	}

	public void wristForward() {
		if (!isEnabled) {
			return;
		}
		
		wristMotor.set(wristVoltage);
	}
	
	public void wristBackward() {
		if (!isEnabled) {
			return;
		}
		
		wristMotor.set(REVERSE_MULTIPLIER * wristVoltage);
	}
	
	public void stopWrist() {
		if (!isEnabled) {
			return;
		}
		
		wristMotor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ArmCommand());
	}	
}
