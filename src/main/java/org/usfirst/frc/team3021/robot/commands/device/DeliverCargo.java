package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ClawCommand;

public class DeliverCargo extends ClawCommand {

	int time = 2; // seconds
	double speed = 0.3;
	
	public DeliverCargo(int time) {
		super();

		this.time = time;
	}
	
	public DeliverCargo(int time, double speed) {
		super();

		this.time = time;
		this.speed = speed;
	}
	
	@Override
	protected void execute() {
		clawSystem.deliver(speed);
	}

	@Override
	protected void end() {
		System.out.println("Done with the delivery of cargo.");
		clawSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return (timeSinceInitialized() >= time);
	}

}