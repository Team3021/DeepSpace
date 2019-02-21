package org.usfirst.frc.team3021.robot.controller.station;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public abstract class BaseController implements Controller {

	// Member Attributes

	protected List<ButtonAction> buttonActions = new ArrayList<ButtonAction>();
	
	protected Joystick controller;
	
	protected ControllerConfiguration configuration;
	
	public BaseController(ControllerConfiguration configuration, int port) {
		this.controller = new Joystick(port);
		this.configuration = configuration;
		
		setButtons();
	}
	
	protected abstract void setButtons();

	@Override
	public boolean isXbox() {
		if (controller != null) {
			return DriverStation.getInstance().getJoystickIsXbox(controller.getPort());
		}
		
		return false;
	}

	// ****************************************************************************
	// **********************            MOVEMENT            **********************
	// ****************************************************************************

	@Override
	public double getMoveValue() {
		return 0;
	}
	
	@Override
	public double getTurnValue() {
		return 0;
	}

	// ****************************************************************************
	// **********************             DRIVE              **********************
	// ****************************************************************************

	@Override
	public boolean isZeroGyro() {
		return getRawButton("isZeroGyro");
	}

	@Override
	public boolean isZeroEncoders() {
		return getRawButton("isZeroEncoders");
	}

	@Override
	public boolean isRotatingToNinety() {
		return getRawButton("isRotatingToNinety");
	}

	@Override
	public boolean isRotatingToNegativeNinety() {
		return getRawButton("isRotatingToNegativeNinety");
	}

	@Override
	public boolean isRotatingToOneHundredEighty() {
		return getRawButton("isRotatingToOneHundredEighty");
	}

	@Override
	public boolean isRotatingRight45() {
		return getRawButton("isRotatingRight45");
	}

	@Override
	public boolean isRotatingLeft45() {
		return getRawButton("isRotatingLeft45");
	}

	// ****************************************************************************
	// **********************             ELEVATOR           **********************
	// ****************************************************************************

	@Override
	public boolean isBottomElevatorExtending() {
		return getRawButton("isBottomElevatorExtending");
	}
	
	@Override
	public boolean isBottomElevatorContracting() {
		return getRawButton("isBottomElevatorContracting");
	}

	@Override
	public boolean isTopElevatorExtending() {
		return getRawButton("isTopElevatorExtending");
	}
	
	@Override
	public boolean isTopElevatorContracting() {
		return getRawButton("isTopElevatorContracting");
	}

	// ****************************************************************************
	// **********************            COLLECTOR           **********************
	// ****************************************************************************
	
	@Override
	public boolean isLaunching() {
		return getRawButton("isLaunching");
	}
	
	public boolean isLowVoltageLaunch() {
		return getRawButton("isLowVoltageLaunch");
	}

	@Override
	public boolean isCollecting() {
		return getRawButton("isCollecting");
	}
	
	@Override
	public boolean isCollectorDeploying() {
		return getRawButton("isCollectorDeploying");
	}
	
	@Override
	public boolean isCollectorStowing() {
		return getRawButton("isCollectorStowing");
	}

	// ****************************************************************************
	// **********************             VISION             **********************
	// ****************************************************************************
	
	@Override
	public boolean isSwitchingCamera() {
		return getRawButton("isSwitchingCamera");
	}

	@Override
	public boolean isScopeEnabled() {
		return getRawButton("isScopeEnabled");
	}

	@Override
	public boolean isTargetLocatorEnabled() {
		return getRawButton("isTargetLocatorEnabled");
	}

	// ****************************************************************************
	// **********************           SCHEDULER            **********************
	// ****************************************************************************
	
	@Override
	public boolean isStoppingCommands() {
		return getRawButton("isStoppingCommands");
	}

	// ****************************************************************************
	// **********************            DEBUGGING           **********************
	// ****************************************************************************
	
	public boolean isCollectingData() {
		return false;
	}

	// ****************************************************************************
	// **********************             ACTIONS            **********************
	// ****************************************************************************

	public boolean getRawButton(String action) {
		
		ButtonAction foundButtonAction = null;
		
		for (ButtonAction buttonAction : buttonActions) {
			if (buttonAction.getAction().equals(action)) {
				foundButtonAction = buttonAction;
				break;
			}
		}
		
		if (foundButtonAction == null) {
			return false;
		}
		
		int number = foundButtonAction.getNumber();
		
		if (controller == null) {
			return false;
		}
		
		if (controller.getButtonCount() < number) {
			return false;
		}
		
		boolean buttonOn = controller.getRawButton(number);
		
		if (buttonOn) {
			return true;
		}
		
		return false;
	}

	@Override
	public void printButtonActions(String controller) {
		System.out.println("******************* " + controller + " *******************");

		for (ButtonAction buttonAction : buttonActions) {
			System.out.println(buttonAction);
		}

		System.out.println("");
	}
}

