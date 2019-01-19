package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToSCALE extends CommandGroup {
	public RightToSCALE() {
		super("[Right] to [SCALE]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();

		addSequential(new MoveForwardForDistance(speed, 26.75));
		addSequential(new TurnLeftToAngle(90));
		addSequential(new OuttakeScale());
	}

	@Override
	protected void initialize() {
		System.out.println("Starting command [Right] to [SCALE]");
	}
}