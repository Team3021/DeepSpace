package org.usfirst.frc.team3021.robot.commands.device;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OuttakeSwitch extends CommandGroup {
	
	public OuttakeSwitch() {
		super();
		
		addSequential(new ExtendClimber(0.5));
		addSequential(new DeployTote(1));
		addSequential(new DeliverTote(2));
		addSequential(new StowTote(1));
		addSequential(new RetractClimber(0.5));
	}
}
