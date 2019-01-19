package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.driving.StopMoving;
import org.usfirst.frc.team3021.robot.commands.driving.StopTurning;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle45;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle90;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle45;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle90;
import org.usfirst.frc.team3021.robot.commands.driving.TurnToAngle180;
import org.usfirst.frc.team3021.robot.commands.driving.ZeroEncoders;
import org.usfirst.frc.team3021.robot.commands.driving.ZeroGyro;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommandConfiguration extends BaseConfiguration {
	
	private final String COMMAND_GROUP = "Drive";

	private  final String PREF_DRIVE_COMMANDS_ENABLED = "Config.drive.commands.enabled";
	private  final boolean DRIVE_COMMANDS_ENABLED_DEFAULT = false;
	
	private List<Command> commands = new ArrayList<Command>();
	
	private boolean enabled = DRIVE_COMMANDS_ENABLED_DEFAULT;
	
	public DriveCommandConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_DRIVE_COMMANDS_ENABLED, DRIVE_COMMANDS_ENABLED_DEFAULT);

		addCommandsToDashboard();
	}
	
	private void addCommandsToDashboard() {
		commands.add(new TurnRightToAngle45());
		commands.add(new TurnLeftToAngle45());
		
		commands.add(new TurnRightToAngle90());
		commands.add(new TurnLeftToAngle90());
		
		commands.add(new TurnToAngle180());

		commands.add(new ZeroEncoders());
		commands.add(new ZeroGyro());
		
		commands.add(new StopMoving());
		commands.add(new StopTurning());
		
		addCommandsToDashboard(COMMAND_GROUP, commands, enabled);
	}
}
