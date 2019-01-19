package org.usfirst.frc.team3021.robot.controller.onboard;

import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.subsystem.DriveSystem;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team3021.robot.configuration.Dashboard;

public class GyroController implements PIDOutput {
	
	private final String PREF_GYRO_USB_ENABLED = "Gyro.usb.enabled";
	private final boolean GYRO_USB_ENABLED_DEFAULT = false;
	
	private final String PREF_GYRO_MXP_ENABLED = "Gyro.mxp.enabled";
	private final boolean GYRO_MXP_ENABLED_DEFAULT = true;
	
	private final String PREF_GYRO_TURN_VALUE_MIN = "Gyro.turn.rate.min";
	private final double GYRO_TURN_VALUE_MIN_DEFAULT = 0.25;
	
	private final String PREF_GYRO_TURN_RATE_MAX = "Gyro.turn.rate.max";
	private final double GYRO_TURN_RATE_DEFAULT = 0.4;
	
	private double turnRateMin = GYRO_TURN_VALUE_MIN_DEFAULT;
	private double turnRateMax = GYRO_TURN_RATE_DEFAULT;

	// Member Attributes
	private AHRS navx;
	private PIDController pidController;
    double rotateToAngleRate;
    
    /* The following PID Controller coefficients will need to be tuned */
    /* to match the dynamics of your drive system.  Note that the      */
    /* Test mode has support for helping you tune    */
    /* controllers by displaying a form where you can enter new P, I,  */
    /* and D constants and test the mechanism.                         */
    
    // Motor tuning values
    static final double kP = 0.01; // Proportion
    static final double kI = 0.00; // Integral 
    static final double kD = 0.02; // Derivative
    static final double kF = 0.00; // Feedback
    
    public static final double kToleranceDegrees = 1.0f; // Tolerance--Precision of turning with the Navx
	
    // Parent drive system reference
    private DriveSystem driveSystem = null;
    
	public GyroController(DriveSystem driveSystem) {
		this.driveSystem = driveSystem;
		
		if (!isGyroEnabled()) {
			DriverStation.reportWarning("WARNING !!! NO GYRO PORT ENABLED", false);
			return;  
		}
		else {
			System.out.println("gyro port enabled");
		}
		
		try {
			if (isUSBEnabled()) {
				// The Navx--connected by USB port
				System.out.println("Using NavX on USB port");
				navx = new AHRS(Port.kUSB, SerialDataType.kProcessedData, (byte) 100); 
			} else {
				// The Navx--connected by MXP port
				System.out.println("Using NavX on MXP port");
				navx = new AHRS(SPI.Port.kMXP, (byte) 100); 
			}
		} catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        
        recalibrateGyro();

        pidController = new PIDController(kP, kI, kD, kF, navx, this, 0.005); 
        
        pidController.setInputRange(-180.0f, 180.0f);
        pidController.setAbsoluteTolerance(kToleranceDegrees);
        pidController.setContinuous(true);

		turnRateMin = Preferences.getInstance().getDouble(PREF_GYRO_TURN_VALUE_MIN, GYRO_TURN_VALUE_MIN_DEFAULT);
        turnRateMax = Preferences.getInstance().getDouble(PREF_GYRO_TURN_RATE_MAX, GYRO_TURN_RATE_DEFAULT);
        
        pidController.setOutputRange(-turnRateMax, turnRateMax);
	}
	
	private boolean isUSBEnabled() {
		return Preferences.getInstance().getBoolean(PREF_GYRO_USB_ENABLED, GYRO_USB_ENABLED_DEFAULT);
	}
	
	private boolean isMXPEnabled() {
		return Preferences.getInstance().getBoolean(PREF_GYRO_MXP_ENABLED, GYRO_MXP_ENABLED_DEFAULT);
	}
	
	private boolean isGyroEnabled() {
		return isUSBEnabled() || isMXPEnabled();
	}

	public boolean isMoving() {
		return navx.isMoving();
	}

	// Input is the range of -180 to 180 to control the PID Controller
	public void setDesiredAngle(double setpoint) {
		
		// if the in coming se tpoint matches the current set point value of pid controller
		// then there is nothing to do and this method can return right away
		if (setpoint == pidController.getSetpoint()) {
			return;
		}
		
		pidController.setSetpoint(setpoint);
	}

	public double getGyroRotation() {
		
		double rotation = navx.getYaw();
		
		Dashboard.putNumber("GyroController : Navx : Rotation", rotation);

		return rotation;
	}

	private double getTurnValue() {

		double turnValue = rotateToAngleRate;
		
		if (Math.abs(turnValue) > turnRateMax) {
			turnValue = turnRateMax;
		}
		
		if (Math.abs(turnValue) < turnRateMin) {
			turnValue = turnRateMin;
		}
		
		turnValue = Math.copySign(turnValue, rotateToAngleRate);
        
        // Stop driving as soon as set point is achieved
        if (isOnTarget()) {
        		turnValue = 0;
        }
		
		Dashboard.putNumber("GyroController : currentRotationRate",  turnValue);

		return turnValue;
	}

	private void recalibrateGyro() {
		System.out.println("Recalibrating gyro [START]");
		
		navx.reset();
		
		Timer.delay(0.250);

		System.out.println("Recalibrating gyro [END]");
	}
	
	public void zeroGyro() {
		System.out.println("Zero the gyro [START]");
		
		navx.zeroYaw();
		
		// wait for the navx to complete the zero of the yaw value
		int checkCount = 0;
		
		while (Math.abs(getGyroRotation()) > 2.0) {
			checkCount++;

			driveSystem.stop();
			
			// End the checking after a given number of checks
			if (checkCount >= 100) {
				DriverStation.reportError("breaking from zero gyro", false);
				break;
			}
			
			Timer.delay(0.005);
		}
		
		System.out.println("Zero the gyro [END]");
	}
	
	public void enable() {
		System.out.println("Enable the gyro");
		
		pidController.enable();
	}
	
	public void reset() {
		System.out.println("Disable the gyro");
		
		pidController.reset();
	}
	
	public boolean isOnTarget() {
		return pidController.onTarget();
	}
	
    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double rotateToAngleRate) {
        this.rotateToAngleRate = rotateToAngleRate;
        
        double turnValue = getTurnValue();
        
		ArcadeDriveInput input = new ArcadeDriveInput(0, turnValue);
		
		driveSystem.drive(input);
    }   
}