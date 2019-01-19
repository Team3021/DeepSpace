package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class CollectTote extends CollectorCommand {
	public CollectTote() {
		super();
	}
	
	@Override
	protected void execute() {
		collectorSystem.collect();
	}

	@Override
	protected void end() {
		System.out.println("Done with the intake.");
		collectorSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return (collectorSystem.hasTote());
	}

}
