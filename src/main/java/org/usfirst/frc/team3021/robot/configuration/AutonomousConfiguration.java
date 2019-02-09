package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.auto.Straight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousConfiguration extends BaseConfiguration {
	
	private  final String PREF_AUTO_COMMANDS_ENABLED = "Config.auto.commands.enabled";
	private  final boolean AUTO_COMMANDS_ENABLED_DEFAULT = true;
	
	private boolean enabled = AUTO_COMMANDS_ENABLED_DEFAULT;
	
	private static final String NO_AUTONOMOUS = "No Command";
	
	public AutonomousConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_AUTO_COMMANDS_ENABLED, AUTO_COMMANDS_ENABLED_DEFAULT);

		if (enabled) {
			Dashboard.putData(Scheduler.getInstance());
		}
		
		addCommandsToDashboard();
		addAutonmousChoices();
		
	}

	private SendableChooser<String> autonomousChooser = new SendableChooser<>();
	
	private List<Command> autoCommands = new ArrayList<Command>();

	// ****************************************************************************
	// **********************             CHOICES            **********************
	// ****************************************************************************

	public void addAutonmousChoices() {
		autonomousChooser.setDefaultOption("[Straight]", "[Straight]");
		
		autonomousChooser.addOption(NO_AUTONOMOUS, NO_AUTONOMOUS);
		
		for (Command command : autoCommands) {
			autonomousChooser.addOption(command.getName(), command.getName());
		}
		
		SmartDashboard.putData("Autonomous Mode", autonomousChooser);
	}

	// ****************************************************************************
	// **********************            COMMANDS            **********************
	// ****************************************************************************
	
	public void addCommandsToDashboard() {
		SmartDashboard.putData(Scheduler.getInstance());

		// AUTONOMOUS COMMANDS
		autoCommands.add(new Straight());
		
		// Add commands to dashboard
		addCommandsToDashboard("Autonomous", autoCommands, enabled);
	}
	
	public String getAutonomousMode() {
		return autonomousChooser.getSelected();
	}

	public Command getAutonomousCommand(String gameData) {
		
		System.out.println("Game Data: " + gameData);

		Command autoCommand = null;
		
		String autoMode = getAutonomousMode();
		System.out.println("Auto command selected by DriverStation: " + autoMode);
		
		/* Autonomous Mode: Straight (Default)*/
		autoCommand = new Straight();
		System.out.println("Initiating [Straight]");
		
		return autoCommand;
	}
}
