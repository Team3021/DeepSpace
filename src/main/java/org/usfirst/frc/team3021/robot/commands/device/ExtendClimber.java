package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ClimberCommand;

public class ExtendClimber extends ClimberCommand {

	double duration; // seconds

	public ExtendClimber() {
		this(1.0);
	}
	
	public ExtendClimber(double duration) {
		super();
		this.duration = duration;
	}

	@Override
	protected void execute() {
		climberSystem.extend();
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
