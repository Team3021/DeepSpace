package org.usfirst.frc.team3021.robot.controller.station;

import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;

public class DefaultController extends BaseController {
	
	public DefaultController(ControllerConfiguration configuration, int port) {
		super(configuration, port);
	}

	@Override
	protected void setButtons() {
		// No button mappings for the default controller
	}

	@Override
	public boolean getRawButton(String action) {
		return false;
	}
}