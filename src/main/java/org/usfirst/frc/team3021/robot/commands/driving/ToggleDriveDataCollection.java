package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;

public class ToggleDriveDataCollection extends DriveCommand {

	@Override
	protected void execute() {
		driveSystem.toggleDataPrinting();
	}
}
