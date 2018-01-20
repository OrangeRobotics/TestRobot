package org.usfirst.frc.team1787.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	private DifferentialDrive drive;
	
	private WPI_TalonSRX frontRight, frontLeft, rearRight, rearLeft;
	
	Joystick rightStick, leftStick;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Robot is inializing
		
		//Initialize talons
		frontRight = new WPI_TalonSRX(8);
		frontLeft = new WPI_TalonSRX(6);
		frontLeft.setInverted(true);
		
		rearRight = new WPI_TalonSRX(9);
		rearRight.follow(frontRight);
		
		rearLeft = new WPI_TalonSRX(7);
		rearLeft.setInverted(true);
		rearLeft.follow(frontLeft);
		
		drive = new DifferentialDrive(frontLeft, frontRight);
		
		//Control the robot
		this.rightStick = new Joystick(0);
		this.leftStick = new Joystick(1);

		//Done!
		System.out.println("Robot init :)");
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//Use arcade controls
		drive.arcadeDrive(rightStick.getY(), -rightStick.getX());
		
	}
	
	

	@Override
	public void teleopInit() {
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}

	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
	}

	
}

