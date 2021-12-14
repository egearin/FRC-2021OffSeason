/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * motor ports, encoder ports and constants
*/
public final class Constants {
    // Motor Ports
    public static final int driveOneLeftMotorPort = 1;
    public static final int driveTwoLeftMotorPort = 2;
    public static final int driveThreeLeftMotorPort = 3;
    public static final int driveOneRightMotorPort = 4;
    public static final int driveTwoRightMotorPort = 5;
    public static final int driveThreeRightMotorPort = 6;

    // Conveyor
    public static final int conveyorMotorPort = 5;

    // Intake
    public static final int pivotMotorPort = 7;
    public static final int centerRightMotorPort = 4;
    public static final int centerLeftMotorPort = 0;
    public static final int intakeMotorPort = 3;
    public static final int climberMotorPort = 8;

    // Shooter
    public static final int feederMotorPort = 10;
    public static final int acceleratorWheelMotorPort = 1;
    public static final int shooterWheelMotorPort = 2;

    // Pigeon Port
    public static final int pigeonPort = 8;

    // Encoder Ports
    public static final int shooterEncPort1 = 16;//
    public static final int shooterEncPort2 = 17; //

    public static final int accEncPort1 = 14; // ok enc2
    public static final int accEncPort2 = 15; // ok

    // Encoder Directions
    public static final boolean shooterEncDirection = true;
    public static final boolean accEncDirection = true;

    // Distance per pulse values
    public static final double shooterEncDistancePerPulse = 0.00277777777;
    public static final double accEncDistancePerPulse = 0.00277777777;
    public static final double wheelPerimeter = 0.95755744081416897908;// unnecessary
    public static final double encoderCPR = 360;
    public static final double ballRadius = 9.0;

    // PID Constants
    public static final double kShooterP = 0.0314;
    public static final double kShooterI = 0;
    public static final double kShooterD = 0.00001;

    public static final double kAccP = 0.0144;
    public static final double kAccI = 0;
    public static final double kAccD = 0.00001;

    public static final double kDriveP = 6.61;
    public static final double kDriveI = 0;
    public static final double kDriveD = 2.86;

    // Feedforward Constants
    public static final double kShooterS = 1.06;
    public static final double kShooterV = 0.127;
    public static final double kShooterA = 0.00157;

    public static final double kAccS = 0.772;
    public static final double kAccV = 0.102;
    public static final double kAccA = 0.000977;

    // Gamepad Constants
    public static final double kSensivity = 0.75;// 0.75 or 1

    // Vision Constants
    public static final double limelightHeightFromGround = 0.57; // to be changed,6 inch wheel
    public static final double limelightAngle = 40; // Limelight angle of repose
    public static final double visionTargetHeightFromGround = 2.29;
    public static final double limelightBlobAreaEstimation = 0;

    // Field Constants
    public static final Translation2d kVisionTargetPos = new Translation2d(0, -2.5);
    public static final Translation2d ballPoint = new Translation2d(0, 0); // fill out real values
    //public static final double initiationLineX = 3.1;

    // Auto Constants
    public static final double blindmps  = 0; // Meter per second in 0.5 power
}
