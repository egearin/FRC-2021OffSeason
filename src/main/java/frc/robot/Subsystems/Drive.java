// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;
import edu.wpi.first.wpilibj.DriverStation;
/*import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
//import edu.wpi.first.wpilibj.geometry.Pose2d;
//import edu.wpi.first.wpilibj.geometry.Rotation2d;
//import edu.wpi.first.wpilibj.geometry.Translation2d;*/
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    public static Drive getInstance() {
        return mInstance;
    }

    public PigeonIMU pigeon;
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
    public VictorSP testMotor;
    public PIDController pid;

    // Autonomous Variables
    public static Translation2d coordinatesToGoal; 
    public static Translation2d startingPos;
    public static double atanTurnAngle;
    public static double distanceToGoal;

    private Drive() {
        
        pigeon = new PigeonIMU(Constants.pigeonPort);
        driveLMaster = new WPI_VictorSPX(Constants.driveOneLeftMotorPort);
        driveRMaster = new WPI_VictorSPX(Constants.driveOneRightMotorPort);
        driveLTwo = new WPI_VictorSPX(Constants.driveTwoLeftMotorPort);
        driveLThree = new WPI_VictorSPX(Constants.driveThreeLeftMotorPort);
        driveRTwo = new WPI_VictorSPX(Constants.driveTwoRightMotorPort);
        driveRThree = new WPI_VictorSPX(Constants.driveThreeRightMotorPort);
        leftMotor = new SpeedControllerGroup(driveLMaster, driveLTwo, driveLThree);
        rightMotor = new SpeedControllerGroup(driveRMaster, driveRTwo, driveRThree);
        differentialDrive = new DifferentialDrive(leftMotor, rightMotor);
        pid = new PIDController(Constants.kDriveP, Constants.kDriveI, Constants.kDriveD);
    }

    /**
     * Robot Drive Using Arcade Drive
     * 
     * @param speed
     * @param rotation_speed
     */
    public void robotDrive(double speed, double rotation_speed) {
        robotDrive(speed, rotation_speed, 1);
    }

    /**
     * Reset Gyro Values
    */ 
    public void gyroReset() {
        pigeon.setYaw(0);
        pigeon.setAccumZAngle(0);
        pigeon.setFusedHeading(0);
        System.out.println("Resets gyro");
    }

    public void resetSensors() {
        gyroReset();
    }

    public boolean isPigeonReady() {
        PigeonState state = pigeon.getState();
        return state == PigeonState.Ready;
    }

    /**
     * Robot Drive Using Arcade Drive
     * 
     * @param speed
     * @param rotation_speed
     * @param sensitivity
     */
    public void robotDrive(double speed, double rotation_speed, double sensivity) {
        differentialDrive.arcadeDrive(speed, rotation_speed * sensivity);
    }

    public void AutoDrive(double speed, double rotation_speed, double sense){
        differentialDrive.arcadeDrive(speed, rotation_speed * sense);
    }
    public void curvatureDrive(double speed, double rotation, boolean isQuickTurn) {
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
     * Get Gyro Angle From Pigeon
     * 
     * @return gyro_angle
     */
    public double getGyroAngle() {
        return pigeon.getFusedHeading();
    }

    /**
     * Fully automated drive with PID
     * 
     * @param speed
     * @param rotation_angle
     */
    public void PIDDrive(double speed, double rotation_angle) {
        double drivePID = pid.calculate(getGyroAngle(), rotation_angle);
        drivePID = MathUtil.clamp(drivePID, -1, 1);
        differentialDrive.curvatureDrive(speed, drivePID, false);
    }

    public void resetPID() {
        pid.reset();
    }

    /**
     * Resets everything resettable
     */
    public void reset() {
        gyroReset();
        resetSensors();
    }

    public double simpleTurnPID(double desired_rotation) {
        double rotation = 0;
        double kP = SmartDashboard.getNumber("Turn PID", 0.01);
        double minMax = SmartDashboard.getNumber("Min PID", 0.3);
        if (desired_rotation > 1.0) {
            rotation = kP * desired_rotation + minMax;
        } 
        else if (desired_rotation < 1.0) {
            rotation = kP * desired_rotation - minMax;
        }
        return rotation;
    }

    public double turnPID(double desired_rotation) {
        double rotation = 0;
        double kP = SmartDashboard.getNumber("Turn PID", 0.1);
        double minMax = 1.9;
        if (desired_rotation > 1.0) { // to the right
            rotation = kP * desired_rotation + minMax;
        } 
        else if (desired_rotation < 1.0) { // to the left
            rotation = kP * desired_rotation - minMax;
        }
        tankDriveVolts(rotation, -rotation);
        return rotation;
    }

    /**
     * Stop Driving Robot
     */
    public void stopDrive() {
        differentialDrive.tankDrive(0, 0);
    }
}