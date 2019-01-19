package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.subsystem.ClimberSystem;

public class ClimberCommand extends Command {
	
	protected ClimberSystem climberSystem = null;
	
	public ClimberCommand() {
		super();
		
		this.climberSystem = QBert.getClimberSystem();
		
		requires(this.climberSystem);
	}
}