/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/

/**

 * NIgel v0.1 (starting from scratch)

 **/

package org.usfirst.frc.team6110.robot;



import com.ctre.CANTalon; 
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//http://first.wpi.edu/FRC/roborio/beta/docs/java/edu/wpi/first/wpilibj/drive/DifferentialDrive.html
//^ Differential drive documentation


/**

 * The VM is configured to automatically run this class, and to call the

 * functions corresponding to each mode, as described in the IterativeRobot

 * documentation. If you change the name of this class or the package after

 * creating this project, you must also update the build.properties file in the

 * project.

 */



public class Robot extends IterativeRobot {

	

	private static final String kDefaultAuto = "Default";

	private static final String kCustomAuto = "My Auto";

	private String m_autoSelected;

	private SendableChooser<String> m_chooser = new SendableChooser<>();


	

	//Declaring joysticks and motor controls
	CameraServer server;

	Joystick joystick = new Joystick(0); // 1 --> 0 JB
	

	//Setting up 4 Motor drivetrain variables:	//	JB
	VictorSP m_0 = new VictorSP(0);

	WPI_TalonSRX m_1 = new WPI_TalonSRX(1); //	port 0
	
	WPI_TalonSRX m_2 = new WPI_TalonSRX(2); //	JB

	WPI_TalonSRX m_3 = new WPI_TalonSRX(3); //	JB
	
	Spark m_4 = new Spark(1);
	/*
	WPI_TalonSRX m_5 = new WPI_TalonSRX(5);
	
	WPI_TalonSRX m_6 = new WPI_TalonSRX(6);
	
	WPI_TalonSRX m_7 = new WPI_TalonSRX(7);
	
	WPI_TalonSRX m_8 = new WPI_TalonSRX(8);
	
	WPI_TalonSRX m_9 = new WPI_TalonSRX(9);
	*/
	//Variables
	
	
	double launch = 0.5;
	double speed = 1;
	double autospeed = 0.75;
	boolean teleop;
	boolean auto;
	String side = "center";
	String gamedata;


	

	 /**

	 * This function is run when the robot is first started up and should be

	 * used for any initialization code.

	 */

	@Override

	public void robotInit() {

		m_chooser.addDefault("Default Auto", kDefaultAuto); 

		m_chooser.addObject("My Auto", kCustomAuto); 

		SmartDashboard.putData("Auto choices", m_chooser);

		teleop = false; //	Autonomous will always begin first so this is set to false
		auto = false;
		
		server = CameraServer.getInstance();
		server.startAutomaticCapture();
	}



	/**

	 * This autonomous (along with the chooser code above) shows how to select

	 * between different autonomous modes using the dashboard. The sendable

	 * chooser code works with the Java SmartDashboard. If you prefer the

	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the

	 * getString line to get the auto name from the text box below the Gyro

	 *

	 * <p>You can add additional auto modes by adding additional comparisons to

	 * the switch structure below with additional strings. If using the

	 * SendableChooser make sure to add them to the chooser code above as well.

	 */

	

	

	@Override

	public void autonomousInit() {

		auto = true;
		
		m_autoSelected = m_chooser.getSelected();

		// autoSelected = SmartDashboard.getString("Auto Selector",

		// defaultAuto);

		System.out.println("Auto selected: " + m_autoSelected);
		
		gamedata = DriverStation.getInstance().getGameSpecificMessage();
		
		//Timer.reset();
	}


	

	/**

	 * This function is called periodically during autonomous.

	 */

	

	@Override

	public void autonomousPeriodic() {
		while(auto == true) {
			
			if(gamedata.charAt(0) == 'L' && side.equals("center")) {
				m_0.set(autospeed);
				m_1.set(autospeed);
				m_2.set(autospeed);
				m_3.set(autospeed);
			}

		} 
		
		
	}



	

	//The teleopInit method is called once each time the robot enters teleop mode

	@Override

	public void teleopInit() {

		

		//Check if this function is called so that the next function will be enabled

		teleop = true;

	}

	

	/**

	 * This function is called periodically during operator control.

	 */

	

	@Override

	public void teleopPeriodic() {

		while(teleop == true /*isEnabled() && isOperatorControl()*/) { //while being used


			//Setting up Joy stick Axes for Tank Drive (much cleaner to set variables)

			double js_Left = joystick.getRawAxis(1); //	Left Joystick

			double js_Right = joystick.getRawAxis(5); //	Right Joystick

			
			leftdrive(js_Left);
			rightdrive(js_Right);
			
			

			Timer.delay(0.01); //	JB

			if(joystick.getRawAxis(3) > 0.5) {
				m_4.set(0.5);
				//m_5.set(0.5);
			}
			else if(joystick.getRawAxis(2) > 0.75) {
				//m_5.set(-0.5);
				//m_6.set(-0.5);
			}
			else if(joystick.getRawButton(6)) {
				//m_7.set(launch);
				//m_8.set(launch);
			}
			else if(joystick.getRawButton(1)) {
				speed = 1;
			}
			else if(joystick.getRawButton(2)) {
				speed = 0.25;
			}
			else if(joystick.getRawButton(3)) {
				speed = 0.5;
			}
			else if(joystick.getRawButton(4)) {
				speed = 0.75;
			}
			else {
				/*
				 * m_4.set(0);
				 * m_5.set(0);
				 * m_6.set(0);
				 * m_7.set(0);
				 * m_8.set(0);
				 * m_9.set(0);
				 * 
				 */
			}
			

		}

	}



	/**

	 * This function is called periodically during test mode.

	 */

	

	/*

	@Override

	public void testPeriodic() {

	}

	*/
	
	public void leftdrive(double d) {
		if (d <= 0.2 && d >= -0.2) {
		m_0.set(0);
		m_1.set(0);
		m_4.set(0);
		}
		else {
		m_0.set(-d * speed);
		m_1.set(-d * speed);
		m_4.set(d * speed);
		}
		
	}
	
	public void rightdrive(double d) {
		if (d <= 0.2 && d >= -0.2) {
		m_2.set(0);
		m_3.set(0);
		}
		else {
		m_2.set(-d * speed);
		m_3.set(-d * speed);
		}
	}

}

