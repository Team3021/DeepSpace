package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;
import org.usfirst.frc.team3021.robot.configuration.Dashboard;
import org.usfirst.frc.team3021.robot.configuration.Preferences;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class ElevatorSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Elevator.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;
	
	private static Compressor compressor;
	
	private Solenoid topSolenoidDown;
	private Solenoid topSolenoidUp;
	
	private Solenoid bottomSolenoidDown;
	private Solenoid bottomSolenoidUp;
	
	private boolean isTopSolenoidExtended;
	private boolean isBottomSolenoidExtended;
	
	public ElevatorSystem() {		
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		
		isTopSolenoidExtended = false;
		isBottomSolenoidExtended = false;

		if (isEnabled) {
			compressor = new Compressor(0);
			compressor.setClosedLoopControl(true);

			topSolenoidDown = new Solenoid(0);
			topSolenoidUp = new Solenoid(1);
			
			bottomSolenoidDown = new Solenoid(2);
			bottomSolenoidUp = new Solenoid(3);
		}

	}
	
	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		// three way switch is in the bottom position
		if (auxController.isAuxStickDown() == true && auxController.isAuxStickUp() == false) {
			contractBottom();
			
			isBottomSolenoidExtended = false;
		}
		// Three way switch is in the middle position
		else if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == false) {
			extendBottom();
			
			isBottomSolenoidExtended = true;
		}
		// Three way switch is in the top position
		else if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == true) {
			extendBottom();
			
			isBottomSolenoidExtended = true;
		}
		
		// Three way switch in the middle position
		if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == false) {
			contractTop();
			
			isTopSolenoidExtended = false;
		}
		// Three way switch in the top position
		else if (auxController.isAuxStickDown() == false && auxController.isAuxStickUp() == true) {
			extendTop();
			
			isTopSolenoidExtended = true;
		}
		// Three way switch in the bottom position
		else if (auxController.isAuxStickDown() == true && auxController.isAuxStickUp() == false) {
			contractTop();
			
			isTopSolenoidExtended = true;
		}
		
		Dashboard.putString("Elevator : BOTTOM : Extended", new String("" + isBottomSolenoidExtended).toUpperCase());
		Dashboard.putString("Elevator : TOP : Extended", new String("" + isTopSolenoidExtended).toUpperCase());
	}

	public void extendBottom() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		bottomSolenoidDown.set(false);
		bottomSolenoidUp.set(true);
	}
	
	public void contractBottom() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		bottomSolenoidDown.set(true);
		bottomSolenoidUp.set(false);
	}

	public void extendTop() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		topSolenoidDown.set(false);
		topSolenoidUp.set(true);
	}
	
	public void contractTop() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
		topSolenoidDown.set(false);
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
