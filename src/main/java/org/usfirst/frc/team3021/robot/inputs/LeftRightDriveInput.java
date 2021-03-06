package org.usfirst.frc.team3021.robot.inputs;

public class LeftRightDriveInput extends DriveInput {
	private double leftInput;
	private double rightInput;
	
	public LeftRightDriveInput(double leftInput, double rightInput) {
		this.leftInput = leftInput;
		this.rightInput = rightInput;
	}
	
	public double getLeftInput() {
		return leftInput;
	}
	
	public double getRightInput() {
		return rightInput;
	}
	
	public void setLeftInput(double leftInput) {
		this.leftInput = leftInput;
	}
	
	public void setRightInput(double rightInput) {
		this.rightInput = rightInput;
	}
	
}
