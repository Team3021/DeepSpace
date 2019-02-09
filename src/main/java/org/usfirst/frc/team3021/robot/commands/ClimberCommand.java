package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.Robot;
import org.usfirst.frc.team3021.robot.subsystem.ClimberSystem;

public class ClimberCommand extends Command {
	
	protected ClimberSystem climberSystem = null;
	
	public ClimberCommand() {
		super();
		
		this.climberSystem = Robot.getClimberSystem();
		
		requires(this.climberSystem);
	}
}