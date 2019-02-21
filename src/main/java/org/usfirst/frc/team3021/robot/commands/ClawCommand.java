package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.Robot;
import org.usfirst.frc.team3021.robot.subsystem.ClawSystem;

public class ClawCommand extends Command {
	
	protected ClawSystem clawSystem = null;
	
	public ClawCommand() {
		super();
		
		this.clawSystem = Robot.getClawSystem();
		
		requires(this.clawSystem);
	}
}