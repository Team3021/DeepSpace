package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class StowTote extends CollectorCommand {
	int duration;
		public StowTote(int duration) {
			super();
			this.duration = duration;
		}
		
		@Override
		protected void execute() {
			collectorSystem.stow();
		}

		@Override
		protected void end() {
			System.out.println("Done stowing.");
		}

		@Override
		protected boolean isFinished() {
			return timeSinceInitialized() >= duration;
		}

	}