package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.subsystem.VisionSystem;

public class VisionCommand extends Command {
	
	protected VisionSystem vision = null;
	
	public VisionCommand() {
		super();
		
		vision = QBert.getVisionSystem();
		
		requires(vision);
	}
}