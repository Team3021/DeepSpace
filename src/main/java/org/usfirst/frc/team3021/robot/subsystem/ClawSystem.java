package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ClawCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class ClawSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Claw.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;

	private static final String PREF_VOLTAGE = "Claw.motor.voltage";
	private static final double VOLTAGE_DEFAULT = 0.7;

	private static final String PREF_OUTTAKE_VOLTAGE_LOW = "Claw.motor.voltage.outtake.low";
	private static final double PREF_OUTTAKE_VOLTAGE_LOW_DEFAULT = 0.3;

	private double voltage = VOLTAGE_DEFAULT;
	private double lowVoltage = 0.5;
	
	private static final double REVERSE_MULTIPLIER = -1.0;

	private WPI_TalonSRX motor;
	
	private Solenoid solenoid;
	
	public ClawSystem() {	
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT);
		lowVoltage = Preferences.getInstance().getDouble(PREF_OUTTAKE_VOLTAGE_LOW, PREF_OUTTAKE_VOLTAGE_LOW_DEFAULT);

		if (isEnabled) {
			solenoid = new Solenoid(3);
			
			motor = new WPI_TalonSRX(30);
		}
	}

	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		// control the solenoid
		// need to quickly retract after deploying
		if (mainController.isClawDeploying() || auxController.isClawDeploying()) {
			// extend the solenoid
			deploy();
			
			// delay
			Timer.delay(0.4);
			
			// contract the solenoid
			contract();
		}

		// Control the motor
		if (mainController.isCollecting() || auxController.isCollecting()) {
			collect();
		}
		else if (mainController.isLaunching() || auxController.isLaunching()) {
			deliver();
		}

		else {
			stop();
		}
	}

	public void collect() {
		if (!isEnabled) {
			return;
		}
		
		motor.set(voltage);
	}
	
	public void deliver() {
		if (!isEnabled) {
			return;
		}
		
		if (auxController.isLowVoltageLaunch()) {
			motor.set(REVERSE_MULTIPLIER * lowVoltage);
		}
		else {
			motor.set(REVERSE_MULTIPLIER * voltage);
		}
	}
	
	public void deliver(double speed) {
		if (!isEnabled) {
			return;
		}
		
		motor.set(REVERSE_MULTIPLIER * speed);
	}
	
	public void stop() {
		if (!isEnabled) {
			return;
		}
		
		motor.set(0);
	}
	
	public void deploy() {
		if (!isEnabled) {
			return;
		}
		
		solenoid.set(true);
	}
	
	public void contract() {
		if (!isEnabled) {
			return;
		}
		
		solenoid.set(false);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ClawCommand());
	}	
}
