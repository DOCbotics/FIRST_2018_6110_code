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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

//	JB

//import edu.wpi.first.wpilibj.RobotDrive;	//	JB

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;	//	JB

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

	/*

	private static final String kDefaultAuto = "Default";

	private static final String kCustomAuto = "My Auto";

	private String m_autoSelected;

	private SendableChooser<String> m_chooser = new SendableChooser<>();

	*/

	

	//Declaring joysticks and motor controls
	/**
	 * I found out what's wrong with the program, turns out they removed all third party libraries
	 * we have to download the talonSRX library
	 * -KL
	 */

	Joystick joystick = new Joystick(0); // 1 --> 0 JB

	/*WPI_TalonSRX rCannon = new WPI_TalonSRX(5);

	WPI_TalonSRX lCannon = new WPI_TalonSRX(6);

	WPI_TalonSRX rArm = new WPI_TalonSRX (7);

	WPI_TalonSRX lArm = new WPI_TalonSRX (8);

	WPI_TalonSRX rPull = new WPI_TalonSRX (9);

	WPI_TalonSRX lPull = new WPI_TalonSRX(10);
	*/

	 

	//RobotDrive NIgel = new RobotDrive(0,1,2,3); //Declaring NIgle and the motor's used for it

	

	//Setting up 4 Motor drivetrain variables:	//	JB

	WPI_TalonSRX m_0 = new WPI_TalonSRX(0); //	port 12

	WPI_TalonSRX m_1 = new WPI_TalonSRX(1); //	port 0

//	SpeedControllerGroup m_Left = new SpeedControllerGroup(m_0, m_1); //	JB
	
	
	WPI_TalonSRX m_2 = new WPI_TalonSRX(2); //	JB

	WPI_TalonSRX m_3 = new WPI_TalonSRX(3); //	JB
	

//	SpeedControllerGroup m_Right = new SpeedControllerGroup(m_2, m_3); //	JB

//	DifferentialDrive NIgel = new DifferentialDrive(m_Left, m_Right); //	JB


	//Variables

	boolean teleop; //	JB

	

	 /**

	 * This function is run when the robot is first started up and should be

	 * used for any initialization code.

	 */

	@Override

	public void robotInit() {

		//m_chooser.addDefault("Default Auto", kDefaultAuto); //	JB

		//m_chooser.addObject("My Auto", kCustomAuto); //	JB

		//SmartDashboard.putData("Auto choices", m_chooser); //	     JB

		teleop = false; //	Autonomous will always begin first so this is set to false JB

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

	

	/*

	@Override

	public void autonomousInit() {

		m_autoSelected = m_chooser.getSelected();

		// autoSelected = SmartDashboard.getString("Auto Selector",

		// defaultAuto);

		System.out.println("Auto selected: " + m_autoSelected);

	}

	*/

	

	/**

	 * This function is called periodically during autonomous.

	 */

	/*

	@Override

	public void autonomousPeriodic() {

		switch (m_autoSelected) {

			case kCustomAuto:

				// Put custom auto code here

				break;

			case kDefaultAuto:

			default:

				// Put default auto code here

				break;

		}

	}

	*/

	

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

			//I greyed out isEnabled and isOperatorControl since

			//we cannot simulate the server sending us these functions.

			//That maybe the reason why the robot did not run at all LOL

			

			//NIgel.tankDrive(control.getRawAxis(2),control.getRawAxis(5)); //Drives NIgel using two analogs on xbox controller

			//Timer.delay(0.001); //note: tankDrive is deprecated, this means it will still work but might not in the future

			

			//Setting up Joy stick Axes for Tank Drive (much cleaner to set variables)	JB

			double js_Left = joystick.getRawAxis(1); //	Left Joystick	JB

			double js_Right = joystick.getRawAxis(5); //	Right Joystick	JB

			if(joystick.getRawButton(1)){
				m_0.set(1.0);
			}
			else {
				m_0.set(0);
			}

			
			leftdrive(joystick.getRawAxis(1));
			rightdrive(joystick.getRawAxis(5));
			
			
			
			
//			NIgel.tankDrive(-js_Left, js_Right); //	Tank Drive	JB

			Timer.delay(0.01); //	JB

			

			/*if(joystick.getRawButton(5)) { //if button pressed, the launch cube, need to change to rTrigger

			lCannon.set(-1);

			rCannon.set(-1);

			}

			else if(joystick.getRawAxis(3) >= 0.75) { //if rTrigger pressed -KL

				rArm.set(-.25);

				lArm.set(-.25);

			}

			else if(joystick.getRawButton(6)) { //if button pressed, close arms

				rArm.set(.25);

				lArm.set(.25);

			}

			else if(joystick.getRawButton(7) ) { //activates wheels to pull cube in

				rPull.set(.33);

				lPull.set(.33);

			}

			else { //motors will continuously run once activated if this else statement isn't here

				lCannon.set(0);

				rCannon.set(0);

				rArm.set(0);

				lArm.set(0);

				rPull.set(0);

				lPull.set(0);
				

			}
			*/
			

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
		m_0.set(-d);
		m_1.set(-d);
	}
	
	public void rightdrive(double d) {
		m_2.set(-d);
		m_3.set(-d);
	}

}

