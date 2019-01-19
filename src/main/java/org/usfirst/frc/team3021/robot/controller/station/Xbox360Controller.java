package org.usfirst.frc.team3021.robot.controller.station;

import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;

public class Xbox360Controller extends BaseController {
	
	// Controller axes
	private static final int LEFT_STICK_X = 0;
	private static final int LEFT_STICK_Y = 1;
	
	private static final int LEFT_TRIGGER = 2;
	private static final int RIGHT_TRIGGER = 3;

//	private static final int RIGHT_STICK_X = 4;
//	private static final int RIGHT_STICK_Y = 5;

	public Xbox360Controller(ControllerConfiguration configuration, int port) {
		super(configuration, port);
	}

	protected void setButtons() {
		buttonActions.add(new ButtonAction(1, "A_BUTTON", "isRotatingToOneHundredEighty"));
		buttonActions.add(new ButtonAction(2, "B_BUTTON", "isRotatingToNinety"));
		buttonActions.add(new ButtonAction(3, "X_BUTTON", "isRotatingToNegativeNinety"));
		buttonActions.add(new ButtonAction(4, "Y_BUTTON", "isStoppingCommands"));
		
		buttonActions.add(new ButtonAction(5, "LEFT_SHOULDER", "isRotatingLeft45"));
		buttonActions.add(new ButtonAction(6, "RIGHT_SHOULDER", "isRotatingRight45"));
		
		buttonActions.add(new ButtonAction(7, "BACK_BUTTON", "isZeroGyro"));
		buttonActions.add(new ButtonAction(8, "START_BUTTON", "isZeroEncoders"));
		
		buttonActions.add(new ButtonAction(9, "LEFT_STICK_CLICK", "isCollectingData"));
		buttonActions.add(new ButtonAction(10, "RIGHT_STICK_CLICK", "isSwitchingCamera"));
	}
	
	@Override
	public double getMoveValue(){
		return controller.getRawAxis(LEFT_STICK_Y);
	}

	@Override
	public double getTurnValue(){
		return controller.getRawAxis(LEFT_STICK_X);
	}

	@Override
	public boolean isCollectingData() {
		return getRawButton("isCollectingData");
	}
	
	@Override
	public boolean isLaunching() {
		if (controller.getRawAxis(LEFT_TRIGGER) > 0.9) {
			System.out.println("Left tigger for isLaunching");
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean isCollecting() {
		if (controller.getRawAxis(RIGHT_TRIGGER) > 0.9) {
			System.out.println("Right tigger for isCollecting");
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void printButtonActions(String controller) {
		super.printButtonActions(controller);
		
		System.out.println("PressureAction { 'number':'1', 'name':'" + LEFT_TRIGGER +", 'action':'isLaunching' }");
		System.out.println("PressureAction { 'number':'2', 'name':'" + RIGHT_TRIGGER +", 'action':'isCollecting' }");
		
		System.out.println("");
	}
}
