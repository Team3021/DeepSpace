package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class DeployTote extends CollectorCommand {
	int duration;
		public DeployTote(int duration) {
			super();
			this.duration = duration;
		}
		
		@Override
		protected void execute() {
			collectorSystem.deploy();
		}

		@Override
		protected void end() {
			System.out.println("Done deploying.");
		}

		@Override
		protected boolean isFinished() {
			return timeSinceInitialized() >= duration;
		}

	}