package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.test.MoveBackwardForDistanceTest;
import org.usfirst.frc.team3021.robot.commands.test.MoveForwardEscalateInputTest;
import org.usfirst.frc.team3021.robot.commands.test.MoveForwardForDistanceTest;
import org.usfirst.frc.team3021.robot.commands.test.TurnRightEscalateInputTest;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommandConfiguration extends BaseConfiguration {
	
	private final String COMMAND_GROUP = "Tests";

	private  final String PREF_TEST_COMMANDS_ENABLED = "Config.test.commands.enabled";
	private  final boolean TEST_COMMANDS_ENABLED_DEFAULT = false;
	
	private List<Command> commands = new ArrayList<Command>();
	
	private boolean enabled = true;
	
	public TestCommandConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_TEST_COMMANDS_ENABLED, TEST_COMMANDS_ENABLED_DEFAULT);

		addCommandsToDashboard();
	}
	
	private void addCommandsToDashboard() {
		commands.add(new MoveForwardEscalateInputTest());
		commands.add(new TurnRightEscalateInputTest());
		
		commands.add(new MoveForwardForDistanceTest());
		commands.add(new MoveBackwardForDistanceTest());


		addCommandsToDashboard(COMMAND_GROUP, commands, enabled);
	}
}
