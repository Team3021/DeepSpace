package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;

public class HoldPosition extends DriveCommand {

	public HoldPosition() {
		setInterruptible(true);
	}
	
	@Override
	protected void execute() {
		driveSystem.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}
