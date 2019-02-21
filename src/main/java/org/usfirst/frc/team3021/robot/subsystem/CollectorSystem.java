package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class CollectorSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Collector.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;

	private static final String PREF_VOLTAGE = "Collector.motor.voltage";
	private static final String PREF_OUTTAKE_VOLTAGE_LOW = "Collector.motor.voltage.outtake.low";
	private static final double VOLTAGE_DEFAULT = 0.65;
	private static final double PREF_OUTTAKE_VOLTAGE_LOW_DEFAULT = 0.325;

	private double voltage = VOLTAGE_DEFAULT;
	private double lowVoltage = 0.5;
	
	private static final double REVERSE_MULTIPLIER = -1.0;

	private WPI_TalonSRX right_motor;
	private WPI_TalonSRX left_motor;
	
	private Solenoid collector_deploy;
	private Solenoid collector_stow;
	
	boolean collecter_deployed = false;

	public CollectorSystem() {	
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT); //0.7
		lowVoltage = Preferences.getInstance().getDouble(PREF_OUTTAKE_VOLTAGE_LOW, PREF_OUTTAKE_VOLTAGE_LOW_DEFAULT); // 0.2

		if (isEnabled) {
			collector_deploy = new Solenoid(1);
			collector_stow = new Solenoid(2);
			
			right_motor = new WPI_TalonSRX(26);
			left_motor = new WPI_TalonSRX(27);
			left_motor.setInverted(true);
		}
	}

	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		if (auxController.isCollectorDeploying()) {
			deploy();
		}

		if (auxController.isCollectorStowing()) {
			stow();
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
		
		right_motor.set(voltage);
		left_motor.set(REVERSE_MULTIPLIER * voltage * 0.9);
	}
	
	public void deliver() {
		if (!isEnabled) {
			return;
		}
		
		if (auxController.isLowVoltageLaunch()) {
			right_motor.set(REVERSE_MULTIPLIER * lowVoltage);
			left_motor.set(lowVoltage);
		}
		else {
			right_motor.set(REVERSE_MULTIPLIER * voltage);
			left_motor.set(voltage);
		}
	}
	
	public void deliver(double speed) {
		if (!isEnabled) {
			return;
		}
		
		right_motor.set(REVERSE_MULTIPLIER * speed);
		left_motor.set(speed);
		
	}
	
	public void stop() {
		if (!isEnabled) {
			return;
		}
		
		right_motor.set(0);
		left_motor.set(0);
	}
	
	public void deploy() {
		if (!isEnabled) {
			return;
		}
		
		collector_deploy.set(true);
		collector_stow.set(false);	
		collecter_deployed = true;
	}
	
	public void stow() {
		if (!isEnabled) {
			return;
		}
		
		collector_deploy.set(false);
		collector_stow.set(true);	
		collecter_deployed = false;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CollectorCommand());
	}	
}
