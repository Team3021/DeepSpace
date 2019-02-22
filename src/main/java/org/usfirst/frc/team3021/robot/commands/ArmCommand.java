package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.Robot;
import org.usfirst.frc.team3021.robot.subsystem.ArmSystem;

public class ArmCommand extends Command {
	
	protected ArmSystem armSystem = null;
	
	public ArmCommand() {
		super();
		
		this.armSystem = Robot.getArmSystem();
		
		requires(this.armSystem);
	}
}