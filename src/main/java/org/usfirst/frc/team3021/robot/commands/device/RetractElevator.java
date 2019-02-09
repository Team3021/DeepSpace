package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;

public class RetractElevator extends ElevatorCommand{
	double duration; // seconds

	public RetractElevator() {
		this(1.5);
	}
	
	public RetractElevator(double duration) {
		super();
		this.duration = duration;
	}

	@Override
	protected void execute() {
		elevatorSystem.contract();
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
