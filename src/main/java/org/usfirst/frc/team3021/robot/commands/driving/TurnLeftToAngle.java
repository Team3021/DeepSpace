package org.usfirst.frc.team3021.robot.commands.driving;

public class TurnLeftToAngle extends TurnToAngle {

	public TurnLeftToAngle(double desiredAngle) {
		// Left turn is negative
		super(-1.0 * desiredAngle);
	}

}
