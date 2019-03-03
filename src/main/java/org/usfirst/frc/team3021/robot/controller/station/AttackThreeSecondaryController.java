package org.usfirst.frc.team3021.robot.controller.station;

import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;

public class AttackThreeSecondaryController extends BaseController {
	
	public AttackThreeSecondaryController(ControllerConfiguration configuration, int port) {
		super(configuration, port);
	}

	protected void setButtons() {
		buttonActions.add(new ButtonAction(1, "STICK_TRIGGER", "isMovingWrist"));
		
		buttonActions.add(new ButtonAction(2, "STICK_MIDDLE_BUTTON", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(3, "STICK_CENTER", "UNASSIGNED"));;
		buttonActions.add(new ButtonAction(4, "STICK_LEFT_BUTTON", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(5, "STICK_RIGHT_BUTTON", "UNASSIGNED"));
	
		buttonActions.add(new ButtonAction(6, "BASE_LEFT_FRONT", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(7, "BASE_LEFT_BACK", "UNASSIGNED"));
	
		buttonActions.add(new ButtonAction(8, "BASE_BOTTOM_LEFT", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(9, "BASE_BOTTOM_RIGHT", "UNASSIGNED"));
		
		buttonActions.add(new ButtonAction(10, "BASE_RIGHT_BACK", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(11, "BASE_RIGHT_FRONT", "UNASSIGNED"));
	}
	
	@Override
	public double getMoveValue() {
		return controller.getY();
	}
	
	@Override
	public double getTurnValue() {
		return controller.getX();
	}
}
