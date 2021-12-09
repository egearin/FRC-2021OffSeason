// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/**
 * Using Başımdemir's PS4 gamepad(buttons and sense can be changed)
 */
public class Gamepad {

    private static Gamepad mInstance = new Gamepad();
    
    public static Gamepad getInstance(){
        return mInstance;
    }

    public XboxController gamepad;

    public Gamepad(){
        gamepad = new XboxController(0);
    }

    public double getForward(){
        return gamepad.getRawAxis(3);
    }
    
    public double getReverse(){
        return gamepad.getRawAxis(2);
    }

    public double getSteering(){
        return gamepad.getRawAxis(0);
    }

    public double getSensetiveSteering(){
        return gamepad.getRawAxis(4);
    }

    /*public boolean shooterTest(){
        return gamepad.getRawButton(4);
    } */
    //public boolean isShooterSpeedUpPressed(){
        //return gamepad.getRawButtonPressed(4);}
    //public boolean isShooterSpeedUpReleased(){
        //return gamepad.getRawButtonReleased(4);}

    public boolean getStartShooting(){
        return gamepad.getRawButton(3);
    }

    public boolean getIntakeGamepad(){
        return gamepad.getRawButton(1);
    }
    public boolean feederReverse(){
        return gamepad.getRawButton(6);
    }   
    public boolean autoAim(){
      return gamepad.getRawButton(7);
    }
    public boolean getReverseIntakeGamepad(){
        return gamepad.getRawButton(2);
    }
    
    public void forceFeedback(double speed, double rotation){
        double leftRotation;
        double rightRotation;
        if (rotation < 0){
            leftRotation = 0.5 * (Math.abs(rotation) + speed); 
            rightRotation = 0.5 * (Math.abs(speed));
        }
        else{
            leftRotation = 0.5 * Math.abs(speed);
            rightRotation = 0.5 * (Math.abs(rotation) + speed);
        }

        //the most necessary part
        gamepad.setRumble(RumbleType.kLeftRumble, leftRotation);
        gamepad.setRumble(RumbleType.kRightRumble, rightRotation);
    }
}