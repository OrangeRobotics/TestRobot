package org.usfirst.frc.team1787.robot;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DriveTrain {
	
	//Right motor controllers
	private final WPI_TalonSRX frontRight;
	private final WPI_TalonSRX rearRight;
	
	//Left motor controllers
	private final WPI_TalonSRX frontLeft;
	private final WPI_TalonSRX rearLeft;
	
	//Encoders
	private final Encoder rightEncoder;
	private final Encoder leftEncoder;
	
	//Constants
	private final double METERS_PER_PULSE = 0.0254 * 0.0149846;
	
	//PID controller
	private final PIDController leftPID;
	private final PIDController rightPID;
	
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
		
		//Get encoders
		this.rightEncoder = new Encoder(0, 1);
		this.leftEncoder = new Encoder(2, 3);
		
		//Set encoder distance per pulse
		rightEncoder.setDistancePerPulse(METERS_PER_PULSE);
		leftEncoder.setDistancePerPulse(METERS_PER_PULSE);
		
		//Get pid
		this.rightPID = new PIDController(0, 0, 0, rightEncoder, frontRight);
		this.leftPID = new PIDController(0, 0, 0, leftEncoder, frontLeft);
		
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
		
		//Disable PIDs
		rightPID.setEnabled(false);
		leftPID.setEnabled(false);
		
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
	
	public void driveDistance(double meters) {
		if (!rightPID.isEnabled() || !leftPID.isEnabled()) { //Check if PIDs are enabled
			//Reset encoders
			rightEncoder.reset();
			leftEncoder.reset();
			
			//Enable PIDs
			rightPID.setEnabled(true);
			leftPID.setEnabled(true);
			
			//Set the setpoint
			rightPID.setSetpoint(meters);
			leftPID.setSetpoint(meters);
		}
	}
	
	public Encoder getRightEncoder() {
		return rightEncoder;
	}
	
	public Encoder getLeftEncoder() {
		return leftEncoder;
	}

	public void publishData() {
		SmartDashboard.putData("Right Encoder", getRightEncoder());
		SmartDashboard.putData("Left Encoder", getLeftEncoder());
		SmartDashboard.putData("Right motor", frontRight);
		SmartDashboard.putData("Left motor", frontLeft);
		SmartDashboard.putData("left pid", leftPID);
		SmartDashboard.putData("right PID", rightPID);
	}

	public PIDController getRightPID() {
		return rightPID;
	}

	public PIDController getLeftPID() {
		return leftPID;
	}
	
//	public Enum DriveConst {
//		
//	}
	
}
