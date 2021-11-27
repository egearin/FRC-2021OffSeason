// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import frc.robot.Subsystems.Shooter;

/** Add your docs here. */
public class BlindSpeedUpAction implements Action {

    Shooter mShooter;
    double _shooterSpeed;
    double _accSpeed;
    static boolean isFinished;

    public BlindSpeedUpAction(double shooterSpeed, double accSpeed){
        mShooter = Shooter.getInstance();
        _shooterSpeed = shooterSpeed;
        _accSpeed = accSpeed;
    }

    @Override
    public void start() {
        isFinished = false;
    }

    @Override
    public void update() {
        mShooter.setShooterMotorSpeed(_shooterSpeed);
        mShooter.setAccMotorSpeed(_accSpeed);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void done() {
        mShooter.shooterStop();
    }

    public static void declareFinished(){
        isFinished = true;
    }

}
