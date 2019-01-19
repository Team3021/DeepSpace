package org.usfirst.frc.team3021.robot.commands.system;

import org.usfirst.frc.team3021.robot.controller.station.AttackThreeController;
import org.usfirst.frc.team3021.robot.controller.station.AuxController;
import org.usfirst.frc.team3021.robot.controller.station.Xbox360Controller;

public class PrintButtonMappingsCommand extends SystemCommand {
	
	public PrintButtonMappingsCommand() {
		super();
	}
	
	protected void execute() {
		new AttackThreeController(null, -1).printButtonActions("Attack Three");
		new Xbox360Controller(null, -1).printButtonActions("Xbox360");
		new AuxController(null, -1).printButtonActions("Aux Panel");
	}
	
	protected boolean isFinished() {
		return true;
	}
}
