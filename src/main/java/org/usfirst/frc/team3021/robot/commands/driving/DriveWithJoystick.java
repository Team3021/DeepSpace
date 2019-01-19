package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.controller.station.Controller;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;

public class DriveWithJoystick extends DriveCommand {

	private static final double REVERSE_MULTIPLIER = -1.0;
	
	private Controller mainController = null;
	
	private boolean hasPrintedHeader = false;
	
	public DriveWithJoystick(Controller mainController) {
		super();
		
		this.mainController = mainController;
	}

	@Override
	protected void initialize() {
		hasPrintedHeader =  false;
	}
	
	@Override
	protected void execute() {
		ArcadeDriveInput input = new ArcadeDriveInput(getMoveValue(), getTurnValue());
		
		driveSystem.drive(input);
		
		if (driveSystem.isPrintingData()) {
			
			if (!hasPrintedHeader) {
				System.out.println("Beginning DriveWithJoystick; outputting data with following structure:");
				driveSystem.printHeaderData();
				
				hasPrintedHeader = true;
			}
			
			driveSystem.printData();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	private double getMoveValue() {
		if (mainController == null) {
			return 0;
		}
		
		double moveValue = mainController.getMoveValue();
		
		// Determines whether the output of expSpeed should be positive or negative.
		int verticalDirection = getDirection(mainController.getMoveValue());

		// These equations are modified versions of the standard exponential form, ab^x + k. 
		// The equations here are of the form d(ab^|x| - k) where d is the respective direction value (note above) and k = -a. 
		// If you wish to alter these equations, adjust b, then solve for a at (1, 1).
		// Greater values of b make the controls less sensitive; smaller values are more sensitive.
		// The REVERSE_MULTIPLIER  constant exists just to make the joystick input properly; upwards on the stick corresponds to -y.
		double expSpeed = REVERSE_MULTIPLIER * verticalDirection * (0.2 * Math.pow(6.0, Math.abs(moveValue)) - 0.2);

		return expSpeed;
	}

	private double getTurnValue() {
		if (mainController == null) {
			return 0;
		}
		
		double turnValue = mainController.getTurnValue();

		int turnDirection = getDirection(turnValue);

		// These equations are modified versions of the standard exponential form, ab^x + k. 
		// The equations here are of the form d(ab^|x| - a) where d is the respective direction value (note above), and k = -a. 
		// If you wish to alter these equations, adjust b, then solve for a at (1, 1).
		// Greater values of b make the controls less sensitive; smaller values are more sensitive.
		// a = 0.25, b = 5
		// a = 0.2, b = 6
		// a = 0.166, b = 7
		// a = 0.1428, b = 8
		// a = 0.125, b = 9
		
		turnValue = turnDirection * (0.125 * Math.pow(9.0, Math.abs(turnValue)) - 0.166);
		
		return turnValue;
	}

	private int getDirection(double n) {
		// Returns either 1, -1, or 0 depending on whether the argument is 
		// positive, negative, or neutral respectively.
		// Returns 0 when given -0 as an argument.

		int result = 0;

		if (n > 0) {
			result = 1;
		}
		else if (n < -0) {
			result = -1;
		}
		else {
			result = 0;
		}

		return result;
	}
}
