/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.Compressor;  
//import edu.wpi.first.wpilibj.Relay;
//import edu.wpi.first.wpilibj.Solenoid;  

/**
 *
 * @author Developer
 */
public class Shooter{
    
    Talon shooter1;
    Talon shooter2;
    Joystick buttons;
    Joystick driveController;
    DigitalInput shooterLimit;
    double shooterPullbackSpeed;
    double shooterHoldSpeed;
    int chargeButton;
    int releaseButton;
    int latchToggle;
    Solenoid shooterSolenoidIn;
    //Solenoid shooterSolenoidOut;
    Solenoid latchSolenoidIn;
    //Solenoid latchSolenoidOut;
    Timer time;
    //boolean outerLatch;
    //boolean innerLatch;
    
    public Shooter(
        Talon Shooter1,
        Talon Shooter2,
        Joystick Buttons,
        Joystick DriveController,
        DigitalInput ShooterLimit,
        double ShooterPullbackSpeed,
        double ShooterHoldSpeed,
        int ChargeButton,
        int ReleaseButton,
        int LatchToggle,
        Solenoid ShooterSolenoidIn,
        //Solenoid ShooterSolenoidOut,
        Solenoid LatchSolenoidIn,
        //Solenoid latchSolenoidOut,
        Timer Time)
        {
            shooter1 = Shooter1;
            shooter2 = Shooter2;
            buttons = Buttons;
            driveController = DriveController;
            shooterLimit = ShooterLimit;
            shooterPullbackSpeed = ShooterPullbackSpeed;
            shooterHoldSpeed = ShooterHoldSpeed;
            chargeButton = ChargeButton;
            releaseButton = ReleaseButton;
            latchToggle = LatchToggle;
            shooterSolenoidIn = ShooterSolenoidIn;
            //shooterSolenoidOut = ShooterSolenoidOut;
            latchSolenoidIn = LatchSolenoidIn;
            time = Time;
        }
    public void poll(){    
        //Charges Shooter
        //If not hitting the base yet
        //(buttons.getRawButton(chargeButton))
        if(driveController.getRawButton(3) /*&& shooterLimit.get() == false*/){
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, "good poll");
            shooter1.set(.7);
            shooter2.set(.7);
            shooterSolenoidIn.set(true);
            //shooterSolenoidOut.set(false);
        }else  {
            shooter1.set(0);
            shooter2.set(0);
        }
        //Holds the shootyer by putting the motors at a lower power
        //(buttons.getRawButton(releaseButton) == true)
        if(buttons.getRawButton(4)){
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
    
    }
}

    
    