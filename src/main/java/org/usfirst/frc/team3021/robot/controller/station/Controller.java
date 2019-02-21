package org.usfirst.frc.team3021.robot.controller.station;

public interface Controller {

	boolean isXbox();

	// ****************************************************************************
	// **********************            MOVEMENT            **********************
	// ****************************************************************************
	
	double getMoveValue();

	double getTurnValue();

	// ****************************************************************************
	// **********************             DRIVE              **********************
	// ****************************************************************************

	boolean isZeroGyro();

	boolean isZeroEncoders();

	boolean isRotatingToNinety();
	
	boolean isRotatingToNegativeNinety();
	
	boolean isRotatingToOneHundredEighty();

	boolean isRotatingRight45();

	boolean isRotatingLeft45();

	// ****************************************************************************
	// **********************             ELEVATOR            *********************
	// ****************************************************************************

	boolean isBottomElevatorExtending();
	
	boolean isBottomElevatorContracting();

	boolean isTopElevatorExtending();
	
	boolean isTopElevatorContracting();

	// ****************************************************************************
	// **********************            COLLECTOR           **********************
	// ****************************************************************************
	
	boolean isLaunching();
	
	boolean isLowVoltageLaunch();
	
	boolean isCollecting();
	
	boolean isCollectorDeploying();

	boolean isCollectorStowing();

	// ****************************************************************************
	// **********************             VISION             **********************
	// ****************************************************************************

	boolean isSwitchingCamera();
 
	boolean isScopeEnabled();

	boolean isTargetLocatorEnabled();

	// ****************************************************************************
	// **********************           SCHEDULER            **********************
	// ****************************************************************************

	boolean isStoppingCommands();

	// ****************************************************************************
	// **********************            DEBUGGING           **********************
	// ****************************************************************************

	boolean isCollectingData();
	
	void printButtonActions(String controller);

}