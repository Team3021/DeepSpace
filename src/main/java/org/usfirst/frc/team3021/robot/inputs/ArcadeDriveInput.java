package org.usfirst.frc.team3021.robot.inputs;

public class ArcadeDriveInput extends DriveInput{
	private double moveValue;
	double turnValue;
	
	public ArcadeDriveInput(double moveValue, double turnValue) {
		this.moveValue = moveValue;
		this.turnValue = turnValue;
	}
	
	public double getMoveValue() {
		return moveValue;
	}
	
	public double getTurnValue() {
		return turnValue;
	}
	
	public void setMoveValue(double moveValue) {
		this.moveValue = moveValue;
	}
	
	public void setTurnValue(double turnValue) {
		this.turnValue = turnValue;
	}
}
