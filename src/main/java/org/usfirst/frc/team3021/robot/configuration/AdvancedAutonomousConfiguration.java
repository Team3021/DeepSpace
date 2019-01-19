package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.auto.Straight;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class AdvancedAutonomousConfiguration extends BaseConfiguration {
	
	private  final String PREF_AUTO_COMMANDS_ENABLED = "Config.auto.commands.enabled";
	private  final boolean AUTO_COMMANDS_ENABLED_DEFAULT = true;

	public static final int START_1 = 1;
	public static final int START_2 = 2;
	public static final int START_3 = 3;
	
	public static final String SWITCH_I = "I";
	public static final String SWITCH_L= "L";
	public static final String SWITCH_R = "R";
	
	public static final String AUTO_LINE_I = "I";
	public static final String AUTO_LINE_G = "G";
	
	public static final String SCALE_I = "I";
	public static final String SCALE_L= "L";
	public static final String SCALE_R = "R";

	public static final String POWERUP_I = "I";
	public static final String POWERUP_G = "G";
	
	private List<AutonomousCommandMapping> commandMappings = new ArrayList<AutonomousCommandMapping>();
	
	private boolean enabled = AUTO_COMMANDS_ENABLED_DEFAULT;
	
	private int startLocation = START_1;
	
	private String switchObjective = SWITCH_I;
	private String autoLineObjective = AUTO_LINE_I;
	private String scaleObjective = SCALE_I;
	private String powerupObjective = POWERUP_I;
	
	public AdvancedAutonomousConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_AUTO_COMMANDS_ENABLED, AUTO_COMMANDS_ENABLED_DEFAULT);
		
		startLocation = DriverStation.getInstance().getLocation();

		Dashboard.putData(Scheduler.getInstance());
		
		addCommandMappings();
	}

	private void addCommandMappings() {
		commandMappings.add(new AutonomousCommandMapping(START_1, SWITCH_L, AUTO_LINE_I, SCALE_I, POWERUP_I, new Straight()));
		commandMappings.add(new AutonomousCommandMapping(START_3, SWITCH_R, AUTO_LINE_I, SCALE_I, POWERUP_I, new Straight()));
	}
	
	private AutonomousCommandMapping getAutonomousMode() {
		
		AutonomousCommandMapping commandMapping = new AutonomousCommandMapping(startLocation, switchObjective, autoLineObjective, scaleObjective, powerupObjective, null);
		
		return commandMapping;
	}

	public Command getAutonomousCommand() {
		
		if (!enabled) {
			return null;
		}
		
		AutonomousCommandMapping mode = getAutonomousMode();
		
		for (AutonomousCommandMapping commandMapping : commandMappings) {
			if (commandMapping.equals(mode)) {
				return commandMapping.getCommand();
			}
		}
		
		return null;
	}
}
