// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.Constants;
import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.Vision;

/** Add your docs here. */
public class FindLocationAction implements Action {

    Vision mVision;
    Drive mDrive;

    public FindLocationAction(){
        mVision = Vision.getInstance();
        mDrive = Drive.getInstance();
    }

    @Override
    public void done() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void start() {
        double[] visionInfo = mVision.getInfo();
        Drive.startingPos = estimateStartingPosition(visionInfo[2]);
        Drive.coordinatesToGoal = convertToRobotRelativePos(Constants.ballPoint, Drive.startingPos);
        Drive.atanTurnAngle = Math.atan2(Drive.coordinatesToGoal.getY(), Drive.coordinatesToGoal.getX());
        System.out.println("Atan result is" + Math.toDegrees(Drive.atanTurnAngle));
        Drive.distanceToGoal = Drive.coordinatesToGoal.getDistance(Drive.startingPos);
    }

    @Override
    public void update() {
    }
    
    public Translation2d estimateStartingPosition(double ty){
        double distance = mVision.estimateDistanceFromAngle(ty);
        double gyroRadian = Math.toRadians(mDrive.getGyroAngle());
        double distance_y = distance * Math.sin(gyroRadian);
        double distance_x = distance * Math.cos(gyroRadian);
        System.out.println("Gyro " + gyroRadian + " X is " + distance_x + " Y is" + distance_y);
        Translation2d startingPos = new Translation2d(distance_x, distance_y);
        startingPos = startingPos.plus(Constants.kVisionTargetPos);
        return startingPos;
    }

    public Translation2d convertToRobotRelativePos(Translation2d fieldRelativePose, Translation2d robotFieldRelativePos){
        Translation2d pos = fieldRelativePose.minus(robotFieldRelativePos).unaryMinus(); // maybe unary minus
        return pos;
    }

}
