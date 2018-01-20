package org.usfirst.frc.team1787.robot;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public final class DriveTrain {
	
	private final WPI_TalonSRX frontRight;
	private final WPI_TalonSRX rearRight;
	
	private final WPI_TalonSRX frontLeft;
	private final WPI_TalonSRX rearLeft;
	
	
	
	public DriveTrain(int frontRightID, int rearRightID, int frontLeftID, int rearLeftID) {
		
		//Get right talons
		this.frontRight = new WPI_TalonSRX(frontRightID);
		this.rearRight = new WPI_TalonSRX(rearRightID);
		
		//Get left talons
		this.frontLeft = new WPI_TalonSRX(frontLeftID);
		this.rearLeft = new WPI_TalonSRX(rearLeftID);
		
		//Invert left talons
		frontLeft.setInverted(true);
		rearLeft.setInverted(true);
		
		//Set rear talons to follow
		rearRight.follow(frontRight);
		rearLeft.follow(frontLeft);
		
		
	}
	
	public void driveForward(double speed) {
		frontRight.set(speed);
		frontLeft.set(speed);
	}
	
	public void turnInPlace(double speed) {
		//Positive is right, negative is left
		frontRight.set(-speed);
		frontLeft.set(speed);
	}
	
	public void drive(double forwardSpeed, double turnSpeed) {
		
		//Adjust speeds
		double dForwardSpeed = forwardSpeed * Math.abs(forwardSpeed);
		double dTurnSpeed = turnSpeed * Math.abs(turnSpeed);
		
		//Get tenative outputs
		double right = dForwardSpeed - dTurnSpeed;
		double left = dForwardSpeed + dTurnSpeed;
		
		//Limit to range -1 < x < 1
		if (right > 1) right = 1;
		if (right < -1) right = -1;
		if (left > 1) left = 1;
		if (left < -1) left = -1;
		
		//Enable voltage compensation
		frontRight.enableVoltageCompensation(true);
		frontLeft.enableVoltageCompensation(true);
		
		//Adjust saturation for voltage compensation
		frontRight.configVoltageCompSaturation(12, 10);
		frontLeft.configVoltageCompSaturation(12, 10);
		
		//Set motor outputs
		frontRight.set(right);
		frontLeft.set(left);
	}
	
}
