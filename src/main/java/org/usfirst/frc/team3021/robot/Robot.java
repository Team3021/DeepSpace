package org.usfirst.frc.team3021.robot;

import org.usfirst.frc.team3021.robot.configuration.AutonomousConfiguration;
import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;
import org.usfirst.frc.team3021.robot.configuration.DeviceCommandConfiguration;
import org.usfirst.frc.team3021.robot.configuration.DriveCommandConfiguration;
import org.usfirst.frc.team3021.robot.configuration.SystemCommandConfiguration;
import org.usfirst.frc.team3021.robot.configuration.TestCommandConfiguration;
import org.usfirst.frc.team3021.robot.controller.station.Controller;
import org.usfirst.frc.team3021.robot.subsystem.ArmSystem;
import org.usfirst.frc.team3021.robot.subsystem.ClawSystem;
import org.usfirst.frc.team3021.robot.subsystem.DriveSystem;
import org.usfirst.frc.team3021.robot.subsystem.ElevatorSystem;
import org.usfirst.frc.team3021.robot.subsystem.VisionSystem;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	
	// Member Attributes
	private static ControllerConfiguration controllerConfiguration;
	private static AutonomousConfiguration autonomousConfiguration;
	
	private static DriveSystem driveSystem;
	private static ArmSystem armSystem;	
	private static ClawSystem clawSystem;	
	private static ElevatorSystem elevatorSystem;
	private static VisionSystem visionSystem;
	
	private Controller mainController;
	private Controller secondaryController;
	private Controller auxController;
	
	private static String gameData = "";

	public Robot() {
		super();

		// Create the sub systems
		visionSystem = new VisionSystem();
		driveSystem = new DriveSystem();
		armSystem = new ArmSystem();
		clawSystem = new ClawSystem();
		elevatorSystem = new ElevatorSystem();
		
		// Create the main configuration
		controllerConfiguration = new ControllerConfiguration();
		autonomousConfiguration = new AutonomousConfiguration();
		
		// Create the command configuration
		new DriveCommandConfiguration();
		new TestCommandConfiguration();
		new SystemCommandConfiguration();
		new DeviceCommandConfiguration();
	}

	// ****************************************************************************
	// **********************              INIT              **********************
	// ****************************************************************************
	
	@Override
	public void robotInit() {
		System.out.println("Robot initializing...");
		
		mainController = controllerConfiguration.getMainController();
		secondaryController = controllerConfiguration.getSecondaryController();
		auxController = controllerConfiguration.getAuxController();

		driveSystem.setControllers(mainController, secondaryController, auxController);
		visionSystem.setControllers(mainController, secondaryController, auxController);
		armSystem.setControllers(mainController, secondaryController, auxController);
		clawSystem.setControllers(mainController, secondaryController, auxController);
		elevatorSystem.setControllers(mainController, secondaryController, auxController);
	}

	// ****************************************************************************
	// **********************          AUTONOMOUS            **********************
	// ****************************************************************************
	
	@Override
	public void autonomousInit() {
		// Stop any commands that might be left running from another mode
		System.out.println("Entering Autonomous Init");
		Scheduler.getInstance().removeAll();

//		DISABLE AUTONOMOUS LOGIC
//		
//		Command autoCommand = autonomousConfiguration.getAutonomousCommand(gameData);
//		
//		System.out.println("AutoCommand Scheduled " + autoCommand.getName());
//		
//		Scheduler.getInstance().add(autoCommand);
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
//		REMOVE THESE LINE IF USING AUTONOMOUS LOGIC
		driveSystem.teleopPeriodic();
		visionSystem.teleopPeriodic();
		armSystem.teleopPeriodic();
		clawSystem.teleopPeriodic();
		elevatorSystem.teleopPeriodic();
	}

	// ****************************************************************************
	// **********************            TELEOP              **********************
	// ****************************************************************************

	@Override
	public void teleopInit() {
		// Stop any commands that might be left running from another mode
		Scheduler.getInstance().removeAll();
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		driveSystem.teleopPeriodic();
//		visionSystem.teleopPeriodic();
		armSystem.teleopPeriodic();
		clawSystem.teleopPeriodic();
		elevatorSystem.teleopPeriodic();
	}

	// ****************************************************************************
	// **********************             TEST               **********************
	// ****************************************************************************

	@Override
	public void testInit() {
		// Stop any commands that might be left running from another mode
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}

	// ****************************************************************************
	// **********************       OTHER BASE MEHTODS       **********************
	// ****************************************************************************

	@Override
	public void disabledInit() {
		// Do nothing to prevent warnings
	}

	@Override
	public void robotPeriodic() {
		// Do nothing to prevent warnings
	}

	@Override
	public void disabledPeriodic() {
		// Do nothing to prevent warnings
	}

	// ****************************************************************************
	// **********************        ACCESSOR METHODS        **********************
	// ****************************************************************************

	public static VisionSystem getVisionSystem() {
		return visionSystem;
	}

	public static DriveSystem getDriveSystem() {
		return driveSystem;
	}

	public static ArmSystem getArmSystem() {
		return armSystem;
	}

	public static ClawSystem getClawSystem() {
		return clawSystem;
	}
	
	public static ElevatorSystem getElevatorSystem() {
		return elevatorSystem;
	}
	
	public static String getGameData() {
		return gameData;
	}
}

