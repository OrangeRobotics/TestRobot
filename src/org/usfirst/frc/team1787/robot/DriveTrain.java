package org.usfirst.frc.team1787.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public final class DriveTrain {
	
	private final WPI_TalonSRX frontRight;
	private final WPI_TalonSRX rearRight;
	
	private final WPI_TalonSRX frontLeft;
	private final WPI_TalonSRX rearLeft;
	
	public DriveTrain(int frontRight, int rearRight, int frontLeft, int rearLeft) {
		
		//Get right talons
		this.frontRight = new WPI_TalonSRX(frontRight);
		this.rearRight = new WPI_TalonSRX(rearRight);
		
		//Get left talons
		this.frontLeft = new WPI_TalonSRX(frontLeft);
		this.rearLeft = new WPI_TalonSRX(rearLeft);
		
		//Invert left talons
		this.frontLeft.setInverted(true);
		this.rearLeft.setInverted(true);
		
	}
	
}
