package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class DeliverTote extends CollectorCommand {

	int time = 2; // seconds
	double speed = 0.3;
	
	public DeliverTote(int time) {
		super();

		this.time = time;
	}
	
	public DeliverTote(int time, double speed) {
		super();

		this.time = time;
		this.speed = speed;
	}
	
	@Override
	protected void execute() {
		collectorSystem.deliver(speed);
	}

	@Override
	protected void end() {
		System.out.println("Done with the outtake.");
		collectorSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return (timeSinceInitialized() >= time);
	}

}