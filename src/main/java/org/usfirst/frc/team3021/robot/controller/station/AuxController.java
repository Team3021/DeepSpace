package org.usfirst.frc.team3021.robot.controller.station;

import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;

public class AuxController extends BaseController {
	
	public AuxController(ControllerConfiguration configuration, int port) {
		super(configuration, port);
	}
	
	protected void setButtons() {
		buttonActions.add(new ButtonAction(1, "RIGHT_TOGGLE_BUTTON", "isTopElevatorExnteded"));
		buttonActions.add(new ButtonAction(2, "MIDDLE_TOGGLE_BUTTON", "isBottomElevatorExtended"));
		buttonActions.add(new ButtonAction(3, "LEFT_TOGGLE_BUTTON", "isLowVoltageLaunch"));

		buttonActions.add(new ButtonAction(4, "SAFETY_TRIGGER", "UNASSIGNED"));
	
		buttonActions.add(new ButtonAction(5, "TOP_BLUE_BUTTON", "isCollecting"));
		buttonActions.add(new ButtonAction(8, "TOP_RED_BUTTON", "isLaunching"));

		buttonActions.add(new ButtonAction(6, "MIDDLE_BLUE_BUTTON", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(9, "MIDDLE_RED_BUTTON", "UNASSIGNED"));

		buttonActions.add(new ButtonAction(7, "BOTTOM_BLUE_BUTTON", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(10, "BOTTOM_RED_BUTTON", "UNASSIGNED"));
	}
	
	@Override
	public boolean getRawButton(String action) {
		if (configuration == null || !configuration.isAuxPanelEnabled()) {
			return false;
		}
		
		return super.getRawButton(action);
	}
}