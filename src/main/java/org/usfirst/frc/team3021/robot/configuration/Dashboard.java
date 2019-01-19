package org.usfirst.frc.team3021.robot.configuration;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	private static Dashboard instance = null;
	
	private static final String PREF_DASHBOARD_DISPLAY_ENABLED = "Dashboard.display.enabled";
	private static final boolean DASHBOARD_DISPLAY_ENABLE_DEFAULT = false;
	
	private final static boolean enabled = Preferences.getInstance().getBoolean(PREF_DASHBOARD_DISPLAY_ENABLED, DASHBOARD_DISPLAY_ENABLE_DEFAULT);

	public static Dashboard getInstance() {
		if (instance == null) {
			instance = new Dashboard();
		}
		
		return instance;
	}

	public static void putData(Sendable value) {
		// Don't display values if the dashboard is disabled
		if (!enabled) {
			return;
		}
		
		SmartDashboard.putData(value);
	}
	public static void putData(String key, Sendable data) {
		// Don't display values if the dashboard is disabled
		if (!enabled) {
			return;
		}
		
		SmartDashboard.putData(key, data);
	}
	
	public static boolean putString(String key, String value) {
		// Don't display values if the dashboard is disabled
		if (!enabled) {
			return false;
		}
		
		return SmartDashboard.putString(key, value);
	}
	
	public static boolean putNumber(String key, double value) {
		// Don't display values if the dashboard is disabled
		if (!enabled) {
			return false;
		}
		
		return SmartDashboard.putNumber(key, value);
	}

	public static void putCommand(Command command) {
		SmartDashboard.putData(command);
	}
}
