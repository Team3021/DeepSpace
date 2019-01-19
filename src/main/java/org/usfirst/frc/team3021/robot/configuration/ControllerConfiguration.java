package org.usfirst.frc.team3021.robot.configuration;

import org.usfirst.frc.team3021.robot.controller.station.AttackThreeController;
import org.usfirst.frc.team3021.robot.controller.station.AuxController;
import org.usfirst.frc.team3021.robot.controller.station.Controller;
import org.usfirst.frc.team3021.robot.controller.station.DefaultController;
import org.usfirst.frc.team3021.robot.controller.station.Xbox360Controller;

import edu.wpi.first.wpilibj.DriverStation;

public class ControllerConfiguration extends BaseConfiguration {
	
	private static final String NO_CONTROLLER = "No Controller";
	private static final String ATTACK_THREE = "Attack Three";
	private static final String XBOX360 = "Xbox360";

	private final String PREF_MAIN_CONTROLLER_PORT = "Controller.main.port";
	private final int MAIN_CONTROLLER_PORT_DEFAULT = 0;

	private  final String PREF_CONTROLLER_XBOX_ENABLED = "Controller.xbox.enabled";
	private  final boolean CONTROLLER_XBOX_ENABLED_DEFAULT = false;

	private  final String PREF_AUX_PANEL_ENABLED = "Controller.aux.enabled";
	private  final boolean AUX_PANEL_ENABLED_DEFAULT = true;
	
	private final String PREF_AUX_PANEL_PORT = "Controller.aux.port";
	private final int AUX_PANEL_PORT_DEFAULT = 1;
	
	private static Controller mainController;
	private static Controller auxController;

	public ControllerConfiguration() {
		mainController = initializeMainController();

		auxController = initializeAuxController();
	}

	public Controller getMainController() {
		return mainController;
	}

	public Controller getAuxController() {
		return auxController;
	}
	
	private String getMainControllerMode() {
		String selected = ATTACK_THREE;

		boolean xboxEnabled = Preferences.getInstance().getBoolean(PREF_CONTROLLER_XBOX_ENABLED, CONTROLLER_XBOX_ENABLED_DEFAULT);

		System.out.println("xboxEnabled" + xboxEnabled);
		
		if (xboxEnabled == true) {
			selected = XBOX360;
		}
		
		Dashboard.putString("Configuration : joystick mode",  selected);
		
		return selected;
	}
	
	private int getMainControllerPort() {
		return Preferences.getInstance().getInt(PREF_MAIN_CONTROLLER_PORT, MAIN_CONTROLLER_PORT_DEFAULT);
	}
	
	private int getAuxPanelPort() {
		return Preferences.getInstance().getInt(PREF_AUX_PANEL_PORT, AUX_PANEL_PORT_DEFAULT);
	}
	
	public boolean isAuxPanelEnabled() {
		return Preferences.getInstance().getBoolean(PREF_AUX_PANEL_ENABLED, AUX_PANEL_ENABLED_DEFAULT);
	}

	private Controller initializeMainController() {
		int mainControllerPort = getMainControllerPort();

		Controller mainController = new AttackThreeController(this, mainControllerPort);
		
		String selectedController = getMainControllerMode();

		if (selectedController.equals(ControllerConfiguration.ATTACK_THREE)) {
			System.out.println("*************** ATTACK THREE ***************");
			
			if (mainController.isXbox()) {
				DriverStation.reportWarning("Dahboard choice is not an XBOX controller, but this is an XBOX CONTROLLER on port " + getMainControllerPort(), false);
			}
		}
		else if (selectedController.equals(ControllerConfiguration.XBOX360)) {
			System.out.println("*************** XBOX ***************");
			mainController = new Xbox360Controller(this, mainControllerPort);
			
			if (!mainController.isXbox()) {
				DriverStation.reportWarning("Dahboard choice is XBOX controller, but this is NOT an XBOX CONTROLLER on port " + getMainControllerPort(), false);
			}
		} else if (selectedController.equals(ControllerConfiguration.NO_CONTROLLER)) {
			DriverStation.reportError("*************** NO CONTROLLER ***************", false);
			mainController = new DefaultController(this, mainControllerPort);
		}
		
		return mainController;
	}

	private Controller initializeAuxController() {
		System.out.println("*************** AUX ***************");
		
		int auxControllerPort = getAuxPanelPort();

		Controller auxController = new AuxController(this, auxControllerPort);
		
		return auxController;
	}
}
