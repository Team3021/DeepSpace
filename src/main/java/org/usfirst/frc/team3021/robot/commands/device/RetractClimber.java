package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ClimberCommand;

public class RetractClimber extends ClimberCommand{
	double duration; // seconds

	public RetractClimber() {
		this(1.5);
	}
	
	public RetractClimber(double duration) {
		super();
		this.duration = duration;
	}

	@Override
	protected void execute() {
		climberSystem.contract();
	}

	@Override
	protected void end() {
		System.out.println("Done with the elevator.");
		climberSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return timeSinceInitialized() >= duration;
	}
}
