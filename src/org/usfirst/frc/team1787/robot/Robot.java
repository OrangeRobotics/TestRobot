package org.usfirst.frc.team1787.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
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

	private DriveTrain drive;
	
	public CameraServer cameraServer;
	
	public UsbCamera camera;
	
	Joystick rightStick, leftStick;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Robot is inializing
		
		//Create drive train
		drive = new DriveTrain(8, 9, 6, 7);
		
		//Get joysticks
		rightStick = new Joystick(0);
		leftStick = new Joystick(1);
		
		//Get the camera server
		cameraServer = CameraServer.getInstance();
		
		//Get the camera
		camera = new UsbCamera("Camera", 1);
		camera.setBrightness(50);
		camera.setExposureAuto();
		
		cameraServer.startAutomaticCapture(camera);

		//Done!
		System.out.println("Robot init :)");
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		drive.drive(-rightStick.getY(), rightStick.getX());
		drive.publishData();
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
		Preferences prefs = Preferences.getInstance();
		
		double p = prefs.getDouble("p", 0);
		double i = prefs.getDouble("i", 0);
		double d = prefs.getDouble("d", 0);
		
		//Set pid args
		drive.getLeftPID().setPID(p, i, d);
		drive.getRightPID().setPID(p, i, d);
		
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		drive.publishData();
		
		Preferences prefs = Preferences.getInstance();
		
		//Get setPoint
		double setPoint = prefs.getDouble("setPoint", 0);
		
		//Drive
		drive.driveDistance(setPoint);
	}

	
}

