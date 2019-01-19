package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToSCALE extends CommandGroup {

	public LeftToSCALE() {
		super("[Left] to [SCALE]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();

		addSequential(new MoveForwardForDistance(speed, 26.75));
		addSequential(new TurnRightToAngle(90));
		addSequential(new OuttakeScale());
	}

	@Override
	protected void initialize() {
		System.out.println("Initializing [Left] To [SCALE]");
	}
}