package org.usfirst.frc.team3021.robot.configuration;

import java.util.HashMap;
import java.util.Vector;

public class Preferences {
	
	private static Preferences instance = null;
	
	private HashMap<String, Object> table = new HashMap<String, Object>();
	
	private Preferences() {
		
	}

	public static Preferences getInstance() {
		if (instance == null) {
			instance = new Preferences();
		}
		
		return instance;
	}

	public String getString(String key, String backup) {
		table.put(key, backup);
		
		return edu.wpi.first.wpilibj.Preferences.getInstance().getString(key, backup);
	}

	public double getDouble(String key, double backup) {
		table.put(key, backup);
		
		return edu.wpi.first.wpilibj.Preferences.getInstance().getDouble(key, backup);
	}
	
	public boolean getBoolean(String key, boolean backup) {
		table.put(key, backup);
		
		return edu.wpi.first.wpilibj.Preferences.getInstance().getBoolean(key, backup);
	}
	
	public int getInt(String key, int backup) {
		table.put(key, backup);
		
		return edu.wpi.first.wpilibj.Preferences.getInstance().getInt(key, backup);
	}

	public void printPreferences() {
		System.out.println("******************* Prefernces: DEFAULT *******************");
		
		for (String key : table.keySet()) {
			Object value = table.get(key);
			
			System.out.println(key + " : " + value.toString());
		}
	}
		
	public void printPreferencesOnRobot() {
		edu.wpi.first.wpilibj.Preferences prefs = edu.wpi.first.wpilibj.Preferences.getInstance();
		
		@SuppressWarnings("rawtypes")
		Vector keys = prefs.getKeys();
		
		System.out.println("**************** Prefernces: ON ROBOT ****************");
		
		for (Object obj : keys) {
			String key = null;
			
			// cast from object to String
			if (obj instanceof String) {
				key = (String) obj;
			}
			
			if (key != null) {
				String value = prefs.getString(key, "");
			
				System.out.println(key + " : " + value);
			}
		}
	}
}
