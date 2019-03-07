package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class ElevatorSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Elevator.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;
	
	private static Compressor compressor;
	
	private Solenoid topSolenoid;
	private Solenoid bottomSolenoid;
	
	private boolean isTopSolenoidExtended;
	private boolean isBottomSolenoidExtended;
	
	public ElevatorSystem() {		
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		
		isTopSolenoidExtended = false;
		isBottomSolenoidExtended = false;

		if (isEnabled) {
			compressor = new Compressor(0);
			compressor.setClosedLoopControl(true);

			topSolenoid = new Solenoid(1);
			bottomSolenoid = new Solenoid(2);
		}

	}
	
	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == false && isBottomSolenoidExtended == false) {
			extendBottom();
			
			isBottomSolenoidExtended = true;
		}
		else if (auxController.isAuxStickDown() == true && auxController.isAuxStickUp() == false  && isBottomSolenoidExtended == true) {
			contractBottom();
			
			isBottomSolenoidExtended = false;
		}
		
		if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == true && isTopSolenoidExtended == false) {
			extendTop();
			
			isTopSolenoidExtended = true;
		}
		else if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == false && isTopSolenoidExtended == true) {
			contractTop();
			
			isTopSolenoidExtended = false;
		}
	}

	public void extendBottom() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		bottomSolenoid.set(true);
	}
	
	public void contractBottom() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		bottomSolenoid.set(false);
	}

	public void extendTop() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		topSolenoid.set(true);
	}
	
	public void contractTop() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		topSolenoid.set(false);
	}
	
	public void stop() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ElevatorCommand());
	}	
}
