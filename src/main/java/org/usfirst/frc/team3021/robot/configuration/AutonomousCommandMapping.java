package org.usfirst.frc.team3021.robot.configuration;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommandMapping {
	
	private int startLocation;
	
	private String switchObjective;
	
	private String autoLineObjective;
	
	private String scaleObjective;
	
	private String powerUpObjective;
	
	private Command command;

	public AutonomousCommandMapping(int startLocation, String switchObjective, String autoLineObjective, String scaleObjective, String powerUpObjective, Command command) {
		this.startLocation = startLocation;
		this.autoLineObjective = autoLineObjective;
		this.switchObjective = switchObjective;
		this.scaleObjective = scaleObjective;
		this.powerUpObjective = powerUpObjective;
		this.command = command;
	}

	public int getStartLocation() {
		return startLocation;
	}

	public String getSwitchObjective() {
		return switchObjective;
	}

	public String getAutoLineObjective() {
		return switchObjective;
	}

	public String getScaleObjective() {
		return scaleObjective;
	}

	public String getPowerUpObjective() {
		return powerUpObjective;
	}

	public Command getCommand() {
		return command;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AutonomousCommandMapping)) {
			return false;
		}
		
		AutonomousCommandMapping commandMapping = (AutonomousCommandMapping) obj;
		
		if (commandMapping.getStartLocation() != this.startLocation) {
			return false;
		}
		
		if (commandMapping.getSwitchObjective() != this.switchObjective) {
			return false;
		}
		
		if (commandMapping.getAutoLineObjective() != this.autoLineObjective) {
			return false;
		}
		
		if (commandMapping.getScaleObjective() != this.scaleObjective) {
			return false;
		}
		
		if (commandMapping.getPowerUpObjective() != this.powerUpObjective) {
			return false;
		}
		
		return true;
	}
}
