// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

/**
 * Add your docs here.
*/ 
public class Drive {
    private static Drive mInstance = new Drive();

    public static Drive getInstance(){
        return mInstance;
    }

    public PigeonIMU pigeon;
    public Encoder leftEncoder;
    public Encoder rightEncoder;
    // Master VictorSPX
    public WPI_VictorSPX driveLMaster;
    public WPI_VictorSPX driveRMaster;

    // Follower VictorSPX
    public WPI_VictorSPX driveLTwo;
    public WPI_VictorSPX driveLThree;
    public WPI_VictorSPX driveRTwo;
    public WPI_VictorSPX driveRThree;
    public SpeedControllerGroup leftMotor;
    public SpeedControllerGroup rightMotor;
    public DifferentialDrive differentialDrive;


    private Drive(){
        // EVERYTHING IS IN METERS
        
        driveLMaster = new WPI_VictorSPX(Constants.driveOneLeftMotorPort);
        driveRMaster = new WPI_VictorSPX(Constants.driveOneRightMotorPort);
        driveLTwo = new WPI_VictorSPX(Constants.driveTwoLeftMotorPort);
        driveLThree = new WPI_VictorSPX(Constants.driveThreeLeftMotorPort);
        driveRTwo = new WPI_VictorSPX(Constants.driveTwoRightMotorPort);
        driveRThree = new WPI_VictorSPX(Constants.driveThreeRightMotorPort);
        leftMotor = new SpeedControllerGroup(driveLMaster, driveLTwo, driveLThree);
        rightMotor = new SpeedControllerGroup(driveRMaster, driveRTwo, driveRThree);
        differentialDrive = new DifferentialDrive(leftMotor, rightMotor);
    }
    
    /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    /**
     * Robot Drive Using Arcade Drive
     * @param speed
     * @param rotation_speed
     */
    public void robotDrive(double speed, double rotation_speed){
        robotDrive(speed, rotation_speed, 1);
    }

    /**
     * Robot Drive Using Arcade Drive
     * @param speed
     * @param rotation_speed
     * @param sensitivity
     */
    public void robotDrive(double speed, double rotation_speed, double sensivity){
        differentialDrive.arcadeDrive(speed, rotation_speed*sensivity);
    }

    public void curvatureDrive(double speed, double rotation, boolean isQuickTurn){
        differentialDrive.curvatureDrive(speed, rotation, isQuickTurn);
    }

    /**
    * Controls the left and right sides of the drive directly with voltages.
    *
    * @param leftVolts  the commanded left output
    * @param rightVolts the commanded right output
    */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotor.setVoltage(leftVolts);
        rightMotor.setVoltage(-rightVolts);
        differentialDrive.feed();
    }
    
    /**
     * Stop Driving Robot
     */
    public void stopDrive(){
        differentialDrive.tankDrive(0, 0);
    }
}