package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.device.DeliverCargo;
import org.usfirst.frc.team3021.robot.commands.device.ExtendElevator;

import edu.wpi.first.wpilibj.command.Command;

public class DeviceCommandConfiguration extends BaseConfiguration {
	
	private final String COMMAND_GROUP = "Device";

	private  final String PREF_SYSTEM_COMMANDS_ENABLED = "Config.device.commands.enabled";
	private  final boolean SYSTEM_COMMANDS_ENABLED_DEFAULT = false;
	
	private List<Command> commands = new ArrayList<Command>();
	
	private boolean enabled = true;
	
	public DeviceCommandConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_SYSTEM_COMMANDS_ENABLED, SYSTEM_COMMANDS_ENABLED_DEFAULT);

		addCommandsToDashboard();
	}
	
	private void addCommandsToDashboard() {
		// Elevator
		commands.add(new ExtendElevator());
		
		// Collector
		commands.add(new DeliverCargo(1));
		
		addCommandsToDashboard(COMMAND_GROUP, commands, enabled);
	}
}
