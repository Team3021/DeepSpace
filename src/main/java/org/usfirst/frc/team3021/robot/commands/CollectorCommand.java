package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.subsystem.CollectorSystem;

public class CollectorCommand extends Command {
	
	protected CollectorSystem collectorSystem = null;
	
	public CollectorCommand() {
		super();
		
		this.collectorSystem = QBert.getCollectorSystem();
		
		requires(this.collectorSystem);
	}
}