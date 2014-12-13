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
//import edu.wpi.first.wpilibj.Compressor;  
//import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;  

/**
 *
 * @author Developer
 */
public class Collection{
    
    Joystick buttons;
    Joystick driveController;
    int armUpButton;
    int armDownButton;
    int beltToggleOut;
    int beltTogglein;
    Jaguar leftArm;
    Jaguar rightArm;
    double armBeltSpeed;
    Solenoid collectionSolenoidIn;
    Solenoid collectionSolenoidOut;

    public Collection(
        Joystick Buttons,
        Joystick DriveController,
        Jaguar LeftArm,
        Jaguar RightArm,
        double ArmBeltSpeed,
        int ArmDownButton,
        int ArmUpButton,
        int BeltToggleIn,
        int BeltToggleOut,
        Solenoid CollectionSolenoidIn,
        Solenoid CollectionSolenoidOut)  
        {
            buttons = Buttons;
            armUpButton = ArmUpButton;
            armDownButton = ArmDownButton;
            beltToggleOut = BeltToggleOut;
            beltTogglein = BeltToggleIn;
            leftArm = LeftArm;
            rightArm = RightArm;
            armBeltSpeed = ArmBeltSpeed;
            collectionSolenoidIn = CollectionSolenoidIn;
            collectionSolenoidOut = CollectionSolenoidOut;        
        }
    public void poll(){
        leftArm.set(driveController.getRawAxis(2));
        rightArm.set(driveController.getRawAxis(2));
        
        if(buttons.getRawButton(beltTogglein)){
            leftArm.set(armBeltSpeed);
            rightArm.set(armBeltSpeed);
        }
        if(buttons.getRawButton(beltToggleOut)){
            leftArm.set(-armBeltSpeed);
            rightArm.set(-armBeltSpeed);
        }
    }
    public void armsUp(){
        collectionSolenoidIn.set(true);
        collectionSolenoidOut.set(false);
    }
    public void armsDown(){
        collectionSolenoidIn.set(false);
        collectionSolenoidOut.set(true);
    }
    public void beltsOut(){
        leftArm.set(-armBeltSpeed);
        rightArm.set(-armBeltSpeed);
    }
    public void beltsIn(){
       leftArm.set(armBeltSpeed);
       rightArm.set(armBeltSpeed);
    }
}
