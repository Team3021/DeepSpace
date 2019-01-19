package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Straight extends CommandGroup {
	public Straight() {
		super("[Straight]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		addSequential(new MoveForwardForDistance(speed, 10));
	}
}