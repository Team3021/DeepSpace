package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ClawCommand;

import edu.wpi.first.wpilibj.Timer;

public class DeliverHatch extends ClawCommand {

	public DeliverHatch() {
		super();

		System.out.println("Starting with the hatch delivery.");
	}
	
	@Override
	protected void execute() {
		// TODO retract the arm at the same time as the the solenoids extend
		
		clawSystem.deploy();
		
		Timer.delay(0.4);
	}

	@Override
	protected void end() {
		System.out.println("Done with the hatch delivery.");
		clawSystem.contract();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}