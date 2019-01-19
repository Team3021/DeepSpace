package org.usfirst.frc.team3021.robot.controller.station;

public class ButtonAction {
	private int number;
	private String name;
	private String action;
	
	public ButtonAction(int number, String name, String action) {
		this.number = number;
		this.name = name;
		this.action = action;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getAction() {
		return action;
	}
	
	@Override
	public String toString() {
		return "ButtonAction { 'number':'" + number + "', 'name':'" + name +", 'action':'" + action + "' }";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ButtonAction)) {
			return false;
		}
		
		ButtonAction toCompare = (ButtonAction) obj;
		
		return this.action.equals(toCompare.action);
	}
}
