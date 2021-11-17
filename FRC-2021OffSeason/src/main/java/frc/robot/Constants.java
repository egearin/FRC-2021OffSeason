/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/*import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;*/

/**
 * nondetermined constants
 */
public final class Constants {
    //Motor Ports 
    public static final int driveOneLeftMotorPort = 1;
    public static final int driveTwoLeftMotorPort = 2;
    public static final int driveThreeLeftMotorPort = 3;
    public static final int driveOneRightMotorPort = 4;
    public static final int driveTwoRightMotorPort = 5;
    public static final int driveThreeRightMotorPort = 6;

    //Conveyor
    public static final int conveyorMotorPort = 6;

    //Intake
    public static final int pivotMotorPort = 3;
    public static final int centerRightMotorPort = 4;
    public static final int centerLeftMotorPort = 2;
    public static final int intakeMotorPort = 5;
    public static final int climberMotorPort = 7;

    //Shooter
    public static final int feederMotorPort = 8;
    public static final int acceleratorWheelMotorPort = 1;
    public static final int shooterWheelMotorPort = 0;

    // Pigeon Port
    public static final int pigeonPort = 8;

     //Encoder Ports
     public static final int shooterEncPort1 = 12;//
     public static final int shooterEncPort2 = 13; //
     
     public static final int accEncPort1 = 10; // ok enc2
     public static final int accEncPort2 = 11; // ok

    //Encoder Directions
    public static final boolean shooterEncDirection = true;
    public static final boolean accEncDirection = true;
    
    //Distance per pulse values
    public static final double shooterEncDistancePerPulse = 0.00277777777;
    public static final double accEncDistancePerPulse = 0.00277777777;
    public static final double wheelPerimeter = 0.31918581360472299303;
    public static final double encoderCPR = 360;
    
    //PID Constants
    public static final double kShooterP = 0.0314;
    public static final double kShooterI = 0;
    public static final double kShooterD = 0.00001;
    
    public static final double kAccP = 0.0144;
    public static final double kAccI = 0;
    public static final double kAccD = 0.00001;
    
    public static final double kDriveP = 6.61;
    public static final double kDriveI = 0;
    public static final double kDriveD = 2.86;

        
    //Feedforward Constants
    public static final double kShooterS = 1.06;
    public static final double kShooterV = 0.127;
    public static final double kShooterA = 0.00157;

    public static final double kAccS = 0.772;
    public static final double kAccV = 0.102;
    public static final double kAccA = 0.000977;

    /*//Shooter Constants
    public static final double kShooterMaxPower = 0;
    public static final double kFeederMaxPower = 0;
    public static final double kAcceleratorMaxPower = 0;
    public static final double kShootingAngle = 0;*/

    // Gamepad Constants
    public static final double kSensivity = 0.1;//0.75 or 1
}
