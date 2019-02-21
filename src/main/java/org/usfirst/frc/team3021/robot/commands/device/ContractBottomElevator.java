package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ElevatorCommand;

public class ContractBottomElevator extends ElevatorCommand{

	public ContractBottomElevator() {
		super();
	}

	@Override
	protected void execute() {
		elevatorSystem.contractBottom();
	}

	@Override
	protected void end() {
		System.out.println("Done with the elevator.");
		elevatorSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
