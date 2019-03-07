package org.usfirst.frc.team3021.robot.controller.station;

import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;

public class AuxController extends BaseController {
	
	public AuxController(ControllerConfiguration configuration, int port) {
		super(configuration, port);
	}
	
	protected void setButtons() {
		buttonActions.add(new ButtonAction(1, "UNASSIGNED", "UNASSIGNED"));
		
		buttonActions.add(new ButtonAction(2, "STICK_DOWN", "isAuxStickDown"));
		buttonActions.add(new ButtonAction(3, "STICK_UP", "isAuxStickUp"));

		buttonActions.add(new ButtonAction(4, "UNASSIGNED", "UNASSIGNED"));
		
		buttonActions.add(new ButtonAction(5, "TOGGLE_UP", "isLaunching"));
		buttonActions.add(new ButtonAction(6, "TOGGLE_DOWN", "isCollecting"));
		
		buttonActions.add(new ButtonAction(8, "UNASSIGNED", "UNASSIGNED"));

		buttonActions.add(new ButtonAction(9, "UNASSIGNED", "UNASSIGNED"));

		buttonActions.add(new ButtonAction(7, "UNASSIGNED", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(10, "UNASSIGNED", "UNASSIGNED"));
	}
	
	@Override
	public boolean getRawButton(String action) {
		if (configuration == null || !configuration.isAuxPanelEnabled()) {
			return false;
		}
		
		return super.getRawButton(action);
	}
}