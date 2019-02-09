package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.Robot;
import org.usfirst.frc.team3021.robot.subsystem.ElevatorSystem;

public class ElevatorCommand extends Command {
	
	protected ElevatorSystem elevatorSystem = null;
	
	public ElevatorCommand() {
		super();
		
		this.elevatorSystem = Robot.getElevatorSystem();
		
		requires(this.elevatorSystem);
	}
}