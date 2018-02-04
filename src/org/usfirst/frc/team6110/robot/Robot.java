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
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;

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

	AHRS accel; // the nav-x micro ahrs

	private static final String kDefaultAuto = "Default";

	private static final String kCustomAuto = "My Auto";

	private String m_autoSelected;

	private SendableChooser<String> m_chooser = new SendableChooser<>();


	

	//Declaring joysticks and motor controls
	CameraServer server;

	Joystick joystick = new Joystick(0); // declaring xbox controller
	

	//Setting up 4 Motor drivetrain variables
	VictorSP m_0 = new VictorSP(0);
	WPI_TalonSRX m_1 = new WPI_TalonSRX(1);
	WPI_TalonSRX m_2 = new WPI_TalonSRX(2);
	WPI_TalonSRX m_3 = new WPI_TalonSRX(3);
	
	//declaring cannon motors
	Spark m_4 = new Spark(4);
	Spark m_5 = new Spark (5);
	Spark m_6 = new Spark (6);
	Spark m_7 = new Spark (7);
	
	//declaring arm motors
	VictorSP m_8 = new VictorSP(8);
	VictorSP m_9 = new VictorSP(9);
	
	//declaring timer for auto
	Timer timer = new Timer();

	
	//Variables
	double launch = 0.5; //cannon stength
	double speed = 1;
	double autospeed = -0.75; //starting NIgel backwards in auto
	boolean teleop;
	boolean auto;
	boolean timerReset = false;
	String StartP = "center"; //starting position of NIgel, will need to be adjusted before games
	//FMS String
	String gamedata;
	
	//yaw axis for navx
	float rotate = accel.getYaw();
	


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
		//setting up camera
		server = CameraServer.getInstance();
		server.startAutomaticCapture();
		
		//finding our navx
		try {
			accel = new AHRS(SerialPort.Port.kUSB1, AHRS.SerialDataType.kProcessedData, (byte) 200);
		}
		
		catch (RuntimeException ex){
			DriverStation.reportError("NAVX MICRO Not found" + ex.getMessage(), true);
		}
		accel.reset();
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
		timerReset = false;
		auto = true;
		
		m_autoSelected = m_chooser.getSelected();

		// autoSelected = SmartDashboard.getString("Auto Selector",

		// defaultAuto);

		System.out.println("Auto selected: " + m_autoSelected);
		
		//receiving FMS message
		gamedata = DriverStation.getInstance().getGameSpecificMessage();
		//starting timer for auto
		timer.start();		
	}


	

	/**
	 * This function is called periodically during autonomous.
	 */

	

	@Override

	public void autonomousPeriodic() {
		while(auto == true   && timer.get() < 15) 
		{	
			if(timerReset == false) 
			{
				timer.reset();
				timerReset = true;
			}
			
			SmartDashboard.putString("DB/Sting 1", String.valueOf(accel.getYaw()));
			
			rotate = accel.getYaw();
			
			char side = gamedata.charAt(0);		
			
			if(side == 'L' && StartP.equals("center")) {
				
			}
			else if(side == 'L' && StartP.equals("right")) {
				
			}
			else if(side == 'L' && StartP.equals("left")) {
	
			}
			else if(side == 'R' && StartP.equals("center")) {
				
			}
			else if(side == 'R' && StartP.equals("right")) {
				
			}
			else if(side == 'R' && StartP.equals("left")) {
				
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

	//putting sparks in a method as they all will be going at the same time w/ same value
	public void fireSpark(double value) {
		m_4.set(value);
		m_5.set(value);
		m_6.set(value);
		m_7.set(value);
	}

	@Override

	public void teleopPeriodic() {
			//refreshing the yaw value of navx
			RefreshDashboard();

			//Setting up Joy stick Axes for Tank Drive (much cleaner to set variables)
			double js_Left = joystick.getRawAxis(1); //	Left Joystick

			double js_Right = joystick.getRawAxis(5); //	Right Joystick

			//driving with the values of the joysticks
			leftdrive(js_Left);
			rightdrive(js_Right);
			

			Timer.delay(0.01);
			
			double rTrigger = joystick.getRawAxis(3);
			double lTrigger = joystick.getRawAxis(2);
			
			if( rTrigger > 0.5) {
				m_8.set(rTrigger);
				m_9.set(rTrigger);
				
			}
			else if(lTrigger > 0.5) {
				m_8.set(-lTrigger);
				m_9.set(-lTrigger);
			}
			else if(joystick.getRawButton(6)) {
				fireSpark(launch);
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
				//all motors must be put in else statement as 0 so that they dont move when called
				fireSpark(0);
				m_8.set(0);
				m_9.set(0);
			}
			

		}



	/**
	 * This function is called periodically during test mode.
	 */

	public void RefreshDashboard() {
		SmartDashboard.putString("DB/String 0", "YAW:");
		SmartDashboard.putString("DB/String 5", String.valueOf(accel.getYaw()));
	}
	

	

	@Override

	public void testPeriodic() {

	}

	
	
	public void leftdrive(double d) {
		if (d <= 0.2 && d >= -0.2) {
		m_0.set(0);
		m_1.set(0);
		}
		else {
		m_0.set(-d * speed);
		m_1.set(-d * speed);
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
