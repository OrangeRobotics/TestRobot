package org.usfirst.frc.team1787.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

public final class PneumaticCylinder {
	
	
	//the doublesolenoid used to control the cylinder
	private final DoubleSolenoid solenoid;

	/**
	 * Instantiates a new pneumatic cylinder. To find the solenoid ID's, look at the PCM on the robot.
	 *
	 * @param extendChannel The channel ID of the solenoid used to extend the cylinder
	 * @param retractChannel The channel ID of the solenoid used to retract the cylinder
	 */
	public PneumaticCylinder(int extendChannel, int retractChannel) {
		this.solenoid = new DoubleSolenoid(extendChannel, retractChannel);
		
//		this.solenoid.set(Value.kOff);
	}
	
	/**
	 * Extend the pneumatic cylinder
	 */
	public void extend() {
		this.solenoid.set(Value.kForward);
	}
	
	
	/**
	 * Retract the pneumatic cylinder
	 */
	public void retract() {
		this.solenoid.set(Value.kReverse);
	}

}
