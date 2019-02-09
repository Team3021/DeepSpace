package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;

public class ExtendElevator extends ElevatorCommand {

	double duration; // seconds

	public ExtendElevator() {
		this(1.0);
	}
	
	public ExtendElevator(double duration) {
		super();
		this.duration = duration;
	}

	@Override
	protected void execute() {
		elevatorSystem.extend();
	}

	@Override
	protected void end() {
		System.out.println("Done with the elevator.");
		elevatorSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return timeSinceInitialized() >= duration;
	}
}
