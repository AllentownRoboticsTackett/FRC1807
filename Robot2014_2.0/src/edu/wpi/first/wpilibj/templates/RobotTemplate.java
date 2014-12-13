/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder; 
//import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Gyro;        
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.Accelerometer;  
import edu.wpi.first.wpilibj.DigitalInput; 
import edu.wpi.first.wpilibj.Compressor;  
//import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
//import edu.wpi.first.wpilibj.  
import edu.wpi.first.wpilibj.AnalogChannel;        
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    
    //PWM port numbers for assigned motors
    public int leftFrontMotorPort = 1;
    public int leftRearMotorPort = 2;
    public int rightFrontMotorPort = 3;
    public int rightRearMotorPort = 4;
    public int shooter1Port = 5;
    public int shooter2Port = 6;
    public int leftArmMotorPort = 7;
    public int rightArmMotorPort = 8;
    
    //solenoid
    
    public Solenoid shooterSolenoidIn = new Solenoid(1);
    //public Solenoid shooterSolenoidOut = new Solenoid(2); 
    public Solenoid collectionSolenoidIn = new Solenoid(3);
    public Solenoid collectionSolenoidOut = new Solenoid(4);
    public Solenoid latchSolenoidIn = new Solenoid(5);
    //public Solenoid latchSolenoidOut = new Solenoid(6);
    /*public Solenoid leftArmSolenoidIn = new Solenoid(4);
    public Solenoid leftArmSolenoidOut = new Solenoid(3);
    public Solenoid rightArmSolenoidIn = new Solenoid(7);
    public Solenoid rightArmSolenoidOut = new Solenoid(8);*/
    //DigitalInput pressureLimit = new DigitalInput(6);
    public Compressor compressor;
    //^^^PLUG PRESSURE SWITCH INTO 6!!
    
    
    //USB port number assignments for joysticks/buttons/etc.
    Joystick leftJoy = new Joystick(1);
    Joystick rightJoy = new Joystick(2);
    Joystick buttons = new Joystick(3);
    Joystick driveController = new Joystick(4);
    public int chargeButton = 1;
    public int releaseButton = 2;
    public int armUpButton = 3;
    public int armDownButton = 4;
    public int beltToggleOut = 5;
    public int beltToggleIn = 6;
    public int latchToggle = 7;
    
    //Here the motors and their respective encoders are constructed
    public Jaguar leftFrontMotor = new Jaguar(leftFrontMotorPort);
    public Jaguar leftBackMotor = new Jaguar(leftRearMotorPort);
    public Encoder leftDriveEncoder = new Encoder(1,2);
    public Double leftDriveRPM;
    public Jaguar rightFrontMotor = new Jaguar(rightFrontMotorPort);
    public Jaguar rightBackMotor = new Jaguar(rightRearMotorPort);
    public Encoder rightDriveEncoder = new Encoder(3,4);
    public Double rightDriveRPM;
    public Jaguar leftArm = new Jaguar(leftArmMotorPort);
    public Jaguar rightArm = new Jaguar(rightArmMotorPort);
    public Talon shooter1 = new Talon(shooter1Port);
    public Talon shooter2 = new Talon(shooter2Port);
    
    
    //Sensors and associated variables
    public Gyro angleGyro = new Gyro(1,1);
    public double Kp = .03;
    public double angle;
    DigitalInput shooterLimit = new DigitalInput(5);
    
    //Misc
    
    public RobotDrive drive = new RobotDrive(leftFrontMotor,leftBackMotor,rightFrontMotor,rightBackMotor);
    //public RobotDrive drive = new RobotDrive(leftFrontMotor,rightFrontMotor);
    public double shooterPullbackSpeed = .7;
    public double shooterHoldSpeed = .2;
    public double armBeltSpeed = .8;
    public Timer time = new Timer();
    
    //auto
   //public AnalogChannel rangeFinder = new AnalogChannel(1,1);
    public Timer autoTimer = new Timer();
    public double shotDistance = 1200;
    public double driveForwardEndTime;
    public double pullbackEndTime;
    
    //Declares the other classes so they can be used in this template
    LCD LCD = new LCD(
        leftDriveEncoder, 
        rightDriveEncoder);
    
    Shooter Shooter = new Shooter (
        shooter1, 
        shooter2,
        buttons,
        driveController,
        shooterLimit,
        shooterPullbackSpeed, 
        shooterHoldSpeed, 
        chargeButton, 
        releaseButton,
        latchToggle,
        shooterSolenoidIn, 
        //shooterSolenoidOut,
        latchSolenoidIn, 
        //latchSolenoidOut,
        time);
    
    Collection Collection = new Collection(
        buttons,
        driveController,
        leftArm,
        rightArm,
        armBeltSpeed, 
        armDownButton, 
        armUpButton, 
        beltToggleIn, 
        beltToggleOut,
        collectionSolenoidIn,
        collectionSolenoidOut);
        
    
    public void robotInit() {
        compressor = new Compressor(6,1);
        compressor.start();
        leftDriveEncoder.start();
        rightDriveEncoder.start();
        angleGyro.reset();
        time.start();
        //Solenoids start In
        //shooterSolenoidIn.set(true);
        //shooterSolenoidOut.set(false);
        //collectionSolenoidIn.set(true);
        //collectionSolenoidOut.set(false);
        //latchSolenoidIn.set(true);
        //latchSolenoidOut.set(false);
        
    }

    /**
     * This function is called periodically during autonomous
     */
    
    public void autonomousInit() {
        autoTimer.reset();
        autoTimer.start();
    }
    public void autonomousPeriodic() {
       if(autoTimer.get() < 2) {
           drive.drive(.5, 0);
       }
        
        /* if (rangeFinder.getAverageVoltage()> shotDistance) {
            drive.drive(.5,0);
            driveForwardEndTime = autoTimer.get();
        }
        else {
           if (driveForwardEndTime+.2 > autoTimer.get()) {
               drive.drive(-.5,0);
           }
           else if (driveForwardEndTime+2 > autoTimer.get() && shooterLimit.get() == false) {
               shooter1.set(shooterPullbackSpeed);
               shooter2.set(shooterPullbackSpeed);
               pullbackEndTime = autoTimer.get();
           }
           else if (pullbackEndTime+.2 > autoTimer.get()){
               shooterSolenoidIn.set(false);
           }
           else {
               latchSolenoidIn.set(false);
           }
            
         */   
        }
        
        
    

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
        latchSolenoidIn.set(true);
        shooterSolenoidIn.set(true);
        
    }
    public void teleopPeriodic() {
        drive.tankDrive(-leftJoy.getRawAxis(2), -rightJoy.getRawAxis(2));
        //LCD.poll();
        //Shooter.poll();
        //Collection.poll();
        if(driveController.getRawButton(3) /*&& shooterLimit.get() == false*/){
            shooter1.set(.7);
            shooter2.set(.7);
            //shooterSolenoidIn.set(false);
            //shooterSolenoidOut.set(false);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "good");
        }else  {
            shooter1.set(0);
            shooter2.set(0);
        }
        //Holds the shootyer by putting the motors at a lower power
        //(buttons.getRawButton(releaseButton) == true)
        if(driveController.getRawButton(4)){
            shooterSolenoidIn.set(false);
        }else{
            shooterSolenoidIn.set(true);
        }
        //pushes out latch
        //(buttons.getRawButton(latchToggle) == true)
        //latchToggle used to be the saftey toggle
        if (driveController.getRawButton(1)) {
            latchSolenoidIn.set(false);
           // latchSolenoidOut.set(true);
        }
        else {
            latchSolenoidIn.set(true);
            //latchSolenoidOut.set(false);
        }
        leftArm.set(driveController.getRawAxis(2));
        rightArm.set(driveController.getRawAxis(2));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }    
}
