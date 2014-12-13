 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author Developer
 */
public class LCD{
    Encoder leftDriveEncoder;
    Encoder rightDriveEncoder;
    public LCD(Encoder LeftDriveEncoder, Encoder RightDriveEncoder){
        leftDriveEncoder = LeftDriveEncoder;
        rightDriveEncoder = RightDriveEncoder;
}
    public void poll() {
        //Updates the user message box on the driver station with important variables the dribver should know
        DriverStationLCD.getInstance().updateLCD();
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "leftRPM:" + leftDriveEncoder.getRate());
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "rightRPM:" + rightDriveEncoder.getRate());
    } 
}
