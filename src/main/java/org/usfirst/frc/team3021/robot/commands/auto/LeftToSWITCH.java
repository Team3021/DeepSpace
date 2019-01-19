package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.DeliverTote;
import org.usfirst.frc.team3021.robot.commands.device.DeployTote;
import org.usfirst.frc.team3021.robot.commands.device.ExtendClimber;
import org.usfirst.frc.team3021.robot.commands.device.RetractClimber;
import org.usfirst.frc.team3021.robot.commands.device.StowTote;
import org.usfirst.frc.team3021.robot.commands.driving.MoveBackwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToSWITCH extends CommandGroup {
	public LeftToSWITCH() {
		super("[Left] to [SWITCH]");
		
		System.out.println("Entering Initialize LeftToSwitch");

		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		addSequential(new MoveForwardForDistance(speed, 10));
		addSequential(new TurnRightToAngle(90));
		addSequential(new MoveForwardForDistance(speed, 1.1));
		
		addSequential(new ExtendClimber(1.0));
		addSequential(new DeployTote(1));
		addSequential(new DeliverTote(2));
		addSequential(new StowTote(1));
		addSequential(new MoveBackwardForDistance(speed - .2, 0.83));
		addSequential(new RetractClimber(0.5));
	}
	
	@Override
	protected void initialize() {
		System.out.println("Starting command [Left] to [SWITCH]");
	}
}
