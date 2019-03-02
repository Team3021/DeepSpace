package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ArmCommand;
import org.usfirst.frc.team3021.robot.configuration.Dashboard;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

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
	
	private AnalogPotentiometer elbowPosition;
	private AnalogPotentiometer wristPosition;
	
	
	public ArmSystem() {	
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		elbowVoltage = Preferences.getInstance().getDouble(PREF_ELBOW_VOLTAGE, ELBOW_VOLTAGE_DEFAULT);
		wristVoltage = Preferences.getInstance().getDouble(PREF_WRIST_VOLTAGE, WRIST_VOLTAGE_DEFAULT);

		if (isEnabled) {
			elbowMotor = new WPI_TalonSRX(42);
			wristMotor = new WPI_TalonSRX(41);
			
			// Enable  the brake mode for neutral
			elbowMotor.setNeutralMode(NeutralMode.Brake);
			wristMotor.setNeutralMode(NeutralMode.Brake);
			
			elbowPosition = new AnalogPotentiometer(0, 360, 180);  // TODO determine the 0 point offset of the potentiometer
			wristPosition = new AnalogPotentiometer(1, 360, 180);  // TODO determine the 0 point offset of the potentiometer
		}
	}

	
	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		// display the position values
		printArmPositions();
		
		boolean isMovingWrist = secondaryController.isMovingWrist();

		System.out.println("isMovingWrist" + isMovingWrist);
		
		// Control the arm motor
		if (isMovingWrist) {
			wristVoltage = secondaryController.getMoveValue();
			wristMotor.set(wristVoltage);
			
			elbowMotor.set(0);
		}
		else {
			elbowVoltage = secondaryController.getMoveValue();
			elbowMotor.set(elbowVoltage);
			
			wristMotor.set(0);
		}
	}
	
	public void printArmPositions() {
		Dashboard.putNumber("Arm : Elbow Position: ", elbowPosition.get());
		Dashboard.putNumber("Arm : Wrist Position: ", wristPosition.get());
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
