package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.controller.station.Controller;

public abstract class Subsystem extends edu.wpi.first.wpilibj.command.Subsystem {
	
	protected Controller mainController;
	protected Controller auxController;
	
	public void setControllers(Controller mainController, Controller auxController) {
		this.mainController = mainController;
		this.auxController = auxController;
	}

	public void teleopPeriodic() {
		
	}

}