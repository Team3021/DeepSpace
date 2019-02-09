package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.Spark;

public class ElevatorSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Elevator.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;
	
	private static final String PREF_VOLTAGE = "Elevator.motor.voltage";
	private static final double VOLTAGE_DEFAULT = 1.0;
	
	private double voltage = VOLTAGE_DEFAULT;
	
	private static final double REVERSE_MULTIPLIER = -1.0;
	
	private Spark motor;
	
	public ElevatorSystem() {		
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT);

		motor = new Spark(0);
		motor.setInverted(true);
	}
	
	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		if (auxController.isElevatorExtending()) {
			extend();
		}
		else if (auxController.isElevatorContracting()) {
			contract();
		}
		else {
			stop();
		}
	}

	public void extend() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		motor.set(voltage);
	}
	
	public void contract() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		motor.set(REVERSE_MULTIPLIER * voltage);
	}
	
	public void stop() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		motor.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ElevatorCommand());
	}	
}
