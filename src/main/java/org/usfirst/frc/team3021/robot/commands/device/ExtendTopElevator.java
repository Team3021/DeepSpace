package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;

public class ExtendTopElevator extends ElevatorCommand {

	public ExtendTopElevator() {

	}

	@Override
	protected void execute() {
		elevatorSystem.extendTop();
	}

	@Override
	protected void end() {
		System.out.println("Done with the elevator.");
		elevatorSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
